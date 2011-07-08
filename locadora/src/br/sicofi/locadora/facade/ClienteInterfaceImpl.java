package br.sicofi.locadora.facade;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import br.sicofi.locadora.dao.ClienteDAO;
import br.sicofi.locadora.model.Cliente;

public class ClienteInterfaceImpl implements ClienteInterface {

	private static final long serialVersionUID = 1818242808424001885L;

	private ClienteDAO clienteDAO;
	private SessionFactory sf;
	private Session session;
	private Transaction tx;

	//Salva o cliente que vem da sessão
	 
	public void salva(Cliente c) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		clienteDAO = new ClienteDAO(session, Cliente.class);

		this.clienteDAO.save(c);

		tx.commit();
		sf.close();
		session.close();
	}

	public void atualiza(Cliente c) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		clienteDAO = new ClienteDAO(session, Cliente.class);

		this.clienteDAO.merge(c);

		tx.commit();
		sf.close();
		session.close();
	}

	public void remove(Cliente c) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		clienteDAO = new ClienteDAO(session, Cliente.class);

		this.clienteDAO.delete(c);

		tx.commit();
		sf.close();
		session.close();
	}

	public List<Cliente> lista() {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		clienteDAO = new ClienteDAO(session, Cliente.class);

		List<Cliente> lista = this.clienteDAO.list();

		tx.commit();
		sf.close();
		session.close();

		return lista;
	}
	
	
	
	public List<Cliente> pesquisa(String p, int i) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		clienteDAO = new ClienteDAO(session, Cliente.class);

		if(i==1){
		List<Cliente> lista = (List<Cliente>) this.clienteDAO.pesquisa(p);
		
		tx.commit();
		sf.close();
		session.close();
		return lista;
		}
		else{
			List<Cliente> lista = (List<Cliente>) this.clienteDAO.pesquisaByCPF(p);
		
			tx.commit();
			session.close();
			sf.close();
			return lista;
		}
		
		
	}

}