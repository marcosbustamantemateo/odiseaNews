package com.noticias.ProyectoFinal.resolvers;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.noticias.ProyectoFinal.bean.Articulo;
import com.noticias.ProyectoFinal.bean.Comentario;
import com.noticias.ProyectoFinal.bean.Suscripcion;
import com.noticias.ProyectoFinal.bean.Tag;
import com.noticias.ProyectoFinal.bean.Usuario;
import com.noticias.ProyectoFinal.bean.UsuariosDetalles;
import com.noticias.ProyectoFinal.service.ArticuloSERVICE;
import com.noticias.ProyectoFinal.service.ComentarioSERVICE;
import com.noticias.ProyectoFinal.service.EmailSERVICE;
import com.noticias.ProyectoFinal.service.RolSERVICE;
import com.noticias.ProyectoFinal.service.SuscripcionSERVICE;
import com.noticias.ProyectoFinal.service.TagSERVICE;
import com.noticias.ProyectoFinal.service.UsuarioDetallesSERVICE;
import com.noticias.ProyectoFinal.service.UsuarioSERVICE;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;

@Component
public class UsuarioQuery {
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Value("${app.url.api}")
	String urlApi;
	
	@Autowired
	UsuarioSERVICE usuarioSERVICE;
	
	@Autowired
	RolSERVICE rolSERVICE;
	
	@Autowired
	EmailSERVICE emailSERVICE;
	
	@Autowired
	UsuarioDetallesSERVICE usuariosDetallesSERVICE;
	
	@Autowired
	ComentarioSERVICE comentarioSERVICE;
	
	@Autowired
	TagSERVICE tagSERVICE;
	
	@Autowired
	ArticuloSERVICE articuloSERVICE;
	
	@Autowired
	SuscripcionSERVICE suscripcionSERVICE;
	
	@GraphQLMutation(name = "creaUsuario")
	public String creaUsuario (@GraphQLArgument(name = "nombre") String nombre, @GraphQLArgument(name = "apellidos") String apellidos, @GraphQLArgument(name = "edad") String edad,
								@GraphQLArgument(name = "email") String email, @GraphQLArgument(name = "login") String login, @GraphQLArgument(name = "password") String password) {
		
		if (nombre != null && apellidos != null && edad != null && email != null && login != null && password != null) {
			
			Usuario usuario = new Usuario(nombre, apellidos, Integer.parseInt(edad), email, login, password);
			
			if (usuarioSERVICE.existeUsuario(usuario))			
				return "ERROR";
			
			if (usuarioSERVICE.guardaUsuario(usuario)) {
							
				UsuariosDetalles detallesUsuario = new UsuariosDetalles();
				detallesUsuario.setUsuario(usuario);
				detallesUsuario.setRol(rolSERVICE.getRolByName("ROLE_USER"));
				
				usuariosDetallesSERVICE.guardaUsuariosDetalles(detallesUsuario);
				
				String uuidEncode = Base64.getEncoder().encodeToString(usuario.getUID().getBytes());
				
				emailSERVICE.enviaEmail
				(
					usuario.getEmail(), 
					"Activación de cuenta", 
					"Estimado " + usuario.getNombre() + " : Haga click en este enlace para activar su cuenta -> " +  urlApi + "/activateAccount?user=" + uuidEncode
				);
				
				return "Usuario guardado con éxito";
			}		
		}
		
		return null;
	}
	
	@GraphQLMutation(name = "actualizaUsuario")
	public Usuario actualizaUsuario (@GraphQLArgument(name = "idUsuario") String idUsuario, @GraphQLArgument(name = "nombre") String nombre, @GraphQLArgument(name = "apellidos") String apellidos, @GraphQLArgument(name = "edad") String edad,
			@GraphQLArgument(name = "email") String email, @GraphQLArgument(name = "login") String login, @GraphQLArgument(name = "password") String password) {
		
		if (idUsuario != null  && nombre != null && apellidos != null && edad != null && email != null && login != null && password != null) {
		
			Usuario usuario = usuarioSERVICE.getUsuarioById(Integer.parseInt(idUsuario));
			
			usuario.setNombre(nombre);
			usuario.setApellidos(apellidos);
			usuario.setEdad(Integer.parseInt(edad));
			usuario.setEmail(email);
			usuario.setLogin(login);
			usuario.setPassword(passwordEncoder.encode(password));
			
			usuarioSERVICE.actulizaUsuario(usuario);
			
			return usuario;
		}
			
		return null;
	}
	
	@GraphQLMutation(name = "eliminaUsuario")
	public String eliminaUsuario (@GraphQLArgument(name = "idUsuario") String idUsuario) {
		
		if (idUsuario != null) {
		
			Usuario usuario = usuarioSERVICE.getUsuarioById(Integer.parseInt(idUsuario));
			
			if (!usuario.getComentarios().isEmpty()) {
				
				for (Comentario comentario : usuario.getComentarios())
					comentarioSERVICE.eliminaComentario(comentario.getIdComentario());
			}
			
			if (!usuario.getDetallesUsuario().isEmpty()) {
				
				for (UsuariosDetalles detallesUsuario : usuario.getDetallesUsuario())
					usuariosDetallesSERVICE.eliminaUsuariosDetalles(detallesUsuario.getIdUsuariosDetalles());
			}
			
			if (!usuario.getSuscripciones().isEmpty()) {
				
				for (Suscripcion suscripcion : usuario.getSuscripciones())
					suscripcionSERVICE.eliminaSuscripcion(suscripcion.getIdSuscripcion());
			}
			
			if (!usuario.getArticulos().isEmpty()) {
				
				for (Articulo articulo : usuario.getArticulos()) {
					
					for (Tag tag : articulo.getTags()) 
						tagSERVICE.eliminaTag(tag.getIdTag());
					
					articuloSERVICE.eliminaArticulo(articulo.getIdArticulo());
				}
			}
				
			usuarioSERVICE.eliminaUsuario(usuario.getIdUsuario());
			return "Usuario eliminado con éxito";
		}
		
		return null;
	}
	
	@GraphQLMutation(name = "getUsuarioById")
	public Usuario getUsuarioById (@GraphQLArgument(name = "idUsuario") int idUsuario) {
		
		return usuarioSERVICE.getUsuarioById(idUsuario);
	}
	
	@GraphQLQuery(name = "compruebaUsuario")
	public Usuario compruebaUsuario (@GraphQLArgument(name = "login") String login, @GraphQLArgument(name = "password") String password) {
		
		Usuario usuario = usuarioSERVICE.compruebaUsuario(login, password);
		
		if (usuario != null) {
		
			if (usuario.getEnabled())
				return usuario;
			else 
				return new Usuario(-1, "", "", -1, "", "", ""); 
		}
		
		return null;
	}
}
