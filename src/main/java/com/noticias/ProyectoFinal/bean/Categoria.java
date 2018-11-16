package com.noticias.ProyectoFinal.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import io.leangen.graphql.annotations.GraphQLQuery;

@Entity
@Table(name="categoria")
public class Categoria implements Serializable {

	private static final long serialVersionUID = -1421736542803315957L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idcategoria")
	private Integer idCategoria;
	
	private String nombre;
	private byte[] imagen;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "categoriaSuscrita")
	private List<Suscripcion> suscripciones = new ArrayList<Suscripcion>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "categoriaTag")
	private List<Tag> tags = new ArrayList<Tag>();
	
	public Categoria() {
		super();
	}
	
	public Categoria(String nombre) {
		
		this.nombre = nombre;
	}
	
	public Categoria(String nombre, byte[] imagen) {

		this.nombre = nombre;
		this.imagen = imagen;
	}
	
	//SETTERS & GETTERS

	@GraphQLQuery(name = "idCategoria")
	public Integer getIdCategoria() {
		return idCategoria;
	}
	
	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}
	
	@GraphQLQuery(name = "nombre")
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@GraphQLQuery(name = "imagen")
	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	@GraphQLQuery(name = "suscripciones")
	public List<Suscripcion> getSuscripciones() {
		return suscripciones;
	}

	public void setSuscripciones(List<Suscripcion> suscripciones) {
		this.suscripciones = suscripciones;
	}

	@GraphQLQuery(name = "tags")
	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
}
