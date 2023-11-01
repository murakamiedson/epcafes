package com.epcafes.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import com.epcafes.enums.TipoCombustivel;
import com.epcafes.model.Maquina;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DespesaDTO {

    private Date mesAno;
    private BigDecimal valorTotal;
    private Long maquinaId;
    private String maquinaNome;
    private String tipoCombustivel;

    public DespesaDTO(LocalDate mesAno, BigDecimal valorTotal, Maquina maquinaId, String maquinaNome, TipoCombustivel tipoCombustivel) {
        this.mesAno = Date.valueOf(mesAno);
        this.maquinaId = maquinaId.getId();
        this.maquinaNome = maquinaNome;
        this.valorTotal = valorTotal;
        this.tipoCombustivel = tipoCombustivel.toString();
    }



    // public LocalDate getMesAnoLocalDate(){
    //     return LocalDate.parse(this.mesAno);
    // }

    public LocalDate getMesAnoLocalDate(Date mesAnoDate){
        return mesAnoDate.toLocalDate();
    }

}
