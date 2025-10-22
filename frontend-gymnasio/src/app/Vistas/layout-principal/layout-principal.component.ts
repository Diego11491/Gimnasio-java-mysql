import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from '../../Componentes/navbar/navbar.component';

@Component({
  selector: 'app-layout-principal',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent], // <-- Importante
  templateUrl: './layout-principal.component.html',
  styleUrls: ['./layout-principal.component.scss']
})
export class LayoutPrincipalComponent {}