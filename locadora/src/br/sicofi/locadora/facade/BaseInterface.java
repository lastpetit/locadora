package br.sicofi.locadora.facade;

import java.io.Serializable;
import java.util.List;


public interface BaseInterface<T> extends Serializable {

	public abstract void salva(T t);

	public abstract void remove(T t);

	public abstract void atualiza(T t);

	public abstract List<T> lista();
	
	
	
	
}