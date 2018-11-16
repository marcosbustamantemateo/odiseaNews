package com.noticias.ProyectoFinal.controllers;

import java.security.Principal;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.noticias.ProyectoFinal.bean.Usuario;
import com.noticias.ProyectoFinal.service.EmailSERVICE;
import com.noticias.ProyectoFinal.service.UsuarioSERVICE;

import java.io.UnsupportedEncodingException;

@Controller
public class LoginController {

	@Autowired
	EmailSERVICE emailSERVICE;
	
	@Autowired
	UsuarioSERVICE usuarioSERVICE;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Value("${app.url.api}")
	String urlApi;
	
	@Value("${server.port}")
	String puerto;
	
	@GetMapping("/login")
	public String login (@RequestParam(value="error", required = false) String error, 
						 @RequestParam(value="logout", required = false) String logout, 
						 Model model, Principal principal, RedirectAttributes flash) {
				
		if (principal != null) {
			
			flash.addFlashAttribute("info", "Info: Ya ha iniciado sesión anteriormente");
			return "redirect:/home";
		}	
		
		if (error != null) {
			model.addAttribute("error", "Error: Nombre de usuario o contraseña incorrecta");
		}
		
		if (logout != null) {
			model.addAttribute("success", "Info: Sesión cerrada");
		}
		
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout (Principal principal) {
				
		principal = null;		
		return "login";
	}
	
	@GetMapping("/forgotPassword")
	public String forgotPassword (@RequestParam(value="error", required = false) String error, Model model) {
				
		if (error != null) {
			model.addAttribute("error", error);
		}
		
		return "forgotPassword";
	}
	
	@PostMapping("/enviarCorreo4Password")
	public ModelAndView enviarCorreo4Password (@RequestParam(name="email")String email) {
		
		String uuid = "";
		Boolean encuentraUsuario = false;
		
		for (Usuario usuario : usuarioSERVICE.listadoUsuarios()) {	
			
			if (usuarioSERVICE.isAdmin(usuario)) {
				
				if (usuario.getEmail().equals(email)) {
					
					uuid = usuario.getUID();
					encuentraUsuario = true;
				}
			}			
		}
								
		String uuidEncode = Base64.getEncoder().encodeToString(uuid.getBytes());
		
		if (encuentraUsuario) {
			
			Usuario usuario = usuarioSERVICE.getUsuarioByUUID(uuid);
			
			emailSERVICE.enviaEmail
			(
				email, 
				"Recuperación de contraseña", 
				"Estimado " + usuario.getNombre() + "Haga click en este enlace para recuperar su cuenta -> " + urlApi + "/resetPassword?user=" + uuidEncode
			);
		} else
			return new ModelAndView("redirect:/forgotPassword", "error", "Error: email not exists");
		
		return new ModelAndView("redirect:/emailSent", "user", uuidEncode);
	}
	
	@GetMapping("/emailSent")
	public String emailSend (@RequestParam(value="user", required = false) String uuidEncode, Model model) throws UnsupportedEncodingException {

		byte[] decodeString = Base64.getDecoder().decode(uuidEncode);
		String uuid = new String(decodeString, "UTF-8");
		
		if (uuid != null) {
			model.addAttribute("usuario", usuarioSERVICE.getUsuarioByUUID(uuid));
		}
		
		return "emailSent";
	}
	
	@GetMapping("/resetPassword")
	public String resetPassword (@RequestParam(value="user", required = false)String uuidEncode, 
								@RequestParam(value="error", required = false) String error, Model model) throws UnsupportedEncodingException {
		
		byte[] decodeString = Base64.getDecoder().decode(uuidEncode);
		String uuid = new String(decodeString, "UTF-8");
		
		if (error != null) {
			model.addAttribute("error", error);
		}
		
		model.addAttribute("usuario", usuarioSERVICE.getUsuarioByUUID(uuid));		
		return "resetPassword";
	}
	
	@PostMapping("/resetPasswordForm")
	public ModelAndView resetPasswordForm (@RequestParam(name="idUsuario")String idUsuario, @RequestParam(name="password")String password,
									@RequestParam(name="password2")String password2) {
			
		Usuario usuario = usuarioSERVICE.getUsuarioById(Integer.parseInt(idUsuario));
		
		if (!password.equals(password2)) {
			
			String uuidEncode = Base64.getEncoder().encodeToString(usuario.getUID().getBytes());
			return new ModelAndView("redirect:/resetPassword?user=" + uuidEncode, "error", "Error: passwords not match");
		}
			
		usuario.setPassword(passwordEncoder.encode(password2));
		usuarioSERVICE.actulizaUsuario(usuario);		
		return new ModelAndView("redirect:/passwordChanged");
	}
	
	@GetMapping("/passwordChanged")
	public String passwordChanged () {
			
		return "passwordChanged";
	}
	
	
}
