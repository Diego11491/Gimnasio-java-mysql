package com.gymnasio.service;

import com.gymnasio.dto.*;

import java.util.List;

public interface ClaseService {
    ClaseResponse crear(ClaseRequest request);

    ClaseResponse obtener(Long id);

    List<ClaseResponse> listar();

    ClaseResponse actualizar(Long id, ClaseRequest request);

    void eliminar(Long id);

    DisponibilidadClaseView disponibilidad(Long claseId);
}
