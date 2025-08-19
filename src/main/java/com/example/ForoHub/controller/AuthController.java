//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.ForoHub.controller;

import com.example.ForoHub.domain.usuario.DatosLogin;
import com.example.ForoHub.domain.usuario.DatosRegistroRespuesta;
import com.example.ForoHub.domain.usuario.DatosRegistroUsuario;
import com.example.ForoHub.domain.usuario.Usuario;
import com.example.ForoHub.domain.usuario.UsuarioRepository;
import com.example.ForoHub.infra.exceptions.ConflicDataException;
import com.example.ForoHub.infra.security.DatosTokenJWT;
import com.example.ForoHub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/auth"})
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
    @PostMapping({"/register"})
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroUsuario usuario) {
        if (this.userRepository.findByNombre(usuario.nombre()).isPresent()) {
            throw new ConflicDataException("El nombre ya está en uso");
        } else if (this.userRepository.findByEmail(usuario.email()).isPresent()) {
            throw new ConflicDataException("El email ya está en uso");
        } else {
            Usuario nuevoUsuario = new Usuario(usuario, this.passwordEncoder.encode(usuario.contrasena()));
            Usuario savedUsuario = (Usuario)this.userRepository.save(nuevoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(new DatosRegistroRespuesta(savedUsuario.getId(), savedUsuario.getNombre(), savedUsuario.getEmail()));
        }
    }

    @PostMapping({"/login"})
    public ResponseEntity login(@RequestBody @Valid DatosLogin datos) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(datos.login(), datos.contrasena());
        Authentication autenticacion = this.manager.authenticate(authenticationToken);
        String tokenJWT = this.tokenService.generarToken((Usuario)autenticacion.getPrincipal());
        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }
}
