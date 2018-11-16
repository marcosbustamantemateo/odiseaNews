package com.noticias.ProyectoFinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.noticias.ProyectoFinal.service.EmailSERVICE;
import com.noticias.ProyectoFinal.service.UsuarioSERVICE;

@Controller
public class EmailController {

	@Autowired
	UsuarioSERVICE usuarioSERVICE;
	
	@Autowired
	EmailSERVICE emailSERVICE;
	
	@Secured("ROLE_ADMIN")
	@GetMapping("envio_email")
	public String envio_email (@RequestParam(value="success", required = false) String success, Model model,
								@RequestParam(value="idUsuario", required = false) String idUsuario) {
				
		if (success != null) {
			model.addAttribute("success", success);
		}
		
		if (idUsuario != null) {
			
			model.addAttribute("usuario", usuarioSERVICE.getUsuarioById(Integer.parseInt(idUsuario)));
		}
		
		return "envio_email";
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("enviarEmail")
	public ModelAndView enviarEmail (@RequestParam(name="destino")String destino, @RequestParam(name="asunto")String asunto,
								@RequestParam(name="contenido")String contenido) {
					
		emailSERVICE.enviaEmail(destino, asunto, contenido);
		
		return new ModelAndView("redirect:/envio_email", "success", "Info: email enviado con éxito");
	}
}
