package com.noticias.ProyectoFinal.dao;

import java.util.List;

import com.noticias.ProyectoFinal.bean.Comentario;

public interface ComentarioDAO {

	Comentario getComentarioById (int idComentario);
	List<Comentario> listadoComentariosPorArticulo (int idArticulo);
	List<Comentario> listadoComentarios ();
	void guardaComentario (Comentario comentario);
	void eliminaComentario (int idComentario);
	Boolean existeComentario (Comentario comentario);
	Long cuentaComentarios ();
}
