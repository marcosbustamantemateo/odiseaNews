package com.noticias.ProyectoFinal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noticias.ProyectoFinal.bean.Categoria;
import com.noticias.ProyectoFinal.dao.CategoriaDAO;

@Service
public class CategoriaSERVICEImpl implements CategoriaSERVICE {
	
	@Autowired
	CategoriaDAO categoriaDAO;
	
	@Override
	public Long cuentaCategorias() {

		return categoriaDAO.cuentaCategorias();
	}

	@Override
	public Categoria getCategoriaById(int idCategoria) {
		
		return categoriaDAO.getCategoriaById(idCategoria);
	}

	@Override
	public List<Categoria> listadoCategorias() {
		
		return categoriaDAO.listadoCategorias();
	}

	@Override
	public Boolean guardaCategoria(Categoria categoria) {
		
		if (existeCategoria(categoria)) {
			return false;
		} else {
			categoriaDAO.guardaCategoria(categoria);
			return true;
		}
		
	}

	@Override
	public void eliminaCategoria(int idCategoria) {
		
		categoriaDAO.eliminaCategoria(idCategoria);
	}

	@Override
	public void actualizaCategoria(Categoria categoria) {

		categoriaDAO.actualizaCategoria(categoria);
	}

	@Override
	public Boolean existeCategoria(Categoria categoria) {
		
		return categoriaDAO.existeCategoria(categoria);
	}

	@Override
	public List<Categoria> getsCategoriasNotInIdCategoria(List<Integer> idCategoria) {

		return categoriaDAO.getsCategoriasNotInIdCategoria(idCategoria);
	}


}
