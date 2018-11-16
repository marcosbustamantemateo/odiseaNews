package com.noticias.ProyectoFinal.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.leangen.graphql.annotations.GraphQLQuery;

@Entity
@Table(name="comentario")
public class Comentario implements Serializable {

	private static final long serialVersionUID = 3570744299882556422L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idcomentario")
	private Integer idComentario;	
	
	private String contenido;
	
	@ManyToOne
	@JoinColumn(name="idusuario")
	private Usuario usuarioEstandar;
	
	@ManyToOne
	@JoinColumn(name="idarticulo")
	private Articulo articulo;
	
	public Comentario() {
		super();
	}
	
	public Comentario(String contenido, Usuario usuarioEstandar, Articulo articulo) {
		super();
		this.contenido = contenido;
		this.usuarioEstandar = usuarioEstandar;
		this.articulo = articulo;
	}
	
	//SETTERS & GETTERS

	@GraphQLQuery(name = "idComentario")
	public Integer getIdComentario() {
		return idComentario;
	}
	
	public void setIdComentario(Integer idComentario) {
		this.idComentario = idComentario;
	}
	
	@GraphQLQuery(name = "contenido")
	public String getContenido() {
		return contenido;
	}
	
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	@GraphQLQuery(name = "usuarioEstandar")
	public Usuario getUsuarioEstandar() {
		return usuarioEstandar;
	}

	public void setUsuarioEstandar(Usuario usuarioEstandar) {
		this.usuarioEstandar = usuarioEstandar;
	}

	@GraphQLQuery(name = "articulo")
	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}
	
}
