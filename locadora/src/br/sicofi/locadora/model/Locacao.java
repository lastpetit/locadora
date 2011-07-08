package br.sicofi.locadora.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Locacao implements Serializable {

	private static final long serialVersionUID = 7743861960225567635L;

	@Id
	@GeneratedValue
	private Long id;

	private float preco;

	@Temporal(TemporalType.DATE)
	private Date dateAluguel;

	@Temporal(TemporalType.DATE)
	private Date dateDevolucao;

	@ManyToOne
 	private Filme filme;

	@ManyToOne
	private Cliente cliente;
	
	@ManyToOne
	private Dependente dependente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public Date getDateAluguel() {
		return dateAluguel;
	}

	public void setDateAluguel(Date dateAluguel) {
		this.dateAluguel = dateAluguel;
	}

	public Date getDateDevolucao() {
		return dateDevolucao;
	}

	public void setDateDevolucao(Date dateDevolucao) {
		this.dateDevolucao = dateDevolucao;
	}


	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Dependente getDependente() {
		return dependente;
	}

	public void setDependente(Dependente dependente) {
		this.dependente = dependente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Locacao other = (Locacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
