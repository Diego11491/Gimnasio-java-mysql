package com.gymnasio.service;

import com.gymnasio.dto.UsuarioRequest;
import com.gymnasio.dto.UsuarioResponse;

import java.util.List;

public interface UsuarioService {
    List<UsuarioResponse> listar();
    UsuarioResponse obtener(Integer id);
    UsuarioResponse crear(UsuarioRequest request);
    UsuarioResponse actualizar(Integer id, UsuarioRequest request);
    void eliminar(Integer id);
}

  //             sad :(    EDELIZA ALIAS LA DORY :V
  