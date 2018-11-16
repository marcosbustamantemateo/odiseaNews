package com.noticias.ProyectoFinal.service;

import java.util.List;
import com.noticias.ProyectoFinal.bean.Rol;

public interface RolSERVICE {

	Rol getRolById (int idRol);
	Rol getRolByName (String name);
	List<Rol> listadoRoles ();
	Boolean guardaRol (Rol rol);
	void actualizaRol (Rol rol);
	void eliminaRol (int idRol);
	Boolean existeRol (Rol rol);
	List<Rol> noFindById(Integer idRol);
	List<Rol> getsRolesNotInIdRol(List<Integer> idRol);
}
