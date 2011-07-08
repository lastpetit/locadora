package br.sicofi.locadora.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.sicofi.locadora.facade.CategoriaInterface;
import br.sicofi.locadora.facade.CategoriaInterfaceImpl;
import br.sicofi.locadora.model.Categoria;

public class CategoriaBean implements Serializable {

	private static final long serialVersionUID = -333995781063775201L;

	private Categoria categoria = new Categoria();

	private Long id;

	private List<Categoria> listCategoria = new ArrayList<Categoria>();
	
	private List<Categoria> listDescricao = new ArrayList<Categoria>();

	public CategoriaBean() {

		if (this.categoria == null) {
			this.categoria = new Categoria();
		}
		listar();
	}

	
	 // Lista as categorias
	 
	public void listar() {
		CategoriaInterface categoriaService = new CategoriaInterfaceImpl();
		this.listCategoria = categoriaService.lista();
		this.listDescricao = categoriaService.lista();

	}

	// Salva nova categoria
	 
	public void save() {
		CategoriaInterface categoriaService = new CategoriaInterfaceImpl();

		categoriaService.salva(this.categoria);

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage("Cadastro Realizado com Sucesso"));

	}

	
	public void limpar() {

		this.categoria = new Categoria();

	}

	
	public void delete() {
		try {
			CategoriaInterface categoriaService = new CategoriaInterfaceImpl();

			this.categoria.setId(this.categoria.getId());
			categoriaService.remove(this.categoria);
			listar();
			limpar();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Removido com Sucesso"));

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Não é possivel remover esta categoria"));
}
		
	}

	
	public void verifica() {
		Long id = this.categoria.getId();
		if (id == null) {
			save();
			listar();
			limpar();

		} else {

			merge();
			listar();
			limpar();
		}
	}

	public void merge() {
		CategoriaInterface categoriaService = new CategoriaInterfaceImpl();

		categoriaService.atualiza(this.categoria);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Editado com Sucesso"));

	}

	public List<Categoria> pesquisarCategorias() {
		CategoriaInterface categoriaService = new CategoriaInterfaceImpl();
		List<Categoria> lista = categoriaService
				.pesquisaByCategoria(this.categoria.getDescricao());
		this.listCategoria = lista;

		if (lista.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(
							"Não foi possivel encontrar, tente novamente"));
			return lista;

		} else {
			
			return lista;
		}

	}

	
	// lista a ser exibida no combo
	 
	public List<SelectItem> getListDescricao() {
		listar();
		List<SelectItem> retorno = new ArrayList<SelectItem>();
		for (Categoria cat : listDescricao) {
			retorno.add(new SelectItem(cat, cat.getDescricao()));

		}
		return retorno;
	}

	
	public void setListDescricao(List<Categoria> listCategoria) {
		this.listCategoria = listCategoria;
	}

	public List<Categoria> getListCategoria() {
		return listCategoria;
	}

	public void setListCategoria(List<Categoria> listCategoria) {
		this.listCategoria = listCategoria;
	}
	
	public Categoria getCategoria() {

		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}