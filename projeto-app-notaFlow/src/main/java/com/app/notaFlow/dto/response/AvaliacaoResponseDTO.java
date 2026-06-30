package com.app.notaFlow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AvaliacaoResponseDTO {

    private Long id;
    private String tituloAvaliacao;
    private Double valor;
    private UsuarioResponseDTO aluno;
    private CursoResponseDTO curso;
}