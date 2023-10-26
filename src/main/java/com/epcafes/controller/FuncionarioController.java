package com.epcafes.controller;


import com.epcafes.model.Funcionario;
import com.epcafes.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/funcionario")
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;
    @GetMapping
    public String carregaFuncionario(Model model){
        model.addAttribute("lista", funcionarioService.acharTodos());
        return "restricted/cadastro/listarFuncionarios";
    }
    @GetMapping("/inserir")
    public String carregaInserir(Long id, Model model){
        if (id != null){
            var funcionario =funcionarioService.acharPorID(id);

            model.addAttribute(funcionario);
        }
        return "restricted/cadastro/cadastroFuncionarios";
    }

    @PostMapping
    public String cadastraFuncionario(Funcionario dados){
        Funcionario funcionario = new Funcionario(dados.getNome(), dados.getSalario(), dados.getNascimento(), 1L);
        funcionarioService.salvar(funcionario);
        return "redirect:/funcionario";
    }

    @PutMapping
    @Transactional
    public String alteraDados(Funcionario dados){
        Funcionario funcionario = funcionarioService.acharPorID(dados.getId());
        funcionario.alteraDados(dados);
        return "redirect:/funcionario";
    }
    @DeleteMapping
    public String deletaFuncionario(Long id){
        funcionarioService.deletarPorId(id);
        return "redirect:/funcionario";
    }
}
