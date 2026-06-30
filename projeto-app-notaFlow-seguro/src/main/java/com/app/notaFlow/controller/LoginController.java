package com.app.notaFlow.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.notaFlow.dto.request.LoginRequestDTO;
import com.app.notaFlow.dto.response.UsuarioResponseDTO;
import com.app.notaFlow.model.Usuario;
import com.app.notaFlow.service.AutenticacaoService;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth/login")
public class LoginController {
	
	@Autowired
	private AutenticacaoService autenticacaoService; 

	@PostMapping
	@PermitAll
	public ResponseEntity<UsuarioResponseDTO> login(@RequestBody LoginRequestDTO dto, HttpServletResponse response) {
	    
	    UsuarioResponseDTO usuario = autenticacaoService.autenticarEGerarCookie(dto.getEmail(), dto.getSenha(), response);
	    
	    if (usuario != null) {
	        return ResponseEntity.ok(usuario);
	    }
	    
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

}
