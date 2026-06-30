package com.app.notaFlow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.notaFlow.model.Role;
import com.app.notaFlow.model.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{
	Usuario findByEmail(String email);
	List<Usuario> findByCursoIdAndRolesContaining(Long cursoId, Role role);

}
