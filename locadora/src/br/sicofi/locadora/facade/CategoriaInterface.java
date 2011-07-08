package br.sicofi.locadora.facade;

import java.util.List;

import br.sicofi.locadora.model.Categoria;

public interface CategoriaInterface extends BaseInterface<Categoria> {

	public void salva(Categoria c);
	
	public void remove(Categoria c);
	
	public Categoria procura(Long id);
	
	public void atualiza(Categoria c);
	
	public List<Categoria> pesquisaByCategoria(String c);
	
}