package com.app.notaFlow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class LoginRequestDTO {
	
	 @NotBlank(message = "O nome de usuário é obrigatório")
	    private String email;

	    @NotBlank(message = "A senha é obrigatória")
	    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
	    private String senha;

}
