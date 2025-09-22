package com.gymnasio.dto;

public interface DisponibilidadClaseView {
  Long getClaseId();
  String getTitulo();
  Integer getCapacidad();
  Long getReservadas();
  Long getDisponibles();
}
