package com.app.notaFlow.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private List<String> role;
    private CursoResponseDTO curso;
}