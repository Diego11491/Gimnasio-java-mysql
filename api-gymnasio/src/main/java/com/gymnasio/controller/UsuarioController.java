package com.gymnasio.controller;

import com.gymnasio.dto.UsuarioRequest;
import com.gymnasio.dto.UsuarioResponse;
import com.gymnasio.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  private final UsuarioService service;

  public UsuarioController(UsuarioService service) {
    this.service = service;
  }

  @Operation(summary = "Listar usuarios")
  @GetMapping
  public List<UsuarioResponse> listar() {
    return service.listar();
  }

  @Operation(summary = "Obtener usuario por id")
  @GetMapping("/{id}")
  public UsuarioResponse obtener(@PathVariable Integer id) {
    return service.obtener(id);
  }

  @Operation(summary = "Crear usuario")
  @PostMapping
  public ResponseEntity<UsuarioResponse> crear(@Valid @RequestBody UsuarioRequest request) {
    UsuarioResponse creado = service.crear(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(creado);
  }

  @Operation(summary = "Actualizar usuario")
  @PutMapping("/{id}")
  public UsuarioResponse actualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioRequest request) {
    return service.actualizar(id, request);
  }

  @Operation(summary = "Eliminar usuario")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
    service.eliminar(id);
    return ResponseEntity.noContent().build();
  }
}
