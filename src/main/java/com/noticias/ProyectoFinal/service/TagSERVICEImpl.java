package com.noticias.ProyectoFinal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noticias.ProyectoFinal.bean.Tag;
import com.noticias.ProyectoFinal.dao.TagDAO;

@Service
public class TagSERVICEImpl implements TagSERVICE {

	@Autowired
	TagDAO tagDao;
	
	@Override
	public Tag getTagById(int idTag) {
		
		return tagDao.getTagById(idTag);
	}

	@Override
	public List<Tag> listadoTags() {

		return tagDao.listadoArticulos();
	}

	@Override
	public void guardaTag(Tag tag) {

		tagDao.guardaTag(tag);
	}

	@Override
	public void eliminaTag(int idTag) {
		
		tagDao.eliminaTag(idTag);
	}

	@Override
	public Long cuentaTags() {

		return tagDao.cuentaTags();
	}

	@Override
	public List<Tag> getTagsByIdArticulo(int idArticulo) {
		
		return tagDao.getTagsByIdArticulo(idArticulo);
	}

	@Override
	public List<Tag> getTagsByIdCategoria(int idCategoria) {
		
		return tagDao.getTagsByIdCategoria(idCategoria);
	}

	

}
