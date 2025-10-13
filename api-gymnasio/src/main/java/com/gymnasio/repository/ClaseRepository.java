package com.gymnasio.repository;

import com.gymnasio.domain.model.Clase;
import com.gymnasio.domain.model.EstadoReserva;
import com.gymnasio.dto.DisponibilidadClaseView;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface ClaseRepository extends JpaRepository<Clase, Integer> {

    @Query("""
            select
              c.id as claseId,
              c.titulo as titulo,
              c.capacidad as capacidad,
              count(r) as reservadas,
              (c.capacidad - count(r)) as disponibles
            from Clase c
            left join Reserva r on r.clase = c and r.estado = :estado
            where c.id = :claseId
            group by c.id, c.titulo, c.capacidad
           """)
    DisponibilidadClaseView disponibilidad(@Param("claseId") Integer claseId,
                                          @Param("estado") EstadoReserva estado);
}

