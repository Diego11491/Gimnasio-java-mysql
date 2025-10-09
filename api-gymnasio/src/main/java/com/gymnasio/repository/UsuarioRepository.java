package com.gymnasio.repository;

import com.gymnasio.domain.model.Usuario;
import com.gymnasio.domain.model.Rol;
import com.gymnasio.domain.model.EstadoUsuario;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

  boolean existsByCorreo(String correo);
  java.util.Optional<Usuario> findByCorreo(String correo);

  @Query("""
         select u
         from Usuario u
         where (:rol is null or u.rol = :rol)
           and (:estado is null or u.estado = :estado)
           and (
                 :q is null
                 or trim(:q) = ''
                 or lower(concat(u.nombres,' ',u.apellidos,' ',u.correo))
                    like lower(concat('%', :q, '%'))
               )
         order by u.nombres asc, u.apellidos asc
         """)
  List<Usuario> buscar(
      @Param("q") String q,
      @Param("rol") Rol rol,
      @Param("estado") EstadoUsuario estado
  );
}
