package com.epcafes.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.exception.BusinessExeption;
import com.epcafes.model.Fertilizante;
import com.epcafes.repository.FertilizanteRepository;

import lombok.extern.java.Log;

@Log
@Service
public class FertilizanteService implements Serializable {
    private static final long serialVersionUID = 1L;
    @Autowired
    private FertilizanteRepository fertilizanteRepository;

    public void salvar(Fertilizante fertilizante) throws BusinessExeption {

        log.info("Service : tenant = " + fertilizante.getTenantId());

        this.fertilizanteRepository.save(fertilizante);
    }

    public void excluir(Fertilizante fertilizante) throws BusinessExeption {

        fertilizanteRepository.delete(fertilizante);
    }

    public Fertilizante buscarPeloCodigo(long codigo) throws BusinessExeption {
        return fertilizanteRepository.findById(codigo).orElse(null);

    }

    public List<Fertilizante> buscarPorTenant(long tenantId) throws BusinessExeption {
        return fertilizanteRepository.findByTenantId(tenantId);

    }

    public List<Fertilizante> buscarFertilizantes() {

        log.info("Primeiro acesso a banco... buscar fertilizantes");

        return fertilizanteRepository.findAll();

    }

    public void atualizar(Fertilizante fertilizante, long id) {
        fertilizante.setId(id);
        this.fertilizanteRepository.save(fertilizante);

    }

}
