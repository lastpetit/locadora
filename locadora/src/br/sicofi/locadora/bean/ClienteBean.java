package br.sicofi.locadora.bean;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import br.sicofi.locadora.facade.ClienteInterface;
import br.sicofi.locadora.facade.ClienteInterfaceImpl;
import br.sicofi.locadora.facade.DependenteInterface;
import br.sicofi.locadora.facade.DependenteInterfaceImpl;
import br.sicofi.locadora.model.Cliente;
import br.sicofi.locadora.model.Dependente;

public class ClienteBean implements Serializable {

	private static final long serialVersionUID = 5373193752170526087L;

	private Cliente cliente = new Cliente();

	private Cliente clientePesquisa = new Cliente();

	private Dependente dependente = new Dependente();
	
	private byte[] arquivo;

	private int size;

	private String nome;

	private int tamanho;

	private Long id;

	private List<Cliente> listClientes = new ArrayList<Cliente>();

	private List<Dependente> listDependente = new ArrayList<Dependente>();

	/**
	 * Construtor que verifica se o bean foi inicializado.
	 */
	public ClienteBean() {

		if (this.cliente == null) {
			this.cliente = new Cliente();
		}
		
		listar();
		listarDependente();
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

	
	/**
	 * Lista todas os clientes
	 */
	public void listar() {
		ClienteInterface clienteService = new ClienteInterfaceImpl();
		this.listClientes = clienteService.lista();

	}
	
	

	public void listarDependente() {
		DependenteInterface dependenteService = new DependenteInterfaceImpl();
		this.listDependente = dependenteService.lista();

	}

	/**
	 * Vem do .xhtml e utiliza o método da interface para salvar o objeto
	 * enviado.
	 * 
	 * @return String mensagem de sucesso
	 */
	public void save() {
		ClienteInterface clienteService = new ClienteInterfaceImpl();
		cliente.setFoto(getArquivo());
		clienteService.salva(this.cliente);

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Cadastro Realizado com Sucesso"));
	}

	public void saveDependente() {
		DependenteInterface dependenteService = new DependenteInterfaceImpl();
		ClienteInterface clienteService = new ClienteInterfaceImpl();

		dependenteService.salva(this.dependente);

		List<Dependente> lista = cliente.getDependente();

		if (lista.size() == 3) {
			dependenteService.remove(this.dependente);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(
							"Não é possivel cadastrar mais dependentes"));
		} else {
			lista.add(this.dependente);
			this.listDependente = lista;
			this.cliente.setDependente(listDependente);
			clienteService.atualiza(this.cliente);

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(
							"Cadastro de dependente realizado com Sucesso"));

		}
		limparDependente();
	}

	/**
	 * Verifica se a cliente vai ser criada ou editada
	 * 
	 * @return
	 */
	public void verifica() {

		Long id = this.cliente.getId();

		if (id == null || id == 0) {
			conferirCpf();
			listar();

		} else {

			conferirCpf2();
			listar();

		}
	}

	public void verificaDependente() {

		Long id = this.dependente.getId();

		if (id == null || id == 0L) {

			saveDependente();
			listarDependente();
			limparDependente();

		} else {

			mergeDependente();
			listarDependente();
			limparDependente();

		}
	}

	/**
	 * Edita utilizando a interface
	 * 
	 * @return retorna mensagem de sucesso
	 */
	public void merge() {
		ClienteInterface clienteService = new ClienteInterfaceImpl();
		clienteService.atualiza(this.cliente);

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Editado com Sucesso"));

	}

	public void mergeDependente() {
		DependenteInterface dependenteService = new DependenteInterfaceImpl();
		dependenteService.atualiza(this.dependente);

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Editado dependente com Sucesso"));
		limparDependente();
	}

	/**
	 * Limpa cliente usar o redirect em faces-config
	 */
	public String limpar() {

		this.cliente = new Cliente();
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().clear();
	
		this.size=0;
		this.arquivo=null;
		this.tamanho= 0;
		return "toAtualizaCliente";
	}

	/**
	 * Limpa cliente usar o redirect em faces-config
	 */
	public void limparDependente() {

		this.dependente = new Dependente();
	}
	
	public void validateEmail(FacesContext context, UIInput toValidate) {

		String email = (String) toValidate.getValue();
		if (email.indexOf('@') == -1) {
			toValidate.setValid(false);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Email não válido"));
		} else {
			toValidate.setValid(true);
		}
	}


	/**
	 * Deleta verificando se o id é o escolhido
	 * 
	 * @return retorna mensagem de sucesso
	 */
	public void delete() {
		try {
			ClienteInterface clienteService = new ClienteInterfaceImpl();
			this.cliente.setId(this.cliente.getId());
			clienteService.remove(this.cliente);
			listar();
			limpar();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Removido com Sucesso"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Não é possivel remover este cliente"));
		}

	}

	public void deleteDependente() {
		try {
			DependenteInterface dependenteService = new DependenteInterfaceImpl();
			this.dependente.setId(this.dependente.getId());
			dependenteService.remove(this.dependente);

			List<Dependente> lista = cliente.getDependente();
			this.listDependente = lista;
			listarDependente();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Removido com Sucesso"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Não é possivel remover"));
		}
		{

		}

	}

	/**
	 * Verifica qual é o tipo de pesquisa 1 é por nome 2 é por cpf
	 * 
	 * @return
	 */
	public List<Cliente> pesquisaClientes() {
		ClienteInterface clienteService = new ClienteInterfaceImpl();

		String verificar = this.cliente.getCpf();

		if (verificar == null || verificar == "") {
			List<Cliente> lista = clienteService.pesquisa(this.cliente
					.getNome(), 1);
			this.listClientes = lista;
			limpar();
			return listClientes;
		} else {

			List<Cliente> lista = clienteService.pesquisa(
					this.cliente.getCpf(), 2);
			this.listClientes = lista;
			limpar();

		}
		return listClientes;

	}

	public void list() {
		ClienteInterface clienteService = new ClienteInterfaceImpl();
		this.listClientes = clienteService.lista();
	}

	/**
	 * Verifica se o cpf já existe
	 */
	public void conferirCpf() {
		ClienteInterface clienteService = new ClienteInterfaceImpl();
		List<Cliente> lista = clienteService.pesquisa(this.cliente.getCpf(), 2);

		if (lista.isEmpty()) {
			save();
		} else {
			if (lista.size() == 1 && lista.contains(this.cliente.getCpf())) {
				merge();
			} else {
				FacesContext.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										"Esse cpf já existe em outro usuário"));
			}
		}
	}

	
	
	
	public void conferirCpf2() {
		ClienteInterface clienteService = new ClienteInterfaceImpl();
		List<Cliente> lista = clienteService.pesquisa(this.cliente.getCpf(), 2);
		List<Cliente> lista2 = clienteService.pesquisa(" ", 1);
		
		
		if (lista.isEmpty()) {
			save();
		} else {
			for(Cliente client : lista2){
				if( client.getCpf().equals(lista.get(0).getCpf()) && (!client.getId().equals(lista.get(0).getId()))){
					System.out.println("============================== TESTE =============================");
					FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									"Esse cpf já existe em outro usuário =("));
					
				}	else {
					System.out.println("-----------coisa---------------");
						merge();
						
						break;
				}
			}
		}
	}
	
	
	
	
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Cliente> getListClientes() {
		return listClientes;
	}

	public void setListClientes(List<Cliente> listClientes) {
		this.listClientes = listClientes;
	}

	public List<Dependente> getListDependente() {

		this.listDependente = this.cliente.getDependente();

		return listDependente;
	}

	public void setListDependente(List<Dependente> listDependente) {
		this.listDependente = listDependente;
	}

	public Dependente getDependente() {
		return dependente;
	}

	public void setDependente(Dependente dependente) {
		this.dependente = dependente;
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

	public Cliente getClientePesquisa() {
		return clientePesquisa;
	}

	public void setClientePesquisa(Cliente clientePesquisa) {
		this.clientePesquisa = clientePesquisa;
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

}