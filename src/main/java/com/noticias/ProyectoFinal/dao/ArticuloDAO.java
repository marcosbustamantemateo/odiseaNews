package com.noticias.ProyectoFinal.dao;

import java.util.List;

import com.noticias.ProyectoFinal.bean.Articulo;

public interface ArticuloDAO {

	Articulo getArticuloById (int idArticulo);
	Articulo getUltimoArticulo ();
	List<Articulo> listadoArticulos ();
	List<Articulo> listadoArticulosFront();
	List<Articulo> getsArticulosByTitulo (String titulo);
	List<Articulo> listadoArticulosPorVisitas ();
	void guardaArticulo (Articulo articulo);
	void eliminaArticulo (int idArticulo);
	Long cuentaArticulos ();
	void actualizaArticulo (Articulo articulo);
}
