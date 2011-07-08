package br.sicofi.locadora.facade;

import java.util.List;

import br.sicofi.locadora.model.Genero;

public interface GeneroInterface extends BaseInterface<Genero> {

	public void salva(Genero g);
	
	public void remove(Genero g);
	
	public Genero procura(Long id);

	public void atualiza(Genero g);

	public List<Genero> pesquisaByGenero(String g);
}