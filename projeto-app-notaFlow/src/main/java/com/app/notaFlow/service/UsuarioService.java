package com.app.notaFlow.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.notaFlow.dto.request.UsuarioRequestDTO;
import com.app.notaFlow.dto.response.UsuarioResponseDTO;
import com.app.notaFlow.model.Role;
import com.app.notaFlow.model.Usuario;
import com.app.notaFlow.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO request) {
		Usuario usuario = mapper.map(request, Usuario.class);
		
		// Garante a role padrão no registro
		usuario.getRoles().add(Role.ROLE_USER);
		usuarioRepo.save(usuario);
		
		// Mapeia o básico e depois converte as roles manualmente
		UsuarioResponseDTO response = mapper.map(usuario, UsuarioResponseDTO.class);
		response.setRole(converterRolesParaString(usuario.getRoles()));
		
		return response;
	}
	
	public UsuarioResponseDTO roleAluno(Long id) {
		Usuario usuario = usuarioRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		usuario.getRoles().add(Role.ROLE_ALUNO);
		usuarioRepo.save(usuario);
		
		UsuarioResponseDTO response = mapper.map(usuario, UsuarioResponseDTO.class);
		response.setRole(converterRolesParaString(usuario.getRoles()));
		
		return response;	
	}
	
	public UsuarioResponseDTO roleProfessor(Long id) {
		Usuario usuario = usuarioRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		usuario.getRoles().add(Role.ROLE_PROFESSOR);
		usuarioRepo.save(usuario);
		
		UsuarioResponseDTO response = mapper.map(usuario, UsuarioResponseDTO.class);
		response.setRole(converterRolesParaString(usuario.getRoles()));
		
		return response;	
	}
	
	public UsuarioResponseDTO buscarPorId(Long id) {
		Usuario usuario = usuarioRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		UsuarioResponseDTO response = mapper.map(usuario, UsuarioResponseDTO.class);
		response.setRole(converterRolesParaString(usuario.getRoles()));
		
		return response;
	}

	// Método auxiliar privado para fazer a conversão limpa de Enum para String
	private List<String> converterRolesParaString(List<Role> roles) {
		if (roles == null) {
			return List.of();
		}
		return roles.stream()
				.map(Role::name)
				.collect(Collectors.toList());
	}
	
	public List<UsuarioResponseDTO> listarAlunosPorCurso(Long cursoId) {
        return usuarioRepo.findByCursoIdAndRolesContaining(cursoId, Role.ROLE_ALUNO).stream()
                .map(usuario -> mapper.map(usuario, UsuarioResponseDTO.class))
                .collect(Collectors.toList());
    }
	
}