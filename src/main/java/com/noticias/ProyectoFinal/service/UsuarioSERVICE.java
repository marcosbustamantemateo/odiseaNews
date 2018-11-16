package com.noticias.ProyectoFinal.service;

import java.util.List;
import com.noticias.ProyectoFinal.bean.Usuario;

public interface UsuarioSERVICE {

	Usuario getUsuarioById (int idUsuario);
	Usuario compruebaUsuario(String login, String password);
	Usuario getUsuarioByLogin(String login);
	List<Usuario> getUsuarioByUsername(String login);
	Usuario getUsuarioByUUID(String uuid);
	List<Usuario> listadoUsuarios ();
	List<Usuario> listadoUsuariosPorTipo (int idTipoUsuario);
	Boolean guardaUsuario (Usuario usuario);
	void eliminaUsuario (int idUsuario);
	void actulizaUsuario (Usuario usuario);
	Boolean existeUsuario (Usuario usuario);
	Long cuentaUsuarios ();
	Boolean isAdmin (Usuario usuario);
}
