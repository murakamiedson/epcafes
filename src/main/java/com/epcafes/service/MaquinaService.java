package com.epcafes.service;

import java.io.Serializable;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.epcafes.exception.InsumoException;
import com.epcafes.model.Maquina;
import com.epcafes.repository.MaquinaRepository;

@Service
public class MaquinaService implements Serializable {
    private static final long serialVersionUID = 1L;
    @Autowired
    private MaquinaRepository maquinaRepository;

    public void salvar(Maquina maquina) throws InsumoException {

        this.maquinaRepository.save(maquina);

    }

    public void excluir(Maquina maquina) throws InsumoException {
        maquinaRepository.delete(maquina);

    }

    public void atualizar(Maquina maquina, long id) throws InsumoException {
        maquina.setId(id);
        this.maquinaRepository.save(maquina);

    }

    public Maquina buscarPeloCodigo(long codigo) throws InsumoException {
        return maquinaRepository.findById(codigo).orElse(null);

    }

    public List<Maquina> buscarPorTenant(long tenantId) {
        return maquinaRepository.findByTenantId(tenantId);

    }

    public List<Maquina> buscarMaquinas() {

        return maquinaRepository.findAll();

    }

    public List<Maquina> findPaginated(int currPage, int pageSize, long tenantId) {
        int start = (currPage - 1) * pageSize;
        int end = Math.min(start + pageSize, this.maquinaRepository.findByTenantId(tenantId).size());
        return this.maquinaRepository.findByTenantId(tenantId).subList(start, end);
    }

    /* testes */

    public MaquinaRepository getMaquinaRepository() {
        return maquinaRepository;

    }

}
