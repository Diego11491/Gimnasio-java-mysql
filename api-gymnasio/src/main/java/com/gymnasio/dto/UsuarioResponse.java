package com.gymnasio.dto;

import com.gymnasio.domain.model.EstadoUsuario;
import com.gymnasio.domain.model.Rol;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record UsuarioResponse(
        Integer id,
        String nombres,
        String apellidos,
        String correo,
        String telefono,
        Rol rol,
        EstadoUsuario estado,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Lima") LocalDateTime fechaCreacion,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Lima") LocalDateTime fechaActualizacion) {
}
