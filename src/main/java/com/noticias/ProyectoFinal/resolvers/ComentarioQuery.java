package com.noticias.ProyectoFinal.resolvers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noticias.ProyectoFinal.bean.Articulo;
import com.noticias.ProyectoFinal.bean.Comentario;
import com.noticias.ProyectoFinal.bean.Usuario;
import com.noticias.ProyectoFinal.service.ArticuloSERVICE;
import com.noticias.ProyectoFinal.service.ComentarioSERVICE;
import com.noticias.ProyectoFinal.service.UsuarioSERVICE;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;

@Component
public class ComentarioQuery {

	@Autowired
	ComentarioSERVICE comentarioSERVICE;
	
	@Autowired
	UsuarioSERVICE usuarioSERVICE;
	
	@Autowired
	ArticuloSERVICE articuloSERVICE;
	
	@GraphQLQuery(name = "listadoComentariosPorArticulo")
	public List<Comentario> listadoComentarios (@GraphQLArgument(name = "idArticulo") int idArticulo) {
		
		return comentarioSERVICE.listadoComentariosPorArticulo(idArticulo);
	}
	
	@GraphQLMutation(name = "creaComentario") 
	public String creaComentario (@GraphQLArgument(name = "contenido") String contenido, @GraphQLArgument(name = "idArticulo") String idArticulo,
									@GraphQLArgument(name = "idUsuario") String idUsuario) {
		
		if (contenido != null && idUsuario != null && idArticulo != null) {
		
			Usuario usuario = usuarioSERVICE.getUsuarioById(Integer.parseInt(idUsuario));
			Articulo articulo = articuloSERVICE.getArticuloById(Integer.parseInt(idArticulo));
			
			Comentario comentario = new Comentario(contenido, usuario, articulo);
			
			if (comentarioSERVICE.existeComentario(comentario))
				return "ERROR";
			
			if (comentarioSERVICE.guardaComentario(comentario)) {
				
				return "Comentario guardado con éxito";
			}
		
		}
		
		return null;
	}
	
	@GraphQLMutation(name = "borraComentario")
	public String borraComentario (@GraphQLArgument(name = "idComentario") int idComentario) {
		
		comentarioSERVICE.eliminaComentario(idComentario);		
		return "Comentario eliminado con éxito";
	}
	
}
