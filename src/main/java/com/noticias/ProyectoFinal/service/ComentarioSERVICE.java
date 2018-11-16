package com.noticias.ProyectoFinal.service;

import java.util.List;

import com.noticias.ProyectoFinal.bean.Comentario;

public interface ComentarioSERVICE {

	Comentario getComentarioById (int idComentario);
	Boolean existeComentario(Comentario comentario);
	List<Comentario> listadoComentariosPorArticulo(int idArticulo);
	List<Comentario> listadoComentarios ();
	Boolean guardaComentario (Comentario comentario);
	void eliminaComentario (int idComentario);
	Long cuentaComentarios ();
}
