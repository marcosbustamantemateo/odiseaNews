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
@Table(name="tag")
public class Tag implements Serializable {

	private static final long serialVersionUID = -3303347235367638597L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtag")
	private Integer idTag;
	
	@ManyToOne
	@JoinColumn(name="idarticulo")
	private Articulo articuloTag;
	
	@ManyToOne
	@JoinColumn(name="idcategoria")
	private Categoria categoriaTag;

	//SETTERS & GETTERS
	
	@GraphQLQuery(name = "idTag")
	public Integer getIdTag() {
		return idTag;
	}

	public void setIdTag(Integer idTag) {
		this.idTag = idTag;
	}

	@GraphQLQuery(name = "articuloTag")
	public Articulo getArticuloTag() {
		return articuloTag;
	}

	public void setArticuloTag(Articulo articuloTag) {
		this.articuloTag = articuloTag;
	}

	@GraphQLQuery(name = "categoriaTag")
	public Categoria getCategoriaTag() {
		return categoriaTag;
	}

	public void setCategoriaTag(Categoria categoriaTag) {
		this.categoriaTag = categoriaTag;
	}
	
}
