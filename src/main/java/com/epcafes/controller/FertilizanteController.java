package com.epcafes.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import com.epcafes.enums.EnumUtil;
import com.epcafes.enums.TipoAuxiliarInsumos;
import com.epcafes.exception.BusinessExeption;
import com.epcafes.model.Fertilizante;
import com.epcafes.service.FertilizanteService;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;

@Getter
@Setter
@Controller
public class FertilizanteController {
    @Autowired
    private FertilizanteService fertilizanteService;

    @GetMapping({ "restricted/cadastro/cadastroFertilizantes", "restricted/cadastro/editarFertilizante/{id}" })
    public String cadastroInsumos(Model model, Fertilizante fertilizanteFind,
            @PathVariable(name = "id") Optional<Long> id) {

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

        model.addAttribute("fertilizantes", fertilizanteService.buscarFertilizantes());

        if (id.isPresent()) {
            fertilizanteFind = fertilizanteService.buscarPeloCodigo(id.get());
            model.addAttribute("fertilizante", fertilizanteFind);

        }

        return "restricted/cadastro/TesteFertilizante";

    }

    @PostMapping({ "/restricted/cadastro/cadastroFertilizantes", "/restricted/cadastro/editarFertilizante/{id}" })
    public String create(Fertilizante fertilizante, @PathVariable(name = "id") Optional<Long> id) {

        if (id.isPresent()) {
            fertilizanteService.atualizar(fertilizante, id.get());

        }
        fertilizanteService.salvar(fertilizante);

        return "redirect:/restricted/cadastro/cadastroFertilizantes";

    }

    @GetMapping("restricted/cadastro/fertilizante/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id) throws BusinessExeption {
        Fertilizante fertilizante = fertilizanteService.buscarPeloCodigo(id);

        fertilizanteService.excluir(fertilizante);
        return "redirect:/restricted/cadastro/cadastroFertilizantes";

    }

}
