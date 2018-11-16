package com.noticias.ProyectoFinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import com.noticias.ProyectoFinal.bean.Usuario;

@Service
public class FacebookSERVICE {

	@Value("${spring.social.facebook.appId}")
    String facebookAppId;
	
    @Value("${spring.social.facebook.appSecret}")
    String facebookSecret;
    
    @Value("${app.url.api}")
	String urlApi;
    
    @Autowired
    UsuarioSERVICE usuarioSERVICE;
    
	public String createFacebookAuthorizationURL(){
		
	    FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
	    
	    OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
	    OAuth2Parameters params = new OAuth2Parameters();
	    
	    params.setRedirectUri("http://localhost:8080/facebook");
	    //params.setRedirectUri(urlApi + "/facebook");
	    params.setScope("public_profile,email,user_birthday");
	    
	    return oauthOperations.buildAuthorizeUrl(params);
	}
	
	public String createFacebookAccessToken(String code) {
		
	    FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
	    AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, "http://localhost:8080/facebook", null);
	    
	    return accessGrant.getAccessToken();
	}
	
	public User getData(String accessToken) {
		
	    Facebook facebook = new FacebookTemplate(accessToken);
	    String[] fields = { "id", "about", "age_range", "birthday", "context", "cover", "currency", "devices", "education", "email", "favorite_athletes", 
	    		"favorite_teams", "first_name", "gender", "hometown", "inspirational_people", "installed", "install_type", "is_verified", "languages", 
	    		"last_name", "link", "locale", "location", "meeting_for", "middle_name", "name", "name_format", "political", "quotes", "payment_pricepoints", 
	    		"relationship_status", "religion", "security_settings", "significant_other", "sports", "test_group", "timezone", "third_party_id", 
	    		"updated_time", "verified", "video_upload_limits", "viewer_can_send_gift", "website", "work"};
	    
	    User profile = facebook.fetchObject("me", User.class, fields);	    
	    return profile;
	}


	public Usuario insertaUsuarioFacebook (User user) {
		
		Usuario usuario = new Usuario(user.getFirstName(), user.getLastName(), null, user.getEmail(), "", "");
		usuario.setEnabled(true);
		
		if(usuarioSERVICE.guardaUsuario(usuario)) 
		 	return usuario;
		else
			return null;
	}


}
