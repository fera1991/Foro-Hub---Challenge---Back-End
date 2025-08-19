package com.example.ForoHub.domain.topico;

import com.example.ForoHub.domain.curso.Curso;
import com.example.ForoHub.domain.respuesta.Respuesta;
import com.example.ForoHub.domain.usuario.Usuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Generated;

@Table(
        name = "topicos"
)
@Entity(
        name = "Topico"
)
public class Topico {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String titulo;
    private String mensaje;
    @Column(
            name = "fecha_creacion"
    )
    private LocalDateTime fechaCreacion;
    private String estado;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "autor_id"
    )
    private Usuario autor;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "curso_id"
    )
    private Curso curso;
    @OneToMany(
            mappedBy = "topico",
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    private List<Respuesta> respuestas;

    public Topico(@Valid DatosRegistrarTopico datos, Usuario usuario, Curso curso) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.autor = usuario;
        this.estado = "ACTIVO";
        this.curso = curso;
        this.fechaCreacion = LocalDateTime.now();
    }

    @Generated
    public Long getId() {
        return this.id;
    }

    @Generated
    public String getTitulo() {
        return this.titulo;
    }

    @Generated
    public String getMensaje() {
        return this.mensaje;
    }

    @Generated
    public LocalDateTime getFechaCreacion() {
        return this.fechaCreacion;
    }

    @Generated
    public String getEstado() {
        return this.estado;
    }

    @Generated
    public Usuario getAutor() {
        return this.autor;
    }

    @Generated
    public Curso getCurso() {
        return this.curso;
    }

    @Generated
    public List<Respuesta> getRespuestas() {
        return this.respuestas;
    }

    @Generated
    public Topico() {
    }

    @Generated
    public Topico(final Long id, final String titulo, final String mensaje, final LocalDateTime fechaCreacion, final String estado, final Usuario autor, final Curso curso, final List<Respuesta> respuestas) {
        this.id = id;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.autor = autor;
        this.curso = curso;
        this.respuestas = respuestas;
    }

    @Generated
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Topico)) {
            return false;
        } else {
            Topico other = (Topico)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$id = this.getId();
                Object other$id = other.getId();
                if (this$id == null) {
                    if (other$id != null) {
                        return false;
                    }
                } else if (!this$id.equals(other$id)) {
                    return false;
                }

                return true;
            }
        }
    }

    @Generated
    protected boolean canEqual(final Object other) {
        return other instanceof Topico;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        return result;
    }
}