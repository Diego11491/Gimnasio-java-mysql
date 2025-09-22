package com.gymnasio.dto;

import com.gymnasio.domain.model.EstadoUsuario;
import com.gymnasio.domain.model.Rol;
import jakarta.validation.constraints.*;

public record UsuarioRequest(
    @NotBlank @Size(max = 100) String nombres,
    @NotBlank @Size(max = 100) String apellidos,
    @NotBlank @Email @Size(max = 255) String correo,
    @NotBlank @Size(min = 6, max = 60) String contrasena,
    @Size(max = 30) String telefono,
    Rol rol,
    EstadoUsuario estado
) {}
