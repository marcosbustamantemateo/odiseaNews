package com.noticias.ProyectoFinal.service;

import java.util.List;

import com.noticias.ProyectoFinal.bean.Articulo;

public interface ArticuloSERVICE {

	Articulo getArticuloById (int idArticulo);
	Articulo getUltimoArticulo ();
	List<Articulo> listadoArticulos ();
	List<Articulo> getsArticulosByTitulo (String titulo);
	List<Articulo> listadoArticulosPorVisitas();
	void guardaArticulo (Articulo articulo);
	void eliminaArticulo (int idArticulo);
	Long cuentaArticulos ();
	void actualizaArticulo (Articulo articulo);
	List<Articulo> listadoArticulosFront();
}
