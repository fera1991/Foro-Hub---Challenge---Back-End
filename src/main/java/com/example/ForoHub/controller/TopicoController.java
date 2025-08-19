package com.example.ForoHub.controller;

import com.example.ForoHub.domain.curso.Curso;
import com.example.ForoHub.domain.curso.CursoRepository;
import com.example.ForoHub.domain.topico.DatosRegistrarTopico;
import com.example.ForoHub.domain.topico.DatosTopicos;
import com.example.ForoHub.domain.topico.Topico;
import com.example.ForoHub.domain.topico.TopicoRepository;
import com.example.ForoHub.domain.usuario.Usuario;
import com.example.ForoHub.infra.exceptions.ConflicDataException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping({"/topicos"})
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity registrarTopico(@RequestBody @Valid DatosRegistrarTopico datos, UriComponentsBuilder uriComponentsBuilder) {
        if (this.topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new ConflicDataException("Ya existe un tópico con el mismo título y mensaje");
        } else {
            Usuario usuario = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Curso> curso = this.cursoRepository.findById(datos.curso());
            if (curso.isEmpty()) {
                throw new EntityNotFoundException("Curso no encontrado");
            } else {
                Topico topico = new Topico(datos, usuario, (Curso)curso.get());
                this.topicoRepository.save(topico);
                URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(new Object[]{topico.getId()}).toUri();
                return ResponseEntity.created(uri).body(new DatosTopicos(topico));
            }
        }
    }

    @GetMapping
    public ResponseEntity<Page<DatosTopicos>> mostrarTopicos(@PageableDefault(page = 0,size = 10,sort = {"fechaCreacion"}) Pageable paginacion) {
        Page<DatosTopicos> page = this.topicoRepository.findAll(paginacion).map(DatosTopicos::new);
        return ResponseEntity.ok(page);
    }

    @Transactional
    @DeleteMapping({"/{id}"})
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Optional<Topico> topico = this.topicoRepository.findById(id);
        if (topico.isEmpty()) {
            throw new EntityNotFoundException("Tópico no encontrado");
        } else {
            this.topicoRepository.deleteById(((Topico)topico.get()).getId());
            return ResponseEntity.ok().body("Topico eliminado exitosamente");
        }
    }

    @GetMapping({"/{id}"})
    public ResponseEntity mostrarTopico(@PathVariable Long id) {
        Optional<Topico> topico = this.topicoRepository.findById(id);
        if (topico.isEmpty()) {
            throw new EntityNotFoundException("Tópico no encontrado");
        } else {
            return ResponseEntity.ok(new DatosTopicos((Topico)topico.get()));
        }
    }
}
