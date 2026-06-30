package com.app.notaFlow.filter;

import com.app.notaFlow.service.TokenService;
import com.app.notaFlow.utils.CookieUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtCookieAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        

    	Optional<String> tokenOptional = CookieUtils.extrairTokenJwt(request);

    	System.out.println(">>> TOKEN PRESENTE: " + tokenOptional.isPresent());

    	if (tokenOptional.isPresent()) {
    	    String token = tokenOptional.get();
    	    System.out.println(">>> TOKEN VALIDO: " + tokenService.isTokenValido(token));

    	    if (tokenService.isTokenValido(token)) {
    	        String username = tokenService.extrairUsername(token);
    	        List<String> roles = tokenService.extrairRoles(token);
    	        
    	        System.out.println(">>> USERNAME: " + username);
    	        System.out.println(">>> ROLES: " + roles);
    	        
    	        List<SimpleGrantedAuthority> authorities = roles.stream()
    	                .map(SimpleGrantedAuthority::new)
    	                .collect(Collectors.toList());
    	        
    	        UsernamePasswordAuthenticationToken authentication = 
    	                new UsernamePasswordAuthenticationToken(username, null, authorities);
    	        
    	        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    	        
    	        SecurityContextHolder.getContext().setAuthentication(authentication);
    	    }
    	}
        
        filterChain.doFilter(request, response);
    }
}