package com.noticias.ProyectoFinal.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.noticias.ProyectoFinal.bean.Categoria;

@Transactional
@Repository
public class CategoriaDAOImpl implements CategoriaDAO {

	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public Categoria getCategoriaById(int idCategoria) {
		
		return entityManager.find(Categoria.class, idCategoria);
	}

	@Override
	public List<Categoria> listadoCategorias() {
		
		return (List<Categoria>) entityManager.createQuery("FROM Categoria", Categoria.class).getResultList() ;
	}

	@Override
	public void guardaCategoria(Categoria categoria) {
		
		entityManager.persist(categoria);
	}

	@Override
	public void eliminaCategoria(int idCategoria) {
		
		entityManager.remove(getCategoriaById(idCategoria));
	}

	@Override
	public Boolean existeCategoria(Categoria categoria) {
		
		return entityManager.createQuery("SELECT c FROM Categoria c WHERE nombre= ?1 ")
				.setParameter(1, categoria.getNombre())
				.getResultList()
				.size() == 1 ? true : false;
	}

	@Override
	public Long cuentaCategorias() {

		return (Long) entityManager.createQuery("SELECT COUNT(*) FROM Categoria").getSingleResult();
	}

	@Override
	public void actualizaCategoria(Categoria categoria) {
		
		entityManager.merge(categoria);
	}

	@Override
	public List<Categoria> getsCategoriasNotInIdCategoria(List<Integer> idCategoria) {
		
		return (List<Categoria>) entityManager.createQuery("FROM Categoria WHERE idCategoria NOT IN ?1", Categoria.class)
				.setParameter(1, idCategoria)
				.getResultList();
	}
}
