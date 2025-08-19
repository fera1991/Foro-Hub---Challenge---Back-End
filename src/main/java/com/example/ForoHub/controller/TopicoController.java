package com.example.ForoHub.controller;

import com.example.ForoHub.domain.curso.Curso;
import com.example.ForoHub.domain.curso.CursoRepository;
import com.example.ForoHub.domain.topico.*;
import com.example.ForoHub.domain.usuario.Usuario;
import com.example.ForoHub.infra.exceptions.ConflicDataException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;


    @PostMapping
    public ResponseEntity registrarTopico(@RequestBody @Valid DatosRegistrarTopico datos, UriComponentsBuilder uriComponentsBuilder) {

        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new ConflicDataException("Ya existe un tópico con el mismo título y mensaje");
        }

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Curso> curso = cursoRepository.findById(datos.curso());

        if (curso.isEmpty()) {
            throw new EntityNotFoundException("Curso no encontrado");
        }

        var topico = new Topico(datos, usuario, curso.get());
        topicoRepository.save(topico);

        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosTopicos(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosTopicos>> mostrarTopicos(@PageableDefault(page = 0, size = 10, sort = {"fechaCreacion"}) Pageable paginacion) {
        var page = topicoRepository.findAll(paginacion).map(DatosTopicos::new);
        return ResponseEntity.ok(page);
    }

    @Transactional
    @PutMapping({"/{id}"})
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizacionTopico datos) {
        Optional<Topico> topico = this.topicoRepository.findById(id);
        if (topico.isEmpty()) {
            throw new EntityNotFoundException("Tópico no encontrado");
        }
        boolean tituloOmensajeCambiaron = !(topico.get()).getTitulo().equals(datos.titulo()) || !(topico.get()).getMensaje().equals(datos.mensaje());
        if (tituloOmensajeCambiaron && this.topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new ConflicDataException("Ya existe un tópico con el mismo título y mensaje");
        }
        Optional<Curso> curso = this.cursoRepository.findById(datos.curso());
        if (curso.isEmpty()) {
            throw new EntityNotFoundException("Curso no encontrado");
        }
        topico.get().actualizarInformaciones(datos, curso.get());
        return ResponseEntity.ok(new DatosTopicos(topico.get()));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        var topico = topicoRepository.findById(id);

        if (topico.isEmpty()) {
            throw new EntityNotFoundException("Tópico no encontrado");
        }

        topicoRepository.deleteById(topico.get().getId());

        return ResponseEntity.ok().body("Topico eliminado exitosamente");
    }

    @GetMapping("/{id}")
    public ResponseEntity mostrarTopico(@PathVariable Long id) {
        var topico = topicoRepository.findById(id);

        if (topico.isEmpty()) {
            throw new EntityNotFoundException("Tópico no encontrado");
        }

        return ResponseEntity.ok(new DatosTopicos(topico.get()));
    }


}
