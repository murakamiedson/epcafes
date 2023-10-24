package com.epcafes.model;

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
    @Setter
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
    }

    public Funcionario(Long id, String nome, Double salario, LocalDate nascimento) {
        this.id = id;
        this.nome = nome;
        this.salario = salario;
        this.nascimento = nascimento;
    }

    public void alteraDados(Funcionario dados){
        this.nome = dados.getNome();
        this.salario = dados.getSalario();
        this.nascimento = dados.getNascimento();
    }
    public int getIdade(){
        int idade = LocalDate.now().getYear() - nascimento.getYear();
        if(nascimento.getMonthValue() > LocalDate.now().getMonthValue()){
            idade--;
            return idade;

        }else if((nascimento.getMonthValue() == LocalDate.now().getMonthValue()) && nascimento.getDayOfMonth() > LocalDate.now().getDayOfMonth()){
            idade--;
            return idade;
        }
        return idade;
    }
}
