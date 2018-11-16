package com.noticias.ProyectoFinal.dao;

import java.util.List;

import com.noticias.ProyectoFinal.bean.Categoria;

public interface CategoriaDAO {

	Categoria getCategoriaById (int idCategoria);
	List<Categoria> listadoCategorias ();
	List<Categoria> getsCategoriasNotInIdCategoria (List<Integer> idCategoria);
	void guardaCategoria (Categoria categoria);
	void actualizaCategoria (Categoria categoria);
	void eliminaCategoria (int idCategoria);
	Boolean existeCategoria (Categoria categoria);
	Long cuentaCategorias ();
}
