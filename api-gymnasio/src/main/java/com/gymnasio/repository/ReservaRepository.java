package com.gymnasio.repository;

import com.gymnasio.domain.model.EstadoReserva;
import com.gymnasio.domain.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    boolean existsByUsuario_IdAndClase_IdAndEstado(Integer usuarioId, Integer claseId, EstadoReserva estado);

    @Query("select count(r) from Reserva r where r.clase.id = :claseId and r.estado = :estado")
    long countByClaseIdAndEstado(Integer claseId, EstadoReserva estado);

    List<Reserva> findByUsuario_Id(Integer usuarioId);
    List<Reserva> findByClase_IdAndEstado(Integer claseId, EstadoReserva estado);
}
