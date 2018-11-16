package com.noticias.ProyectoFinal.service;

import java.util.List;

import com.noticias.ProyectoFinal.bean.Tag;

public interface TagSERVICE {

	Tag getTagById (int idTag);
	List<Tag> getTagsByIdArticulo (int idArticulo);
	List<Tag> getTagsByIdCategoria (int idCategoria);
	List<Tag> listadoTags ();
	void guardaTag (Tag tag);
	void eliminaTag (int idTag);
	Long cuentaTags ();
}
