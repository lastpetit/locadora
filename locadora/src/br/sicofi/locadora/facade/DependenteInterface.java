package br.sicofi.locadora.facade;

import br.sicofi.locadora.model.Dependente;

public interface DependenteInterface extends BaseInterface<Dependente> {

	public void salva(Dependente d);

	public void remove(Dependente d);

	public void atualiza(Dependente d);


}