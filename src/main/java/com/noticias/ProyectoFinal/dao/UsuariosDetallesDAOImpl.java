package com.noticias.ProyectoFinal.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.noticias.ProyectoFinal.bean.UsuariosDetalles;

@Transactional
@Repository
public class UsuariosDetallesDAOImpl implements UsuariosDetallesDAO {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public UsuariosDetalles getUsuariosDetallesById(int idUsuariosDetalles) {
		
		return entityManager.find(UsuariosDetalles.class, idUsuariosDetalles);
	}

	@Override
	public List<UsuariosDetalles> listadoUsuariosDetalles() {
		
		return (List<UsuariosDetalles>) entityManager.createQuery("FROM UsuariosDetalles", UsuariosDetalles.class).getResultList();
	}

	@Override
	public void guardaUsuariosDetalles(UsuariosDetalles usuariosDetalles) {
		
		entityManager.persist(usuariosDetalles);
	}

	@Override
	public void eliminaUsuariosDetalles(int idUsuariosDetalles) {
		
		entityManager.remove(getUsuariosDetallesById(idUsuariosDetalles));
	}
	
	@Override
	public List<UsuariosDetalles> getUsuariosDetallesByIdUsuario(int idUsuario) {

		return (List<UsuariosDetalles>) entityManager.createQuery("SELECT u FROM UsuariosDetalles u WHERE idusuario= ?1", UsuariosDetalles.class).setParameter(1, idUsuario).getResultList();
	}

}
