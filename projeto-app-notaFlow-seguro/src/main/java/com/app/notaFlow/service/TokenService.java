package com.app.notaFlow.service;

import com.app.notaFlow.config.RsaKeyProperties;
import com.app.notaFlow.model.UsuarioPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Autowired
    private RsaKeyProperties rsaKeys;

    private final long EXPIRATION_TIME = 7200000; 

    public String gerarToken(Authentication authentication) {
        UsuarioPrincipal principal = (UsuarioPrincipal) authentication.getPrincipal();
        
        List<String> roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Date agora = new Date();
        Date dataExpiracao = new Date(agora.getTime() + EXPIRATION_TIME);


        return Jwts.builder()
                .subject(principal.getUsername())
                .claim("roles", roles)
                .issuedAt(agora)
                .expiration(dataExpiracao)
                .signWith(rsaKeys.getPrivateKey(), Jwts.SIG.RS256)
                .compact();
    } 

    public String extrairUsername(String token) {
        return extrairTodosClaims(token).getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> extrairRoles(String token) {
        return extrairTodosClaims(token).get("roles", List.class);
    }

    public boolean isTokenValido(String token) {
        try {
            Date expiration = extrairTodosClaims(token).getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            return false; 
        }
    }

    // Este método é privado e separado
    private Claims extrairTodosClaims(String token) {
        return Jwts.parser()
                .verifyWith(rsaKeys.getPublicKey()) 
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}