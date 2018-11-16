package com.noticias.ProyectoFinal.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.noticias.ProyectoFinal.bean.UsuariosDetalles;
import com.noticias.ProyectoFinal.bean.Usuario;
import com.noticias.ProyectoFinal.dao.UsuarioDAO;

import ch.qos.logback.classic.Logger;

@Service
public class LoginSpringSecurityService implements UserDetailsService {

	@Autowired
	UsuarioDAO usuarioDAO;
	
	@Autowired
	LoginAttemptService loginAttemptService;
	
	@Autowired
	HttpServletRequest request;
	
	private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioDAO.getUsuarioByLogin(username);
		String ip = getClientIP();
		
		
		if (loginAttemptService.isBlocked(ip)) {
			
            throw new RuntimeException("IP: " + ip + " BLOQUEADA");
		}

		if(usuario == null) {
			
        	logger.error("Error en el Login: no existe el usuario '" + username + "' en el sistema!");
        	throw new UsernameNotFoundException("Username: " + username + " no existe en el sistema!");
        }
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for (UsuariosDetalles usuarioDetalles : usuario.getDetallesUsuario()) {
			
			logger.info("Role: ".concat(usuarioDetalles.getRol().getNombre()));
        	authorities.add(new SimpleGrantedAuthority(usuarioDetalles.getRol().getNombre()));
		}		
		
		if(authorities.isEmpty()) {
			
        	logger.error("Error en el Login: Usuario '" + username + "' no tiene roles asignados!");
        	throw new UsernameNotFoundException("Error en el Login: usuario '" + username + "' no tiene roles asignados!");
        }
		
		String login = usuario.getLogin().trim();
		
		return new User(login, usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}
	
	private String getClientIP() {
		
        String xfHeader = request.getHeader("X-Forwarded-For");
        
        if (xfHeader == null)
            return request.getRemoteAddr();

        return xfHeader.split(",")[0];
    }

}
