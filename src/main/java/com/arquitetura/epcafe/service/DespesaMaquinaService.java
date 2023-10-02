package com.arquitetura.epcafe.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arquitetura.epcafe.enums.TipoCombustivel;
import com.arquitetura.epcafe.model.DespesaMaquina;
import com.arquitetura.epcafe.model.Maquina;
import com.arquitetura.epcafe.repository.DespesaMaquinaRepository;

@Service
public class DespesaMaquinaService {

    @Autowired
    private DespesaMaquinaRepository despesaMaquinaRepository;

    @Autowired
    private MaquinaService maquinaService;

    public List<Maquina> findAllMaquinas() {
        return this.maquinaService.findAll();
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

    private BigDecimal calcularValorTotal(DespesaMaquina despesaMaquina) {
        BigDecimal valor = new BigDecimal(0);

        if (despesaMaquina.getMaquina().getTipoCombustivel() == TipoCombustivel.DIESEL) {
            valor = despesaMaquina.getMaquina().getPotencia()
                    .multiply(despesaMaquina.getFatorPotencia().getValor().divide(new BigDecimal(100)))
                    .multiply(new BigDecimal(0.15))
                    .multiply(despesaMaquina.getPrecoCombustivel())
                    .multiply(despesaMaquina.getHorasTrabalhadas());
        }else if (despesaMaquina.getMaquina().getTipoCombustivel() == TipoCombustivel.ENERGIA_ELETRICA) {
            valor = despesaMaquina.getMaquina().getPotencia()
						.multiply(new BigDecimal(0.735))
						.multiply(despesaMaquina.getPrecoCombustivel())
						.multiply(despesaMaquina.getHorasTrabalhadas());
        }

        return valor;
    }
}
