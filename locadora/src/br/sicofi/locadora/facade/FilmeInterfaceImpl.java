package br.sicofi.locadora.facade;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import br.sicofi.locadora.dao.FilmeDAO;
import br.sicofi.locadora.model.Filme;
import br.sicofi.locadora.model.Genero;

public class FilmeInterfaceImpl implements FilmeInterface {

	private static final long serialVersionUID = 1818242808424001885L;

	private FilmeDAO filmeDAO;
	private SessionFactory sf;
	private Session session;
	private Transaction tx;

	// Salva o filme que vem da sessão
	 
	public void salva(Filme f) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		filmeDAO = new FilmeDAO(session, Filme.class);

		this.filmeDAO.save(f);

		tx.commit();
		sf.close();
		session.close();
	}

	public void atualiza(Filme f) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		filmeDAO = new FilmeDAO(session, Filme.class);

		this.filmeDAO.merge(f);

		tx.commit();
		sf.close();
		session.close();
	}

	public void remove(Filme f) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		filmeDAO = new FilmeDAO(session, Filme.class);

		this.filmeDAO.delete(f);

		tx.commit();
		sf.close();
		session.close();
	}

	public List<Filme> lista() {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		filmeDAO = new FilmeDAO(session, Filme.class);

		List<Filme> lista = this.filmeDAO.list();

		tx.commit();
		sf.close();
		session.close();

		return lista;
	}
	


	public List<Filme> pesquisaByTitulo(String titulo) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		filmeDAO = new FilmeDAO(session, Filme.class);

		List<Filme> lista = (List<Filme>) this.filmeDAO.pesquisaTitulos(titulo);
		tx.commit();
		sf.close();
		session.close();

		return lista;
	}
	
	public List<Filme> pesquisaByGenero(Long id) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		filmeDAO = new FilmeDAO(session, Filme.class);

		Genero g = this.filmeDAO.buscaDescricao(id);
		List<Filme> f = this.filmeDAO.pesquisaGeneros(g.getDescricao());

		tx.commit();
		sf.close();
		session.close();

		return f;
	}

}