package com.app.notaFlow.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.notaFlow.dto.response.UsuarioResponseDTO;
import com.app.notaFlow.model.Role;
import com.app.notaFlow.model.Usuario;
import com.app.notaFlow.repository.UsuarioRepository;

@Service
public class AutenticacaoService {
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ModelMapper mapper;

	public UsuarioResponseDTO autenticarVulneravel(String email, String senha) {
	       
        Usuario usuario = usuarioRepository.findByEmail(email);
        
        if (usuario != null && usuario.getSenha().equals(senha)) {
            
           
            UsuarioResponseDTO response = mapper.map(usuario, UsuarioResponseDTO.class);
            
            response.setRole(converterRolesParaString(usuario.getRoles()));
            
            return response;
        }
        
        return null;
    }
    
    private List<String> converterRolesParaString(List<Role> roles) {
		if (roles == null) {
			return List.of();
		}
		return roles.stream()
				.map(Role::name) 
				.collect(Collectors.toList());
	}
    


}
