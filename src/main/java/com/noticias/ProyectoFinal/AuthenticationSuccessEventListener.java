package com.noticias.ProyectoFinal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.noticias.ProyectoFinal.service.LoginAttemptService;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

	@Autowired
    private final LoginAttemptService loginAttemptService;

    
    public AuthenticationSuccessEventListener(LoginAttemptService loginAttemptService) {
    	
        this.loginAttemptService = loginAttemptService;
    }

    public void onApplicationEvent(AuthenticationSuccessEvent e) {
    	
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        loginAttemptService.loginSucceeded(request.getRemoteAddr());
    }
}
