package com.epcafes.controller;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.epcafes.enums.TipoCertificado;
import com.epcafes.model.FileDB;
import com.epcafes.model.Funcionario;
import com.epcafes.repository.FileDBRepository;
import com.epcafes.service.FileStorageService;
import com.epcafes.service.FuncionarioService;


@Controller
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private FileStorageService storageService;

    @Autowired
    private FileDBRepository fileDBRepository;

    @GetMapping ("/funcionario")
    public String carregaFuncionario(Model model, @RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size,
                                     @RequestParam("qtdPorPagina") Optional<Integer> qtdPorPagina,
                                     @PathVariable(name = "id") Optional<Long> id) {
        int currPage = page.orElse(1);
        int currSize = size.orElse(5);
        int pageSize = size.orElse(5);
        int qtdPorPaginaInt = qtdPorPagina.orElse(5);

        List<Funcionario> funcionarios = funcionarioService.findPaginated(currPage, pageSize);
        int qtdPaginas = (int) Math.ceil(funcionarioService.acharTodos().size() / (double) pageSize);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, qtdPaginas).boxed().collect(Collectors.toList());
        List<Integer> qtdPorPaginaList = List.of(1, 2, 5, 10, 15, 20, 25);

        model.addAttribute("lista", funcionarios);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("qtdPaginas", qtdPaginas);
        model.addAttribute("currPage", currPage);
        model.addAttribute("qtdPorPagina", qtdPorPaginaInt);
        model.addAttribute("qtdPorPaginaList", qtdPorPaginaList);
        model.addAttribute("size", currSize);

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

        List<FileDB> certificados = fileDBRepository.findByIdFuncionario(id);

        for (FileDB file:certificados) {
            storageService.delete(file.getUuidRegistrado());
        }


        return "redirect:/funcionario";
    }

    //controller arquivos

    @PostMapping("/files/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Long id, TipoCertificado tipoCertificado) {
        String message = "";

        try {
            storageService.store(file, id, tipoCertificado);

            message = "Arquivo submetido com sucesso: " + file.getOriginalFilename();
            return "redirect:/funcionario";
        } catch (Exception e) {
            message = "Não foi possível submeter o arquivo: " + file.getOriginalFilename() + "!";
            return "erro";
        }
    }

    @GetMapping("/funcionario/certificados")
    public String carregaCertificados(Long id, Model model){
        List<FileDB> fileInfos = fileDBRepository.findByIdFuncionario(id);


        model.addAttribute("files", fileInfos);


        return "restricted/cadastro/listaCertificados";
    }

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/files/delete/{filename:.+}")
    public String deleteFile(@PathVariable String filename) {
            boolean existed = storageService.delete(filename);
            return "redirect:/funcionario";
    }
}



