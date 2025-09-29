package com.gymnasio.dto;

import com.gymnasio.domain.model.EstadoClase;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record ClaseRequest(
        @NotBlank @Size(max = 100) String titulo,
        String descripcion,
        @Size(max = 50) String tipo,
        @NotNull Long instructorId,
        LocalDateTime fechaInicio,
        Integer duracionMinutos,
        @NotNull @Min(1) Integer capacidad,
        EstadoClase estado) {
}
