package com.epcafes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import com.epcafes.enums.EnumUtil;
import com.epcafes.enums.TipoAuxiliarInsumos;
import com.epcafes.service.FertilizanteService;
import com.epcafes.util.NegocioExeption;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;

@Getter
@Setter
@Controller
public class FertilizanteController {
    @Autowired
    private FertilizanteService fertilizanteService;

    @GetMapping("restricted/cadastro/cadastroFertilizantes")
    public String cadastroInsumos(Model model) {

        List<TipoAuxiliarInsumos> opcoesInsumos = EnumUtil.getTiposInsumos();
        List<TipoAuxiliarInsumos> opcoesFertilizantes = EnumUtil.getTiposFertilizantes();
        List<TipoAuxiliarInsumos> opcoesFungicidas = EnumUtil.getTiposFungicidas();
        List<TipoAuxiliarInsumos> opcoesHerbicidas = EnumUtil.getTiposHerbicidas();
        List<TipoAuxiliarInsumos> opcoesInseticidas = EnumUtil.getTiposInseticidas();
        List<TipoAuxiliarInsumos> opcoesAdjuvantes = EnumUtil.getTiposAdjuvantes();

        model.addAttribute("opcoesInsumos", opcoesInsumos);
        model.addAttribute("opcoesFertilizantes", opcoesFertilizantes);
        model.addAttribute("opcoesFungicidas", opcoesFungicidas);
        model.addAttribute("opcoesHerbicidas", opcoesHerbicidas);
        model.addAttribute("opcoesInseticidas", opcoesInseticidas);
        model.addAttribute("opcoesAdjuvantes", opcoesAdjuvantes);

        return "restricted/cadastro/cadastroFertilizantes";
    }

}
