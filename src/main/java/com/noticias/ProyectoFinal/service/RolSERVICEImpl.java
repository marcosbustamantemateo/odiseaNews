package com.noticias.ProyectoFinal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noticias.ProyectoFinal.bean.Rol;
import com.noticias.ProyectoFinal.dao.RolDAO;

@Service
public class RolSERVICEImpl implements RolSERVICE {

	@Autowired
	RolDAO rolDao;
	
	@Override
	public Rol getRolById(int idRol) {

		return rolDao.getRolById(idRol);
	}

	@Override
	public List<Rol> listadoRoles() {
		
		return rolDao.listadoRoles();
	}

	@Override
	public Boolean guardaRol(Rol rol) {
		
		if (existeRol(rol)) {
			return false;
		} else {
			rolDao.guardaRol(rol);
			return true;
		}
	}

	@Override
	public void eliminaRol(int idRol) {

		rolDao.eliminaRol(idRol);
	}

	@Override
	public Boolean existeRol(Rol rol) {
		
		return rolDao.existeRol(rol);
	}

	@Override
	public void actualizaRol(Rol rol) {
		
		rolDao.actualizaRol(rol);
	}

	@Override
	public List<Rol> noFindById(Integer idRol) {
		
		return rolDao.noFindById(idRol);
	}

	@Override
	public List<Rol> getsRolesNotInIdRol(List<Integer> idRol) {
		
		return rolDao.getsRolesNotInIdRol(idRol);
	}

	@Override
	public Rol getRolByName(String name) {
		
		return rolDao.getRolByName(name);
	}


}
