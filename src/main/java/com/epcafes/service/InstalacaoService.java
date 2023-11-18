package com.epcafes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.exception.BusinessException;
import com.epcafes.model.Instalacao;
import com.epcafes.model.Propriedade;
import com.epcafes.repository.InstalacaoRepository;

@Service
public class InstalacaoService {
	
	@Autowired
	private InstalacaoRepository repository;
	
	public List<Instalacao> findAll() {
		return this.repository.findAll();
	}
	
	public List<Instalacao> findPaginated(int currPage, int pageSize) {
        int start = (currPage - 1) * pageSize;
        int end = Math.min(start + pageSize, repository.findAll().size());
        return repository.findAll().subList(start, end);
    }
	
	public void save(Instalacao instalacao) throws BusinessException{
		this.repository.save(instalacao);
	}
	
	public void delete(Instalacao instalacao) throws BusinessException{
		this.repository.delete(instalacao);
	}
	

	public Instalacao findById(Long id) {
		return this.repository.findById(id).orElse(null);
  }
  
	public List<Instalacao> listarInstalacoesPorPropriedade(Propriedade propriedade){
		
		return repository.findAllByPropriedade(propriedade);
	}
	
	public List<Instalacao> findAllByPropriedade(Propriedade propriedade){
		return repository.findAllByPropriedade(propriedade);
	}
}
