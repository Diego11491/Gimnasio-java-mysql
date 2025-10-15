import { Routes } from '@angular/router';
import { InicioComponent } from './Vistas/inicio/inicio.component';
import { LoginComponent } from './Vistas/login/login.component';
import { RegistroComponent } from './Vistas/registro/registro.component';
import { DashboardComponent } from './Vistas/dashboard/dashboard.component';

export const routes: Routes = [
    { path: '', component: InicioComponent }, // Ruta principal
    { path: 'login', component: LoginComponent },
    { path: 'registro', component: RegistroComponent },
    { path: 'dashboard', component: DashboardComponent },
    { path: '**', redirectTo: '', pathMatch: 'full' } 
];