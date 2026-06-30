package com.app.notaFlow.dto.update;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioUpdateDTO {
    @NotBlank(message = "A senha não pode ficar vazia")
    private String senha;
}
