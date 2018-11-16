package com.noticias.ProyectoFinal;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.noticias.ProyectoFinal.resolvers.ArticuloQuery;
import com.noticias.ProyectoFinal.resolvers.CategoriaQuery;
import com.noticias.ProyectoFinal.resolvers.ComentarioQuery;
import com.noticias.ProyectoFinal.resolvers.SuscripcionQuery;
import com.noticias.ProyectoFinal.resolvers.TagQuery;
import com.noticias.ProyectoFinal.resolvers.UsuarioQuery;
import com.noticias.ProyectoFinal.service.LoginAttemptService;

import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.query.PublicResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
		
	@Bean
    GraphQLSchema schema(UsuarioQuery usuarioQuery, ArticuloQuery articuloQuery, CategoriaQuery categoriaQuery, SuscripcionQuery suscripcionQuery,
    						TagQuery tagQuery, ComentarioQuery comentarioQuery) {
				
        return new GraphQLSchemaGenerator()
                .withResolverBuilders(
                        //Resolvemos por anotaciones
                        new AnnotatedResolverBuilder(),
                        //Resolvemos metodos publicos en paquete raiz
                        new PublicResolverBuilder("com.noticias.ProyectoFinal"))
                .withOperationsFromSingleton(usuarioQuery)
                .withOperationsFromSingleton(articuloQuery)
                .withOperationsFromSingleton(categoriaQuery)
                .withOperationsFromSingleton(suscripcionQuery)
                .withOperationsFromSingleton(tagQuery)
                .withOperationsFromSingleton(comentarioQuery)
                .withValueMapperFactory(new JacksonValueMapperFactory())
                .generate();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder () {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public ApplicationListener<AuthenticationSuccessEvent> loginSuccessListener(LoginAttemptService loginAttemptService){
		
        return new AuthenticationSuccessEventListener(loginAttemptService);
    }

    @Bean
    public ApplicationListener<AuthenticationFailureBadCredentialsEvent> loginFailureListener(LoginAttemptService loginAttemptService){
    	
        return new AuthenticationFailureEventListener(loginAttemptService);
    }
    
    @Bean
    public RequestContextListener requestContextListener(){
    	
        return new RequestContextListener();
    }
}
