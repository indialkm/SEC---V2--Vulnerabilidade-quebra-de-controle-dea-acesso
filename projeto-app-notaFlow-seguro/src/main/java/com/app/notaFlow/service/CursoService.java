package com.app.notaFlow.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.notaFlow.dto.request.CursoRequestDTO;
import com.app.notaFlow.dto.response.CursoResponseDTO;
import com.app.notaFlow.model.Curso;
import com.app.notaFlow.repository.CursoRepository;

@Service
public class CursoService {
	
	@Autowired
    private CursoRepository cursoRepo;
    
    @Autowired
    private ModelMapper mapper;
    
    public CursoResponseDTO registrarCurso(CursoRequestDTO request) {
        
        Curso curso = mapper.map(request, Curso.class);
        
        cursoRepo.save(curso);
        
        CursoResponseDTO response = mapper.map(curso, CursoResponseDTO.class);
        return response;
    }
    
    public CursoResponseDTO buscarPorId(Long id) {
       
        Curso curso = cursoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));
        
        return mapper.map(curso, CursoResponseDTO.class);
    }
	
}
