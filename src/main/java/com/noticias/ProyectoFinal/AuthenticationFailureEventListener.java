package com.noticias.ProyectoFinal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.noticias.ProyectoFinal.service.LoginAttemptService;

@Component
public class AuthenticationFailureEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	@Autowired
    private final LoginAttemptService loginAttemptService;

    public AuthenticationFailureEventListener(LoginAttemptService loginAttemptService) {
    	
        this.loginAttemptService = loginAttemptService;
    }

    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
    	  
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        loginAttemptService.loginFailed(request.getRemoteAddr());
    }
}
