package com.gymnasio.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "usuarios",
       uniqueConstraints = @UniqueConstraint(name = "uk_usuarios_correo", columnNames = "correo"))
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String nombres;

  @Column(nullable = false, length = 100)
  private String apellidos;

  @Column(nullable = false, length = 255)
  private String correo;          // UNIQUE en la BD

  @Column(nullable = false, length = 60)
  private String contrasena;      // guarda hash (BCrypt)

  @Column(length = 30)
  private String telefono;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private Rol rol = Rol.CLIENTE;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private EstadoUsuario estado = EstadoUsuario.ACTIVO;

  @CreationTimestamp
  @Column(name = "fecha_creacion", updatable = false)
  private LocalDateTime fechaCreacion;

  @UpdateTimestamp
  @Column(name = "fecha_actualizacion")
  private LocalDateTime fechaActualizacion;
}
