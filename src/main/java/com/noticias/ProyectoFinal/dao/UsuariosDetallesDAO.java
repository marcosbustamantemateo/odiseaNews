package com.noticias.ProyectoFinal.dao;

import java.util.List;
import com.noticias.ProyectoFinal.bean.UsuariosDetalles;

public interface UsuariosDetallesDAO {

	UsuariosDetalles getUsuariosDetallesById (int idUsuariosDetalles);
	List<UsuariosDetalles> listadoUsuariosDetalles ();
	void guardaUsuariosDetalles (UsuariosDetalles usuariosDetalles);
	void eliminaUsuariosDetalles (int idUsuariosDetalles);
	List<UsuariosDetalles> getUsuariosDetallesByIdUsuario(int idUsuario);
}
