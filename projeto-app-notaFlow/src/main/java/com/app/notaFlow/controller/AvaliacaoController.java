package com.app.notaFlow.controller;

import com.app.notaFlow.dto.request.AvaliacaoRequestDTO;
import com.app.notaFlow.dto.response.AvaliacaoResponseDTO;
import com.app.notaFlow.dto.update.AvaliacaoUpdateDTO;
import com.app.notaFlow.service.AvaliacaoService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/avaliacoes")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH})
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping
    public ResponseEntity<AvaliacaoResponseDTO> lancarNota(@Valid @RequestBody AvaliacaoRequestDTO request) {
        AvaliacaoResponseDTO response = avaliacaoService.lancarNota(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoResponseDTO>> exibirTodasAsNotas() {
        List<AvaliacaoResponseDTO> response = avaliacaoService.exibirTodasAsNotas();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> exibirNotasPorAluno(@PathVariable Long alunoId) {
        List<AvaliacaoResponseDTO> response = avaliacaoService.exibirNotasPorAluno(alunoId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> atualizarNota(
            @PathVariable Long id, 
            @Valid @RequestBody AvaliacaoUpdateDTO request) {
        
        AvaliacaoResponseDTO response = avaliacaoService.atualizarNota(id, request);
        return ResponseEntity.ok(response);
    }
}