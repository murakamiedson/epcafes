package com.epcafes.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.epcafes.dto.DespesaDTO;
import com.epcafes.model.DespesaMaquina;
import com.epcafes.model.Maquina;

@Repository
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

}
