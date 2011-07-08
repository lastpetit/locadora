package br.sicofi.locadora.facade;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import br.sicofi.locadora.dao.LocacaoDAO;
import br.sicofi.locadora.model.Locacao;

public class LocacaoInterfaceImpl implements LocacaoInterface {

	private static final long serialVersionUID = 1818242808424001885L;

	private LocacaoDAO locacaoDAO;
	private SessionFactory sf;
	private Session session;
	private Transaction tx;

	
	public void salva(Locacao l) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		locacaoDAO = new LocacaoDAO(session, Locacao.class);

		this.locacaoDAO.save(l);

		tx.commit();
		sf.close();
		session.close();
	}

	public void atualiza(Locacao l) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		locacaoDAO = new LocacaoDAO(session, Locacao.class);

		this.locacaoDAO.merge(l);

		tx.commit();
		sf.close();
		session.close();
	}

	public Locacao procura(Long id) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		locacaoDAO = new LocacaoDAO(session, Locacao.class);

		Locacao l = this.locacaoDAO.load(id);

		tx.commit();
		sf.close();
		session.close();

		return l;
	}

	public void remove(Locacao l) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		locacaoDAO = new LocacaoDAO(session, Locacao.class);

		this.locacaoDAO.delete(l);

		tx.commit();
		sf.close();
		session.close();
	}

	public List<Locacao> lista() {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		locacaoDAO = new LocacaoDAO(session, Locacao.class);

		List<Locacao> lista = this.locacaoDAO.list();

		tx.commit();
		sf.close();
		session.close();

		return lista;
	}
	
	public List<Locacao> listaByCliente(Long id) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		locacaoDAO = new LocacaoDAO(session, Locacao.class);

		List<Locacao> lista = this.locacaoDAO.pesquisaClientes(id);

		tx.commit();
		sf.close();
		session.close();

		return lista;
	}

}