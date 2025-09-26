import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';

import { UsuarioApiService } from '../../services/usuario-api.service';
import { UsuarioResponse } from '../../models/usuario';

@Component({
  selector: 'app-usuario-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './usuario-list.component.html',
  styleUrl: './usuario-list.component.scss'
})
export class UsuarioListComponent implements OnInit {
  private readonly usuarioApi = inject(UsuarioApiService);
  private readonly router = inject(Router);

  usuarios: UsuarioResponse[] = [];
  cargando = false;
  error: string | null = null;

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios(): void {
    this.cargando = true;
    this.error = null;
    this.usuarioApi.listar().subscribe({
      next: (usuarios) => {
        this.usuarios = usuarios;
        this.cargando = false;
      },
      error: (err) => {
        console.error(err);
        this.error = 'No se pudieron cargar los usuarios.';
        this.cargando = false;
      }
    });
  }

  crearUsuario(): void {
    this.router.navigate(['/usuarios/nuevo']);
  }

  editarUsuario(usuario: UsuarioResponse): void {
    this.router.navigate(['/usuarios', usuario.id, 'editar']);
  }

  eliminarUsuario(usuario: UsuarioResponse): void {
    const confirmado = confirm(`¿Deseas eliminar al usuario ${usuario.nombres} ${usuario.apellidos}?`);
    if (!confirmado) {
      return;
    }

    this.usuarioApi.eliminar(usuario.id).subscribe({
      next: () => {
        this.usuarios = this.usuarios.filter((u) => u.id !== usuario.id);
      },
      error: (err) => {
        console.error(err);
        this.error = 'No se pudo eliminar el usuario.';
      }
    });
  }

  trackById(_index: number, item: UsuarioResponse): number {
    return item.id;
  }
}
