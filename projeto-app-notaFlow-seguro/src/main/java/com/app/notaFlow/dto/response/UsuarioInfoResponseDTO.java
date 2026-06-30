package com.app.notaFlow.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class UsuarioInfoResponseDTO {
	
	private String nomeUsuario;
	private Long idUsuario;
	private String nomeCurso;
	private Long idCurso;
	private List<String> roles;

}
