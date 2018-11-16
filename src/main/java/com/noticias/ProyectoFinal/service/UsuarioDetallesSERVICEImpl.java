package com.noticias.ProyectoFinal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noticias.ProyectoFinal.bean.UsuariosDetalles;
import com.noticias.ProyectoFinal.dao.UsuariosDetallesDAO;

@Service
public class UsuarioDetallesSERVICEImpl implements UsuarioDetallesSERVICE {

	@Autowired
	UsuariosDetallesDAO usuarioDetallesDAO;
	
	@Override
	public UsuariosDetalles getUsuariosDetallesById(int idUsuariosDetalles) {
		
		return usuarioDetallesDAO.getUsuariosDetallesById(idUsuariosDetalles);
	}

	@Override
	public List<UsuariosDetalles> listadoUsuariosDetalles() {
		
		return usuarioDetallesDAO.listadoUsuariosDetalles();
	}

	@Override
	public void guardaUsuariosDetalles(UsuariosDetalles usuariosDetalles) {
		
		usuarioDetallesDAO.guardaUsuariosDetalles(usuariosDetalles);
	}

	@Override
	public void eliminaUsuariosDetalles(int idUsuariosDetalles) {
		
		usuarioDetallesDAO.eliminaUsuariosDetalles(idUsuariosDetalles);
	}

	@Override
	public List<UsuariosDetalles> getUsuariosDetallesByIdUsuario(int idUsuario) {
		
		return usuarioDetallesDAO.getUsuariosDetallesByIdUsuario(idUsuario);
	}

}
