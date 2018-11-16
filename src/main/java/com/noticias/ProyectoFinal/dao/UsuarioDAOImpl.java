package com.noticias.ProyectoFinal.dao;

import java.util.List;
import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.noticias.ProyectoFinal.bean.Usuario;

@Transactional
@Repository
public class UsuarioDAOImpl implements UsuarioDAO {
			
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public Usuario getUsuarioByLogin(String login) {
		
		return (Usuario) entityManager.createQuery("SELECT u FROM Usuario u WHERE login= ?1").setParameter(1, login).getSingleResult();
	}
	
	@Override
	public Usuario getUsuarioById(int idUsuario) {
		
		return entityManager.find(Usuario.class, idUsuario);
	}
	
	@Override
	public List<Usuario> listadoUsuarios() {
	    
	    return (List<Usuario>) entityManager.createQuery("FROM Usuario", Usuario.class)
	    									.getResultList();
	}
	
	@Override
	public List<Usuario> listadoUsuariosPorTipo(int idTipoUsuario) {
		
		return (List<Usuario>) entityManager.createQuery("FROM Usuario WHERE idtipousuario = ?1", Usuario.class)
							   				.setParameter(1, idTipoUsuario).getResultList();
	}

	@Override
	public void guardaUsuario(Usuario usuario) {
		
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		entityManager.persist(usuario);
	}

	@Override
	public void eliminaUsuario(int idUsuario) {
		
		entityManager.remove(getUsuarioById(idUsuario));
	}

	@Override
	public Boolean existeUsuario(Usuario usuario) {
		
		return entityManager.createQuery("FROM Usuario WHERE email = ?1 OR login = ?2", Usuario.class)
				.setParameter(1, usuario.getEmail())
				.setParameter(2, usuario.getLogin())
				.getResultList()
				.size() > 0 ? true : false;
	}
	
	@Override
	public Long cuentaUsuarios () {
		
		return (Long) entityManager.createQuery("SELECT COUNT(*) FROM Usuario").getSingleResult();
	}

	@Override
	public void actualizaUsuario(Usuario usuario) {
		
		//Aqui no se encripta la password porque a no ser que se escriba expresamente no se actualiza, por lo que el encriptado se hace en donde se llame
		entityManager.merge(usuario);
	}

	@Override
	public Usuario getUsuarioByUUID(String uuid) {
		
		return (Usuario) entityManager.createQuery("SELECT u FROM Usuario u WHERE uid= ?1").setParameter(1, uuid).getSingleResult();
	}

	@Override
	public List<Usuario> getUsuarioByUsername(String login) {
		
		return  (List<Usuario>) entityManager.createQuery("FROM Usuario u WHERE login LIKE ?1", Usuario.class).setParameter(1, "%" + login + "%").getResultList();
	}

	@Override
	public Usuario compruebaUsuario(String login, String password) {
		
		for (Usuario usuario : listadoUsuarios()) 
			if (usuario.getLogin().equals(login) && passwordEncoder.matches(password, usuario.getPassword())) 
				return usuario;
		
		return null;
	}
	
	

}
