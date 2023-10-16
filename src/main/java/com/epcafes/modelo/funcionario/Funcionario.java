package com.epcafes.modelo.funcionario;

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


//    public Funcionario(DadosCadastroFuncionario dados, Long tenant_id) {
//        LocalDate date = LocalDate.parse(dados.nascimento());
//        this.nome = dados.nome();
//        this.salario = dados.salario();
//        this.tenant_id = tenant_id;
//        this.nascimento  = date;
//    }

    public Funcionario(String nome, Double salario, LocalDate nascimento, Long tenant_id) {
        this.nome = nome;
        this.salario = salario;
        this.nascimento = nascimento;
        this.tenant_id = tenant_id;
    }
    public Funcionario(String nome, Double salario, LocalDate nascimento) {
        this.nome = nome;
        this.salario = salario;
        this.nascimento = nascimento;
        this.tenant_id = tenant_id;
    }

    public void alteraDados(DadosAlteraFuncionario dados){
        LocalDate date = LocalDate.parse(dados.nascimento());
        this.nome = dados.nome();
        this.salario = dados.salario();
        this.nascimento = date;
    }

    public void alteraDados(Funcionario dados){
        this.nome = dados.getNome();
        this.salario = dados.getSalario();
        this.nascimento = dados.getNascimento();
    }
}
