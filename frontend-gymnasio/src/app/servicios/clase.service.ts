// src/app/servicios/clase.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClaseService {
  private apiUrl = 'http://localhost:8080/api/clases';

  constructor(private http: HttpClient) { }

  // Método para obtener TODAS las clases programadas
  getClasesDisponibles(): Observable<any[]> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any[]>(this.apiUrl, { headers });
  }
}