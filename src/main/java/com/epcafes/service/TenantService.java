package com.epcafes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.model.Tenant;
import com.epcafes.repository.TenantRepository;

@Service
public class TenantService {
    @Autowired
    TenantRepository repository;

    public Tenant loadTenantById(long id){
        if(repository.findById(id).isPresent()){
            Tenant tenant = new Tenant(repository.findById(id).get().getId(),repository.findById(id).get().getNome());
            return tenant;
        }
        else{
            return null;
        }
    }

    public Tenant createTenant(String nome){
        Tenant newTenant = new Tenant(nome);
        return repository.save(newTenant);
    }
}
