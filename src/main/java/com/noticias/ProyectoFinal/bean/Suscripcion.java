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
@Table(name="suscripcion")
public class Suscripcion implements Serializable {

	private static final long serialVersionUID = 4952408077583257971L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idsuscripcion")
	private Integer idSuscripcion;
	
	@ManyToOne
	@JoinColumn(name="idusuario")
	private Usuario usuarioSuscriptor;
	
	@ManyToOne
	@JoinColumn(name="idcategoria")
	private Categoria categoriaSuscrita;

	public Suscripcion() {
		super();
	}
	
	public Suscripcion(Usuario usuarioSuscriptor, Categoria categoriaSuscrita) {
		super();
		this.usuarioSuscriptor = usuarioSuscriptor;
		this.categoriaSuscrita = categoriaSuscrita;
	}
	
	//SETTERS & GETTERS

	@GraphQLQuery(name = "idSuscripcion")
	public Integer getIdSuscripcion() {
		return idSuscripcion;
	}

	public void setIdSuscripcion(Integer idSuscripcion) {
		this.idSuscripcion = idSuscripcion;
	}

	@GraphQLQuery(name = "usuarioSuscriptor")
	public Usuario getUsuarioSuscriptor() {
		return usuarioSuscriptor;
	}

	public void setUsuarioSuscriptor(Usuario usuarioSuscriptor) {
		this.usuarioSuscriptor = usuarioSuscriptor;
	}

	@GraphQLQuery(name = "categoriaSuscrita")
	public Categoria getCategoriaSuscrita() {
		return categoriaSuscrita;
	}

	public void setCategoriaSuscrita(Categoria categoriaSuscrita) {
		this.categoriaSuscrita = categoriaSuscrita;
	}
	
}
