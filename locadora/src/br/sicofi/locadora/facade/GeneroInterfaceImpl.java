package br.sicofi.locadora.facade;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import br.sicofi.locadora.dao.GeneroDAO;
import br.sicofi.locadora.model.Genero;


public class GeneroInterfaceImpl implements GeneroInterface {

	private static final long serialVersionUID = 1818242808424001885L;

	private GeneroDAO generoDAO;
	private SessionFactory sf;
	private Session session;
	private Transaction tx;

	
	public void salva(Genero g) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		generoDAO = new GeneroDAO(session, Genero.class);

		this.generoDAO.save(g);

		tx.commit();
		sf.close();
		session.close();
	}

	public void atualiza(Genero g) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		generoDAO = new GeneroDAO(session, Genero.class);

		this.generoDAO.merge(g);

		tx.commit();
		sf.close();
		session.close();
	}

	public Genero procura(Long id) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		generoDAO = new GeneroDAO(session, Genero.class);

		Genero f = this.generoDAO.load(id);

		tx.commit();
		sf.close();
		session.close();

		return f;
	}

	public void remove(Genero g) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		generoDAO = new GeneroDAO(session, Genero.class);

		this.generoDAO.delete(g);

		tx.commit();
		sf.close();
		session.close();
	}

	public List<Genero> lista() {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		generoDAO = new GeneroDAO(session, Genero.class);

		List<Genero> lista = this.generoDAO.list();

		tx.commit();
		sf.close();
		session.close();

		return lista;
	}

	public List<Genero> pesquisaByGenero (String descricao) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		generoDAO = new GeneroDAO(session, Genero.class);

		List<Genero> lista = this.generoDAO.pesquisaGeneros(descricao);
		tx.commit();
		sf.close();
		session.close();

		return lista;
	}
	
	
}

