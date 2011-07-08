package br.sicofi.locadora.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.sicofi.locadora.model.Genero;

public class GeneroDAO extends DAO<Genero> {

	private Logger logger = Logger.getLogger(GeneroDAO.class);

	public GeneroDAO(Session session, Class<?> classe) {
		super(session, classe);
	}

	@SuppressWarnings("unchecked")
	public List<Genero> pesquisaGeneros(String descricao) {
		logger.info("pesquisaGeneros: " + descricao);
		Criteria c = session.createCriteria(Genero.class);
		c.add(Restrictions.ilike("descricao", "%" + descricao + "%"));
		c.addOrder(Order.asc("descricao"));

		return c.list();
	}
}
