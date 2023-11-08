package com.epcafes.service;

import java.io.Serializable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.exception.BusinessExeption;
import com.epcafes.model.Maquina;
import com.epcafes.repository.MaquinaRepository;

@Service
public class MaquinaService implements Serializable {
    private static final long serialVersionUID = 1L;
    @Autowired
    private MaquinaRepository maquinaRepository;

    public void salvar(Maquina maquina) throws BusinessExeption {

        this.maquinaRepository.save(maquina);

    }

    public void excluir(Maquina maquina) throws BusinessExeption {
        maquinaRepository.delete(maquina);

    }

    public void atualizar(Maquina maquina, long id) throws BusinessExeption {
        maquina.setId(id);
        this.maquinaRepository.save(maquina);

    }

    public Maquina buscarPeloCodigo(long codigo) throws BusinessExeption {
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
