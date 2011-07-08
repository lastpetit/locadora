package br.sicofi.locadora.bean;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import br.sicofi.locadora.facade.FilmeInterface;
import br.sicofi.locadora.facade.FilmeInterfaceImpl;
import br.sicofi.locadora.model.Filme;

public class FilmeBean implements Serializable {

	private static final long serialVersionUID = 5373193752170526087L;

	private Filme filme = new Filme();

	private Long id;

	private byte[] arquivo;

	private int size;

	private String nome;

	private int tamanho;

	private List<Filme> listFilmes = new ArrayList<Filme>();

	private List<Filme> listFilmesNotNull = new ArrayList<Filme>();

	
	public FilmeBean() {
		if (this.filme == null) {
			this.filme = new Filme();
		}
		listar();
		listarNotNull();
		size = 0;
	}

	public void listener(UploadEvent event) throws Exception {
		UploadItem item = event.getUploadItem();

		this.nome = item.getFileName();
		this.tamanho = item.getFileSize();
		setArquivo(item.getData());
		setSessionParam("foto", getArquivo());
		this.size=1;

	}

	public void paint(OutputStream stream, Object object) throws IOException {

		byte[] foto = (byte[]) getSessionParam("foto");
		stream.write(foto);
	}


	
	 //Lista filmes
	 
	public void listar() {
		FilmeInterface filmeService = new FilmeInterfaceImpl();
		this.listFilmes = filmeService.lista();

	}

	public void listarNotNull() {
		int x = this.listFilmes.size();

		for (int i = 0; i < x; ++i) {
			Filme fil = listFilmes.get(i);

			if (fil.getQtdCopias() != 0)
				this.listFilmesNotNull.add(fil);

		}
		
	}

	
	public void save() {
		FilmeInterface filmeService = new FilmeInterfaceImpl();
		this.filme.setImagem(getArquivo());
		filmeService.salva(this.filme);

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Cadastro Realizado com Sucesso"));
	
	}

	
	public String limpar() {

		this.filme = new Filme();
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().clear();
				
		this.size=0;
		this.arquivo=null;
		this.tamanho= 0;
		return "toAtualizaFilme";

	}

	
	public void delete() {
		FilmeInterface filmeService = new FilmeInterfaceImpl();
		this.filme.setId(this.filme.getId());
		filmeService.remove(this.filme);
		limpar();
		listar();

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Removido com Sucesso"));
	}

	
	public void verifica() {
		Long id = this.filme.getId();
		if (id == null) {

			save();
			listar();

		} else {

			merge();
			listar();
			
		}

	}

	public void merge() {
		FilmeInterface filmeService = new FilmeInterfaceImpl();
		filmeService.atualiza(this.filme);

		byte[] novaFoto = this.getArquivo();

		int i = 1;
		if (novaFoto == null)
			i = 0;

		if (i == 0) {

			filmeService.atualiza(this.filme);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Não editada a foto"));
		} else {
			this.filme.setImagem(getArquivo());
			filmeService.atualiza(this.filme);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Editado Foto"));
		}

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Editado com Sucesso"));

	}

	public List<Filme> filmesByTitulo() {
		FilmeInterface filmeService = new FilmeInterfaceImpl();

		List<Filme> lista = filmeService.pesquisaByTitulo(this.filme
				.getTitulo());
		this.listFilmes = lista;
		if (lista.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(
							"Não foi possivel encontrar, tente novamente"));
			return listFilmes;

		} else {
			return listFilmes;
		}

	}

	public List<Filme> filmesByGenero() {
		FilmeInterface filmeService = new FilmeInterfaceImpl();

		List<Filme> lista = filmeService.pesquisaByGenero(this.filme
				.getGenero().getId());
		this.listFilmes = lista;
			
		if (listFilmes.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(
							"Não foi possivel encontrar, tente novamente"));
			return listFilmes;

		} else {

			return listFilmes;
		}

	}
	
	public List<Filme> filmesByTituloNotNull() {
		this.listFilmesNotNull.clear();
		FilmeInterface filmeService = new FilmeInterfaceImpl();

		List<Filme> lista = filmeService.pesquisaByTitulo(this.filme
				.getTitulo());
		
		this.listFilmes = lista;
		listarNotNull();
				
		if (listFilmesNotNull.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(
							"Não foi possivel encontrar, tente novamente"));
			return listFilmesNotNull;

		} else {
			return listFilmesNotNull;
		}

	}

	public List<Filme> filmesByGeneroNotNull() {
		this.listFilmesNotNull.clear();
		FilmeInterface filmeService = new FilmeInterfaceImpl();

		List<Filme> lista = filmeService.pesquisaByGenero(this.filme
				.getGenero().getId());
		this.listFilmes = lista;

		if (lista.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(
							"Não foi possivel encontrar, tente novamente"));
			return listFilmes;

		} else {

			return listFilmes;
		}

	}

	@SuppressWarnings("unchecked")
	private static Object getSessionParam(String param) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map sessionMap = context.getExternalContext().getSessionMap();
		return sessionMap.get(param);
	}

	@SuppressWarnings("unchecked")
	private static void setSessionParam(String param, Object object) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map sessionMap = context.getExternalContext().getSessionMap();
		sessionMap.put(param, object);
	}
	public void list() {
		FilmeInterface filmeService = new FilmeInterfaceImpl();
		this.listFilmes = filmeService.lista();
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Filme> getListFilmes() {
		return listFilmes;
	}

	public void setListFilmes(List<Filme> listFilmes) {
		this.listFilmes = listFilmes;
	}

	public List<Filme> getListFilmesNotNull() {
		return listFilmesNotNull;
	}

	public void setListFilmesNotNull(List<Filme> listFilmesNotNull) {
		this.listFilmesNotNull = listFilmesNotNull;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	

}