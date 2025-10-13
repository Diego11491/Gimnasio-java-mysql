package com.gymnasio.controller;

import com.gymnasio.domain.model.Rol;
import com.gymnasio.domain.model.EstadoUsuario;
import com.gymnasio.dto.UsuarioRequest;
import com.gymnasio.dto.UsuarioResponse;
import com.gymnasio.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  private final UsuarioService service;

  public UsuarioController(UsuarioService service) {
    this.service = service;
  }

  @Operation(summary = "Listar usuarios (ADMIN/TRAINER)")
  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
  public List<UsuarioResponse> listar() {
    return service.listar();
  }

  @Operation(summary = "Obtener usuario por id (ADMIN/TRAINER)")
  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
  public UsuarioResponse obtener(@PathVariable Integer id) {
    return service.obtener(id);
  }

  @Operation(summary = "Crear usuario (solo ADMIN)")
  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UsuarioResponse> crear(@Valid @RequestBody UsuarioRequest request) {
    UsuarioResponse creado = service.crear(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(creado);
  }

  @Operation(summary = "Actualizar usuario (solo ADMIN)")
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public UsuarioResponse actualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioRequest request) {
    return service.actualizar(id, request);
  }

  @Operation(summary = "Eliminar usuario (solo ADMIN)")
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
    service.eliminar(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Buscar usuarios (ADMIN/TRAINER)")
  @GetMapping("/buscar")
  @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
  public List<UsuarioResponse> buscar(
      @RequestParam(required = false) String q,
      @RequestParam(required = false) Rol rol,
      @RequestParam(required = false) EstadoUsuario estado) {
    return service.buscar(q, rol, estado);
  }
}
