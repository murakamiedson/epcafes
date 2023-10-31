package com.epcafes.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.dto.DespesaDTO;
import com.epcafes.dto.DespesaTO;
import com.epcafes.enums.TipoCombustivel;
import com.epcafes.model.DespesaMaquina;
import com.epcafes.model.Maquina;
import com.epcafes.repository.DespesaMaquinaRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class DespesaMaquinaService {

    @Autowired
    private DespesaMaquinaRepository despesaMaquinaRepository;

    @Autowired
    private MaquinaService maquinaService;

    public List<Maquina> findAllMaquinas() {
        return this.maquinaService.buscarMaquinas();
    }

    public void save(DespesaMaquina despesaMaquina) {
        despesaMaquina.setValorTotal(this.calcularValorTotal(despesaMaquina));
        this.despesaMaquinaRepository.save(despesaMaquina);
    }

    public List<DespesaMaquina> findAll() {
        return this.despesaMaquinaRepository.findAll();
    }

    public List<DespesaMaquina> findPaginated(int currPage, int pageSize) {
        int start = (currPage - 1) * pageSize;
        int end = Math.min(start + pageSize, this.despesaMaquinaRepository.findAll().size());
        return this.despesaMaquinaRepository.findAll().subList(start, end);
    }

    public void delete(DespesaMaquina despesaMaquina){
        this.despesaMaquinaRepository.delete(despesaMaquina);
    }

    public DespesaMaquina findById(long id){
        return this.despesaMaquinaRepository.findById(id);
    }

    private BigDecimal calcularValorTotal(DespesaMaquina despesaMaquina) {
        BigDecimal valor = new BigDecimal(0);

        if (despesaMaquina.getMaquina().getTipoCombustivel() == TipoCombustivel.DIESEL) {
            valor = despesaMaquina.getMaquina().getPotencia()
                    .multiply(despesaMaquina.getFatorPotencia().getValor().divide(new BigDecimal(100)))
                    .multiply(new BigDecimal(0.15))
                    .multiply(despesaMaquina.getPrecoCombustivel())
                    .multiply(despesaMaquina.getHorasTrabalhadas());
        } else if (despesaMaquina.getMaquina().getTipoCombustivel() == TipoCombustivel.ENERGIA_ELETRICA) {
            valor = despesaMaquina.getMaquina().getPotencia()
                    .multiply(new BigDecimal(0.735))
                    .multiply(despesaMaquina.getPrecoCombustivel())
                    .multiply(despesaMaquina.getHorasTrabalhadas());
        }

        return valor;
    }

    /*
     * Relatorio Despesa Maquina
     */

     public List<DespesaTO> buscarDespesasTO() {
        
        log.info("montando to...");

        List<DespesaDTO> despesasDTO = this.despesaMaquinaRepository.buscarDespesasDTO();

		log.info("qde DTO..." + despesasDTO.size());
        List<DespesaTO> despesasTO = new ArrayList<>();
		
		List<DespesaTO> auxiliar = new ArrayList<>();

        int cont=-1;
		
		for(int i=0; i<despesasDTO.size(); i++) {
				
			
			DespesaDTO dto = despesasDTO.get(i);

			log.info("DTO"+i+": mesAno: "+ dto.getMesAno() + " valortotal: "+dto.getValorTotal()+" maquinaID: "+
						dto.getMaquinaId()+" maquinaNome: "+dto.getMaquinaNome()+" tipoCombustivel: " + dto.getTipoCombustivel()
						);
			

			DespesaTO to = new DespesaTO();
			

			if(i==0 || (dto.getMaquinaId() != auxiliar.get(cont).getMaquinaId())) {
				cont++;
				
				to.setMaquinaId(dto.getMaquinaId());
				to.setMaquinaNome(dto.getMaquinaNome());				
				
				verificaMesAno(dto.getMesAnoLocalDate(dto.getMesAno()), to, dto.getValorTotal());
				
				
				
				auxiliar.add(to);
				
				log.info("IF: maquinaID: "+to.getMaquinaId()+" cont: "+cont+"despesasTOcont: "
						+auxiliar.get(cont).getMaquinaId());
				
			}else {
				
								
				verificaMesAno(dto.getMesAnoLocalDate(dto.getMesAno()), auxiliar.get(cont), dto.getValorTotal());
				

				log.info("ELSE: maquinaID: "+to.getMaquinaId()+" cont: "+cont+"despesasTOcont: "+auxiliar.get(cont).getMaquinaId());
			}
			
			
			log.info("qde TO..." + auxiliar.size());
			
		}
		
		despesasTO = auxiliar;
		
		for(int i=0; i<despesasTO.size(); i++) {
			calcValorAnual(despesasTO.get(i));
		}
		
		return despesasTO;
     }

     public void verificaMesAno(LocalDate mesAno, DespesaTO to, BigDecimal valorDespesa) {
		
		int data = mesAno.getMonthValue();
		
		switch(data) {
		
			case 1:
				
				if(to.getValorTotalJan()== new BigDecimal(0)) {
					
					to.setValorTotalJan(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalJan();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalJan(novoTotal);
				}
				
				break;
			
			case 2:
				
				if(to.getValorTotalFev()== new BigDecimal(0)) {
					
					to.setValorTotalFev(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalFev();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalFev(novoTotal);
				}
				break;
				
			case 3:
				if(to.getValorTotalMar()== new BigDecimal(0)) {
					
					to.setValorTotalMar(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalMar();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalMar(novoTotal);
				}
				break;
				
			case 4:
				if(to.getValorTotalAbr() == new BigDecimal(0)) {
					to.setValorTotalAbr(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalAbr();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalAbr(novoTotal);
				}
				break;
				
			case 5:
				if(to.getValorTotalMai() == new BigDecimal(0)) {
					to.setValorTotalMai(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalMai();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalMai(novoTotal);
				}
				break;
				
			case 6:
				if(to.getValorTotalJun() == new BigDecimal(0)) {
					to.setValorTotalJun(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalJun();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalJun(novoTotal);
				}
				break;
				
			case 7:				
				if(to.getValorTotalJul() == new BigDecimal(0)) {
					to.setValorTotalJul(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalJul();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalJul(novoTotal);
				}
				break;
				
			case 8:
				if(to.getValorTotalAgo() == new BigDecimal(0)) {
					to.setValorTotalAgo(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalAgo();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalAgo(novoTotal);
				}
				break;
				
			case 9:
				if(to.getValorTotalSet() == new BigDecimal(0)) {
					to.setValorTotalSet(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalSet();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalSet(novoTotal);
				}
				break;
				
			case 10:
				if(to.getValorTotalOut() == new BigDecimal(0)) {
					to.setValorTotalOut(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalOut();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalOut(novoTotal);
				}
				break;
				
			case 11:
				if(to.getValorTotalNov() == new BigDecimal(0)) {
					to.setValorTotalNov(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalNov();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalNov(novoTotal);
				}
				break;
				
			case 12:
				if(to.getValorTotalDez() == new BigDecimal(0)) {
					to.setValorTotalDez(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalDez();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalDez(novoTotal);
				}
				break;
		}
	}
	
	public void calcValorAnual(DespesaTO to) {
		
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
