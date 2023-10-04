package com.epcafes.DAO;

import com.epcafes.model.funcionario.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;

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
