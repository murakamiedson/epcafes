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
import com.epcafes.model.Usuario;
import com.epcafes.service.UsuarioService;

import lombok.Data;

@Data
@Controller
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping({ "/CadastroUsuario", "/CadastroUsuario/edit/{id}" })
    public String listarUsuario(Usuario usuario, Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("qtdPorPagina") Optional<Integer> qtdPorPagina,
            @PathVariable(name = "id") Optional<Long> id) {
        int currPage = page.orElse(1);
        int currSize = size.orElse(5);
        int pageSize = size.orElse(5);
        int qtdPorPaginaInt = qtdPorPagina.orElse(5);

        List<Usuario> usuarios = usuarioService.findPaginated(currPage, pageSize);
        int qtdPaginas = (int) Math.ceil(usuarioService.findAll().size() / (double) pageSize);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, qtdPaginas).boxed().collect(Collectors.toList());
        List<Integer> qtdPorPaginaList = List.of(1, 2, 5, 10, 15, 20, 25);

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("qtdPaginas", qtdPaginas);
        model.addAttribute("currPage", currPage);
        model.addAttribute("qtdPorPagina", qtdPorPaginaInt);
        model.addAttribute("qtdPorPaginaList", qtdPorPaginaList);
        model.addAttribute("size", currSize);

        if (id.isPresent()) {
            usuario = usuarioService.getById(id.get());
            model.addAttribute("usuario", usuario);
        }

        return "restricted/cadastro/CadastroUsuario";
    }

    @PostMapping({ "/CadastroUsuario", "/CadastroUsuario/edit/CadastroUsuario" })
    public String novo(Usuario usuario, BindingResult result) throws BusinessException {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
        
        if (result.hasErrors()) {
            //log.info("Erro: " + result.toString());
            return "restricted/cadastro/CadastroUsuario";
        }

        if (usuario.getId() != null) {
        	Usuario existingUsuario = usuarioService.getById(usuario.getId());
        	
        	existingUsuario.setNome(usuario.getNome());
        	existingUsuario.setLogin(usuario.getLogin());
        	existingUsuario.setRegistroProfissional(usuario.getRegistroProfissional());
        	existingUsuario.setStatus(usuario.getStatus());
        	existingUsuario.setRole(usuario.getRole());
        	
            usuarioService.salvar(existingUsuario);
        } else {
            //log.info("Salvando Novo Usuario: " + usuario.toString());
            
            usuario.setPropriedade(user.getPropriedade());
            usuario.setTenant(user.getTenant());
            usuarioService.salvar(usuario);
        }
        
        return "redirect:/CadastroUsuario";
    }

    @GetMapping("/CadastroUsuario/excluir/{id}")
    public String excluir(@PathVariable(name = "id") Long id) throws BusinessException{

        Usuario usuario = usuarioService.getById(id);
        usuarioService.excluir(usuario);

        return "redirect:/CadastroUsuario";
    }

    /* Modal de Cadastro de Usuario */
    @GetMapping("/CadastroUsuario/modal")
    public String modalUsuario(Model model, Optional<Long> id) {
      
        Usuario usuario;
        if(id.isPresent()){
            usuario = usuarioService.getById(id.get());
        }else{
            usuario = new Usuario();
        }
        model.addAttribute("usuario", usuario);

        return "restricted/cadastro/ModalUsuario";
    }
}
