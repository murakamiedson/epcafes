package com.epcafes.controller;


import com.epcafes.message.ResponseFile;
import com.epcafes.message.ResponseMessage;
import com.epcafes.model.FileDB;
import com.epcafes.model.Funcionario;
import com.epcafes.repository.FileDBRepository;
import com.epcafes.service.FileStorageService;
import com.epcafes.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;


@Controller
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private FileStorageService storageService;

    @Autowired
    private FileDBRepository fileDBRepository;

    @GetMapping ("/funcionario")
    public String carregaFuncionario(Model model){
        model.addAttribute("lista", funcionarioService.acharTodos());

        return "restricted/cadastro/PesquisaFuncionarios";
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

    //controller arquivos

    @PostMapping("/files/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Long id) {
        String message = "";
        try {
            storageService.store(file, id);

            message = "Arquivo submetido com sucesso: " + file.getOriginalFilename();
            return "redirect:/funcionario";
        } catch (Exception e) {
            message = "Não foi possível submeter o arquivo: " + file.getOriginalFilename() + "!";
            return "erro";
        }
    }

//    @GetMapping("/files")
//    public ResponseEntity<List<ResponseFile>> getListFiles() {
//
//
//        return ResponseEntity.status(HttpStatus.OK).body(files);
//    }

//    @GetMapping("/files/{id}")
//    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
//        FileDB fileDB = storageService.getFile(id);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
//                .body(fileDB.getData());
//    }

//    @GetMapping("/funcionario/certificados")
//    public String carregaCertificados(@PathVariable(name = "id") Long id, Model model){
//        List<ResponseFile> fileInfos = storageService.getAllFiles().map(dbFile -> {
//            String fileDownloadUri = ServletUriComponentsBuilder
//                    .fromCurrentContextPath()
//                    .path("/files/")
//                    .path(dbFile.getId())
//                    .toUriString();
//
//            return new ResponseFile(
//                    dbFile.getName(),
//                    fileDownloadUri,
//                    dbFile.getType(),
//                    dbFile.getData().length);
//        }).collect(Collectors.toList());
//
//        model.addAttribute("files", fileInfos);
//
//        return "restricted/cadastro/listaCertificados";
    }

