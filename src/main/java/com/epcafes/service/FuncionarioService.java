package com.epcafes.service;

import com.epcafes.modelo.funcionario.Funcionario;
import com.epcafes.DAO.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public void salvar(Funcionario funcionario){
        funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> acharTodos(){
        return funcionarioRepository.findAll();
    }

    public Funcionario acharPorID(Long id){
        return funcionarioRepository.getReferenceById(id);
    }

    public void deletarPorId(Long id){
        funcionarioRepository.deleteById(id);
    }
}
