package com.arquitetura.epcafe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arquitetura.epcafe.model.Maquina;
import com.arquitetura.epcafe.repository.MaquinaRepository;

@Service
public class MaquinaService {

    @Autowired
    private MaquinaRepository maquinaRepository;

    public void save(Maquina maquina){
        maquinaRepository.save(maquina);
    }

    public List<Maquina> findAll(){
        return maquinaRepository.findAll();
    }

}
