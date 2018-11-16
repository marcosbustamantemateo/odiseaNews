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
@Table(name="rol")
public class Rol implements Serializable {

	private static final long serialVersionUID = 6342343740091327523L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idrol")
	private Integer idRol;
	
	private String nombre;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "rol")
	private List<UsuariosDetalles> detallesRol = new ArrayList<UsuariosDetalles>();
	
	public Rol() {
		super();
	}
	
	public Rol(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	//SETTERS & GETTERS

	@GraphQLQuery(name = "idRol")
	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	@GraphQLQuery(name = "nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@GraphQLQuery(name = "detallesUsuarios")
	public List<UsuariosDetalles> getDetallesRol() {
		return detallesRol;
	}

	public void setDetallesRol(List<UsuariosDetalles> detallesRol) {
		this.detallesRol = detallesRol;
	}	
	
}
