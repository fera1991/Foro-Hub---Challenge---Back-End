package com.example.ForoHub.infra.security;

import com.example.ForoHub.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.filter.OncePerRequestFilter;

@RestControllerAdvice
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private TokenService tokenService;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = this.recuperarToken(request);
        if (tokenJWT != null) {
            String subject = this.tokenService.getSubject(tokenJWT);
            UserDetails usuario = this.repository.findUserDetailsByNombre(subject);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, (Object)null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return authorizationHeader != null ? authorizationHeader.replace("Bearer ", "") : null;
    }
}
