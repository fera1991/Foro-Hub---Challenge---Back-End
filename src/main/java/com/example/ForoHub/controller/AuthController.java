package com.example.ForoHub.controller;

import com.example.ForoHub.domain.usuario.*;
import com.example.ForoHub.infra.exceptions.ConflicDataException;
import com.example.ForoHub.infra.security.DatosTokenJWT;
import com.example.ForoHub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

    @Transactional
    @PostMapping("/register")
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroUsuario usuario){

        if (userRepository.findByNombre(usuario.nombre()).isPresent()) {
            throw new ConflicDataException("El nombre ya está en uso");
        }
        if (userRepository.findByEmail(usuario.email()).isPresent()) {
            throw new ConflicDataException("El email ya está en uso");
        }

        Usuario nuevoUsuario = new Usuario(usuario, passwordEncoder.encode(usuario.contrasena()));
        Usuario savedUsuario = userRepository.save(nuevoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new DatosRegistroRespuesta(
                        savedUsuario.getId(),
                        savedUsuario.getNombre(),
                        savedUsuario.getEmail()
                ));

    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid DatosLogin datos) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(datos.login(), datos.contrasena());
        var autenticacion = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.generarToken((Usuario) autenticacion.getPrincipal());
        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }

}