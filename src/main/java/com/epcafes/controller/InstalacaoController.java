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
import com.epcafes.model.Instalacao;
import com.epcafes.model.Usuario;
import com.epcafes.service.InstalacaoService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class InstalacaoController {
	@Autowired
	private InstalacaoService instalacaoService;
	
	@GetMapping({ "/CadastroInstalacao", "/CadastroInstalacao/edit/{id}" })
    public String listarInstalacao(Instalacao instalacao, Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("qtdPorPagina") Optional<Integer> qtdPorPagina,
            @PathVariable(name = "id") Optional<Long> id) {
        int currPage = page.orElse(1);
        int currSize = size.orElse(5);
        int pageSize = size.orElse(5);
        int qtdPorPaginaInt = qtdPorPagina.orElse(5);

        List<Instalacao> instalacoes = instalacaoService.findPaginated(currPage, pageSize);
        int qtdPaginas = (int) Math.ceil(instalacaoService.findAll().size() / (double) pageSize);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, qtdPaginas).boxed().collect(Collectors.toList());
        List<Integer> qtdPorPaginaList = List.of(1, 2, 5, 10, 15, 20, 25);

        model.addAttribute("instalacoes", instalacoes);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("qtdPaginas", qtdPaginas);
        model.addAttribute("currPage", currPage);
        model.addAttribute("qtdPorPagina", qtdPorPaginaInt);
        model.addAttribute("qtdPorPaginaList", qtdPorPaginaList);
        model.addAttribute("size", currSize);

        if (id.isPresent()) {
            instalacao = instalacaoService.findById(id.get());
            model.addAttribute("instalacao", instalacao);
        }

        return "restricted/cadastro/CadastroInstalacao";
    }

    @PostMapping({ "/CadastroInstalacao", "/CadastroInstalacao/edit/CadastroInstalacao" })
    public String novo(Instalacao instalacao, BindingResult result) throws BusinessException {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
        
        if (result.hasErrors()) {
            log.info("Erro: " + result.toString());
            return "restricted/cadastro/CadastroInstalacao";
        }

        if (instalacao.getId() != null) {
        	Instalacao existingInstalacao = instalacaoService.findById(instalacao.getId());
            log.info("Atualizando Instalacao existente: " + existingInstalacao.toString());

            existingInstalacao.setNome(instalacao.getNome());
            existingInstalacao.setValor(instalacao.getValor());
            existingInstalacao.setVidaUtilAnos(instalacao.getVidaUtilAnos());
            existingInstalacao.setValorResidual(instalacao.getValorResidual());
            instalacaoService.save(existingInstalacao);
        } else {
            log.info("Salvando Novo Instalacao: " + instalacao.toString());
            instalacao.setTenant_id(user.getTenant().getId());
            instalacao.setPropriedade(user.getPropriedade());
            instalacaoService.save(instalacao);
        }
        
        return "redirect:/CadastroInstalacao";
    }

    @GetMapping("/CadastroInstalacao/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id) throws BusinessException{

        Instalacao instalacao = instalacaoService.findById(id);
        instalacaoService.delete(instalacao);

        return "redirect:/CadastroInstalacao";
    }

    /* Modal de Cadastro de Instalacao */
    @GetMapping("/CadastroInstalacao/modal")
    public String modalInstalacao(Model model, Optional<Long> id) {
      
        Instalacao instalacao;
        if(id.isPresent()){
            instalacao = instalacaoService.findById(id.get());
        }else{
            instalacao = new Instalacao();
        }
        model.addAttribute("instalacao", instalacao);

        return "restricted/cadastro/ModalInstalacao";
    }
}
