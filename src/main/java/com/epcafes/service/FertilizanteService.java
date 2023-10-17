package com.epcafes.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.epcafes.model.Fertilizante;
import com.epcafes.repository.FertilizanteRepository;
import com.epcafes.util.NegocioExeption;

import lombok.extern.java.Log;

@Log
public class FertilizanteService implements Serializable {
    private static final long serialVersionUID = 1L;
    @Autowired
    private FertilizanteRepository fertilizanteRepository;

    public void salvar(Fertilizante fertilizante) throws NegocioExeption {

        log.info("Service : tenant = " + fertilizante.getTenantId());

        this.fertilizanteRepository.save(fertilizante);
    }

    public void excluir(Fertilizante fertilizante) throws NegocioExeption {

        fertilizanteRepository.delete(fertilizante);
    }

    public Fertilizante buscarPeloCodigo(long codigo) throws NegocioExeption {
        return fertilizanteRepository.findById(codigo).orElse(null);

        // Excluir depois caso nao de pau
        // Fertilizante f =
        // fertilizanteRepository.findFertilizanteById(codigo).orElse(null);

        // return f;
    }

    public List<Fertilizante> buscarFertilizantes(Long tenantId) {

        log.info("Primeiro acesso a banco... buscar fertilizantes");

        return fertilizanteRepository.findByTenantId(tenantId);

    }

}
