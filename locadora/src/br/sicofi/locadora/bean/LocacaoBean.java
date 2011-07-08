package br.sicofi.locadora.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.sicofi.locadora.facade.DependenteInterface;
import br.sicofi.locadora.facade.DependenteInterfaceImpl;
import br.sicofi.locadora.facade.FilmeInterface;
import br.sicofi.locadora.facade.FilmeInterfaceImpl;
import br.sicofi.locadora.facade.LocacaoInterface;
import br.sicofi.locadora.facade.LocacaoInterfaceImpl;
import br.sicofi.locadora.model.Cliente;
import br.sicofi.locadora.model.Dependente;
import br.sicofi.locadora.model.Filme;
import br.sicofi.locadora.model.Locacao;

public class LocacaoBean implements Serializable {

	private static final long serialVersionUID = 5373193752170526087L;

	private Locacao locacao = new Locacao();

	private Cliente cliente = new Cliente();

	private Calendar hoje;

	private Filme filme = new Filme();
	
	private Date dataAluguel;
	
	private Date dataDevolucao;

	private Dependente dependente = new Dependente();

	private float multa;
	
	private float preco;
	
	private float precoTotal;

	private boolean temDependente = false;

	private List<Locacao> listLocacao = new ArrayList<Locacao>();

	private List<Filme> listFilmes = new ArrayList<Filme>();

	private List<Dependente> listDependente = new ArrayList<Dependente>();

	private Long id;

	
	public LocacaoBean() {
		if (this.locacao == null) {
			this.locacao = new Locacao();

		}
		listarLocacao();
		listarDependente();
	}

	public void listarDependente() {
		DependenteInterface dependenteService = new DependenteInterfaceImpl();
		this.listDependente = dependenteService.lista();

	}

	//Calcula a data de devolução.

	
	public String calcularData() {

		try {

			SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
			Calendar calendar = Calendar.getInstance();
			setDataAluguel(calendar.getTime());
			int i = getFilme().getCategoria().getTotalDias();
			formatData.format(i);
			calendar.add(Calendar.DAY_OF_MONTH, i);
			setDataDevolucao(calendar.getTime());
			setPreco(getFilme().getCategoria().getPreco());
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		listarLocacao();
		return "toAlugarFilmeCliente";

	}

	public String apontarDependente() {
		return "toAlugarFilme";

	}

	//  Calcula se está em atraso e envia para calcular o preco total
	
	@SuppressWarnings("static-access")
	public String calculo() {
		Date date = this.locacao.getDateDevolucao();
		
		hoje = Calendar.getInstance(); // Hoje é o dia atual 
		Calendar devolucao = Calendar.getInstance(); // Cria uma outra instancia do calendar
		devolucao.setTime(date); // Seta a devolucao pra data marcada

		int h = this.hoje.get(this.hoje.DAY_OF_MONTH);
		int d = devolucao.get(devolucao.DAY_OF_MONTH);
		int hm = this.hoje.get(this.hoje.MONTH);
		int hd = devolucao.get(devolucao.MONTH);

		if ((h == d || h < d) && (hm < hd || hm == hd)) {
			this.preco = this.locacao.getFilme().getCategoria().getPreco();
			this.locacao.setPreco(this.preco);
			this.multa = 0.0f;
			this.precoTotal =preco;
		} else {

			this.preco = this.locacao.getFilme().getCategoria().getPreco();

			if (hm > hd) {

				int diasMes = devolucao.getActualMaximum(Calendar.DAY_OF_MONTH);
				int diasAtraso = d - diasMes;
				
				setMulta(( diasAtraso + h)* this.preco);
				this.precoTotal = this.preco + this.multa;
				
				
			} else {

				int count = (h - 1) - d;
				this.multa =(count * preco);
				this.precoTotal = preco + multa;
				
				merge();
			}
		}
		return "toDevolverFilmeCliente";
	}

	public void merge() {
		LocacaoInterface locacaoService = new LocacaoInterfaceImpl();
		locacaoService.atualiza(this.locacao);
	}

	// Lista as locações do usuário 

	public String listarLocacao() {
		LocacaoInterface locacaoService = new LocacaoInterfaceImpl();
		this.listLocacao = locacaoService.listaByCliente(this.cliente.getId());
		locacao = new Locacao();
		this.temDependente = false;
		for (Object object : listLocacao) {
			locacao = (Locacao) object;
			if (locacao.getDependente() != null) {
				this.temDependente = true;
			}
		}
		return "toDevolverFilme";

	}


	@SuppressWarnings("static-access")
	public void validaSave() {
		hoje = Calendar.getInstance();

		Calendar devolucao = Calendar.getInstance();
		devolucao.setTime(this.getDataDevolucao());

		int h = this.hoje.get(this.hoje.DAY_OF_MONTH);
		int d = devolucao.get(devolucao.DAY_OF_MONTH);
		int esteMes = this.hoje.get(this.hoje.MONTH);
		int mesAluguel = devolucao.get(devolucao.MONTH);

		int diasMes = devolucao.getActualMaximum(Calendar.DAY_OF_MONTH);
		int dias = d - diasMes; // calcula quantos dias desde o fim do mes
		Locacao loc = new Locacao();
		
		
		if (esteMes <= mesAluguel) { // verifica se a locação é deste mes ou nao
			int i = listLocacao.size();
			
			try {
								
				for (Object o : listLocacao) {
					loc = (Locacao) o;
					float y = loc.getFilme().getId();
					float x = this.filme.getId();
					i--;
					
					if ((x == y) && (h - d) <= 1) { // verifica se o filme está na lista
						
								
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(
										"Este filme já está na sua lista de alugados"));
						break;
						
					} else {
						if (i==0) {
							save();
						break;
						}
					}
				}
			}
				
			 catch (Exception e) {
			
				e.printStackTrace();
			}

		} else {
			if ((dias + h) <= 1) {

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Este filme já foi alugado por você"));
			}
		}
		if(listLocacao.isEmpty()){
			save();
		}
	}


