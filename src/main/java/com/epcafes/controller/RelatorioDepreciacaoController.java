package com.epcafes.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epcafes.dto.DepreciacaoInstalacaoTO;
import com.epcafes.dto.DepreciacaoLavouraCafeTO;
import com.epcafes.dto.DepreciacaoMaquinaTO;
import com.epcafes.dto.DepreciacaoTotalTO;
import com.epcafes.model.DepreciacaoMaquina;
import com.epcafes.model.Talhao;
import com.epcafes.model.Usuario;
import com.epcafes.service.DepreciacaoInstalacaoService;
import com.epcafes.service.DepreciacaoLavouraCafeService;
import com.epcafes.service.DepreciacaoMaquinaService;
import com.epcafes.service.TalhaoService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Controller
@RequestMapping("/depreciacao/relatorio")
public class RelatorioDepreciacaoController {
	
	private DepreciacaoMaquina depreciacaoMaquina;
	private List<DepreciacaoMaquinaTO> depreciacoesMaquinaTO = new ArrayList<>();
	private List<DepreciacaoInstalacaoTO> depreciacoesInstalacaoTO = new ArrayList<>();
	private List<DepreciacaoLavouraCafeTO> depreciacoesLavouraCafeTO = new ArrayList<>();
	
	@Autowired
	private DepreciacaoMaquinaService depreciacaoMaquinaService;
	
	@Autowired
	private DepreciacaoInstalacaoService depreciacaoInstalacaoService;
	
	@Autowired
	private DepreciacaoLavouraCafeService depreciacaoLavouraCafeService;
	
	@Autowired
    private TalhaoService talhaoService;
	
