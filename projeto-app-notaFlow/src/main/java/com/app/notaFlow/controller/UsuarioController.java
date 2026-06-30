package com.app.notaFlow.controller;

import com.app.notaFlow.dto.request.UsuarioRequestDTO;
import com.app.notaFlow.dto.response.UsuarioResponseDTO;
import com.app.notaFlow.service.UsuarioService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(@Valid @RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO response = usuarioService.registrarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        UsuarioResponseDTO response = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/aluno/{id}")
    public ResponseEntity<UsuarioResponseDTO> concederRoleAluno(@PathVariable Long id) {
        UsuarioResponseDTO response = usuarioService.roleAluno(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/professor/{id}")
    public ResponseEntity<UsuarioResponseDTO> concederRoleProfessor(@PathVariable Long id) {
        UsuarioResponseDTO response = usuarioService.roleProfessor(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<UsuarioResponseDTO>> listarAlunos(@PathVariable Long cursoId) {
        return ResponseEntity.ok(usuarioService.listarAlunosPorCurso(cursoId));
    }
}