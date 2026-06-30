package com.app.notaFlow.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name="tb_usuario")
public class Usuario {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O nome é obrigatório")
	private String nome;

	@Email(message = "Esse campo deve ser um e-mail válido")
    @NotBlank(message = "O e-mail não pode ficar vazio")
	@Column(unique = true)
	private String email;
	
	@NotBlank(message = "A senha não pode ficar vazia")
	private String senha;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
	    name = "tb_usuario_roles", 
	    joinColumns = @JoinColumn(name = "usuario_id") 
	)
	@Enumerated(EnumType.STRING)
	private List<Role> roles = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "curso_id")
	private Curso curso;


}
