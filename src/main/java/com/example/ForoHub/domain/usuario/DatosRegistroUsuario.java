package com.example.ForoHub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record DatosRegistroUsuario(
        @NotBlank(message = "El Nombre es obligatorio") String nombre,
        @NotBlank(message = "El email es obligatorio") @Email  String email,
        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String contrasena
) {
}
