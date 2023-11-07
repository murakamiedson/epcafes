package com.epcafes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epcafes.model.Propriedade;
import com.epcafes.model.Talhao;

import jakarta.persistence.EntityManager;

public interface TalhaoRepository extends JpaRepository<Talhao, Long>{
	
	public static final EntityManager manager = null;
	
	public static List<Talhao> buscarTalhoesPorUnidade(Propriedade propriedade, Long tenantId) {
		return manager.createNamedQuery("Talhao.buscarPorUnidade", Talhao.class)				
				.setParameter("unidade", propriedade)
				.setParameter("tenantId", tenantId)
				.getResultList();
	}
}