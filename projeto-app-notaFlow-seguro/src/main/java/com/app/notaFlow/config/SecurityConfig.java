package com.app.notaFlow.config;

import com.app.notaFlow.filter.JwtCookieAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {

    @Autowired
    private JwtCookieAuthenticationFilter jwtCookieAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth

                // Auth — público
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers("/", "/index.html", "/dashboard.html",
                        "/api.js", "/routes.js", "/style.css").permitAll()
                .requestMatchers("/notas.html").hasAuthority("ROLE_PROFESSOR")

                // Usuários — cadastro público, resto protegido
                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/usuarios/me").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.GET,   "/api/usuarios/{id}").hasAuthority("ROLE_PROFESSOR")
                .requestMatchers(HttpMethod.GET,   "/api/usuarios/curso/{cursoId}").hasAuthority("ROLE_PROFESSOR")
                .requestMatchers(HttpMethod.PATCH, "/api/usuarios/aluno/{id}").hasAuthority("ROLE_PROFESSOR")
                .requestMatchers(HttpMethod.PATCH, "/api/usuarios/professor/{id}").hasAuthority("ROLE_PROFESSOR")
                .requestMatchers(HttpMethod.PATCH, "/api/usuarios/{is}/senha").hasAuthority("ROLE_USER")
                
                // Cursos
                .requestMatchers(HttpMethod.POST, "/api/cursos").hasAuthority("ROLE_PROFESSOR")
                .requestMatchers(HttpMethod.GET,  "/api/cursos/{id}").hasAnyAuthority("ROLE_PROFESSOR", "ROLE_ALUNO")

                // Avaliações
                .requestMatchers(HttpMethod.POST,  "/api/avaliacoes").hasAuthority("ROLE_PROFESSOR")
                .requestMatchers(HttpMethod.GET,   "/api/avaliacoes").hasAuthority("ROLE_PROFESSOR")
                .requestMatchers(HttpMethod.GET,   "/api/avaliacoes/aluno/{alunoId}").hasAnyAuthority("ROLE_PROFESSOR", "ROLE_ALUNO")
                .requestMatchers(HttpMethod.PATCH, "/api/avaliacoes/{id}").hasAuthority("ROLE_PROFESSOR")

                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtCookieAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://127.0.0.1:5500"));  // sua origem real
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}