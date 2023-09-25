package com.epcafes.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.List;

public class FuncionarioDBO {
    @Autowired
    FuncionarioRepository repository;
    Funcionario funcionario;

    public FuncionarioDBO() {
    }

    public Funcionario carregaFuncionarioPorId(Long id){
        return repository.getReferenceById(id);
    }
}
