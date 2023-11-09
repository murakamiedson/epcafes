package com.epcafes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.model.DespesaFertilizante;
import com.epcafes.model.Fertilizante;
import com.epcafes.repository.DespesaFertilizanteRepository;

@Service
public class DespesaFertilizanteService {
    
    @Autowired
    private DespesaFertilizanteRepository despesaFertilizanteRepository;

    @Autowired
    private FertilizanteService fertilizanteService;

    public void save(DespesaFertilizante despesaFertilizante) {
        this.despesaFertilizanteRepository.save(despesaFertilizante);
    }

    public void delete(DespesaFertilizante despesaFertilizante){
        this.despesaFertilizanteRepository.delete(despesaFertilizante);
    }

    public DespesaFertilizante findById(long id){
        return this.despesaFertilizanteRepository.findById(id);
    }

    public List<Fertilizante> findAllFertilizantes(long tenantId) {
        return this.fertilizanteService.buscarFertilizantes(tenantId);
    }

}
