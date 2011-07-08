package br.sicofi.locadora.facade;

import java.util.List;

import br.sicofi.locadora.model.Filme;

public interface FilmeInterface extends BaseInterface<Filme> {

	public void salva(Filme f);
	
	public void remove(Filme f);
	
	public void atualiza(Filme f);

	public List<Filme> pesquisaByTitulo(String titulo);

	public List<Filme> pesquisaByGenero(Long id);
	

}