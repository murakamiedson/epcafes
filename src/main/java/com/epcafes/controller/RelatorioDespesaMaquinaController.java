package com.epcafes.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String RelatorioDespesaMaquina(Model model, @RequestParam("ano") Optional<Integer> ano){
        DespesaTO total = new DespesaTO();
        mesAno = LocalDate.now();
        List<Integer> anos = this.despesaService.buscarAnos();
        model.addAttribute("anos", anos);

        int anoSelected = ano.orElse(mesAno.getYear());
        model.addAttribute("anoSelected", anoSelected);

        despesasTO = this.despesaService.buscarDespesasTO(anoSelected);
        
        for (DespesaTO despesaTO : despesasTO) {
            total.setValorTotalJan(total.getValorTotalJan().add(despesaTO.getValorTotalJan()));
            total.setValorTotalFev(total.getValorTotalFev().add(despesaTO.getValorTotalFev()));
            total.setValorTotalMar(total.getValorTotalMar().add(despesaTO.getValorTotalMar()));
            total.setValorTotalAbr(total.getValorTotalAbr().add(despesaTO.getValorTotalAbr()));
            total.setValorTotalMai(total.getValorTotalMai().add(despesaTO.getValorTotalMai()));
            total.setValorTotalJun(total.getValorTotalJun().add(despesaTO.getValorTotalJun()));
            total.setValorTotalJul(total.getValorTotalJul().add(despesaTO.getValorTotalJul()));
            total.setValorTotalAgo(total.getValorTotalAgo().add(despesaTO.getValorTotalAgo()));
            total.setValorTotalSet(total.getValorTotalSet().add(despesaTO.getValorTotalSet()));
            total.setValorTotalOut(total.getValorTotalOut().add(despesaTO.getValorTotalOut()));
            total.setValorTotalNov(total.getValorTotalNov().add(despesaTO.getValorTotalNov()));
            total.setValorTotalDez(total.getValorTotalDez().add(despesaTO.getValorTotalDez()));
            total.setValorTotalAnual(total.getValorTotalAnual().add(despesaTO.getValorTotalAnual()));
        }
        total.setMaquinaNome("Total");
        if(despesasTO.size() > 0){
            despesasTO.add(total);
        }
        model.addAttribute("despesasTO", despesasTO);
        log.info("teste controller:" + despesasTO.size());

        return "restricted/custo/RelatorioDespesaMaquina";
    }

}
