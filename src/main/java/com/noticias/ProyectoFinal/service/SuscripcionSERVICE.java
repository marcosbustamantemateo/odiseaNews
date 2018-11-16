package com.noticias.ProyectoFinal.service;

import java.util.List;

import com.noticias.ProyectoFinal.bean.Suscripcion;

public interface SuscripcionSERVICE {

	Suscripcion getSuscripcionById (int idSuscripcion);
	List<Suscripcion> listadoSuscripcionesPorUsuario(int idUsuario);
	List<Suscripcion> listadoSuscripciones ();
	void guardaSuscripcion (Suscripcion suscripcion);
	void actualizaSuscripcion(Suscripcion suscripcion);
	void eliminaSuscripcion (int idSuscripcion);
	Long cuentaSuscripciones ();
}
