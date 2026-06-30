package com.app.notaFlow.service;


import com.app.notaFlow.dto.request.AvaliacaoRequestDTO;
import com.app.notaFlow.dto.response.AvaliacaoResponseDTO;
import com.app.notaFlow.dto.update.AvaliacaoUpdateDTO;
import com.app.notaFlow.model.Avaliacao;
import com.app.notaFlow.model.Curso;
import com.app.notaFlow.model.Usuario;
import com.app.notaFlow.repository.AvaliacaoRepository;
import com.app.notaFlow.repository.CursoRepository;
import com.app.notaFlow.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private CursoRepository cursoRepo;

    @Autowired
    private ModelMapper mapper;

    public AvaliacaoResponseDTO lancarNota(AvaliacaoRequestDTO request) {
        Usuario aluno = usuarioRepo.findById(request.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Curso curso = cursoRepo.findById(request.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        Avaliacao avaliacao = mapper.map(request, Avaliacao.class);
        avaliacao.setAluno(aluno);
        avaliacao.setCurso(curso);

        avaliacaoRepo.save(avaliacao);

        return mapper.map(avaliacao, AvaliacaoResponseDTO.class);
    }

    public List<AvaliacaoResponseDTO> exibirTodasAsNotas() {
        List<Avaliacao> avaliacoes = avaliacaoRepo.findAll();
        return avaliacoes.stream()
                .map(av -> mapper.map(av, AvaliacaoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<AvaliacaoResponseDTO> exibirNotasPorAluno(Long alunoId) {
        List<Avaliacao> avaliacoes = avaliacaoRepo.findByAlunoId(alunoId);
        return avaliacoes.stream()
                .map(av -> mapper.map(av, AvaliacaoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public AvaliacaoResponseDTO atualizarNota(Long id, AvaliacaoRequestDTO request) {
        Avaliacao avaliacaoExistente = avaliacaoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        avaliacaoExistente.setTituloAvaliacao(request.getTituloAvaliacao());
        avaliacaoExistente.setDescricao(request.getDescricao());
        avaliacaoExistente.setValor(request.getValor());

        if (request.getAlunoId() != null) {
            Usuario aluno = usuarioRepo.findById(request.getAlunoId())
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            avaliacaoExistente.setAluno(aluno);
        }
        
        if (request.getCursoId() != null) {
            Curso curso = cursoRepo.findById(request.getCursoId())
                    .orElseThrow(() -> new RuntimeException("Curso não encontrado"));
            avaliacaoExistente.setCurso(curso);
        }

        avaliacaoRepo.save(avaliacaoExistente);

        return mapper.map(avaliacaoExistente, AvaliacaoResponseDTO.class);
    }
    
    public AvaliacaoResponseDTO atualizarNota(Long id, AvaliacaoUpdateDTO request) {
        
        // Busca a avaliação base que será atualizada
        Avaliacao avaliacaoExistente = avaliacaoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação com o ID " + id + " não encontrada."));

        // Atualização dos campos de texto e valor numérico direto com ifPresent
        request.getTituloAvaliacao().ifPresent(avaliacaoExistente::setTituloAvaliacao);
        request.getDescricao().ifPresent(avaliacaoExistente::setDescricao);
        request.getValor().ifPresent(avaliacaoExistente::setValor);

        // Atualização de relacionamentos encadeando as buscas caso o id esteja presente
        request.getAlunoId().ifPresent(alunoId -> {
            Usuario aluno = usuarioRepo.findById(alunoId)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado para o ID informado."));
            avaliacaoExistente.setAluno(aluno);
        });

        request.getCursoId().ifPresent(cursoId -> {
            Curso curso = cursoRepo.findById(cursoId)
                    .orElseThrow(() -> new RuntimeException("Curso não encontrado para o ID informado."));
            avaliacaoExistente.setCurso(curso);
        });

        // Salva o registro final atualizado
        avaliacaoRepo.save(avaliacaoExistente);
        return mapper.map(avaliacaoExistente, AvaliacaoResponseDTO.class);
    }
    
}