package com.epcafes.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import com.epcafes.enums.EnumUtil;
import com.epcafes.enums.TipoAuxiliarInsumos;
import com.epcafes.exception.InsumoException;
import com.epcafes.model.Fertilizante;
import com.epcafes.model.Usuario;
import com.epcafes.service.FertilizanteService;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
@Controller
public class FertilizanteController {
    @Autowired
    private FertilizanteService fertilizanteService;

    @GetMapping({ "restricted/cadastro/cadastroFertilizantes", "restricted/cadastro/editarFertilizante/{id}" })
    public String cadastroInsumos(Model model, Fertilizante fertilizanteFind,
            @PathVariable(name = "id") Optional<Long> id,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("qtdPorPagina") Optional<Integer> qtdPorPagina) throws InsumoException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();

        int currPage = page.orElse(1);
        int currSize = size.orElse(5);
        int pageSize = size.orElse(5);
        int qtdPorPaginaInt = qtdPorPagina.orElse(5);
        List<Fertilizante> fertilizantesPaginated = fertilizanteService.findPaginated(currPage,
                pageSize, user.getTenant().getId());
        int qtdPaginas = (int) Math.ceil(fertilizanteService.buscarFertilizantes().size() / (double) pageSize);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, qtdPaginas).boxed().collect(Collectors.toList());
        List<Integer> qtdPorPaginaList = List.of(1, 2, 5, 10, 15, 20, 25);

        List<TipoAuxiliarInsumos> opcoesInsumos = EnumUtil.getTiposInsumos();
        List<TipoAuxiliarInsumos> opcoesFertilizantes = EnumUtil.getTiposFertilizantes();
        List<TipoAuxiliarInsumos> opcoesFungicidas = EnumUtil.getTiposFungicidas();
        List<TipoAuxiliarInsumos> opcoesHerbicidas = EnumUtil.getTiposHerbicidas();
        List<TipoAuxiliarInsumos> opcoesInseticidas = EnumUtil.getTiposInseticidas();
        List<TipoAuxiliarInsumos> opcoesAdjuvantes = EnumUtil.getTiposAdjuvantes();
        List<TipoAuxiliarInsumos> opcoesMedidas = EnumUtil.getTiposMedidas();

        model.addAttribute("opcoesInsumos", opcoesInsumos);
        model.addAttribute("opcoesFertilizantes", opcoesFertilizantes);
        model.addAttribute("opcoesFungicidas", opcoesFungicidas);
        model.addAttribute("opcoesHerbicidas", opcoesHerbicidas);
        model.addAttribute("opcoesInseticidas", opcoesInseticidas);
        model.addAttribute("opcoesAdjuvantes", opcoesAdjuvantes);
        model.addAttribute("opcoesMedidas", opcoesMedidas);

        model.addAttribute("fertilizantes", fertilizantesPaginated);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("qtdPaginas", qtdPaginas);
        model.addAttribute("currPage", currPage);
        model.addAttribute("qtdPorPagina", qtdPorPaginaInt);
        model.addAttribute("qtdPorPaginaList", qtdPorPaginaList);
        model.addAttribute("size", currSize);

        // model.addAttribute("fertilizantes",
        // fertilizanteService.buscarPorTenant(user.getTenant().getId()));

        if (id.isPresent()) {
            fertilizanteFind = fertilizanteService.buscarPeloCodigo(id.get());
            model.addAttribute("fertilizante", fertilizanteFind);

        }

        return "restricted/cadastro/CadastroFertilizante";

    }

    @PostMapping({ "/restricted/cadastro/cadastroFertilizantes", "/restricted/cadastro/editarFertilizante/{id}" })
    public String create(Fertilizante fertilizante, @PathVariable(name = "id") Optional<Long> id)
            throws InsumoException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
        fertilizante.setTenantId(user.getTenant().getId());

        if (id.isPresent()) {
            fertilizanteService.atualizar(fertilizante, id.get());

        }
        fertilizanteService.salvar(fertilizante);

        return "redirect:/restricted/cadastro/cadastroFertilizantes";

    }

    @GetMapping("restricted/cadastro/fertilizante/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id) throws InsumoException {
        Fertilizante fertilizante = fertilizanteService.buscarPeloCodigo(id);

        fertilizanteService.excluir(fertilizante);
        return "redirect:/restricted/cadastro/cadastroFertilizantes";

    }

}
