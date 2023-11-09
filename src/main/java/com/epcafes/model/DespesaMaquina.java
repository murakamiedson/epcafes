package com.epcafes.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.NamedNativeQuery;

import com.epcafes.dto.DespesaDTO;
import com.epcafes.enums.FatorPotencia;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SqlResultSetMapping;
import lombok.Data;

@Data
// @NamedQueries({
// @NamedQuery(name = "DespesaMaquina.buscarDespesasDTO", query = "SELECT new
// com.epcafes.dto.DespesaDTO( "
// + "d.mesAno, "
// + "d.valorTotal, "
// + "m.id, "
// + "m.nome, "
// + "m.tipoCombustivel) "
// + "FROM DespesaMaquina d "
// + " INNER JOIN Maquina m on d.maquina = m.id "
// + "WHERE "
// + " mesAno <= :mesAno "
// + "ORDER BY m.id"),
// })

@NamedNativeQuery(name = "DespesaMaquina.buscarDespesasDTO", 
    query="select d.mes_ano as mesAno, d.valor_total as valorTotal, m.id as maquinaId, m.nome as maquinaNome, m.tipo_combustivel as tipoCombustivel from despesa_maquina d inner join maquina m on (d.maquina_id=m.id) order by m.id",
    resultSetMapping = "Mapping.DespesasDTO")
@SqlResultSetMapping(name = "Mapping.DespesasDTO",
    classes = @ConstructorResult(targetClass = DespesaDTO.class,
                                    columns = {@ColumnResult(name = "mesAno"),
                                                @ColumnResult(name = "valorTotal"),
                                                @ColumnResult(name = "maquinaId"),
                                                @ColumnResult(name = "maquinaNome"),
                                                @ColumnResult(name = "tipoCombustivel")}))
@Entity
public class DespesaMaquina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tenantId;

    private LocalDate mesAno;

    private BigDecimal horasTrabalhadas = new BigDecimal(0);

    @Enumerated(EnumType.STRING)
    private FatorPotencia fatorPotencia;

    @ManyToOne
    private Propriedade propriedade;

    @ManyToOne
    private Maquina maquina;

    private BigDecimal precoCombustivel;

    private BigDecimal valorTotal;
}
