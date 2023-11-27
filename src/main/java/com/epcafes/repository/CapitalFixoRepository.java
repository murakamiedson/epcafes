package com.epcafes.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epcafes.dto.DespesaDTO;
import com.epcafes.model.CapitalFixo;

public interface CapitalFixoRepository extends JpaRepository<CapitalFixo, Long>  {

    @Query("SELECT SUM(valorTotal) from DespesaMaquina d WHERE d.mesAno BETWEEN :mesAnoStart AND :mesAnoEnd")
    Double buscarValorBemNovo(Optional<LocalDate> mesAnoStart, Optional<LocalDate> mesAnoEnd);
	
}
