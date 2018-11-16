package com.noticias.ProyectoFinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.noticias.ProyectoFinal.service.ArticuloSERVICE;
import com.noticias.ProyectoFinal.service.CategoriaSERVICE;
import com.noticias.ProyectoFinal.service.ComentarioSERVICE;
import com.noticias.ProyectoFinal.service.FacebookSERVICE;
import com.noticias.ProyectoFinal.service.SuscripcionSERVICE;
import com.noticias.ProyectoFinal.service.UsuarioSERVICE;

@Controller
public class HomeController {
	
	@Autowired
	UsuarioSERVICE usuarioService;
	
	@Autowired
	ComentarioSERVICE comentarioService;
	
	@Autowired
	SuscripcionSERVICE suscripcionSERVICE;
	
	@Autowired
	ArticuloSERVICE articuloSERVICE;
	
	@Autowired
	CategoriaSERVICE categoriaSERVICE;
	
	@Autowired
    FacebookSERVICE facebookSERVICE;
	
	@Secured("ROLE_ADMIN")
	@GetMapping({"/", "home"})
	public String admin () {
				
		return "redirect:/admin";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/admin")
	public String home (Model model, Authentication authentication, @RequestParam(value="nombreFacebook", required = false) String nombreFacebook) {
				
		model.addAttribute("totalUsuarios", usuarioService.cuentaUsuarios());
		model.addAttribute("totalComentarios", comentarioService.cuentaComentarios());
		model.addAttribute("totalSuscripciones", suscripcionSERVICE.cuentaSuscripciones());
		model.addAttribute("totalArticulos", articuloSERVICE.cuentaArticulos());
		model.addAttribute("totalCategorias", categoriaSERVICE.cuentaCategorias());
		model.addAttribute("nombreFacebook", nombreFacebook);
		
		if (authentication != null)
			model.addAttribute("usuario", usuarioService.getUsuarioByLogin(authentication.getName()));
		
		return "admin";
	}


}
