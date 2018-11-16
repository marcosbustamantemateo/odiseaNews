package com.noticias.ProyectoFinal.dao;

import java.util.List;

import com.noticias.ProyectoFinal.bean.Tag;

public interface TagDAO {

	Tag getTagById (int idTag);
	List<Tag> getTagsByIdArticulo (int idArticulo);
	List<Tag> getTagsByIdCategoria (int idCategoria);
	List<Tag> listadoArticulos ();
	void guardaTag (Tag tag);
	void eliminaTag (int idTag);
	Long cuentaTags ();
}
