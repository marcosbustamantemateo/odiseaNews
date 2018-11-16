package com.noticias.ProyectoFinal.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.noticias.ProyectoFinal.bean.Comentario;

@Transactional
@Repository
public class ComentarioDAOImpl implements ComentarioDAO {

	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public Comentario getComentarioById(int idComentario) {
		
		return entityManager.find(Comentario.class, idComentario);
	}

	@Override
	public List<Comentario> listadoComentarios() {
		
		return (List<Comentario>) entityManager.createQuery("FROM Comentario", Comentario.class).getResultList();
	}

	@Override
	public void guardaComentario(Comentario comentario) {
		
		entityManager.persist(comentario);
	}

	@Override
	public void eliminaComentario(int idComentario) {
		
		entityManager.remove(getComentarioById(idComentario));
	}

	@Override
	public Boolean existeComentario(Comentario comentario) {
		
		return entityManager.createQuery("FROM Comentario WHERE contenido = ?1 AND idusuario = ?2")
				.setParameter(1, comentario.getContenido())
				.setParameter(2, comentario.getUsuarioEstandar().getIdUsuario())
				.getResultList()
				.size() > 0 ? true : false;
	}

	@Override
	public Long cuentaComentarios() {
		
		return (Long) entityManager.createQuery("SELECT COUNT(*) FROM Comentario").getSingleResult();
	}

	@Override
	public List<Comentario> listadoComentariosPorArticulo(int idArticulo) {
		
		return (List<Comentario>) entityManager.createQuery("FROM Comentario WHERE idArticulo = ?1", Comentario.class)
				.setParameter(1, idArticulo)
				.getResultList();
	}

}