	public void save() {

		LocacaoInterface locacaoService = new LocacaoInterfaceImpl();
		this.locacao.setCliente(this.cliente);
		this.locacao.setFilme(this.filme);
		this.locacao.setDateAluguel(getDataAluguel());
		this.locacao.setDateDevolucao(getDataDevolucao());
		this.locacao.setPreco(getPreco());
		if (this.dependente != null) {
			this.locacao.setDependente(this.dependente);
		}
		this.locacao.setDependente(null);

		locacaoService.salva(this.locacao);

		FilmeInterface filmeService = new FilmeInterfaceImpl();
		int copias = this.filme.getQtdCopias();
		this.filme.setQtdCopias(--copias);
		filmeService.atualiza(this.filme);

		listarLocacao();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Alugado com Sucesso"));

	}

	public void limpar() {

		this.locacao = new Locacao();
	}

	
	public void delete() {
		LocacaoInterface locacaoService = new LocacaoInterfaceImpl();
		FilmeInterface filmeService = new FilmeInterfaceImpl();
		int copias = this.locacao.getFilme().getQtdCopias();
		this.locacao.getFilme().setQtdCopias(++copias);
		this.locacao.setPreco(precoTotal);
		filmeService.atualiza(this.locacao.getFilme());

		this.locacao.setId(this.locacao.getId());
		locacaoService.remove(this.locacao);
		limpar();
		listarLocacao();

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Devolvido com Sucesso"));
	}

	public Locacao getLocacao() {
		return locacao;
	}

	public void setLocacao(Locacao locacao) {
		this.locacao = locacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Locacao> getListLocacao() {
		
		return listLocacao;
	}

	public void setListLocacao(List<Locacao> listLocacao) {
		this.listLocacao = listLocacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	public List<Filme> getListFilmes() {
		return listFilmes;
	}

	public void setListFilmes(List<Filme> listFilmes) {
		this.listFilmes = listFilmes;
	}

	public Calendar getHoje() {
		return hoje;
	}

	public void setHoje(Calendar hoje) {
		this.hoje = hoje;
	}

	public List<Dependente> getListDependente() {
		this.listDependente = this.cliente.getDependente();
		return listDependente;
	}

	public void setListDependente(List<Dependente> listDependente) {
		this.listDependente = listDependente;
	}

	public float getMulta() {
		return multa;
	}

	public void setMulta(float multa) {
		this.multa = multa;
	}

	public Dependente getDependente() {
		return dependente;
	}

	public void setDependente(Dependente dependente) {
		System.out
				.println("==============================================================="
						+ temDependente);
		this.temDependente = true;
		System.out
				.println("==============================================================="
						+ temDependente);
		this.dependente = dependente;
		System.out
				.println("==============================================================="
						+ dependente.getNome());
	}

	public boolean isTemDependente() {
		return temDependente;
	}

	public void setTemDependente(boolean temDependente) {
		this.temDependente = temDependente;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public float getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(float precoTotal) {
		this.precoTotal = precoTotal;
	}

	public Date getDataAluguel() {
		return dataAluguel;
	}

	public void setDataAluguel(Date dataAluguel) {
		this.dataAluguel = dataAluguel;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

}