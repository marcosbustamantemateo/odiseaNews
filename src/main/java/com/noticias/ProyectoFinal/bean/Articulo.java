package com.noticias.ProyectoFinal.bean;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import io.leangen.graphql.annotations.GraphQLQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="articulo")
public class Articulo implements Serializable {

	private static final long serialVersionUID = -5973795631370178423L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idarticulo")
	private Integer idArticulo;
	
	private String titulo;
	private byte[] imagen ;
	private String descripcion;	
	private String contenido;
	
	@Column(name="numvisitas")
	private Integer numVisitas;
	
	@ManyToOne
	@JoinColumn(name="idusuario")
	private Usuario usuarioAdmin;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "articulo")
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "articuloTag")
	private List<Tag> tags = new ArrayList<Tag>();
	
	public Articulo() {
		super();
	}

	public Articulo(String titulo, byte[] imagen, String descripcion, String contenido) {
		super();
		this.titulo = titulo;
		this.imagen = imagen;
		this.descripcion = descripcion;
		this.contenido = contenido;
		this.numVisitas = 0;
	}
	
	//SETTERS & GETTERS

	@GraphQLQuery(name = "idArticulo")
	public Integer getIdArticulo() {
		return idArticulo;
	}
	
	public void setIdArticulo(Integer idArticulo) {
		this.idArticulo = idArticulo;
	}
	
	@GraphQLQuery(name = "titulo")
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	@GraphQLQuery(name = "imagen")
	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	@GraphQLQuery(name = "descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@GraphQLQuery(name = "contenido")
	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	@GraphQLQuery(name = "numVisitas")
	public Integer getNumVisitas() {
		return numVisitas;
	}
	
	public void setNumVisitas(Integer numVisitas) {
		this.numVisitas = numVisitas;
	}
		
	@GraphQLQuery(name = "usuario")
	public Usuario getUsuarioAdmin() {
		return usuarioAdmin;
	}

	public void setUsuarioAdmin(Usuario usuarioAdmin) {
		this.usuarioAdmin = usuarioAdmin;
	}

	@GraphQLQuery(name = "comentarios")
	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	@GraphQLQuery(name = "tags")
	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
}
