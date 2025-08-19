package com.example.ForoHub.controller;

import com.example.ForoHub.domain.topico.DatosTopicos;
import com.example.ForoHub.domain.topico.TopicoRepository;
import com.example.ForoHub.domain.usuario.DatosRegistroRespuesta;
import com.example.ForoHub.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/usuario"})
public class UserController {
    @Autowired
    TopicoRepository topicoRepository;

    @GetMapping({"/token"})
    public ResponseEntity obtenerDatosToken() {
        Usuario usuario = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DatosRegistroRespuesta resultadoUsuario = new DatosRegistroRespuesta(usuario);
        return ResponseEntity.ok(resultadoUsuario);
    }

    @GetMapping({"/topicos"})
    public ResponseEntity<Page<DatosTopicos>> obtenerTopicos(@PageableDefault(page = 0,size = 10,sort = {"fechaCreacion"}) Pageable paginacion) {
        Usuario usuario = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<DatosTopicos> page = this.topicoRepository.findAllByAutor_Id(usuario.getId(), paginacion).map(DatosTopicos::new);
        return ResponseEntity.ok(page);
    }
}