package com.noticias.ProyectoFinal.resolvers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.noticias.ProyectoFinal.bean.Tag;
import com.noticias.ProyectoFinal.service.TagSERVICE;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

@Component
public class TagQuery {

	@Autowired
	TagSERVICE tagSERVICE;
	
	@GraphQLQuery(name = "listadoTagsPorCategoria")
	public List<Tag> listadoTags (@GraphQLArgument(name = "idCategoria") int idCategoria) {
		
		return tagSERVICE.getTagsByIdCategoria(idCategoria);
	}
}
