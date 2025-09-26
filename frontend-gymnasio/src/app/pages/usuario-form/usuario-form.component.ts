import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import {
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import {
  EstadoUsuario,
  Rol,
  UsuarioRequest,
  UsuarioResponse
} from '../../models/usuario';
import { UsuarioApiService } from '../../services/usuario-api.service';

@Component({
  selector: 'app-usuario-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './usuario-form.component.html',
  styleUrl: './usuario-form.component.scss'
})
export class UsuarioFormComponent implements OnInit {
  private readonly fb = inject(NonNullableFormBuilder);
  private readonly usuarioApi = inject(UsuarioApiService);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);

  form = this.fb.group({
    nombres: this.fb.control('', {
      validators: [Validators.required, Validators.maxLength(100)]
    }),
    apellidos: this.fb.control('', {
      validators: [Validators.required, Validators.maxLength(100)]
    }),
    correo: this.fb.control('', {
      validators: [Validators.required, Validators.email, Validators.maxLength(255)]
    }),
    contrasena: this.fb.control('', {
      validators: [Validators.required, Validators.minLength(6), Validators.maxLength(60)]
    }),
    telefono: this.fb.control('', {
      validators: [Validators.maxLength(30)]
    }),
    rol: this.fb.control<Rol>('CLIENTE', {
      validators: [Validators.required]
    }),
    estado: this.fb.control<EstadoUsuario>('ACTIVO', {
      validators: [Validators.required]
    })
  });

  roles: Rol[] = ['ADMIN', 'TRAINER', 'CLIENTE'];
  estados: EstadoUsuario[] = ['ACTIVO', 'INACTIVO'];

  cargando = false;
  guardando = false;
  error: string | null = null;
  esEdicion = false;
  private usuarioId: number | null = null;

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const id = params.get('id');
      if (id) {
        this.esEdicion = true;
        this.usuarioId = Number(id);
        this.cargarUsuario(this.usuarioId);
      }
    });
  }

  private cargarUsuario(id: number): void {
    this.cargando = true;
    this.error = null;
    this.usuarioApi.obtener(id).subscribe({
      next: (usuario) => {
        this.asignarDatos(usuario);
        this.cargando = false;
      },
      error: (err) => {
        console.error(err);
        this.error = 'No se pudo cargar la información del usuario.';
        this.cargando = false;
      }
    });
  }

  private asignarDatos(usuario: UsuarioResponse): void {
    this.form.patchValue({
      nombres: usuario.nombres,
      apellidos: usuario.apellidos,
      correo: usuario.correo,
      telefono: usuario.telefono ?? '',
      rol: usuario.rol,
      estado: usuario.estado
    });
    this.form.controls.contrasena.reset('');
  }

  guardar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const valores = this.form.getRawValue();
    const payload: UsuarioRequest = {
      nombres: valores.nombres.trim(),
      apellidos: valores.apellidos.trim(),
      correo: valores.correo.trim(),
      contrasena: valores.contrasena,
      telefono: valores.telefono.trim() ? valores.telefono.trim() : null,
      rol: valores.rol,
      estado: valores.estado
    };

    this.guardando = true;
    this.error = null;

    const peticion = this.esEdicion && this.usuarioId !== null
      ? this.usuarioApi.actualizar(this.usuarioId, payload)
      : this.usuarioApi.crear(payload);

    peticion.subscribe({
      next: () => {
        this.guardando = false;
        this.router.navigate(['/usuarios']);
      },
      error: (err) => {
        console.error(err);
        this.error = 'Ocurrió un error al guardar el usuario.';
        this.guardando = false;
      }
    });
  }
}
