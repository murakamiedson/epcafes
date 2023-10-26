package com.epcafes.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.enums.TipoCombustivel;
import com.epcafes.model.DespesaMaquina;
import com.epcafes.model.Maquina;
import com.epcafes.repository.DespesaMaquinaRepository;

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
}
