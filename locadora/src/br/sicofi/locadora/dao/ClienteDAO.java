package br.sicofi.locadora.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.sicofi.locadora.model.Cliente;

public class ClienteDAO extends DAO<Cliente> {

	private Logger logger = Logger.getLogger(ClienteDAO.class);

	public ClienteDAO(Session session, Class<?> classe) {
		super(session, classe);
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> pesquisaByCPF(String cpf) {
		logger.info("pesquisaByCPF : " + cpf);
		Criteria c = session.createCriteria(Cliente.class);
		c.add(Restrictions.ilike("cpf", "%" + cpf + "%"));
		c.addOrder(Order.asc("cpf"));

		return c.list();
	}
	
	

	@SuppressWarnings("unchecked")
	public List<Cliente> pesquisa(String nome) {
		logger.info("pesquisaByNome : " + nome);
		Criteria c = session.createCriteria(Cliente.class);
		c.add(Restrictions.ilike("nome", "%" + nome + "%"));
		c.addOrder(Order.asc("nome"));

		return c.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> list() {
		
		Criteria c = session.createCriteria(Cliente.class);
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);		
		
		return c.list();
	} 


}
