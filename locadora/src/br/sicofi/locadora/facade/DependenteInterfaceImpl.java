package br.sicofi.locadora.facade;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import br.sicofi.locadora.dao.DependenteDAO;
import br.sicofi.locadora.model.Dependente;

public class DependenteInterfaceImpl implements DependenteInterface {

	private static final long serialVersionUID = 1818242808424001885L;

	private DependenteDAO dependenteDAO;
	private SessionFactory sf;
	private Session session;
	private Transaction tx;

	//Salva o dependente que vem da sessão
	 
	public void salva(Dependente c) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		dependenteDAO = new DependenteDAO(session, Dependente.class);

		this.dependenteDAO.save(c);

		tx.commit();
		sf.close();
		session.close();
	}

	public void atualiza(Dependente c) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		dependenteDAO = new DependenteDAO(session, Dependente.class);

		this.dependenteDAO.merge(c);

		tx.commit();
		sf.close();
		session.close();
	}

	public void remove(Dependente c) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		dependenteDAO = new DependenteDAO(session, Dependente.class);

		this.dependenteDAO.delete(c);

		tx.commit();
		sf.close();
		session.close();
	}

	public List<Dependente> lista() {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		dependenteDAO = new DependenteDAO(session, Dependente.class);

		List<Dependente> lista = this.dependenteDAO.list();

		tx.commit();
		sf.close();
		session.close();

		return lista;
	}

}