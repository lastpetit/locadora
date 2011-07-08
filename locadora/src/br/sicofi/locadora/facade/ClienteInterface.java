package br.sicofi.locadora.facade;

import java.util.List;

import br.sicofi.locadora.model.Cliente;

public interface ClienteInterface extends BaseInterface<Cliente> {

	public void salva(Cliente c);
	
	public void remove(Cliente c);
	
	public void atualiza(Cliente c);

	public List<Cliente> pesquisa(String p, int i);
	
	
}