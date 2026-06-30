package com.app.notaFlow.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CursoRequestDTO {

    @NotBlank(message = "O nome do curso é obrigatório")
    private String nomeCurso;
}