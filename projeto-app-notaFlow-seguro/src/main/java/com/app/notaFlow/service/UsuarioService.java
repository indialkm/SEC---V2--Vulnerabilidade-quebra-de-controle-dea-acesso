package com.app.notaFlow.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.notaFlow.dto.request.UsuarioRequestDTO;
import com.app.notaFlow.dto.response.UsuarioInfoResponseDTO;
import com.app.notaFlow.dto.response.UsuarioResponseDTO;
import com.app.notaFlow.dto.update.UsuarioUpdateDTO;
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
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper mapper;
	
	public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO request) {
        Usuario usuario = mapper.map(request, Usuario.class);
        
        String senhaCriptografada = passwordEncoder.encode(request.getSenha());
        usuario.setSenha(senhaCriptografada);
    
        usuario.getRoles().add(Role.ROLE_USER);
        usuarioRepo.save(usuario);
        
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
	
	public UsuarioResponseDTO atualizarSenha(Long id, UsuarioUpdateDTO dto, String emailAutenticado) {
        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
 
        if (!usuario.getEmail().equals(emailAutenticado)) {
            throw new RuntimeException("Acesso negado: você não pode alterar a senha de outro usuário");
        }
 
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuarioRepo.save(usuario);
 
        UsuarioResponseDTO response = mapper.map(usuario, UsuarioResponseDTO.class);
        response.setRole(converterRolesParaString(usuario.getRoles()));
 
        return response;
    }
	
	public UsuarioInfoResponseDTO obterInformacoesUsuario(String email) {
        Usuario usuario = usuarioRepo.findByEmailComCurso(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado ou sem curso vinculado"));

        // Mapeamos manualmente ou via ModelMapper para o seu DTO
        UsuarioInfoResponseDTO dto = new UsuarioInfoResponseDTO();
        dto.setNomeUsuario(usuario.getNome());
        dto.setIdUsuario(usuario.getId());
        dto.setRoles(
        	    usuario.getRoles().stream()
        	        .map(Role::name)
        	        .collect(Collectors.toList())
        	);
        
        if (usuario.getCurso() != null) {
            dto.setNomeCurso(usuario.getCurso().getNomeCurso());
            dto.setIdCurso(usuario.getCurso().getId());
        }
        
        return dto;
    }

}