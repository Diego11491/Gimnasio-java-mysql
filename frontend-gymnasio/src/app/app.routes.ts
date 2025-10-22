import { Routes } from '@angular/router';
import { InicioComponent } from './Vistas/inicio/inicio.component';
import { LoginComponent } from './Vistas/login/login.component';
import { RegistroComponent } from './Vistas/registro/registro.component';
import { DashboardComponent } from './Vistas/dashboard/dashboard.component';
import { LayoutPrincipalComponent } from './Vistas/layout-principal/layout-principal.component';
import { ListaClasesComponent } from './Vistas/cliente/lista-clases/lista-clases.component';
import { MisReservasComponent } from './Vistas/cliente/mis-reservas/mis-reservas.component';
import { MiPerfilComponent } from './Vistas/cliente/mi-perfil/mi-perfil.component';
import { authGuard } from './seguridad/auth.guard';



export const routes: Routes = [
    { path: '', component: InicioComponent }, // Ruta principal
    { path: 'login', component: LoginComponent },
    { path: 'registro', component: RegistroComponent },
    { path: 'dashboard', component: DashboardComponent },
    {
        path: '',
        component: LayoutPrincipalComponent,
        canActivate: [authGuard], // El guardián protege a todos los hijos
        children: [
          { path: 'dashboard', component: DashboardComponent },
          { path: 'mi-perfil', component: MiPerfilComponent },
          { path: 'clases', component: ListaClasesComponent },
          { path: 'mis-reservas', component: MisReservasComponent }
          // { path: 'mis-rutinas', component: MisRutinasComponent },
          // { path: 'mi-progreso', component: MiProgresoComponent },
        ]
      },
    { path: '**', redirectTo: '', pathMatch: 'full' } 
];