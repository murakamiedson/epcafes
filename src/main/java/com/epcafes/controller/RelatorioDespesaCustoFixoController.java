package com.epcafes.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epcafes.dto.DespesaCustoFixoTO;
import com.epcafes.model.DespesaCustoFixo;
import com.epcafes.model.Talhao;
import com.epcafes.model.Usuario;
import com.epcafes.service.DespesaCustoFixoService;
import com.epcafes.service.TalhaoService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Controller
@RequestMapping("/custoFixo/despesa/relatorio")
public class RelatorioDespesaCustoFixoController {
	
	private LocalDate mesAno;
    private DespesaCustoFixo despesaCustoFixo;
    private List<DespesaCustoFixoTO> despesasCustoFixoTO = new ArrayList<>();
    
    @Autowired
    private DespesaCustoFixoService despesaCustoFixoService;
    
    @Autowired
    private TalhaoService talhaoService;
    
    @GetMapping()
    public String RelatorioDespesaCustoFixo(Model model, @RequestParam("ano") Optional<Integer> ano){
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
    	
    	DespesaCustoFixoTO total = new DespesaCustoFixoTO();
        mesAno = LocalDate.now();
        
        List<Talhao> talhoes = this.talhaoService.findAllByPropriedade(user.getPropriedade());
        model.addAttribute("listaTalhoes", talhoes);
        
        List<Integer> anos = this.despesaCustoFixoService.buscarAnos();
        model.addAttribute("anos", anos);

        int anoSelected = ano.orElse(mesAno.getYear());
        model.addAttribute("anoSelected", anoSelected);

        despesasCustoFixoTO = this.despesaCustoFixoService.buscarDespesasTO(anoSelected, user.getPropriedade());
        
        for(int i = 0; i < despesasCustoFixoTO.size(); i++) {
        	
        	DespesaCustoFixoTO despesaTO = despesasCustoFixoTO.get(i);
        	
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
            
            total.setValorTotalLavoura(total.getValorTotalLavoura().add(despesasCustoFixoTO.get(i).getValorTotalLavoura()));
            
            for(int j = 0; j < despesaTO.getValoresPorTalhao().size(); j++) {
            	
            	BigDecimal valor = despesaTO.getValoresPorTalhao().get(j);
            	BigDecimal soma = new BigDecimal(0);
            	
            	if(i == 0)
            		total.getValoresPorTalhao().add(valor);
            	else {
            		
            		soma = total.getValoresPorTalhao().get(j).add(valor);
            		total.getValoresPorTalhao().set(j, soma);
            	}
            		
            }
            
        }
        
        total.setPorcentagemUtilizacao(null);
        total.setNomeCustoFixo("Total");
        
        if(despesasCustoFixoTO.size() > 0){
        	despesasCustoFixoTO.add(total);
        }
        
        model.addAttribute("despesasCustoFixoTO", despesasCustoFixoTO);

        return "restricted/custo/RelatorioDespesaCustoFixo";
    }
}
