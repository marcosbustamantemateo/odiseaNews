package com.noticias.ProyectoFinal.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.noticias.ProyectoFinal.bean.Articulo;
import com.noticias.ProyectoFinal.bean.Comentario;
import com.noticias.ProyectoFinal.bean.Rol;
import com.noticias.ProyectoFinal.bean.Suscripcion;
import com.noticias.ProyectoFinal.bean.Tag;
import com.noticias.ProyectoFinal.bean.Usuario;
import com.noticias.ProyectoFinal.bean.UsuariosDetalles;
import com.noticias.ProyectoFinal.service.ArticuloSERVICE;
import com.noticias.ProyectoFinal.service.ComentarioSERVICE;
import com.noticias.ProyectoFinal.service.FacebookSERVICE;
import com.noticias.ProyectoFinal.service.RolSERVICE;
import com.noticias.ProyectoFinal.service.SuscripcionSERVICE;
import com.noticias.ProyectoFinal.service.TagSERVICE;
import com.noticias.ProyectoFinal.service.UsuarioDetallesSERVICE;
import com.noticias.ProyectoFinal.service.UsuarioSERVICE;

@Controller
public class UsuariosController {
	
	@Autowired
	UsuarioSERVICE usuarioSERVICE;
	
	@Autowired
	RolSERVICE rolSERVICE;
	
	@Autowired
	ComentarioSERVICE comentarioSERVICE;
	
	@Autowired
	SuscripcionSERVICE suscripcionSERVICE;
	
	@Autowired
	UsuarioDetallesSERVICE usuariosDetallesSERVICE;
	
	@Autowired
	ArticuloSERVICE articuloSERVICE;
	
	@Autowired
	TagSERVICE tagSERVICE;

