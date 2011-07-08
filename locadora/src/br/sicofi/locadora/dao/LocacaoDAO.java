package br.sicofi.locadora.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.sicofi.locadora.model.Locacao;

public class LocacaoDAO extends DAO<Locacao> {

	private Logger logger = Logger.getLogger(LocacaoDAO.class);

	public LocacaoDAO(Session session, Class<?> classe) {
		super(session, classe);
	}

	public Locacao pesquisaLocacaoById(Long idLocacao) {
		logger.info("pesquisaLocacaoById : " + idLocacao);
		return (Locacao) session.load(Locacao.class, idLocacao);
	}

	@SuppressWarnings("unchecked")
	public List<Locacao> pesquisaClientes(Long id) {

		logger.info("PesquisarCliente : " + id);
		Criteria c = session.createCriteria(Locacao.class);
		Criteria gen = c.createCriteria("cliente");
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		gen.add(Restrictions.eq("id", id));
		
		return c.list();
	}

	@SuppressWarnings("unchecked")
	public List<Locacao> list() {
	Criteria c = session.createCriteria(Locacao.class);
	c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);		
	
	return c.list();
} 
}
