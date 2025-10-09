package com.gymnasio.dto;

import com.gymnasio.domain.model.EstadoReserva;
import java.time.LocalDateTime;

public record ReservaResponse(
    Integer id,
    Integer claseId,
    String tituloClase,
    Integer usuarioId,
    String nombreUsuario,
    EstadoReserva estado,
    LocalDateTime fechaReserva,
    LocalDateTime fechaCreacion,
    LocalDateTime fechaActualizacion
) {}
