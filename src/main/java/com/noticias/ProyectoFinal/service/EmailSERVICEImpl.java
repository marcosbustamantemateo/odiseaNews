package com.noticias.ProyectoFinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSERVICEImpl implements EmailSERVICE {

	@Autowired
	JavaMailSender javaMailSender;
	
	@Override
	public void enviaEmail (String destino, String asunto, String contenido) {
		
		SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(destino);
        message.setSubject(asunto);
        message.setText(contenido);

        try {
        	
        	javaMailSender.send(message);
        	
        } catch (Exception e) {
        	
            e.printStackTrace();
        }
	}
}
