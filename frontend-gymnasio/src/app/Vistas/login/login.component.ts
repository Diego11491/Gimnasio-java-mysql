import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AutenticacionService } from '../../services/autenticacion.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMensaje: string | null = null; // Para mostrar errores de login

  constructor(
    private fb: FormBuilder,
    private autenticacionService: AutenticacionService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      correo: ['', [Validators.required, Validators.email]],
      contrasena: ['', [Validators.required]]
    });
  }

  onSubmit(): void {
    this.errorMensaje = null; // Limpiamos errores previos
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.autenticacionService.login(this.loginForm.value).subscribe({
      next: (response) => {
        console.log('Login exitoso, token recibido:', response.token);
        
        localStorage.setItem('token', response.token);
        localStorage.setItem('usuario_nombres', response.nombres);   // <--- LÍNEA NUEVA
        localStorage.setItem('usuario_apellidos', response.apellidos);  
        localStorage.setItem('usuario_id', response.id);

        // Redirigimos al usuario a una página principal o dashboard (puedes cambiar '/dashboard' a tu ruta deseada)
        alert(`¡Bienvenido de vuelta, ${response.nombres}!`);
        this.router.navigate(['/dashboard']); // Cambia '/dashboard' por tu ruta principal post-login
      },
      error: (error) => {
        console.error('Error en el login:', error);
        this.errorMensaje = 'Correo o contraseña incorrectos. Por favor, verifica tus credenciales.';
      }
    });
  }
}