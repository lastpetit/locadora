package br.sicofi.locadora.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.sicofi.locadora.model.Dependente;

public class DependenteDAO extends DAO<Dependente> {

	private Logger logger = Logger.getLogger(DependenteDAO.class);

	public DependenteDAO(Session session, Class<?> classe) {
		super(session, classe);
	}

		
	@SuppressWarnings("unchecked")
	public List<Dependente> pesquisaByCPF(String cpf) {
		logger.info("pesquisaByCPF : " + cpf);
		Criteria c = session.createCriteria(Dependente.class);
		c.add(Restrictions.ilike("cpf", "%" + cpf + "%"));
		c.addOrder(Order.asc("cpf"));
		
		return c.list();
	}

	@SuppressWarnings("unchecked")
	public List<Dependente> pesquisa(String nome) {
		logger.info("pesquisaByNome : " + nome);
		Criteria c = session.createCriteria(Dependente.class);
		c.add(Restrictions.ilike("nome", "%" + nome + "%"));
		c.addOrder(Order.asc("nome"));

		return c.list();
	}

}

