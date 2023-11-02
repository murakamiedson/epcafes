package com.epcafes.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.epcafes.model.FileInfo;
import com.epcafes.model.Funcionario;
import com.epcafes.model.Propriedade;
import com.epcafes.service.FilesStorageService;
import com.epcafes.service.FuncionarioService;

@Controller
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;
    @Autowired
    FilesStorageService storageService;
    
    @GetMapping ("/funcionario")
    public String carregaFuncionario(Model model){
        model.addAttribute("lista", funcionarioService.acharTodos());
		/*
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FuncionarioController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        model.addAttribute("files", fileInfos);
        */
        return "restricted/cadastro/PesquisarFuncionarios";
    }
    @GetMapping("/funcionario/certificados")
    public String carregaCertificados(Long id, Model model){
        List<FileInfo> fileInfos = storageService.loadCertificados("funcionario" + id).map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FuncionarioController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        model.addAttribute("files", fileInfos);

        return "restricted/cadastro/listaCertificados";
    }
    @GetMapping("/funcionario/inserir")
    public String carregaInserir(Long id, Model model){
        if (id != null){
            var funcionario =funcionarioService.acharPorID(id);

            model.addAttribute(funcionario);
        }
        return "restricted/cadastro/cadastroFuncionarios";
    }

    @PostMapping("/funcionario")
    public String cadastraFuncionario(Funcionario dados){
       
    	Propriedade propriedade = new Propriedade();
    	propriedade.setId(1L);
    	dados.setTenant_id(1L);
    	dados.setPropriedade(propriedade);
        funcionarioService.salvar(dados);
        return "redirect:/funcionario";
    }


    @PutMapping("/funcionario")
    @Transactional
    public String alteraDados(Funcionario dados){
        Funcionario funcionario = funcionarioService.acharPorID(dados.getId());
        funcionario.alteraDados(dados);
        return "redirect:/funcionario";
    }
    @DeleteMapping("/funcionario")
    public String deletaFuncionario(Long id){
        funcionarioService.deletarPorId(id);
        return "redirect:/funcionario";
    }

    @PostMapping("/files/upload")
    public String uploadFile(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            storageService.save(file);

            message = "Upload de arquivo bem sucedido: " + file.getOriginalFilename();
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Não foi possível fazer o upload do arquivo: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            model.addAttribute("message", message);
        }

        return "redirect:/funcionario";
    }

    @GetMapping("/funcionario/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


}
