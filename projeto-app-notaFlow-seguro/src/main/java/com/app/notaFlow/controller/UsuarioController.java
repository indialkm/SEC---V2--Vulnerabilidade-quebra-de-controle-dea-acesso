package com.app.notaFlow.controller;

import com.app.notaFlow.dto.request.UsuarioRequestDTO;
import com.app.notaFlow.dto.response.UsuarioInfoResponseDTO;
import com.app.notaFlow.dto.response.UsuarioResponseDTO;
import com.app.notaFlow.dto.update.UsuarioUpdateDTO;
import com.app.notaFlow.service.UsuarioService;
import org.springframework.security.core.Authentication;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PermitAll
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(@Valid @RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO response = usuarioService.registrarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAuthority('ROLE_PROFESSOR')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        UsuarioResponseDTO response = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }
    
    @PreAuthorize("hasAuthority('ROLE_PROFESSOR')")
    @PatchMapping("/aluno/{id}")
    public ResponseEntity<UsuarioResponseDTO> concederRoleAluno(@PathVariable Long id) {
        UsuarioResponseDTO response = usuarioService.roleAluno(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('ROLE_PROFESSOR')")
    @PatchMapping("/professor/{id}")
    public ResponseEntity<UsuarioResponseDTO> concederRoleProfessor(@PathVariable Long id) {
        UsuarioResponseDTO response = usuarioService.roleProfessor(id);
        return ResponseEntity.ok(response);
    }
    
    @PreAuthorize("hasAuthority('ROLE_PROFESSOR')")
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<UsuarioResponseDTO>> listarAlunos(@PathVariable Long cursoId) {
        return ResponseEntity.ok(usuarioService.listarAlunosPorCurso(cursoId));
    }
    
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PatchMapping("/{id}/senha")
    public ResponseEntity<UsuarioResponseDTO> atualizarSenha(
        @PathVariable Long id,
        @Valid @RequestBody UsuarioUpdateDTO dto,
        Authentication authentication) {

    String emailAutenticado = authentication.getName();
    UsuarioResponseDTO response = usuarioService.atualizarSenha(id, dto, emailAutenticado);
    return ResponseEntity.ok(response);
    }
    
    @GetMapping("/me")
    public ResponseEntity<UsuarioInfoResponseDTO> getInfoUsuario(Authentication authentication) {
 
        String email = authentication.getName();
        
        UsuarioInfoResponseDTO info = usuarioService.obterInformacoesUsuario(email);
        
        return ResponseEntity.ok(info);
    }
}