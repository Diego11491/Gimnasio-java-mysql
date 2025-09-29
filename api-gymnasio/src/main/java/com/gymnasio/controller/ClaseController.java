package com.gymnasio.controller;

import com.gymnasio.dto.*;
import com.gymnasio.service.ClaseService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clases")
public class ClaseController {

    private final ClaseService service;

    public ClaseController(ClaseService service) {
        this.service = service;
    }

    @Operation(summary = "Listar clases")
    @GetMapping
    public List<ClaseResponse> listar() {
        return service.listar();
    }

    @Operation(summary = "Obtener clase por id")
    @GetMapping("/{id}")
    public ClaseResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @Operation(summary = "Disponibilidad de cupos (JPQL)")
    @GetMapping("/{id}/disponibilidad")
    public DisponibilidadClaseView disponibilidad(@PathVariable Long id) {
        return service.disponibilidad(id);
    }

    @Operation(summary = "Crear clase (ADMIN/TRAINER)")
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    public ClaseResponse crear(@Valid @RequestBody ClaseRequest request) {
        return service.crear(request);
    }

    @Operation(summary = "Actualizar clase (ADMIN/TRAINER)")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    public ClaseResponse actualizar(@PathVariable Long id, @Valid @RequestBody ClaseRequest request) {
        return service.actualizar(id, request);
    }

    @Operation(summary = "Eliminar clase (ADMIN)")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
