package com.example.ForoHub.domain.curso;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Generated;

@Table(
        name = "cursos"
)
@Entity(
        name = "Curso"
)
public class Curso {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String nombre;
    private String categoria;

    @Generated
    public Long getId() {
        return this.id;
    }

    @Generated
    public String getNombre() {
        return this.nombre;
    }

    @Generated
    public String getCategoria() {
        return this.categoria;
    }

    @Generated
    public Curso() {
    }

    @Generated
    public Curso(final Long id, final String nombre, final String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    @Generated
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Curso)) {
            return false;
        } else {
            Curso other = (Curso)o;
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
        return other instanceof Curso;
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
