package com.noticias.ProyectoFinal.dao;

import java.util.List;
import com.noticias.ProyectoFinal.bean.Suscripcion;

public interface SuscripcionDAO {

	Suscripcion getSuscripcionById (int idSuscripcion);
	List<Suscripcion> listadoSuscripciones ();
	List<Suscripcion> listadoSuscripcionesPorUsuario (int idUsuario);
	void guardaSuscripcion (Suscripcion suscripcion);
	void actualizaSuscripcion(Suscripcion suscripcion);
	void eliminaSuscripcion (int idSuscripcion);
	Long cuentaSuscripciones ();
}
