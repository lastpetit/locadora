package br.sicofi.locadora.teste;

import java.util.List;

import br.sicofi.locadora.facade.ClienteInterface;
import br.sicofi.locadora.facade.ClienteInterfaceImpl;
import br.sicofi.locadora.model.Cliente;

public class Teste {

	public void Fazer(){
		ClienteInterface clienteService = new ClienteInterfaceImpl();
		List<Cliente> lista = clienteService.pesquisa("222.333.444-05", 2);
		List<Cliente> lista2 = clienteService.pesquisa(" ", 1);
		
		for(Cliente client : lista2){			
			if(lista.get(0).getId().equals(client.getId())){
				System.out.println("ACHEI!");
				lista2.remove(client);
					for(Cliente client2 : lista2){
						System.out.println("\n\n\nO meu Id eh: " + client2.getId());
						System.out.println("O nome eh: " + client2.getNome());
						System.out.println("Meu CPF eh: " + client2.getCpf());
					}
			}	else {
					System.out.println("DIFERENTE!");
			}
		}
	}
	public static void main(String[] args) {
		
		Teste teste = new Teste();
		
		teste.Fazer();
	}
}
