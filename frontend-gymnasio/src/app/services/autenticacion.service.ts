import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router'; // Importamos el Router

@Injectable({
  providedIn: 'root'
})
export class AutenticacionService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(
    private http: HttpClient,
    private router: Router // Inyectamos el Router
  ) { }

  login(credenciales: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credenciales);
  }

  registro(datosUsuario: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/registro`, datosUsuario);
  }

  // --- NUEVA FUNCIÓN ---
  // Revisa si hay un token en el localStorage. Devuelve true si existe, false si no.
  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  getNombreUsuario(): string | null {
    const nombres = localStorage.getItem('usuario_nombres');
    const apellidos = localStorage.getItem('usuario_apellidos');
    if (nombres && apellidos) {
      return `${nombres} ${apellidos}`;
    }
    return null;
  }

  logout(): void {
    localStorage.removeItem('token');
    // --- LÍNEA NUEVA ---
    // Asegúrate de borrar también los datos del usuario al cerrar sesión
    localStorage.removeItem('usuario_nombres');
    localStorage.removeItem('usuario_apellidos');
    this.router.navigate(['/']);
  }
}