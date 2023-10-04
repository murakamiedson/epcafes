package com.epcafes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.model.Maquina;
import com.epcafes.repository.MaquinaRepository;

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
