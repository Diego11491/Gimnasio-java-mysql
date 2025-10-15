import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // Necesario para ngIf y ngFor
import { RouterLink } from '@angular/router';
import { AutenticacionService } from '../../services/autenticacion.service';

@Component({
  selector: 'app-inicio',
  standalone: true,
  // Asegúrate de importar CommonModule y RouterLink
  imports: [CommonModule, RouterLink], 
  templateUrl: './inicio.component.html',
  styleUrl: './inicio.component.scss'
})
export class InicioComponent {

  // Inyectamos el servicio de autenticación para usar sus métodos
  constructor(public autenticacionService: AutenticacionService) {}

  // Este método llamará al logout del servicio
  cerrarSesion(): void {
    this.autenticacionService.logout();
    alert('Has cerrado sesión correctamente.');
  }
}