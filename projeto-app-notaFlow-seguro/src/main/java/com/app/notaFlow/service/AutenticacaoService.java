package com.app.notaFlow.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.app.notaFlow.dto.response.UsuarioResponseDTO;
import com.app.notaFlow.model.Usuario;
import com.app.notaFlow.repository.UsuarioRepository;
import com.app.notaFlow.utils.CookieUtils;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class AutenticacaoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper mapper;

    public UsuarioResponseDTO autenticarEGerarCookie(String email, String senha, HttpServletResponse response) {
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, senha)
        );

        String jwt = tokenService.gerarToken(authentication);

        Usuario usuario = usuarioRepository.findByEmailComCurso(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado ou sem curso vinculado"));

        CookieUtils.adicionarCookiesAutenticacao(response, jwt, usuario);

        return mapper.map(usuario, UsuarioResponseDTO.class);
    }
}