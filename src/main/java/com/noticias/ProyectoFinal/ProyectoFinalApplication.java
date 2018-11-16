package com.noticias.ProyectoFinal;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import ch.qos.logback.classic.Logger;

@SpringBootApplication
public class ProyectoFinalApplication extends SpringBootServletInitializer implements CommandLineRunner  {

	private Logger logger = (Logger) LoggerFactory.getLogger(ProyectoFinalApplication.class);
	
	public static void main(String[] args) {
		
		SpringApplication.run(ProyectoFinalApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		logger.info("Iniciando...");
	}

	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
        return application.sources(ProyectoFinalApplication.class);
    }
}


