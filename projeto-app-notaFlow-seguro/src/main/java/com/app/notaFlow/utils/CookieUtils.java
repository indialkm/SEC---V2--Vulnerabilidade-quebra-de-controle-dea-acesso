package com.app.notaFlow.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

import org.springframework.http.ResponseCookie;

import com.app.notaFlow.model.Usuario;

public class CookieUtils {

    private static final String COOKIE_NAME = "AUTH_TOKEN";
    private static final int COOKIE_MAX_AGE_SECONDS = 7200; 

    
    public static void adicionarCookiesAutenticacao(HttpServletResponse response, String token, Usuario usuario) {
        
        addCookie(response, "AUTH_TOKEN", token, true);
  
    }

    private static void addCookie(HttpServletResponse response, String name, String value, boolean httpOnly) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .httpOnly(httpOnly)
                .secure(false) 
                .path("/")
                .maxAge(7200)
                .sameSite("Lax") 
                .build();
        
        response.addHeader("Set-Cookie", cookie.toString());
    }

    public static Optional<String> extrairTokenJwt(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    return Optional.of(cookie.getValue());
                }
            }
        }
        return Optional.empty();
    }

    
    public static void limparCookiesAutenticacao(HttpServletResponse response) {
        String[] cookiesParaLimpar = {COOKIE_NAME, "USER_ID", "USER_NAME"};
        
        for (String nomeCookie : cookiesParaLimpar) {
            Cookie cookie = new Cookie(nomeCookie, null);
            cookie.setPath("/");
            cookie.setMaxAge(0); 
            response.addCookie(cookie);
        }
    }
}