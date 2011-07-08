package br.sicofi.locadora.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.sicofi.locadora.facade.GeneroInterface;
import br.sicofi.locadora.facade.GeneroInterfaceImpl;
import br.sicofi.locadora.model.Genero;

public class GeneroBean implements Serializable {

	private static final long serialVersionUID = -333995781063775201L;

	private Genero genero = new Genero();

	private Long id;

	private List<Genero> listGeneros = new ArrayList<Genero>();
	
	private List<Genero> listDescricao = new ArrayList<Genero>();

	public GeneroBean() {

		if (this.genero == null) {
			this.genero = new Genero();
		}
		
		listar();
	}

	//Lista gêneros
	 
	public void listar() {
		GeneroInterface generoService = new GeneroInterfaceImpl();
		this.listDescricao = generoService.lista();
		this.listGeneros = generoService.lista();

	}

	public void save() {
		GeneroInterface generoService = new GeneroInterfaceImpl();

		generoService.salva(this.genero);

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage("Cadastro Realizado com Sucesso",
						"Erro ao cadastrar"));

	}

	public void limpar() {

		this.genero = new Genero();

	}

	public void delete() {
		
		try {
			GeneroInterface generoService = new GeneroInterfaceImpl();
			this.genero.setId(this.genero.getId());
			generoService.remove(this.genero);
			listar();
			limpar();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Removido com Sucesso"));
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Não é possivel remover este gênero"));
		}
	
	}

	
	public void verifica() {
		Long id = this.genero.getId();
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
		GeneroInterface generoService = new GeneroInterfaceImpl();

		generoService.atualiza(this.genero);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Editado com Sucesso"));

	}

	public List<Genero> pesquisarGeneros() {
		GeneroInterface generoService = new GeneroInterfaceImpl();
		List<Genero> lista = generoService.pesquisaByGenero(this.genero
				.getDescricao());
		this.listGeneros = lista;

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
	
	public void setListDescricao(List<Genero> listDescricao) {
		this.listDescricao = listDescricao;
	}
	
	public List<SelectItem> getListDescricao() {
		listar();
		List<SelectItem> retorno = new ArrayList<SelectItem>();
		for(Genero gen : listDescricao){
			
			retorno.add(new SelectItem(gen, gen.getDescricao()));
			
		}
		return retorno;
	}



	public List<Genero> getListGeneros() {
		return listGeneros;
	}
	
	public void setListGenero(List<Genero> listGeneros) {
		this.listGeneros = listGeneros;
	}

	public Genero getGenero() {

		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

}