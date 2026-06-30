package com.app.notaFlow.service;

import com.app.notaFlow.model.Usuario;
import com.app.notaFlow.model.UsuarioPrincipal;
import com.app.notaFlow.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
// CERTIFIQUE-SE DE QUE ESSES DOIS IMPORTS ESTÃO EXATAMENTE ASSIM:
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email));

        return new UsuarioPrincipal(usuario);
    }
}