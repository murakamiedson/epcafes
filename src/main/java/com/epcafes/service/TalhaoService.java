package com.epcafes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.exception.BusinessException;
import com.epcafes.model.Propriedade;
import com.epcafes.model.Talhao;
import com.epcafes.repository.TalhaoRepository;

@Service
public class TalhaoService {
	
	@Autowired
	private TalhaoRepository repository;
	
	public List<Talhao> findAll() {
		return this.repository.findAll();
	}
	
	public List<Talhao> findPaginated(int currPage, int pageSize) {
        int start = (currPage - 1) * pageSize;
        int end = Math.min(start + pageSize, repository.findAll().size());
        return repository.findAll().subList(start, end);
    }
	
	public void save(Talhao talhao) throws BusinessException{
		this.repository.save(talhao);
	}
	
	public void delete(Talhao talhao) throws BusinessException{
		this.repository.delete(talhao);
	}
	
	public Talhao findById(Long id) {
		return this.repository.findById(id).orElse(null);
	}
	
	public List<Talhao> findAllByPropriedade(Propriedade propriedade){
		
		return this.repository.findAllByPropriedade(propriedade);
	}
}