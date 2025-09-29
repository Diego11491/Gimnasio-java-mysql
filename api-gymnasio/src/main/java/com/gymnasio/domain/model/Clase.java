package com.gymnasio.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "clases")
public class Clase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String titulo;

  @Column(columnDefinition = "TEXT")
  private String descripcion;

  @Column(length = 50)
  private String tipo; // Yoga, HIIT, etc.

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "instructor_id", nullable = false)
  private Usuario instructor;

  @Column(name = "fecha_inicio")
  private LocalDateTime fechaInicio;

  @Column(name = "duracion_minutos")
  private Integer duracionMinutos;

  @Column(nullable = false)
  private Integer capacidad;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 12)
  @Builder.Default
  private EstadoClase estado = EstadoClase.PROGRAMADA;

  @CreationTimestamp
  @Column(name = "fecha_creacion", updatable = false)
  private LocalDateTime fechaCreacion;

  @UpdateTimestamp
  @Column(name = "fecha_actualizacion")
  private LocalDateTime fechaActualizacion;
}
