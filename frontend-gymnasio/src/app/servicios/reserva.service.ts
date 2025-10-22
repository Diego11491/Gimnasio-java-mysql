// src/app/servicios/reserva.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservaService {
  private apiUrl = 'http://localhost:8080/api/reservas';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  // Llama a POST /api/reservas
  crearReserva(claseId: number): Observable<any> {
    const headers = this.getHeaders();
    // Tu DTO ReservaRequest espera un claseId
    const body = { claseId: claseId }; 
    return this.http.post(this.apiUrl, body, { headers });
  }

  // Llama a GET /api/reservas/mis
  getMisReservas(): Observable<any[]> {
    const headers = this.getHeaders();
    return this.http.get<any[]>(`${this.apiUrl}/mis`, { headers });
  }

  // Llama a PUT /api/reservas/{id}/cancelar
  cancelarReserva(id: number): Observable<any> {
    const headers = this.getHeaders();
    return this.http.put(`${this.apiUrl}/${id}/cancelar`, {}, { headers });
  }
}