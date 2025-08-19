package com.example.ForoHub.domain.usuario;

import com.example.ForoHub.domain.topico.Topico;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import lombok.Generated;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table(
        name = "usuarios"
)
@Entity(
        name = "Usuario"
)
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String nombre;
    private String email;
    private String contrasena;
    @OneToMany(
            mappedBy = "autor"
    )
    @JsonIgnore
    private List<Topico> topicos;

    public Usuario(DatosRegistroUsuario usuario, String claveHash) {
        this.nombre = usuario.nombre();
        this.email = usuario.email();
        this.contrasena = claveHash;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public String getPassword() {
        return this.contrasena;
    }

    public String getUsername() {
        return this.nombre;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    @Generated
    public Long getId() {
        return this.id;
    }

    @Generated
    public String getNombre() {
        return this.nombre;
    }

    @Generated
    public String getEmail() {
        return this.email;
    }

    @Generated
    public String getContrasena() {
        return this.contrasena;
    }

    @Generated
    public List<Topico> getTopicos() {
        return this.topicos;
    }

    @Generated
    public Usuario() {
    }

    @Generated
    public Usuario(final Long id, final String nombre, final String email, final String contrasena, final List<Topico> topicos) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.topicos = topicos;
    }

    @Generated
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Usuario)) {
            return false;
        } else {
            Usuario other = (Usuario)o;
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
        return other instanceof Usuario;
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
