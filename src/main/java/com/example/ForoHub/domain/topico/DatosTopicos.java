package com.example.ForoHub.domain.topico;

import com.example.ForoHub.domain.curso.Curso;
import java.time.LocalDateTime;

public record DatosTopicos(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, String estado, String autor, Curso curso) {
    public DatosTopicos(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getEstado(), topico.getAutor().getNombre(), topico.getCurso());
    }
}