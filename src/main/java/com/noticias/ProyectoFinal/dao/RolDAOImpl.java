package com.noticias.ProyectoFinal.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.noticias.ProyectoFinal.bean.Rol;

@Transactional
@Repository
public class RolDAOImpl implements RolDAO {

	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public Rol getRolById(int idRol) {
		
		return entityManager.find(Rol.class, idRol);
	}
	
	@Override
	public Rol getRolByName(String nombre) {
		
		return (Rol) entityManager.createQuery("SELECT r FROM Rol r WHERE nombre= ?1").setParameter(1, nombre).getSingleResult();
	}

	@Override
	public List<Rol> listadoRoles() {
		
		return (List<Rol>) entityManager.createQuery("FROM Rol", Rol.class).getResultList();
	}

	@Override
	public void guardaRol(Rol rol) {

		entityManager.persist(rol);
	}

	@Override
	public void eliminaRol(int idRol) {
		
		entityManager.remove(getRolById(idRol));
	}

	@Override
	public Boolean existeRol(Rol rol) {
		
		return entityManager.createQuery("SELECT r FROM Rol r WHERE nombre= ?1 ")
				.setParameter(1, rol.getNombre())
				.getResultList()
				.size() == 1 ? true : false;
	}
	
	@Override
	public List<Rol> noFindById(Integer idRol) {
		
		return (List<Rol>) entityManager.createQuery("SELECT r FROM Rol r WHERE nombre NOT LIKE ?1", Rol.class)
				.setParameter(1, idRol)
				.getResultList();
	}

	@Override
	public void actualizaRol(Rol rol) {
		
		entityManager.merge(rol);
	}
	
	@Override
	public List<Rol> getsRolesNotInIdRol(List<Integer> idRol) {
		
		return (List<Rol>) entityManager.createQuery("FROM Rol WHERE idRol NOT IN ?1", Rol.class)
				.setParameter(1, idRol).getResultList();
	}
}
