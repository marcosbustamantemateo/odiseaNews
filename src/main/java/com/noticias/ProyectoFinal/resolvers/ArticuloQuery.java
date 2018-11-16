package com.noticias.ProyectoFinal.resolvers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.noticias.ProyectoFinal.bean.Articulo;

import com.noticias.ProyectoFinal.service.ArticuloSERVICE;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;

@Component
public class ArticuloQuery {

	@Autowired
	ArticuloSERVICE articuloSERVICE;
	
	@GraphQLQuery(name = "getArticuloById")
	public Articulo getArticuloById (@GraphQLArgument(name = "idArticulo") int idArticulo) {
		
		return articuloSERVICE.getArticuloById(idArticulo);
	}
	
	@GraphQLQuery(name = "listadoArticulos")
	public List<Articulo> listadoArticulos () {
		
		return articuloSERVICE.listadoArticulosFront();
	}
	
	@GraphQLQuery(name = "listadoArticulosPorVisitas")
	public List<Articulo> listadoArticulosPorVisitas () {
		
		return articuloSERVICE.listadoArticulosPorVisitas();
	}
	
	@GraphQLQuery(name = "listadoArticulosPorTitulo")
	public List<Articulo> listadoArticulosPorTitulo (@GraphQLArgument(name = "titulo") String titulo) {
		
		return articuloSERVICE.getsArticulosByTitulo(titulo);
	}
	
	@GraphQLQuery(name = "ultimoArticulo")
	public Articulo ultimoArticulo () {
		
		return articuloSERVICE.getUltimoArticulo();
	}
	
	@GraphQLMutation(name = "aumentarNumVisitas")
	public String aumentarNumVisitas (@GraphQLArgument(name = "idArticulo") int idArticulo) {
		
		Articulo articulo = articuloSERVICE.getArticuloById(idArticulo);	
		articulo.setNumVisitas(articulo.getNumVisitas() + 1);
		articuloSERVICE.actualizaArticulo(articulo);
		
		return "Número de visitas aumentado con éxito";
	}
	
	@GraphQLQuery(name = "muestraImagenArticulo")
	public byte[] muestraImagenArticulo (@GraphQLArgument(name = "idArticulo") int idArticulo) {
		
		Articulo articulo = articuloSERVICE.getArticuloById(idArticulo);
	    return articulo.getImagen();
	}
	
}