	@Autowired
    FacebookSERVICE facebookSERVICE;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/usuarios")
	public ModelAndView usuarios (@RequestParam(value="error", required = false) String error,
								  @RequestParam(value="success", required = false) String success,
								  Model model) {
		
		if (error != null) {
			model.addAttribute("error", error);
		}
		
		if (success != null) {
			model.addAttribute("success", success);
		}
			
		List<Integer> listaIdUsuarios = new ArrayList<Integer>();
		
		for (Usuario usuario : usuarioSERVICE.listadoUsuarios()) {
			
			listaIdUsuarios.add(usuario.getIdUsuario());
		}
		
		model.addAttribute("listaIdUsuarios", listaIdUsuarios);
		
		return new ModelAndView("usuarios", "listaUsuarios", usuarioSERVICE.listadoUsuarios());
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/filtraUsuarios")
	public ModelAndView filtraUsuarios (@RequestParam(value="login", required = false) String login, Model model) {
		
		List<Integer> listaIdUsuarios = new ArrayList<Integer>();
		
		for (Usuario usuario : usuarioSERVICE.listadoUsuarios()) {
			
			listaIdUsuarios.add(usuario.getIdUsuario());
		}
		
		model.addAttribute("listaIdUsuarios", listaIdUsuarios);
		
		model.addAttribute("username", login);
		
		if (login == "") 			
			return new ModelAndView("usuarios", "listaUsuarios", usuarioSERVICE.listadoUsuarios());			
		else 			
			return new ModelAndView("usuarios", "listaUsuarios", usuarioSERVICE.getUsuarioByUsername(login));			
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/info_usuario")
	public ModelAndView info_usuario (@RequestParam(name="idUsuario")String idUsuario, Model model,
										@RequestParam(name="error", required=false)String error){
		
		List<UsuariosDetalles> listadodetallesUsuario = usuariosDetallesSERVICE.getUsuariosDetallesByIdUsuario(Integer.parseInt(idUsuario));
		
		if (error != null)
			model.addAttribute("error", error);
		
		if (listadodetallesUsuario.size() > 0) {
			
			List<Rol> listadoRolesCheck = new ArrayList<Rol>();
			List<Rol> listadoRolesNoCheck = new ArrayList<Rol>();
			
			List<Integer> listadoIdRoles = new ArrayList<Integer>();
			
			for (UsuariosDetalles detallesUsuario : listadodetallesUsuario) {
				
				listadoRolesCheck.add(detallesUsuario.getRol());
				listadoIdRoles.add(detallesUsuario.getRol().getIdRol());
			}
			
			listadoRolesNoCheck = rolSERVICE.getsRolesNotInIdRol(listadoIdRoles);
			
			model.addAttribute("listadoRolesCheck", listadoRolesCheck);
			model.addAttribute("listadoRolesNoCheck", listadoRolesNoCheck);
			
		} else {
			
			model.addAttribute("listadoRolesNoCheck", rolSERVICE.listadoRoles());
		}
		
		return new ModelAndView("info_usuario", "usuario", usuarioSERVICE.getUsuarioById(Integer.parseInt(idUsuario)));
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/nuevoUsuario")
	public String nuevoUsuario (Model model) {
			
		model.addAttribute("listaRoles", rolSERVICE.listadoRoles());	
		model.addAttribute("urlFacebook", facebookSERVICE.createFacebookAuthorizationURL());
		
	    return "nuevoUsuario";
	}

	@GetMapping("/facebook")
	public ModelAndView createFacebookAccessToken(@RequestParam(name="code", required=false) String code, 
													@RequestParam(name="error", required=false) String error,
													@RequestParam(name="error_code", required=false) String error_code,
													@RequestParam(name="error_description", required=false) String error_description,
													@RequestParam(name="error_reason", required=false) String error_reason){
		
		if (error != null)
			return new ModelAndView("redirect:/usuarios", "error", "Error: " + error_reason);
		
		Usuario usuario = facebookSERVICE.insertaUsuarioFacebook(facebookSERVICE.getData(facebookSERVICE.createFacebookAccessToken(code)));
		
		if (usuario == null) 
			return new ModelAndView("redirect:/usuarios", "error", "Error: Login y/o Email ya existente");
			
		UsuariosDetalles detallesUsuario = new UsuariosDetalles();
		detallesUsuario.setUsuario(usuario);
		detallesUsuario.setRol(rolSERVICE.getRolByName("ROLE_USER"));
		
		usuariosDetallesSERVICE.guardaUsuariosDetalles(detallesUsuario);
		
		return new ModelAndView("redirect:/usuarios", "success", "Info: usuario de facebook creado con éxito");
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/crearUsuario")
	public ModelAndView crearUsuario (@RequestParam(name="nombre")String nombre, @RequestParam(name="apellidos")String apellidos, 
			@RequestParam(name="edad")String edad, @RequestParam(name="email")String email, @RequestParam(name="login")String login, 
			@RequestParam(name="password1")String password1, @RequestParam(name="password2")String password2,
			@RequestParam(name="roles", required=false) List<String> roles,
			Model model) {
		
		Usuario usuario = new Usuario(nombre, apellidos, Integer.parseInt(edad), email, login, password1);
		model.addAttribute("listaRoles", rolSERVICE.listadoRoles());
		
		if(!password1.equals(password2)) {
			
			return new ModelAndView("nuevoUsuario", "error", "Error: La password no coincide");
		}
		
		if (usuarioSERVICE.existeUsuario(usuario)) {
			
			return new ModelAndView("nuevoUsuario", "error", "Error: Login y/o email existente");
		}
		
		if (roles == null) {
			
			return new ModelAndView("nuevoUsuario", "error", "Error: No puede existir usuario sin rol");
		}
		
		usuarioSERVICE.guardaUsuario(usuario);
	
		if (roles != null) 	{	
			
			for (String idRol : roles) {
				UsuariosDetalles usuariosDetalles = new UsuariosDetalles();
				usuariosDetalles.setUsuario(usuario);
				usuariosDetalles.setRol(rolSERVICE.getRolById(Integer.parseInt(idRol)));
				usuariosDetallesSERVICE.guardaUsuariosDetalles(usuariosDetalles);
			}
		}
			
		return new ModelAndView("redirect:/usuarios", "success", "Info: usuario creado con éxito");
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/actualizaUsuario")
	public ModelAndView actualizaUsuario (@RequestParam(name="nombre")String nombre, @RequestParam(name="apellidos")String apellidos, 
			@RequestParam(name="edad")String edad, @RequestParam(name="email")String email, @RequestParam(name="login")String login, 
			@RequestParam(name="password1")String password1, @RequestParam(name="password2")String password2,
			@RequestParam(name="rolesCheck", required=false) List<String> rolesCheck, @RequestParam(name="idUsuario")String idUsuario,
			Model model, @RequestParam(name="rolesNoCheck", required=false) List<String> rolesNoCheck) {
		
		Usuario usuario = usuarioSERVICE.getUsuarioById(Integer.parseInt(idUsuario));
		
		if (password1 != "" && password2 != "") {		
		
			if(!password1.equals(password2)) 							
				return new ModelAndView("redirect:/info_usuario?idUsuario=" + usuario.getIdUsuario(), "error", "Error: La password no coincide");
			else 				
				usuario.setPassword(passwordEncoder.encode(password1));	
		}
		
		if (rolesCheck == null && rolesNoCheck == null) {
			
			return new ModelAndView("redirect:/info_usuario?idUsuario=" + usuario.getIdUsuario(), "error", "Error: No puede existir usuario sin rol");
		}
		
		usuario.setNombre(nombre);
		usuario.setApellidos(apellidos);
		usuario.setEdad(Integer.parseInt(edad));
		usuario.setLogin(login);	
		usuario.setEmail(email);				
		
		//Actualizo usuario
		usuarioSERVICE.actulizaUsuario(usuario);
		
		//Elimino todos los roles antiguos asociados a este articulo
		List<UsuariosDetalles> listadoDetallesUsuario = usuariosDetallesSERVICE.getUsuariosDetallesByIdUsuario(Integer.parseInt(idUsuario));		
		for (UsuariosDetalles usuariosDetalles : listadoDetallesUsuario)
			usuariosDetallesSERVICE.eliminaUsuariosDetalles(usuariosDetalles.getIdUsuariosDetalles());
		
		if (rolesCheck != null) {
			
			for (String idRol : rolesCheck) {
				
				UsuariosDetalles detallesUsuario = new UsuariosDetalles();
				detallesUsuario.setUsuario(usuario);
				detallesUsuario.setRol(rolSERVICE.getRolById(Integer.parseInt(idRol)));
				usuariosDetallesSERVICE.guardaUsuariosDetalles(detallesUsuario);
			}
		}
		
		if (rolesNoCheck != null) {
			
			for (String idRol : rolesNoCheck) {
				
				UsuariosDetalles detallesUsuario = new UsuariosDetalles();
				detallesUsuario.setUsuario(usuario);
				detallesUsuario.setRol(rolSERVICE.getRolById(Integer.parseInt(idRol)));
				usuariosDetallesSERVICE.guardaUsuariosDetalles(detallesUsuario);
			}
		}
			
		return new ModelAndView("redirect:/usuarios", "success", "Info: usuario actualizado con éxito");
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/elimina_usuario", method = RequestMethod.GET)
	public ModelAndView eliminaUsuario(@RequestParam(name="idUsuario")String idUsuario, Authentication authentication) {
	    
		Usuario usuario = usuarioSERVICE.getUsuarioById(Integer.parseInt(idUsuario));		
			
		if (usuario == usuarioSERVICE.getUsuarioByLogin(authentication.getName()))
			return new ModelAndView("redirect:/usuarios", "error", "Error: No se puede borrar a su mismo usuario ");
		
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
	    return new ModelAndView("redirect:/usuarios", "success", "Info: usuario " + usuario.getLogin() + " eliminado con éxito");
	}
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/desactiva_usuario", method = RequestMethod.GET)
	public ModelAndView desactivaUsuario(@RequestParam(name="idUsuario")String idUsuario, Model model, Authentication authentication) {
	    
		Usuario usuario = usuarioSERVICE.getUsuarioById(Integer.parseInt(idUsuario));
		Usuario usuario2 = usuarioSERVICE.getUsuarioByLogin(authentication.getName());
		
		if (usuario.getLogin().equals(usuario2.getLogin())) {
			
			model.addAttribute("listaUsuarios", usuarioSERVICE.listadoUsuarios());
			return new ModelAndView("redirect:/usuarios", "error", "Error: no se puede desactivar a su mismo usuario");
		}
		
		usuario.setEnabled(false);	
		usuarioSERVICE.actulizaUsuario(usuario);
		model.addAttribute("listaUsuarios", usuarioSERVICE.listadoUsuarios());
		
		return new ModelAndView("redirect:/usuarios", "success", "Info: usuario " + usuario.getLogin() + " desactivado con éxito");
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/activa_usuario", method = RequestMethod.GET)
	public ModelAndView activaUsuario(@RequestParam(name="idUsuario")String idUsuario, Model model) {
	    
		Usuario usuario = usuarioSERVICE.getUsuarioById(Integer.parseInt(idUsuario));
		
		usuario.setEnabled(true);
		usuarioSERVICE.actulizaUsuario(usuario);
		model.addAttribute("listaUsuarios", usuarioSERVICE.listadoUsuarios());
		
		return new ModelAndView("redirect:/usuarios", "success", "Info: usuario " + usuario.getLogin() + " activado con éxito");
	}
	
	@GetMapping("/activateAccount")
	public String resetPassword (@RequestParam(value="user", required = false)String uuidEncode, Model model) throws UnsupportedEncodingException {
		
		byte[] decodeString = Base64.getDecoder().decode(uuidEncode);
		String uuid = new String(decodeString, "UTF-8");
		
		Usuario usuario = usuarioSERVICE.getUsuarioByUUID(uuid);
	
		usuario.setEnabled(true);
		usuarioSERVICE.actulizaUsuario(usuario);
		
		return "userActivated";
	}

}
