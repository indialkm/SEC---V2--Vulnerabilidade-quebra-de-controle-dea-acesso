package com.app.notaFlow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.notaFlow.model.Role;
import com.app.notaFlow.model.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{
	Optional<Usuario> findByEmail(String email);
	List<Usuario> findByCursoIdAndRolesContaining(Long cursoId, Role role);
	
	@Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.curso WHERE u.email = :email")
    Optional<Usuario> findByEmailComCurso(@Param("email") String email);

}
