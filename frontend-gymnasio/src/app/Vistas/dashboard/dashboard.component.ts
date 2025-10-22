import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AutenticacionService } from '../../services/autenticacion.service';
import { NavbarComponent } from '../../Componentes/navbar/navbar.component';
import { RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, NavbarComponent, RouterLink, RouterLinkActive],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  nombreUsuario: string | null = null;

  constructor(private autenticacionService: AutenticacionService) {}

  ngOnInit(): void {
    this.nombreUsuario = this.autenticacionService.getNombreUsuario();
  }

  cerrarSesion(): void {
    this.autenticacionService.logout();
  }
}