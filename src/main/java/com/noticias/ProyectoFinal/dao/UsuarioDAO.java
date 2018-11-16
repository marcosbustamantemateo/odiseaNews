package com.noticias.ProyectoFinal.dao;

import java.util.List;
import com.noticias.ProyectoFinal.bean.Usuario;

public interface UsuarioDAO {

	Usuario getUsuarioById (int idUsuario);
	Usuario compruebaUsuario(String login, String password);
	List<Usuario> listadoUsuarios ();
	List<Usuario> listadoUsuariosPorTipo (int idTipoUsuario);
	void guardaUsuario (Usuario usuario);
	void actualizaUsuario (Usuario usuario);
	void eliminaUsuario (int idUsuario);
	Boolean existeUsuario (Usuario usuario);
	Usuario getUsuarioByLogin(String login);
	List<Usuario> getUsuarioByUsername(String login);
	Usuario getUsuarioByUUID(String uuid);
	Long cuentaUsuarios ();
}
