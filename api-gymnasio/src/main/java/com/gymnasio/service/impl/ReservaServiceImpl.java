package com.gymnasio.service.impl;

import com.gymnasio.domain.model.*;
import com.gymnasio.dto.ReservaRequest;
import com.gymnasio.dto.ReservaResponse;
import com.gymnasio.repository.ClaseRepository;
import com.gymnasio.repository.ReservaRepository;
import com.gymnasio.repository.UsuarioRepository;
import com.gymnasio.service.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ReservaServiceImpl implements ReservaService {

  private final ReservaRepository reservaRepo;
  private final ClaseRepository claseRepo;
  private final UsuarioRepository usuarioRepo;

  public ReservaServiceImpl(ReservaRepository reservaRepo, ClaseRepository claseRepo, UsuarioRepository usuarioRepo) {
    this.reservaRepo = reservaRepo;
    this.claseRepo = claseRepo;
    this.usuarioRepo = usuarioRepo;
  }

  // ===== Helpers =====
  private Usuario currentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String correo = auth.getName(); // tu UsernamePasswordAuthenticationToken - correo
    return usuarioRepo.findByCorreo(correo)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado"));
  }

  private ReservaResponse toResponse(Reserva r) {
    String nombreUsuario = r.getUsuario().getNombres() + " " + r.getUsuario().getApellidos();
    return new ReservaResponse(
        r.getId(),
        r.getClase().getId(),
        r.getClase().getTitulo(),
        r.getUsuario().getId(),
        nombreUsuario,
        r.getEstado(),
        r.getFechaReserva(),
        r.getFechaCreacion(),
        r.getFechaActualizacion()
    );
  }

  // API

  @Override
  public ReservaResponse reservarComoCliente(ReservaRequest req) {
    Usuario user = currentUser();
    if (user.getRol() != Rol.CLIENTE) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo CLIENTE puede reservar");
    }

    Clase clase = claseRepo.findById(req.claseId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clase no existe"));

    if (clase.getEstado() != EstadoClase.PROGRAMADA) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "La clase no está programada");
    }

    long reservadas = reservaRepo.countByClaseIdAndEstado(clase.getId(), EstadoReserva.RESERVADA);
    if (reservadas >= clase.getCapacidad()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "La clase está llena");
    }

    boolean yaReservada = reservaRepo.existsByUsuario_IdAndClase_IdAndEstado(
        user.getId(), clase.getId(), EstadoReserva.RESERVADA);
    if (yaReservada) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya tienes una reserva activa en esta clase");
    }

    Reserva r = Reserva.builder()
        .usuario(user)
        .clase(clase)
        .estado(EstadoReserva.RESERVADA)
        .fechaReserva(LocalDateTime.now())
        .build();

    r = reservaRepo.save(r);
    return toResponse(r);
  }

  @Override
  public void cancelarMiReserva(Integer reservaId) {
    Usuario user = currentUser();
    Reserva r = reservaRepo.findById(reservaId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada"));

    // Un cliente solo puede cancelar su propia reserva
    if (!r.getUsuario().getId().equals(user.getId()) && user.getRol() == Rol.CLIENTE) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes cancelar reservas de otros usuarios");
    }

    // Cambiamos estado
    r.setEstado(EstadoReserva.CANCELADA);
    // la entidad está administrada; se sincroniza al commit
  }

  @Override
  @Transactional(readOnly = true)
  public List<ReservaResponse> misReservas() {
    Usuario user = currentUser();
    return reservaRepo.findByUsuario_Id(user.getId()).stream().map(this::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<ReservaResponse> asistentesDeClase(Integer claseId) {
    // Solo ADMIN o TRAINER deberían poder ver asistentes
    Usuario user = currentUser();
    if (user.getRol() == Rol.CLIENTE) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No autorizado");
    }
    return reservaRepo.findByClase_IdAndEstado(claseId, EstadoReserva.RESERVADA)
        .stream().map(this::toResponse).toList();
  }
}
