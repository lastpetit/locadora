package br.sicofi.locadora.facade;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import br.sicofi.locadora.dao.CategoriaDAO;
import br.sicofi.locadora.model.Categoria;

public class CategoriaInterfaceImpl implements CategoriaInterface {

	private static final long serialVersionUID = 1818242808424001885L;

	private CategoriaDAO categoriaDAO;
	private SessionFactory sf;
	private Session session;
	private Transaction tx;

	
	public void salva(Categoria c) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		categoriaDAO = new CategoriaDAO(session, Categoria.class);

		this.categoriaDAO.save(c);

		tx.commit();
		sf.close();
		session.close();
	}

	public void atualiza(Categoria c) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		categoriaDAO = new CategoriaDAO(session, Categoria.class);

		this.categoriaDAO.merge(c);

		tx.commit();
		sf.close();
		session.close();
	}

	
	public Categoria procura(Long id) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		categoriaDAO = new CategoriaDAO(session, Categoria.class);

		Categoria c = this.categoriaDAO.load(id);

		tx.commit();
		sf.close();
		session.close();

		return c;
	}

		public void remove(Categoria c) {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		categoriaDAO = new CategoriaDAO(session, Categoria.class);

		this.categoriaDAO.delete(c);

		tx.commit();
		sf.close();
		session.close();
	}
	
	@Override
	public List<Categoria> lista() {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		categoriaDAO = new CategoriaDAO(session, Categoria.class);

		List<Categoria> lista = this.categoriaDAO.list();

		tx.commit();
		sf.close();
		session.close();

		return lista;
	}
	
	public List <Categoria> pesquisaByCategoria (String descricao){
		sf = new AnnotationConfiguration().configure().buildSessionFactory(); 
		session = sf.openSession();
		tx = session.beginTransaction();
		categoriaDAO = new CategoriaDAO(session, Categoria.class);
		
		List<Categoria> lista = this.categoriaDAO.pesquisaCategorias(descricao);
		
		tx.commit();
		sf.close();
		session.close();
		
		return lista;
	}
}

	
