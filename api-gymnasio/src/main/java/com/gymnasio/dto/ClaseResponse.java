package com.gymnasio.dto;

import com.gymnasio.domain.model.EstadoClase;

import java.time.LocalDateTime;

public record ClaseResponse(
        Integer id,
        String titulo,
        String descripcion,
        String tipo,
        Integer instructorId,
        String instructorNombre,
        LocalDateTime fechaInicio,
        Integer duracionMinutos,
        Integer capacidad,
        EstadoClase estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion) {
}
