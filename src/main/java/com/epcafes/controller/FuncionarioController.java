package com.epcafes.controller;


import com.epcafes.model.FileInfo;
import com.epcafes.model.Funcionario;
import com.epcafes.service.FilesStorageService;
import com.epcafes.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;
    @Autowired
    FilesStorageService storageService;
    @GetMapping ("/funcionario")
    public String carregaFuncionario(Model model){
        model.addAttribute("lista", funcionarioService.acharTodos());

        return "restricted/cadastro/PesquisaFuncionarios";
    }
    @GetMapping("/funcionario/certificados")
    public String carregaCertificados(Long id, Model model){
        List<FileInfo> fileInfos = storageService.loadCertificados("funcionario" + id+ "_").map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FuncionarioController.class, "getFile", path.getFileName().toString()).build().toString();

            if(id < 10){
                return new FileInfo(filename.substring(13), url);
            } else
            return new FileInfo(filename.substring(14), url);
        }).collect(Collectors.toList());

        model.addAttribute("files", fileInfos);

        return "restricted/cadastro/listaCertificados";
    }
    @GetMapping("/funcionario/upload")
    public String carregaUpload(Long id, Model model){
        var funcionario =funcionarioService.acharPorID(id);

        model.addAttribute(funcionario);

        return "restricted/cadastro/CadastroCertificados";
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
        Funcionario funcionario = new Funcionario(dados.getNome(), dados.getSalario(), dados.getNascimento(), 1L);
        funcionarioService.salvar(funcionario);
        return "redirect:/funcionario";
    }


    @PutMapping("/funcionario")
    @Transactional
    public String alteraDados(Funcionario dados){
        Funcionario funcionario = funcionarioService.acharPorID(dados.getId());
        funcionario.alteraDados(dados);
        return "redirect:/funcionario";
    }
    @GetMapping("/funcionario/delete/{id}")
    public String deletaFuncionario(@PathVariable(name = "id") Long id){
        funcionarioService.deletarPorId(id);
        return "redirect:/funcionario";
    }

    @PostMapping("/files/upload")
    public String uploadFile(Model model, @RequestParam("file") MultipartFile file, Long id) {
        String message = "";
        System.out.println(id);
        try {
            storageService.saveCertificados(file, id);


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
