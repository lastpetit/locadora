package br.sicofi.locadora.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.sicofi.locadora.model.Categoria;

public class CategoriaDAO extends DAO<Categoria> {

	private Logger logger = Logger.getLogger(CategoriaDAO.class);


	public CategoriaDAO(Session session, Class<?> classe) {
		super(session, classe);
	}

	public Categoria pesquisaCategoriaById(Long idCategoria) {
		logger.info("pesquisaCategoriaById : " + idCategoria);
		return (Categoria) session.load(Categoria.class, idCategoria);
	}


	
	@SuppressWarnings("unchecked")
	public List<Categoria> pesquisaCategorias(String descricao) {
		Criteria c = session.createCriteria(Categoria.class);
		c.add(Restrictions.ilike("descricao", "%" + descricao + "%"));
		c.addOrder(Order.asc("descricao"));

		return c.list();
	}


}


