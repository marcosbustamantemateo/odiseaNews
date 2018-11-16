package com.noticias.ProyectoFinal.service;

import java.util.List;

import com.noticias.ProyectoFinal.bean.Categoria;

public interface CategoriaSERVICE {

	Categoria getCategoriaById (int idCategoria);
	List<Categoria> listadoCategorias ();
	List<Categoria> getsCategoriasNotInIdCategoria (List<Integer> idCategoria);
	Boolean guardaCategoria (Categoria categoria);
	void actualizaCategoria (Categoria categoria);
	void eliminaCategoria (int idCategoria);
	Boolean existeCategoria (Categoria categoria);
	Long cuentaCategorias ();
}
