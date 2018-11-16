package com.noticias.ProyectoFinal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noticias.ProyectoFinal.bean.Articulo;
import com.noticias.ProyectoFinal.dao.ArticuloDAO;

@Service
public class ArticuloSERVICEImpl implements ArticuloSERVICE {

	@Autowired
	ArticuloDAO articuloDAO;
	
	@Override
	public Long cuentaArticulos() {
		
		return articuloDAO.cuentaArticulos();
	}

	@Override
	public Articulo getArticuloById(int idArticulo) {
		
		return articuloDAO.getArticuloById(idArticulo);
	}

	@Override
	public List<Articulo> listadoArticulos() {
		
		return articuloDAO.listadoArticulos();
	}

	@Override
	public void guardaArticulo(Articulo articulo) {
		
		articuloDAO.guardaArticulo(articulo);
	}

	@Override
	public void eliminaArticulo(int idArticulo) {
		
		articuloDAO.eliminaArticulo(idArticulo);
	}

	@Override
	public void actualizaArticulo(Articulo articulo) {
		
		articuloDAO.actualizaArticulo(articulo);
	}

	@Override
	public List<Articulo> getsArticulosByTitulo(String titulo) {
		
		return articuloDAO.getsArticulosByTitulo(titulo);
	}

	@Override
	public List<Articulo> listadoArticulosPorVisitas() {
		
		return articuloDAO.listadoArticulosPorVisitas();
	}

	@Override
	public Articulo getUltimoArticulo() {
		
		return articuloDAO.getUltimoArticulo();
	}

	@Override
	public List<Articulo> listadoArticulosFront() {
		
		return articuloDAO.listadoArticulosFront();
	}

}
