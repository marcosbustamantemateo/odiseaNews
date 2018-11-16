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
@Table(name="usuariosdetalles")
public class UsuariosDetalles implements Serializable {

	private static final long serialVersionUID = 5946743617750181622L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idusuariosdetalles")
	private Integer idUsuariosDetalles;
	
	@ManyToOne
	@JoinColumn(name="idusuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="idrol")
	private Rol rol;
	
	//SETTERS & GETTERS

	@GraphQLQuery(name = "idUsuariosDetalles")
	public Integer getIdUsuariosDetalles() {
		return idUsuariosDetalles;
	}

	public void setIdUsuariosDetalles(Integer idUsuariosDetalles) {
		this.idUsuariosDetalles = idUsuariosDetalles;
	}

	@GraphQLQuery(name = "usuario")
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@GraphQLQuery(name = "rol")
	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	
	
}
