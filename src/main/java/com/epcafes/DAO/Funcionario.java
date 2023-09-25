package com.epcafes.DAO;

import com.epcafes.model.funcionario.DadosAlteraFuncionario;
import com.epcafes.model.funcionario.DadosCadastroFuncionario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table (name = "funcionario")
@Getter
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Setter
    private String nome;
    @Getter
    @Setter
    private Double salario;
    @Getter
    @Setter
    private LocalDate nascimento;
    @Getter
    @Setter
    private Long tenant_id;

    public Funcionario(){}


    public Funcionario(DadosCadastroFuncionario dados, Long tenant_id) {
        LocalDate date = LocalDate.parse(dados.nascimento());
        this.nome = dados.nome();
        this.salario = dados.salario();
        this.tenant_id = tenant_id;
        this.nascimento  = date;

    }

    public void alteraDados(DadosAlteraFuncionario dados){
        LocalDate date = LocalDate.parse(dados.nascimento());
        this.nome = dados.nome();
        this.salario = dados.salario();
        this.nascimento = date;

    }
}
