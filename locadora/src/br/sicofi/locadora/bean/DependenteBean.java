package br.sicofi.locadora.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.sicofi.locadora.facade.ClienteInterface;
import br.sicofi.locadora.facade.ClienteInterfaceImpl;
import br.sicofi.locadora.model.Cliente;
import br.sicofi.locadora.model.Dependente;

public class DependenteBean implements Serializable {

	private static final long serialVersionUID = 5373193752170526087L;

	private Dependente dependente = new Dependente();
	private Cliente cliente = new Cliente();
	private List<Cliente> listClientes = new ArrayList<Cliente>();

	public DependenteBean() {

		if (this.dependente == null) {
			this.dependente = new Dependente();
		}
		listar();
	}

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

	public void listar() {
		ClienteInterface clienteService = new ClienteInterfaceImpl();
		this.listClientes = clienteService.lista();

	}
	
	public void limpar() {

		this.cliente = new Cliente();
	}
	
	public void limparDependente() {

		this.dependente = new Dependente();
	}

	public Dependente getDependente() {
		return dependente;
	}

	public void setDependente(Dependente dependente) {
		this.dependente = dependente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getListClientes() {
		return listClientes;
	}

	public void setListClientes(List<Cliente> listClientes) {
		this.listClientes = listClientes;
	}

}