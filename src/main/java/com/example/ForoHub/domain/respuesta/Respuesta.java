package com.example.ForoHub.domain.respuesta;

import com.example.ForoHub.domain.topico.Topico;
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
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Generated;

@Table(
        name = "respuestas"
)
@Entity(
        name = "Respuesta"
)
public class Respuesta {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String mensaje;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "topico_id"
    )
    private Topico topico;
    @Column(
            name = "fecha_creacion"
    )
    private LocalDateTime fechaCreacion;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(
            name = "autor_id"
    )
    private Usuario autor;

    @Generated
    public Long getId() {
        return this.id;
    }

    @Generated
    public String getMensaje() {
        return this.mensaje;
    }

    @Generated
    public Topico getTopico() {
        return this.topico;
    }

    @Generated
    public LocalDateTime getFechaCreacion() {
        return this.fechaCreacion;
    }

    @Generated
    public Usuario getAutor() {
        return this.autor;
    }

    @Generated
    public Respuesta() {
    }

    @Generated
    public Respuesta(final Long id, final String mensaje, final Topico topico, final LocalDateTime fechaCreacion, final Usuario autor) {
        this.id = id;
        this.mensaje = mensaje;
        this.topico = topico;
        this.fechaCreacion = fechaCreacion;
        this.autor = autor;
    }

    @Generated
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Respuesta)) {
            return false;
        } else {
            Respuesta other = (Respuesta)o;
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
        return other instanceof Respuesta;
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
