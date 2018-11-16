package com.noticias.ProyectoFinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.noticias.ProyectoFinal.bean.Rol;
import com.noticias.ProyectoFinal.bean.UsuariosDetalles;
import com.noticias.ProyectoFinal.service.RolSERVICE;
import com.noticias.ProyectoFinal.service.UsuarioDetallesSERVICE;

@Controller
public class RolController {

	@Autowired
	RolSERVICE rolSERVICE;
	
	@Autowired
	UsuarioDetallesSERVICE usuarioDetallesSERVICE;
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/roles")
	public ModelAndView roles (@RequestParam(value="error", required = false) String error,
								@RequestParam(value="success", required = false) String success,
								Model model) {
		
		if (error != null) {
			model.addAttribute("error", error);
		}
		
		if (success != null) {
			model.addAttribute("success", success);
		}
				
		return new ModelAndView("roles", "listaRoles", rolSERVICE.listadoRoles());
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/info_rol")
	public ModelAndView info_rol (@RequestParam(name="idRol")String idRol) {
		
		return new ModelAndView("info_rol", "rol", rolSERVICE.getRolById(Integer.parseInt(idRol)));
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/nuevoRol")
	public String nuevoRol () {
		
		return "nuevoRol";
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/crearRol")
	public ModelAndView crearRol (@RequestParam(name="nombre")String nombre) {
		
		String nombreRol = "ROLE_" + nombre;
		
		Rol rol = new Rol(nombreRol.toUpperCase());
		
		if (rolSERVICE.existeRol(rol)) {
			return new ModelAndView("nuevoRol", "error", "Error: rol ya existente");
		}
		
		rolSERVICE.guardaRol(rol);
		
		return new ModelAndView("redirect:/roles", "success", "Info: rol creado con éxito");
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/actualizaRol")
	public ModelAndView actualizaRol (@RequestParam(name="idRol")String idRol, @RequestParam(name="nombre")String nombre) {
				
		Rol rol = rolSERVICE.getRolById(Integer.parseInt(idRol));		
		rol.setNombre(nombre.toUpperCase());
		
		rolSERVICE.actualizaRol(rol);
		
		return new ModelAndView("redirect:/roles", "success", "Info: rol creado con éxito");
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/elimina_rol")
	public ModelAndView eliminaRol (@RequestParam(name="idRol")String idRol, Model model) {
		
		Rol rol = rolSERVICE.getRolById(Integer.parseInt(idRol));
		
		if (rol.getNombre().equals("ROLE_ADMIN") || rol.getNombre().equals("ROLE_USER")) {
			
			return new ModelAndView("redirect:/roles", "error", "Error: No se puede borrar " + rol.getNombre());
		}
		
		if (!rol.getDetallesRol().isEmpty()) {
			
			for (UsuariosDetalles usuarioDetalle : rol.getDetallesRol())			
				usuarioDetallesSERVICE.eliminaUsuariosDetalles(usuarioDetalle.getIdUsuariosDetalles());
		}
		
		rolSERVICE.eliminaRol(rol.getIdRol());
		
		return new ModelAndView("redirect:/roles", "success", "Info: " + rol.getNombre() + " eliminado con éxito");
	}
}
