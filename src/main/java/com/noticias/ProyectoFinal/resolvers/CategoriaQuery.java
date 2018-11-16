package com.noticias.ProyectoFinal.resolvers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.noticias.ProyectoFinal.bean.Categoria;
import com.noticias.ProyectoFinal.bean.Tag;
import com.noticias.ProyectoFinal.service.CategoriaSERVICE;
import com.noticias.ProyectoFinal.service.TagSERVICE;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

@Component
public class CategoriaQuery {

	@Autowired
	CategoriaSERVICE categoriaSERVICE;
	
	@Autowired
	TagSERVICE tagSERVICE;
	
	@GraphQLQuery(name = "listadoCategorias")
	public List<Categoria> listadoCategorias () {
		
		return categoriaSERVICE.listadoCategorias();
	}
	
	@GraphQLQuery(name = "listadoCategoriasPorArticulo")
	public List<Categoria> listadoCategoriasPorArticulo (@GraphQLArgument(name = "idArticulo") int idArticulo) {
		
		List<Tag> listadoTags = tagSERVICE.getTagsByIdArticulo(idArticulo);
		List<Categoria> listadoCategorias = new ArrayList<Categoria>();
		
		for (Tag tag : listadoTags) {
			listadoCategorias.add(tag.getCategoriaTag());
		}
		
		return listadoCategorias;
	}
	
	@GraphQLQuery(name = "getCategoriaById")
	public Categoria getCategoriaById (@GraphQLArgument(name = "idCategoria") String idCategoria) {
		
		if (idCategoria != null) {
			
			return categoriaSERVICE.getCategoriaById(Integer.parseInt(idCategoria));
		}
		
		return null;
	}
	
}
