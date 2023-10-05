package com.epcafes.service;

import java.io.Serializable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.model.Maquina;
import com.epcafes.repository.MaquinaRepository;

import com.epcafes.util.NegocioExeption;

@Service
public class MaquinaService implements Serializable {
    private static final long serialVersionUID = 1L;
    @Autowired
    private MaquinaRepository maquinaRepository;

    public void salvar(Maquina maquina) throws NegocioExeption {

        // log.info("Service : tenant = " + maquina.getTenantId());
        this.maquinaRepository.save(maquina);

    }

    public void excluir(Maquina maquina) throws NegocioExeption {
        maquinaRepository.delete(maquina);

    }

    public Maquina buscarPeloCodigo(long codigo) throws NegocioExeption {
        return maquinaRepository.findById(codigo).orElse(null);

    }

    public List<Maquina> buscarMaquinas() {

        return maquinaRepository.findAll();

    }

    /* testes */

    public MaquinaRepository getMaquinaRepository() {
        return maquinaRepository;

    }

}