	@GetMapping()
    public String RelatorioDepreciacao(Model model){
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
    	
    	DepreciacaoMaquinaTO totalMaquina = new DepreciacaoMaquinaTO();
    	DepreciacaoInstalacaoTO totalInstalacao = new DepreciacaoInstalacaoTO();
    	DepreciacaoLavouraCafeTO totalLavouraCafe = new DepreciacaoLavouraCafeTO();
    	DepreciacaoTotalTO total = new DepreciacaoTotalTO();
        
        List<Talhao> talhoes = this.talhaoService.findAllByPropriedade(user.getPropriedade());
        model.addAttribute("listaTalhoes", talhoes);
        
        // --------- Depreciacao de Maquina ---------

        depreciacoesMaquinaTO = this.depreciacaoMaquinaService.buscarDepreciacoesTO(user.getPropriedade());
        
        for(int i = 0; i < depreciacoesMaquinaTO.size(); i++) {
        	
        	DepreciacaoMaquinaTO depreciacaoTO = depreciacoesMaquinaTO.get(i);
        	            
        	totalMaquina.setValorTotalLavoura(totalMaquina.getValorTotalLavoura().add(depreciacaoTO.getValorTotalLavoura()));
            
            for(int j = 0; j < depreciacaoTO.getValoresPorTalhao().size(); j++) {
            	
            	BigDecimal valor = depreciacaoTO.getValoresPorTalhao().get(j);
            	BigDecimal soma = new BigDecimal(0);
            	BigDecimal somaTotal = new BigDecimal(0);
            	
            	if(i == 0) {
            		totalMaquina.getValoresPorTalhao().add(valor);
            		total.getValoresPorTalhao().add(valor);
            	}
            	else {
            		
            		soma = totalMaquina.getValoresPorTalhao().get(j).add(valor);
            		totalMaquina.getValoresPorTalhao().set(j, soma);
            		
            		somaTotal = total.getValoresPorTalhao().get(j).add(valor);
            		total.getValoresPorTalhao().set(j, somaTotal);
            	}
            		
            }
            
        }
        
        totalMaquina.setValorBemNovo(null);
        totalMaquina.setValorResidual(null);
        totalMaquina.setVidaUtilHoras(null);
        totalMaquina.setHorasTrabalhadas(null);
        totalMaquina.setValorDepreciacao(null);
        totalMaquina.setPorcentagemUtilizacao(null);
        totalMaquina.setNome("Total");
        
        if(depreciacoesMaquinaTO.size() > 0){
        	depreciacoesMaquinaTO.add(totalMaquina);
        }
        
        model.addAttribute("depreciacoesMaquinaTO", depreciacoesMaquinaTO);
        
        // --------- Depreciacao de Instalação ---------
        
        depreciacoesInstalacaoTO = this.depreciacaoInstalacaoService.buscarDepreciacoesTO(user.getPropriedade());
        
        for(int i = 0; i < depreciacoesInstalacaoTO.size(); i++) {
        	
        	DepreciacaoInstalacaoTO depreciacaoTO = depreciacoesInstalacaoTO.get(i);
        	
        	totalInstalacao.setValorDepreciacao(totalInstalacao.getValorDepreciacao().add(depreciacaoTO.getValorDepreciacao()));
 
            for(int j = 0; j < depreciacaoTO.getValoresPorTalhao().size(); j++) {
            	
            	BigDecimal valor = depreciacaoTO.getValoresPorTalhao().get(j);
            	BigDecimal soma = new BigDecimal(0);
            	BigDecimal somaTotal = new BigDecimal(0);
            	
            	if(i == 0) {
            		totalInstalacao.getValoresPorTalhao().add(valor);
            		
            		if(total.getValoresPorTalhao().isEmpty()) {
            			
            			total.getValoresPorTalhao().add(valor);
            		}
            		else {
            			
            			somaTotal = total.getValoresPorTalhao().get(j).add(valor);
                		total.getValoresPorTalhao().set(j, somaTotal);
            		}
            	}
            	else {
            		
            		soma = totalInstalacao.getValoresPorTalhao().get(j).add(valor);
            		totalInstalacao.getValoresPorTalhao().set(j, soma);
            		
            		somaTotal = total.getValoresPorTalhao().get(j).add(valor);
            		total.getValoresPorTalhao().set(j, somaTotal);
            	}
            		
            }
            
        }
        
        totalInstalacao.setValorBemNovo(null);
        totalInstalacao.setValorResidual(null);
        totalInstalacao.setVidaUtilAnos(null);
        totalInstalacao.setPorcentagemUtilizacao(null);
        totalInstalacao.setNome("Total");
        
        if(depreciacoesInstalacaoTO.size() > 0){
        	depreciacoesInstalacaoTO.add(totalInstalacao);
        }
        
        model.addAttribute("depreciacoesInstalacaoTO", depreciacoesInstalacaoTO);
        
        // --------- Depreciacao de Lavoura de Café ---------
        
        depreciacoesLavouraCafeTO = this.depreciacaoLavouraCafeService.buscarDepreciacoesTO(user.getPropriedade());
        
        for(int i = 0; i < depreciacoesLavouraCafeTO.size(); i++) {
        	
        	DepreciacaoLavouraCafeTO depreciacaoTO = depreciacoesLavouraCafeTO.get(i);
        	            
        	totalLavouraCafe.setValorDepreciacao(totalLavouraCafe.getValorDepreciacao().add(depreciacaoTO.getValorDepreciacao()));
            
            for(int j = 0; j < depreciacaoTO.getValoresPorTalhao().size(); j++) {
            	
            	BigDecimal valor = depreciacaoTO.getValoresPorTalhao().get(j);
            	BigDecimal soma = new BigDecimal(0);
            	BigDecimal somaTotal = new BigDecimal(0);
            	
            	if(i == 0) {
            		totalLavouraCafe.getValoresPorTalhao().add(valor);
            		
            		if(total.getValoresPorTalhao().isEmpty()) {
            			
            			total.getValoresPorTalhao().add(valor);
            		}
            		else {
            			
            			somaTotal = total.getValoresPorTalhao().get(j).add(valor);
                		total.getValoresPorTalhao().set(j, somaTotal);
            		}
            	}
            	else {
            		
            		soma = totalLavouraCafe.getValoresPorTalhao().get(j).add(valor);
            		totalLavouraCafe.getValoresPorTalhao().set(j, soma);
            		
            		somaTotal = total.getValoresPorTalhao().get(j).add(valor);
            		total.getValoresPorTalhao().set(j, somaTotal);
            	}
            		
            }
            
        }
        
        totalLavouraCafe.setCustoImplantacao(null);
        totalLavouraCafe.setReceitasObtidas(null);
        totalLavouraCafe.setVidaUtilAnos(null);
        totalLavouraCafe.setDescricao("Total");
        
        if(depreciacoesLavouraCafeTO.size() > 0){
        	depreciacoesLavouraCafeTO.add(totalLavouraCafe);
        }
        
        model.addAttribute("depreciacoesLavouraCafeTO", depreciacoesLavouraCafeTO);
        
        // --------- Depreciacao Total ---------
        
        for(int i = 0; i < total.getValoresPorTalhao().size(); i++) {
        	
        	BigDecimal valor = total.getValoresPorTalhao().get(i);
        	
        	total.setTotal(total.getTotal().add(valor));       		
        }
        
        model.addAttribute("depreciacaoTotal", total);

        return "restricted/custo/RelatorioDepreciacao";
    }
}
