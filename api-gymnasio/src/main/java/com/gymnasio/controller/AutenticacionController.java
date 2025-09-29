package com.gymnasio.controller;

import com.gymnasio.dto.*;
import com.gymnasio.repository.UsuarioRepository;
import com.gymnasio.security.JwtTokenProvider;
import com.gymnasio.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AutenticacionController {
  private final AuthenticationManager authManager; private final JwtTokenProvider jwt;
  private final UsuarioRepository repo; private final PasswordEncoder encoder; private final UsuarioService usuarioService;
  public AutenticacionController(AuthenticationManager am, JwtTokenProvider jwt, UsuarioRepository repo, PasswordEncoder enc, UsuarioService svc){
    this.authManager=am; this.jwt=jwt; this.repo=repo; this.encoder=enc; this.usuarioService=svc; }

  @PostMapping("/registro")
  public ResponseEntity<UsuarioResponse> registro(@Valid @RequestBody UsuarioRequest req) {
    return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(req)); // asegurarse de hashear en el service
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
    Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.correo(), req.contrasena()));
    var user = repo.findByCorreo(req.correo()).orElseThrow();
    String token = jwt.generar(user.getCorreo(), user.getRol().name());
    return ResponseEntity.ok(new AuthResponse("Bearer", token, user.getCorreo(), user.getRol().name()));
  }
}
