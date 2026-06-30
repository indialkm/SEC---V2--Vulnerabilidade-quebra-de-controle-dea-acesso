package com.app.notaFlow.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.notaFlow.dto.request.LoginRequestDTO;
import com.app.notaFlow.dto.response.UsuarioResponseDTO;
import com.app.notaFlow.model.Usuario;
import com.app.notaFlow.service.AutenticacaoService;

@RestController
@RequestMapping("/api/auth/login")
@CrossOrigin(origins = "*")
public class LoginController {
	
	@Autowired
	private AutenticacaoService autenticacaoService; 

	@PostMapping
	public ResponseEntity<UsuarioResponseDTO> login(@RequestBody LoginRequestDTO dto) {
		
		UsuarioResponseDTO response = autenticacaoService.autenticarVulneravel(dto.getEmail(), dto.getSenha());
		
		if (response != null) {
			return ResponseEntity.ok(response);
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	}

}
