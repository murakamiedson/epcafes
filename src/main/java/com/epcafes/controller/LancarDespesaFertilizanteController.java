package com.epcafes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.epcafes.model.DespesaFertilizante;
import com.epcafes.model.Fertilizante;
import com.epcafes.service.DespesaFertilizanteService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class LancarDespesaFertilizanteController {
    
    @Autowired
    private DespesaFertilizanteService despesaFertilizanteService;

    private long tenantId = 1;

    @GetMapping("/restricted/custo/LancarDespesaFertilizante")
    public String lancarDespesaFertilizante(DespesaFertilizante despesaFertilizante, Model model) {

        List<Fertilizante> fertilizantes = despesaFertilizanteService.findAllFertilizantes(tenantId);
        model.addAttribute("fertilizantes", fertilizantes);

        return "restricted/custo/LancarDespesaFertilizante";
    }

    @PostMapping("/restricted/custo/LancarDespesaFertilizante")
    public String novo(@Valid DespesaFertilizante despesaFertilizante, BindingResult result) {

        if (result.hasErrors()) {
            log.info("Erro: " + result.toString());
            return "restricted/custo/LancarDespesaFertilizante";
        }

        log.info("Salvando DespesaFertilizante: " + despesaFertilizante.toString());
        despesaFertilizanteService.save(despesaFertilizante);

        
        return "redirect:/restricted/custo/LancarDespesaFertilizante";
    }
}
