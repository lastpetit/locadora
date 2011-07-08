package br.sicofi.locadora.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.sicofi.locadora.model.Filme;
import br.sicofi.locadora.model.Genero;

public class FilmeDAO extends DAO<Filme> {

	private Logger logger = Logger.getLogger(FilmeDAO.class);

	public FilmeDAO(Session session, Class<?> classe) {
		super(session, classe);
	}

	public Filme pesquisaFilmeById(Long idFilme) {
		logger.info("pesquisaFilmeById : " + idFilme);
		return (Filme) session.load(Filme.class, idFilme);
	}

	@SuppressWarnings("unchecked")
	public List<Filme> pesquisaTitulos(String titulo) {

		Criteria c = session.createCriteria(Filme.class);
		c.add(Restrictions.ilike("titulo", "%" + titulo + "%"));
		c.addOrder(Order.asc("titulo"));

		return c.list();
	}

	@SuppressWarnings("unchecked")
	public List<Filme> pesquisaGeneros(String genero) {
		logger.info("PesquisarGenero : " + genero);
		Criteria c = session.createCriteria(Filme.class);
		Criteria gen = c.createCriteria("genero");

		gen.add(Restrictions.eq("descricao", genero));
		return c.list();
	}

	public Genero buscaDescricao(Long id) {
		Query q = session.createQuery("select descricao from "
				+ Genero.class.getName()
				+ " as descricao where descricao.id like :id");

		q.setParameter("id", id);

		return (Genero) q.uniqueResult();

	}

}
