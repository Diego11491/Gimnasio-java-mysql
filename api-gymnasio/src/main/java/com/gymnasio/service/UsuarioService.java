package com.gymnasio.service;

import com.gymnasio.dto.UsuarioRequest;
import com.gymnasio.dto.UsuarioResponse;

import java.util.List;

public interface UsuarioService {
  UsuarioResponse crear(UsuarioRequest request);
  UsuarioResponse obtener(Long id);
  List<UsuarioResponse> listar();
  UsuarioResponse actualizar(Long id, UsuarioRequest request);
  void eliminar(Long id);
}
  //             sad :(    EDELIZA ALIAS LA DORY :V
  