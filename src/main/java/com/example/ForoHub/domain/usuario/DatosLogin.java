package com.example.ForoHub.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DatosLogin(
        @NotBlank
        String login,
        @NotBlank
        String contrasena) {
}