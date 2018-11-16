package com.noticias.ProyectoFinal.resolvers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noticias.ProyectoFinal.bean.Categoria;
import com.noticias.ProyectoFinal.bean.Suscripcion;
import com.noticias.ProyectoFinal.bean.Usuario;
import com.noticias.ProyectoFinal.service.CategoriaSERVICE;
import com.noticias.ProyectoFinal.service.SuscripcionSERVICE;
import com.noticias.ProyectoFinal.service.UsuarioSERVICE;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;

@Component
public class SuscripcionQuery {

	@Autowired
	SuscripcionSERVICE suscripcionSERVICE;
	
	@Autowired
	UsuarioSERVICE usuarioSERVICE;
	
	@Autowired
	CategoriaSERVICE categoriaSERVICE;
	
	@GraphQLMutation(name = "creaSuscripcion")
	public String creaSuscripcion (@GraphQLArgument(name = "idUsuario") String idUsuario, @GraphQLArgument(name = "idCategoria") String idCategoria) {
				
		if (idUsuario != null && idCategoria != null) {
			
			Usuario usuario = usuarioSERVICE.getUsuarioById(Integer.parseInt(idUsuario));
			Categoria categoria = categoriaSERVICE.getCategoriaById(Integer.parseInt(idCategoria));
			
			Suscripcion suscripcion = new Suscripcion(usuario, categoria);
			suscripcionSERVICE.guardaSuscripcion(suscripcion);
			
			return "Suscripción guardada con éxito";
		}
		
		return null;
	}
	
	@GraphQLMutation(name = "eliminaSuscripcion")
	public String eliminaSuscripcion (@GraphQLArgument(name = "idSuscripcion") String idSuscripcion) {
		
		if (idSuscripcion != null) {
			
			suscripcionSERVICE.eliminaSuscripcion(Integer.parseInt(idSuscripcion));
			return "Suscripcion eliminada con éxito";
		}
		
		return null;
	}
	
	@GraphQLQuery(name = "getCategoriasSuscritas")
	public List<Suscripcion> getCategoriasSuscritas (@GraphQLArgument(name = "idUsuario") int idUsuario) {
		
	
		return suscripcionSERVICE.listadoSuscripcionesPorUsuario(idUsuario);
	}
	
	@SuppressWarnings("unused")
	@GraphQLQuery(name = "getCategoriasNoSuscritas")
	public List<Categoria> getCategoriasNoSuscritas (@GraphQLArgument(name = "listaIdCategoriasSuscritas") List<Integer> listaIdCategoriasSuscritas) {
		
		if (listaIdCategoriasSuscritas.isEmpty()) {
			
			return categoriaSERVICE.listadoCategorias();
		}
		
		if (listaIdCategoriasSuscritas != null) {
			
			return categoriaSERVICE.getsCategoriasNotInIdCategoria(listaIdCategoriasSuscritas);
		}
		
		return null;		
	}
		
}
