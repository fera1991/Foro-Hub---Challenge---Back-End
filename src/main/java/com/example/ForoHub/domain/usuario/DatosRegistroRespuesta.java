package com.example.ForoHub.domain.usuario;

public record DatosRegistroRespuesta(
        Long id,
        String nombre,
        String email
) {
    public DatosRegistroRespuesta(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }
}