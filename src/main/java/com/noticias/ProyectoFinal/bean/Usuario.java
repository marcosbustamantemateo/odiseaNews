package com.noticias.ProyectoFinal.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import io.leangen.graphql.annotations.GraphQLQuery;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = -2351538740850389462L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idusuario")
	private Integer idUsuario;	
	
	private String nombre;		
	private String apellidos;
	private Integer edad;
	private String email;
	private String login;	
	private String password;
	private Boolean enabled;
	
	@Column(name="uid")
	private String UID;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "usuario")
	private List<UsuariosDetalles> detallesUsuario = new ArrayList<UsuariosDetalles>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "usuarioEstandar")
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "usuarioAdmin")
	private List<Articulo> articulos = new ArrayList<Articulo>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "usuarioSuscriptor")
	private List<Suscripcion> suscripciones = new ArrayList<Suscripcion>();
	
	public Usuario() {
		super();
	}

	public Usuario(String nombre, String apellidos, Integer edad, String email, String login, String password) {
		super();
		
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
		this.email = email;
		this.login = login;
		this.password = password;
		this.enabled = false;
		this.UID = UUID.randomUUID().toString();
	}
	
	public Usuario(Integer idUsuario, String nombre, String apellidos, Integer edad, String email, String login, String password) {
		super();
		
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
		this.email = email;
		this.login = login;
		this.password = password;
		this.enabled = false;
		this.UID = UUID.randomUUID().toString();
	}
	
	//SETTERS & GETTERS

	@GraphQLQuery(name = "idUsuario")
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	@GraphQLQuery(name = "nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@GraphQLQuery(name = "apellidos")
	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	@GraphQLQuery(name = "edad")
	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	@GraphQLQuery(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@GraphQLQuery(name = "login")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@GraphQLQuery(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@GraphQLQuery(name = "enabled")
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
		
	@GraphQLQuery(name = "uuid")
	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	@GraphQLQuery(name = "detallesUsuario")
	public List<UsuariosDetalles> getDetallesUsuario() {
		return detallesUsuario;
	}

	public void setDetallesUsuario(List<UsuariosDetalles> detallesUsuario) {
		this.detallesUsuario = detallesUsuario;
	}

	@GraphQLQuery(name = "comentarios")
	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	@GraphQLQuery(name = "articulos")
	public List<Articulo> getArticulos() {
		return articulos;
	}

	public void setArticulos(List<Articulo> articulos) {
		this.articulos = articulos;
	}

	@GraphQLQuery(name = "suscripciones")
	public List<Suscripcion> getSuscripciones() {
		return suscripciones;
	}

	public void setSuscripciones(List<Suscripcion> suscripciones) {
		this.suscripciones = suscripciones;
	}
	
	
}
