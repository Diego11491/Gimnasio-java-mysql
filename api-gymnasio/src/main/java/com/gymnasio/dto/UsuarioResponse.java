package com.gymnasio.dto;

import com.gymnasio.domain.model.EstadoUsuario;
import com.gymnasio.domain.model.Rol;

import java.time.LocalDateTime;

public record UsuarioResponse(
    Integer id,
    String nombres,
    String apellidos,
    String correo,
    String telefono,
    Rol rol,
    EstadoUsuario estado,
    LocalDateTime fechaCreacion,
    LocalDateTime fechaActualizacion
) {}
