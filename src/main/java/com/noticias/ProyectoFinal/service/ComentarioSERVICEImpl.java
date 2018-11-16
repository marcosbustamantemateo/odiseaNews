package com.noticias.ProyectoFinal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noticias.ProyectoFinal.bean.Comentario;
import com.noticias.ProyectoFinal.dao.ComentarioDAO;
import com.noticias.ProyectoFinal.dao.ComentarioDAOImpl;

@Service
public class ComentarioSERVICEImpl implements ComentarioSERVICE {

	@Autowired
	ComentarioDAO comentarioDAO = new ComentarioDAOImpl();
	
	@Override
	public Comentario getComentarioById(int idComentario) {
		
		return comentarioDAO.getComentarioById(idComentario);
	}

	@Override
	public List<Comentario> listadoComentarios() {
		
		return comentarioDAO.listadoComentarios();
	}

	@Override
	public Boolean guardaComentario(Comentario comentario) {
		
		if(existeComentario(comentario))
			return false;
		
		else {
			comentarioDAO.guardaComentario(comentario);
			return true;
		}
	}

	@Override
	public void eliminaComentario(int idComentario) {
		
		comentarioDAO.eliminaComentario(idComentario);
	}

	@Override
	public Long cuentaComentarios() {

		return comentarioDAO.cuentaComentarios();
	}

	@Override
	public List<Comentario> listadoComentariosPorArticulo(int idArticulo) {
		
		return comentarioDAO.listadoComentariosPorArticulo(idArticulo);
	}

	@Override
	public Boolean existeComentario(Comentario comentario) {
		
		return comentarioDAO.existeComentario(comentario);
	}

}
