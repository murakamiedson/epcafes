package com.epcafes.controller;


import com.epcafes.model.funcionario.DadosAlteraFuncionario;
import com.epcafes.model.funcionario.DadosCadastroFuncionario;
import com.epcafes.DAO.Funcionario;
import com.epcafes.DAO.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/funcionario")
public class FuncionarioController {
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @GetMapping
    public String carregaFuncionario(Model model){
        model.addAttribute("lista", funcionarioRepository.findAll());
        return "funcionario/funcionario";
    }
    @GetMapping("/inserir")
    public String carregaInserir(Long id, Model model){
        if (id != null){
            var funcionario = funcionarioRepository.getReferenceById(id);
            model.addAttribute(funcionario);
        }
        return "funcionario/inserir";
    }

    @PostMapping
    @Transactional //inicia uma transação com o banco de dados com o Spring
    public String cadastraFuncionario(DadosCadastroFuncionario dados){
        Funcionario funcionario = new Funcionario(dados, 1L);
        System.out.println(funcionario);
        funcionarioRepository.save(funcionario);
        return "redirect:/funcionario";
    }

    @PutMapping
    @Transactional
    public String alteraDados(DadosAlteraFuncionario dados){
        Funcionario funcionario = funcionarioRepository.getReferenceById(dados.id());
        funcionario.alteraDados(dados);
        return "redirect:/funcionario";
    }
    @DeleteMapping
    @Transactional
    public String deletaFuncionario(Long id){
        funcionarioRepository.deleteById(id);
        return "redirect:/funcionario";
    }
}
