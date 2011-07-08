package br.sicofi.locadora.facade;

import java.util.List;

import br.sicofi.locadora.model.Locacao;

public interface LocacaoInterface extends BaseInterface<Locacao> {

	public void salva(Locacao l);
	
	public void remove(Locacao l);
	
	public Locacao procura(Long id);

	public void atualiza(Locacao l);
	
	public List<Locacao> listaByCliente (Long id);

}