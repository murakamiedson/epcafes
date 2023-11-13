package com.epcafes.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epcafes.dto.DespesaCustoFixoDTO;
import com.epcafes.dto.DespesaCustoFixoTO;
import com.epcafes.exception.BusinessException;
import com.epcafes.model.CustoFixo;
import com.epcafes.model.DespesaCustoFixo;
import com.epcafes.model.Propriedade;
import com.epcafes.model.Talhao;
import com.epcafes.repository.DespesaCustoFixoRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DespesaCustoFixoService {
	
	@Autowired
	private DespesaCustoFixoRepository despesaCustoFixoRepository;
	
	@Autowired
	private CustoFixoService custoFixoService;
	
	@Autowired
	private TalhaoService talhaoService;
	
	@Transactional
	public DespesaCustoFixo salvar(DespesaCustoFixo despesaCustoFixo) throws BusinessException {
		
		List<DespesaCustoFixo> despesas = despesaCustoFixoRepository.findAllByCustoFixo(despesaCustoFixo.getCustoFixo());
		
		if(!despesas.isEmpty()) {
			
			for (DespesaCustoFixo despesa : despesas) {
				
				if(despesa.getMesAno().getMonth() == despesaCustoFixo.getMesAno().getMonth()
						&& despesa.getMesAno().getYear() == despesaCustoFixo.getMesAno().getYear()
						&& despesa.getId() != despesaCustoFixo.getId()) {
					
					throw new BusinessException("", "O CustoFixo " + despesa.getCustoFixo().getNome() +
							" já possui uma despesa em " + 
							despesa.getMesAno().getMonth().getValue() + "/" + despesa.getMesAno().getYear());
				}
			}
		}
	
		return despesaCustoFixoRepository.save(despesaCustoFixo);
	}
	
	public Optional<DespesaCustoFixo> buscarPorId(Long id) {
		
		return despesaCustoFixoRepository.findById(id);
	}
	
	public List<DespesaCustoFixo> listarDespesasCustosFixosPorPropriedade(Propriedade propriedade){
		
		return despesaCustoFixoRepository.findAllByPropriedade(propriedade);
	}
	
	public List<DespesaCustoFixo> listarDespesasDeUmCustoFixo(CustoFixo custoFixo){
		
		return despesaCustoFixoRepository.findAllByCustoFixo(custoFixo);
	}
	
	@Transactional
	public void excluir(Long id) {
		
		despesaCustoFixoRepository.deleteById(id);
	}
	
	/*
     * Relatorio Despesa Custo Fixo
     */

    public List<Integer> buscarAnos(){
        return this.despesaCustoFixoRepository.buscarAnos();
    }
    
	public List<DespesaCustoFixoTO> buscarDespesasTO(int ano, Propriedade propriedade) {
    	
    	//lista de DespesaCustoFixoTO de cada CustoFixo
        List<DespesaCustoFixoTO> despesasTO = new ArrayList<>();
        
        List<Talhao> talhoes = this.talhaoService.findAllByPropriedade(propriedade);
    	
    	List<CustoFixo> custosFixos = this.custoFixoService.listarCustosFixosPorPropriedade(propriedade);
    	
    	for(CustoFixo custoFixo : custosFixos) {
    		
    		List<DespesaCustoFixoDTO> despesasDTO = this.despesaCustoFixoRepository.buscarDespesasCustoFixoDTO(custoFixo, ano, propriedade);
    		
    		log.info("qde DTO..." + despesasDTO.size());
    		
    		if(despesasDTO.size() > 0) {
    			
    			DespesaCustoFixoTO to = new DespesaCustoFixoTO();
    			
    			to.setNomeCustoFixo(custoFixo.getNome());
                
                for(DespesaCustoFixoDTO despesaDTO : despesasDTO) {
                	
                	to.setPorcentagemUtilizacao(despesaDTO.getPorcentagemUtilizacao()); //verificar
                	
                	verificaMesAno(despesaDTO.getMesAno(), to, despesaDTO.getValor());
                }
                
                despesasTO.add(to);
    		}
    	}
    	
    	for (int i = 0; i < despesasTO.size(); i++) {
    		
    		calcValorAnual(despesasTO.get(i));
    	}
    	
    	//Relatorio Parte Por Talhão
    	
    	for(DespesaCustoFixoTO despesaTO : despesasTO) {
    		
    		Float soma = 0.0f;
    		
    		for(Talhao talhao : talhoes) {
    			
    			soma+= talhao.getArea();
    		}
    		
    		for(Talhao talhao : talhoes) {
        		
    			//valorTotalLavoura = valorTotalAnual * (porcentagem de utilização / 100)
    			Float porcentagem = despesaTO.getPorcentagemUtilizacao() / 100;
        		despesaTO.setValorTotalLavoura(despesaTO.getValorTotalAnual()
        				.multiply(new BigDecimal(porcentagem))
        				.setScale(2, RoundingMode.HALF_UP));
        		
        		//valorPorTalhap = (valorTotalLavoura * areaTalhao) / somaAreasTalhoes
        		BigDecimal calculo = despesaTO.getValorTotalLavoura()
        				.multiply(new BigDecimal(talhao.getArea()))
        				.divide(new BigDecimal(soma), 2, RoundingMode.HALF_UP);
        		
        		calculo = calculo.setScale(2, RoundingMode.HALF_UP); //arredondando para 2 casas decimais
        		despesaTO.getValoresPorTalhao().add(calculo);
    		}
    	}

        return despesasTO;
    }
    
    public void verificaMesAno(LocalDate mesAno, DespesaCustoFixoTO to, BigDecimal valorDespesa) {

        int data = mesAno.getMonthValue();
        
        switch (data) {

            case 1:

                if (to.getValorTotalJan() == new BigDecimal(0)) {
                    to.setValorTotalJan(valorDespesa);
                } else {
                    BigDecimal totalExistente = to.getValorTotalJan();
                    BigDecimal novoTotal = totalExistente.add(valorDespesa);
                    to.setValorTotalJan(novoTotal);
                }

                break;

            case 2:

                if (to.getValorTotalFev() == new BigDecimal(0)) {

                    to.setValorTotalFev(valorDespesa);
                } else {
                    BigDecimal totalExistente = to.getValorTotalFev();
                    BigDecimal novoTotal = totalExistente.add(valorDespesa);
                    to.setValorTotalFev(novoTotal);
                }
                break;

            case 3:
                if (to.getValorTotalMar() == new BigDecimal(0)) {

                    to.setValorTotalMar(valorDespesa);
                } else {
                    BigDecimal totalExistente = to.getValorTotalMar();
                    BigDecimal novoTotal = totalExistente.add(valorDespesa);
                    to.setValorTotalMar(novoTotal);
                }
                break;

            case 4:
                if (to.getValorTotalAbr() == new BigDecimal(0)) {
                    to.setValorTotalAbr(valorDespesa);
                } else {
                    BigDecimal totalExistente = to.getValorTotalAbr();
                    BigDecimal novoTotal = totalExistente.add(valorDespesa);
                    to.setValorTotalAbr(novoTotal);
                }
                break;

            case 5:
                if (to.getValorTotalMai() == new BigDecimal(0)) {
                    to.setValorTotalMai(valorDespesa);
                } else {
                    BigDecimal totalExistente = to.getValorTotalMai();
                    BigDecimal novoTotal = totalExistente.add(valorDespesa);
                    to.setValorTotalMai(novoTotal);
                }
                break;

            case 6:
                if (to.getValorTotalJun() == new BigDecimal(0)) {
                    to.setValorTotalJun(valorDespesa);
                } else {
                    BigDecimal totalExistente = to.getValorTotalJun();
                    BigDecimal novoTotal = totalExistente.add(valorDespesa);
                    to.setValorTotalJun(novoTotal);
                }
                break;

            case 7:
                if (to.getValorTotalJul() == new BigDecimal(0)) {
                    to.setValorTotalJul(valorDespesa);
                } else {
                    BigDecimal totalExistente = to.getValorTotalJul();
                    BigDecimal novoTotal = totalExistente.add(valorDespesa);
                    to.setValorTotalJul(novoTotal);
                }
                break;

            case 8:
                if (to.getValorTotalAgo() == new BigDecimal(0)) {
                    to.setValorTotalAgo(valorDespesa);
                } else {
                    BigDecimal totalExistente = to.getValorTotalAgo();
                    BigDecimal novoTotal = totalExistente.add(valorDespesa);
                    to.setValorTotalAgo(novoTotal);
                }
                break;

            case 9:
                if (to.getValorTotalSet() == new BigDecimal(0)) {
                    to.setValorTotalSet(valorDespesa);
                } else {
                    BigDecimal totalExistente = to.getValorTotalSet();
                    BigDecimal novoTotal = totalExistente.add(valorDespesa);
                    to.setValorTotalSet(novoTotal);
                }
                break;

            case 10:
                if (to.getValorTotalOut() == new BigDecimal(0)) {
                    to.setValorTotalOut(valorDespesa);
                } else {
                    BigDecimal totalExistente = to.getValorTotalOut();
                    BigDecimal novoTotal = totalExistente.add(valorDespesa);
                    to.setValorTotalOut(novoTotal);
                }
                break;

            case 11:
                if (to.getValorTotalNov() == new BigDecimal(0)) {
                    to.setValorTotalNov(valorDespesa);
                } else {
                    BigDecimal totalExistente = to.getValorTotalNov();
                    BigDecimal novoTotal = totalExistente.add(valorDespesa);
                    to.setValorTotalNov(novoTotal);
                }
                break;

            case 12:
                if (to.getValorTotalDez() == new BigDecimal(0)) {
                    to.setValorTotalDez(valorDespesa);
                } else {
                    BigDecimal totalExistente = to.getValorTotalDez();
                    BigDecimal novoTotal = totalExistente.add(valorDespesa);
                    to.setValorTotalDez(novoTotal);
                }
                break;
        }
    }
    
    public void calcValorAnual(DespesaCustoFixoTO to) {

        log.info("calcValorAnual...");

        BigDecimal valor = to.getValorTotalJan()
                .add(to.getValorTotalFev())
                .add(to.getValorTotalMar())
                .add(to.getValorTotalAbr())
                .add(to.getValorTotalMai())
                .add(to.getValorTotalJun())
                .add(to.getValorTotalJul())
                .add(to.getValorTotalAgo())
                .add(to.getValorTotalSet())
                .add(to.getValorTotalOut())
                .add(to.getValorTotalNov())
                .add(to.getValorTotalDez());

        to.setValorTotalAnual(valor);

        log.info(valor);

    }
}
