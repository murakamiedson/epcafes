package com.epcafes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.model.Funcionario;
import com.epcafes.repository.FuncionarioRepository;

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

    public List<Funcionario> findPaginated(int currPage, int pageSize) {
        int start = (currPage - 1) * pageSize;
        int end = Math.min(start + pageSize, funcionarioRepository.findAll().size());
        return funcionarioRepository.findAll().subList(start, end);
    }

    public Funcionario acharPorID(Long id){
        return funcionarioRepository.findById(id).orElse(null);
    }

    public void deletarPorId(Long id){
        funcionarioRepository.deleteById(id);
    }
}
