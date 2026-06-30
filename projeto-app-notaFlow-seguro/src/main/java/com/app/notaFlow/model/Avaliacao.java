package com.app.notaFlow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "tb_avaliacao")
public class Avaliacao {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String tituloAvaliacao;

    private String descricao;
    
    @ManyToOne 
    @JoinColumn(name = "aluno_id")
    private Usuario aluno;
    
    @ManyToOne 
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @NotNull(message = "O valor da nota é obrigatório")
    @Min(0) @Max(10)
    private Double valor;
      

}

