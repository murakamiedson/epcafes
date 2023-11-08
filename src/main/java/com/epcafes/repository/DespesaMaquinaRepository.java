package com.epcafes.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epcafes.dto.DespesaDTO;
import com.epcafes.model.DespesaMaquina;
import com.epcafes.model.Maquina;

public interface DespesaMaquinaRepository extends JpaRepository<DespesaMaquina, Long> {
 

    DespesaMaquina findById(long id);


    List<DespesaMaquina> findByMaquina(Maquina maquina);

    @Query("select d from DespesaMaquina d where d.maquina = :maquina")
    List<DespesaMaquina> buscarPorMaquina(Maquina maquina); 
    // @Query("select d.mesAno, d.valorTotal, m.id AS maquinaId, m.nome AS maquinaNome, m.tipoCombustivel from DespesaMaquina d inner join Maquina m on d.maquina = m.id where mesAno = ?1")
    // List<DespesaDTO> buscarDespesasDTO(LocalDate mesAno);

    @Query("select d.mesAno, d.valorTotal from DespesaMaquina d where d.mesAno = :mesAno")
    List<DespesaMaquina> buscarDespesasMaquinaPorMesAno(LocalDate mesAno);

    // @Query(value = "select new com.epcafes.dto.DespesaDTO(d.mes_ano as mesAno, d.valor_total as valorTotal, m.id as maquinaId, m.nome as maquinaNome, m.tipo_combustivel as tipoCombustivel) from despesa_maquina d inner join maquina m on (d.maquina_id=m.id) order by m.id")
    // List<DespesaDTO> buscarDespesasDTO();
    
    @Query(nativeQuery = true)
    List<DespesaDTO> buscarDespesasDTO();


    @Query("SELECT new com.epcafes.dto.DespesaDTO(d.mesAno, d.valorTotal, d.maquina, m.nome, m.tipoCombustivel) FROM DespesaMaquina d INNER JOIN Maquina m ON d.maquina.id = m.id WHERE FUNCTION('YEAR', d.mesAno) = :ano ORDER BY d.maquina.id")
    List<DespesaDTO> buscarDespesasDTO(int ano);

    @Query("SELECT FUNCTION('YEAR', d.mesAno) FROM DespesaMaquina d GROUP BY FUNCTION('YEAR', d.mesAno)")
    List<Integer> buscarAnos();

}
