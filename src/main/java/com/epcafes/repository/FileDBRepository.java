package com.epcafes.repository;
import com.epcafes.model.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
    List<FileDB> findByIdFuncionario(Long idFuncionario);
    FileDB findByUuidRegistrado(String uuidRegistrado);
}
