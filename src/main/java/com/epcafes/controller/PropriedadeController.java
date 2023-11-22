package com.epcafes.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epcafes.exception.BusinessException;
import com.epcafes.model.Propriedade;
import com.epcafes.model.Usuario;
import com.epcafes.service.PropriedadeService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class PropriedadeController {
	@Autowired
	private PropriedadeService propriedadeService;
	
	@GetMapping({ "/CadastroPropriedade", "/CadastroPropriedade/edit/{id}" })
    public String listarPropriedade(Propriedade propriedade, Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("qtdPorPagina") Optional<Integer> qtdPorPagina,
            @PathVariable(name = "id") Optional<Long> id) {
        int currPage = page.orElse(1);
        int currSize = size.orElse(5);
        int pageSize = size.orElse(5);
        int qtdPorPaginaInt = qtdPorPagina.orElse(5);

        List<Propriedade> propriedades = propriedadeService.findPaginated(currPage, pageSize);
        int qtdPaginas = (int) Math.ceil(propriedadeService.findAll().size() / (double) pageSize);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, qtdPaginas).boxed().collect(Collectors.toList());
        List<Integer> qtdPorPaginaList = List.of(1, 2, 5, 10, 15, 20, 25);

        model.addAttribute("propriedades", propriedades);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("qtdPaginas", qtdPaginas);
        model.addAttribute("currPage", currPage);
        model.addAttribute("qtdPorPagina", qtdPorPaginaInt);
        model.addAttribute("qtdPorPaginaList", qtdPorPaginaList);
        model.addAttribute("size", currSize);

        if (id.isPresent()) {
            propriedade = propriedadeService.getById(id.get());
            model.addAttribute("propriedade", propriedade);
        }

        return "restricted/cadastro/CadastroPropriedade";
    }

    @PostMapping({ "/CadastroPropriedade", "/CadastroPropriedade/edit/CadastroPropriedade" })
    public String novo(Propriedade propriedade, BindingResult result) throws BusinessException {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
        
        if (result.hasErrors()) {
            log.info("Erro: " + result.toString());
            return "restricted/cadastro/CadastroPropriedade";
        }

        if (propriedade.getId() != null) {
        	Propriedade existingPropriedade = propriedadeService.getById(propriedade.getId());
            log.info("Atualizando Propriedade existente: " + existingPropriedade.toString());
            existingPropriedade.setNome(propriedade.getNome());
            existingPropriedade.setEndereco(propriedade.getEndereco());
            propriedade.setTenantId(user.getTenant().getId());
            existingPropriedade.setTipo(propriedade.getTipo());
            
            propriedadeService.salvar(existingPropriedade);
        } else {
            log.info("Salvando Novo Propriedade: " + propriedade.toString());
            propriedade.setTenantId(user.getTenant().getId());
            propriedade.setContato(user.getTenant().getNome());
            propriedadeService.salvar(propriedade);
        }
        
        return "redirect:/CadastroPropriedade";
    }

    @GetMapping("/CadastroPropriedade/excluir/{id}")
    public String excluir(@PathVariable(name = "id") Long id) throws BusinessException{

        Propriedade propriedade = propriedadeService.getById(id);
        propriedadeService.excluir(propriedade);

        return "redirect:/CadastroPropriedade";
    }

    /* Modal de Cadastro de Propriedade */
    @GetMapping("/CadastroPropriedade/modal")
    public String modalPropriedade(Model model, Optional<Long> id) {
      
        Propriedade propriedade;
        if(id.isPresent()){
            propriedade = propriedadeService.getById(id.get());
        }else{
            propriedade = new Propriedade();
        }
        model.addAttribute("propriedade", propriedade);

        return "restricted/cadastro/modalPropriedade";
    }
}
