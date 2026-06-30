package com.app.notaFlow.dto.update;

import java.util.Optional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AvaliacaoUpdateDTO {

    private Optional<String> tituloAvaliacao = Optional.empty();
    private Optional<String> descricao = Optional.empty();
    
    private Optional<Double> valor = Optional.empty();

    private Optional<Long> alunoId = Optional.empty();
    private Optional<Long> cursoId = Optional.empty();
}
