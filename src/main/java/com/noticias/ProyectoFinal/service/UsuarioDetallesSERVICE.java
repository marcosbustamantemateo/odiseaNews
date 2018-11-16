package com.noticias.ProyectoFinal.service;

import java.util.List;

import com.noticias.ProyectoFinal.bean.UsuariosDetalles;

public interface UsuarioDetallesSERVICE {

	UsuariosDetalles getUsuariosDetallesById (int idUsuariosDetalles);
	List<UsuariosDetalles> listadoUsuariosDetalles ();
	void guardaUsuariosDetalles (UsuariosDetalles usuariosDetalles);
	void eliminaUsuariosDetalles (int idUsuariosDetalles);
	List<UsuariosDetalles> getUsuariosDetallesByIdUsuario(int idUsuario);
}
