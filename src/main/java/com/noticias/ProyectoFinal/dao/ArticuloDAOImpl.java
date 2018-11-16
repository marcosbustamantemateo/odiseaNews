package com.noticias.ProyectoFinal.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.noticias.ProyectoFinal.bean.Articulo;

@Transactional
@Repository
public class ArticuloDAOImpl implements ArticuloDAO {

	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public Articulo getArticuloById(int idArticulo) {
		
		return entityManager.find(Articulo.class, idArticulo);
	}

	@Override
	public List<Articulo> listadoArticulos() {
		
		return (List<Articulo>) entityManager.createQuery("FROM Articulo", Articulo.class).getResultList();
	}
	
	@Override
	public void guardaArticulo(Articulo articulo) {
		
		entityManager.persist(articulo);
	}

	@Override
	public void eliminaArticulo(int idArticulo) {

		entityManager.remove(getArticuloById(idArticulo));
	}

	@Override
	public Long cuentaArticulos() {

		return (Long) entityManager.createQuery("SELECT COUNT(*) FROM Articulo").getSingleResult();
	}

	@Override
	public void actualizaArticulo(Articulo articulo) {
		
		entityManager.merge(articulo);
	}

	@Override
	public List<Articulo> getsArticulosByTitulo (String titulo) {
		
		return (List<Articulo>) entityManager.createQuery("FROM Articulo WHERE titulo LIKE ?1", Articulo.class)
   				.setParameter(1, "%" + titulo + "%")
   				.getResultList();
	}

	@Override
	public List<Articulo> listadoArticulosPorVisitas() {
		
		return (List<Articulo>) entityManager.createQuery("FROM Articulo ORDER BY numvisitas DESC", Articulo.class)
				.setMaxResults(5)
				.getResultList();
	}

	@Override
	public Articulo getUltimoArticulo() {
		
		return (Articulo) entityManager.createQuery("FROM Articulo ORDER BY idArticulo DESC", Articulo.class)
				.setMaxResults(1)
				.getSingleResult();
	}

	@Override
	public List<Articulo> listadoArticulosFront() {
		
		Articulo articulo = getUltimoArticulo();
		
		return (List<Articulo>) entityManager.createQuery("FROM Articulo WHERE idArticulo NOT IN ?1 ORDER BY idArticulo DESC", Articulo.class)
				.setParameter(1, articulo.getIdArticulo())
				.setMaxResults(6)
				.getResultList();
	}

}
