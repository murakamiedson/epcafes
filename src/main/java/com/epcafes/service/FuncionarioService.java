package com.epcafes.service;

import com.epcafes.model.funcionario.Funcionario;
import com.epcafes.DAO.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository repositorio;

    public void salvar(Funcionario funcionario){
        repositorio.save(funcionario);
    }

    public List<Funcionario> acharTodos(){
        return repositorio.findAll();
    }

    public Funcionario acharPorID(Long id){
        return repositorio.getReferenceById(id);
    }

    public void deletarPorId(Long id){
        repositorio.deleteById(id);
    }
}
