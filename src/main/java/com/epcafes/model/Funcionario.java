package com.epcafes.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Entity
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private Long tenant_id;
    
    @NotBlank
    @Column(nullable = false)
    private String nome;
    
    @PositiveOrZero
    private Double salario;
    
    @NotNull
    private LocalDate nascimento;
    
    @NotNull
	@ManyToOne
	private Propriedade propriedade;
    
    /*
    @OneToMany(cascade=CascadeType.ALL, mappedBy="funcionario", fetch = FetchType.EAGER)
	private List<Formacao> formacao;
	*/
    
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
    
    /*
	 * Datas de Criação e Modificação
	 */
	
	@CreationTimestamp	
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataCriacao;
	
	@UpdateTimestamp
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataModificacao;
	
}
