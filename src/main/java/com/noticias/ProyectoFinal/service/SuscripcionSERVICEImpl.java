package com.noticias.ProyectoFinal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noticias.ProyectoFinal.bean.Suscripcion;
import com.noticias.ProyectoFinal.dao.SuscripcionDAO;

@Service
public class SuscripcionSERVICEImpl implements SuscripcionSERVICE {

	@Autowired
	SuscripcionDAO suscripcionDAO;
	
	@Override
	public Long cuentaSuscripciones () {

		return suscripcionDAO.cuentaSuscripciones();
	}

	@Override
	public Suscripcion getSuscripcionById(int idSuscripcion) {
		
		return suscripcionDAO.getSuscripcionById(idSuscripcion);
	}

	@Override
	public List<Suscripcion> listadoSuscripciones() {
		
		return suscripcionDAO.listadoSuscripciones();
	}

	@Override
	public void guardaSuscripcion(Suscripcion suscripcion) {
		
		suscripcionDAO.guardaSuscripcion(suscripcion);
	}

	@Override
	public void eliminaSuscripcion(int idSuscripcion) {
		
		suscripcionDAO.eliminaSuscripcion(idSuscripcion);
	}

	@Override
	public void actualizaSuscripcion(Suscripcion suscripcion) {
		
		suscripcionDAO.actualizaSuscripcion(suscripcion);
	}

	@Override
	public List<Suscripcion> listadoSuscripcionesPorUsuario(int idUsuario) {
		
		return suscripcionDAO.listadoSuscripcionesPorUsuario(idUsuario);
	}

}
