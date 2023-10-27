package com.epcafes.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.epcafes.dto.DespesaTO;
import com.epcafes.model.DespesaMaquina;
import com.epcafes.service.DespesaMaquinaService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Getter
@Setter
@Log4j2
@Controller
public class RelatorioDespesaMaquinaController{
    
    private LocalDate mesAno;
    private DespesaMaquina despesaMaquina;
    private List<DespesaTO> despesasTO = new ArrayList<>();

    @Autowired
    private DespesaMaquinaService despesaService;

    @GetMapping("/restricted/custo/RelatorioDespesaMaquina")
    public String RelatorioDespesaMaquina(Model model){

        mesAno = LocalDate.now();
        despesasTO = this.despesaService.buscarDespesasTO();
        model.addAttribute("despesasTO", despesasTO);
        log.info("teste controller:" + despesasTO.size());

        return "restricted/custo/RelatorioDespesaMaquina";
    }

}
