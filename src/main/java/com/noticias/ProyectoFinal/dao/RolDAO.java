package com.noticias.ProyectoFinal.dao;

import java.util.List;

import com.noticias.ProyectoFinal.bean.Rol;

public interface RolDAO {

	Rol getRolById (int idRol);
	List<Rol> listadoRoles ();
	void guardaRol (Rol rol);
	void actualizaRol (Rol rol);
	void eliminaRol (int idRol);
	Boolean existeRol (Rol rol);
	Rol getRolByName(String nombre);
	List<Rol> noFindById(Integer idRol);
	List<Rol> getsRolesNotInIdRol(List<Integer> idRol);
}
