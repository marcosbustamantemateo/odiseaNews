package com.noticias.ProyectoFinal.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.noticias.ProyectoFinal.bean.Tag;

@Transactional
@Repository
public class TagDAOImpl implements TagDAO{

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public Tag getTagById(int idTag) {
		
		return entityManager.find(Tag.class, idTag);
	}

	@Override
	public List<Tag> listadoArticulos() {
		
		return (List<Tag>) entityManager.createQuery("FROM Tag", Tag.class).getResultList();
	}

	@Override
	public void guardaTag(Tag tag) {
		
		entityManager.persist(tag);
	}

	@Override
	public void eliminaTag(int idTag) {
		
		entityManager.remove(getTagById(idTag));
	}

	@Override
	public Long cuentaTags() {
		
		return (Long) entityManager.createQuery("SELECT COUNT(*) FROM Tag").getSingleResult();
	}

	@Override
	public List<Tag> getTagsByIdArticulo(int idArticulo) {
		
		return (List<Tag>) entityManager.createQuery("SELECT t FROM Tag t WHERE idarticulo= ?1", Tag.class).setParameter(1, idArticulo).getResultList();
	}

	@Override
	public List<Tag> getTagsByIdCategoria(int idCategoria) {

		return (List<Tag>) entityManager.createQuery("SELECT t FROM Tag t WHERE idcategoria= ?1", Tag.class).setParameter(1, idCategoria).getResultList();
	}

}
