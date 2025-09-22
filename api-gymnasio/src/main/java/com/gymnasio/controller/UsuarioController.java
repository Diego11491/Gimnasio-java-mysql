package com.gymnasio.controller;

import com.gymnasio.dto.UsuarioRequest;
import com.gymnasio.dto.UsuarioResponse;
import com.gymnasio.service.UsuarioService;
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

  @GetMapping
  public List<UsuarioResponse> listar() { return service.listar(); }

  @GetMapping("/{id}")
  public UsuarioResponse obtener(@PathVariable Long id) { return service.obtener(id); }

  @PostMapping
  public ResponseEntity<UsuarioResponse> crear(@Valid @RequestBody UsuarioRequest request) {
    UsuarioResponse creado = service.crear(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(creado);
  }

  @PutMapping("/{id}")
  public UsuarioResponse actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequest request) {
    return service.actualizar(id, request);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable Long id) {
    service.eliminar(id);
    return ResponseEntity.noContent().build();
  }
}
