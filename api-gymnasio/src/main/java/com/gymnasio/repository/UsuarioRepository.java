package com.gymnasio.repository;

import com.gymnasio.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  boolean existsByCorreo(String correo);
  Optional<Usuario> findByCorreo(String correo);
}

//  @Repository
//  public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
//    @Query("SELECT e FROM Empleado e WHERE e.departamento = :dep")
//    List<Empleado> findByDepartamento(@Param("dep") String dep);
//   }