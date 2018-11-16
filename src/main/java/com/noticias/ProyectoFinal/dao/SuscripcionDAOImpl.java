package com.noticias.ProyectoFinal.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.noticias.ProyectoFinal.bean.Suscripcion;

@Transactional
@Repository
public class SuscripcionDAOImpl implements SuscripcionDAO {

	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public Long cuentaSuscripciones() {
		
		return (Long) entityManager.createQuery("SELECT COUNT(*) FROM Suscripcion").getSingleResult();
	}

	@Override
	public Suscripcion getSuscripcionById(int idSuscripcion) {
		
		return entityManager.find(Suscripcion.class, idSuscripcion);
	}

	@Override
	public List<Suscripcion> listadoSuscripciones() {
		
		return (List<Suscripcion>) entityManager.createQuery("FROM Suscripcion", Suscripcion.class).getResultList();
	}

	@Override
	public void guardaSuscripcion(Suscripcion suscripcion) {
		
		entityManager.persist(suscripcion);
	}
	
	@Override
	public void actualizaSuscripcion(Suscripcion suscripcion) {
		
		entityManager.merge(suscripcion);
	}

	@Override
	public void eliminaSuscripcion(int idSuscripcion) {
		
		entityManager.remove(getSuscripcionById(idSuscripcion));
	}

	@Override
	public List<Suscripcion> listadoSuscripcionesPorUsuario(int idUsuario) {
		
		return (List<Suscripcion>) entityManager.createQuery("FROM Suscripcion WHERE idUsuario = ?1", Suscripcion.class)
				.setParameter(1, idUsuario)
				.getResultList();
	}

}
