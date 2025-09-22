package com.gymnasio.service.impl;

import com.gymnasio.domain.model.EstadoUsuario;
import com.gymnasio.domain.model.Rol;
import com.gymnasio.domain.model.Usuario;
import com.gymnasio.dto.UsuarioRequest;
import com.gymnasio.dto.UsuarioResponse;
import com.gymnasio.repository.UsuarioRepository;
import com.gymnasio.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

  private final UsuarioRepository repo;

  public UsuarioServiceImpl(UsuarioRepository repo) {
    this.repo = repo;
  }

  @Override
  public UsuarioResponse crear(UsuarioRequest r) {
    if (repo.existsByCorreo(r.correo())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
    }
    Usuario u = Usuario.builder()
        .nombres(r.nombres())
        .apellidos(r.apellidos())
        .correo(r.correo())
        .contrasena(r.contrasena()) // hashear con BCrypt en seguridad
        .telefono(r.telefono())
        .rol(r.rol() != null ? r.rol() : Rol.CLIENTE)
        .estado(r.estado() != null ? r.estado() : EstadoUsuario.ACTIVO)
        .build();
    u = repo.save(u);
    return toResponse(u);
  }

  @Override
  @Transactional(readOnly = true)
  public UsuarioResponse obtener(Long id) {
    Usuario u = repo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    return toResponse(u);
  }

  @Override
  @Transactional(readOnly = true)
  public List<UsuarioResponse> listar() {
    return repo.findAll().stream().map(this::toResponse).toList();
  }

  @Override
  public UsuarioResponse actualizar(Long id, UsuarioRequest r) {
    Usuario u = repo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

    if (!u.getCorreo().equals(r.correo()) && repo.existsByCorreo(r.correo())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
    }

    u.setNombres(r.nombres());
    u.setApellidos(r.apellidos());
    u.setCorreo(r.correo());
    u.setContrasena(r.contrasena()); // hashear
    u.setTelefono(r.telefono());
    u.setRol(r.rol() != null ? r.rol() : u.getRol());
    u.setEstado(r.estado() != null ? r.estado() : u.getEstado());
    return toResponse(u);
  }

  @Override
  public void eliminar(Long id) {
    if (!repo.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
    }
    repo.deleteById(id);
  }

  private UsuarioResponse toResponse(Usuario u) {
    return new UsuarioResponse(
        u.getId(), u.getNombres(), u.getApellidos(), u.getCorreo(),
        u.getTelefono(), u.getRol(), u.getEstado(),
        u.getFechaCreacion(), u.getFechaActualizacion()
    );
  }
}
