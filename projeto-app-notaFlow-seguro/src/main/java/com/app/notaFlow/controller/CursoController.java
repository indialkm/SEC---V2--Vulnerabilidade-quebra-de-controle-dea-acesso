package com.app.notaFlow.controller;

import com.app.notaFlow.dto.request.CursoRequestDTO;
import com.app.notaFlow.dto.response.CursoResponseDTO;
import com.app.notaFlow.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PreAuthorize("hasAuthority('ROLE_PROFESSOR')")
    @PostMapping
    public ResponseEntity<CursoResponseDTO> registrarCurso(@Valid @RequestBody CursoRequestDTO request) {
        CursoResponseDTO response = cursoService.registrarCurso(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAuthority('ROLE_PROFESSOR') or hasAuthority('ROLE_ALUNO')")
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> buscarPorId(@PathVariable Long id) {
        CursoResponseDTO response = cursoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }
}