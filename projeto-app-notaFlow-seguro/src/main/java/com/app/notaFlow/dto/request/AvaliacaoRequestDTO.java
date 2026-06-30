package com.app.notaFlow.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AvaliacaoRequestDTO {

    @NotBlank(message = "O título da avaliação é obrigatório")
    private String tituloAvaliacao;

    private String descricao;

    @NotNull(message = "O ID do aluno é obrigatório")
    private Long alunoId;

    @NotNull(message = "O ID do curso é obrigatório")
    private Long cursoId;

    @NotNull(message = "O valor da nota é obrigatório")
    @Min(value = 0, message = "A nota mínima é 0")
    @Max(value = 10, message = "A nota máxima é 10")
    private Double valor;
}