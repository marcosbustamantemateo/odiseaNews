package com.noticias.ProyectoFinal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.noticias.ProyectoFinal.bean.Usuario;
import com.noticias.ProyectoFinal.bean.UsuariosDetalles;
import com.noticias.ProyectoFinal.dao.UsuarioDAO;

@Service
public class UsuarioSERVICEImpl implements UsuarioSERVICE {

	@Autowired
	UsuarioDAO usuarioDAO;
	
	@Override
	public Usuario getUsuarioById(int idUsuario) {
		
		return usuarioDAO.getUsuarioById(idUsuario);
	}
	
	@Override
	public List<Usuario> listadoUsuarios() {
		
		return usuarioDAO.listadoUsuarios();
	}

	@Override
	public List<Usuario> listadoUsuariosPorTipo(int idTipoUsuario) {

		return usuarioDAO.listadoUsuariosPorTipo(idTipoUsuario);
	}

	@Override
	public Boolean guardaUsuario(Usuario usuario) {
				
		if (existeUsuario(usuario))			
			return false;
		
		else {			
			usuarioDAO.guardaUsuario(usuario);
			return true;
		}
	}

	@Override
	public void eliminaUsuario(int idUsuario) {
		
		usuarioDAO.eliminaUsuario(idUsuario);
	}
	
	@Override
	public Long cuentaUsuarios () {
		
		return usuarioDAO.cuentaUsuarios();
	}

	@Override
	public Usuario getUsuarioByLogin(String login) {
		
		return usuarioDAO.getUsuarioByLogin(login);
	}

	@Override
	public Boolean existeUsuario(Usuario usuario) {

		return usuarioDAO.existeUsuario(usuario);
	}

	@Override
	public Boolean isAdmin (Usuario usuario) {
		
		for (UsuariosDetalles usuariosDetalles : usuario.getDetallesUsuario()) {
			
			if (usuariosDetalles.getRol().getNombre().contains("ROLE_ADMIN")) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void actulizaUsuario(Usuario usuario) {
		
		usuarioDAO.actualizaUsuario(usuario);
	}

	@Override
	public Usuario getUsuarioByUUID(String uuid) {
		
		return usuarioDAO.getUsuarioByUUID(uuid);
	}

	@Override
	public List<Usuario> getUsuarioByUsername(String login) {
		
		return usuarioDAO.getUsuarioByUsername(login);
	}

	@Override
	public Usuario compruebaUsuario(String login, String password) {

		return usuarioDAO.compruebaUsuario(login, password);
	}

}
