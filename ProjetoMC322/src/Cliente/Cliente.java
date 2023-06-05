package Cliente;

import java.util.LinkedList;

import Veiculo.Veiculo;

public abstract class Cliente {

	// Propriedades
	protected String nome;
	protected String endereco;
	protected String telefone;
	protected String email;
	
	// Construtor
	public Cliente(String nome, String endereco, String telefone, String email) {
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.email = email;
	}
	
	// Getters e Setters
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// Gera String com todas as informações do cliente
	public String toString() {
		String ret = "";
		
		ret += "Nome: " + this.nome + "\n";
		ret += "Telefone: "+ this.telefone +"\n";
		ret += "Endereco: " + this.endereco + "\n";
		ret += "E-mail: "+ this.email +"\n";
		return ret;
	}
	
	/* ====================
	 *  MÉTODOS AUXILIARES
	 * ==================== */
	
	// Gera String mais simples com algumas informações do cliente
	public String toStringSimples() {
		String ret = "";
		
		ret += "Nome: " + nome + "\n";
		ret += "Telefone: " + telefone + "\n";

		return ret;
	}
	
	public abstract Veiculo getVeiculoByPlaca(String placa);
	
	public abstract LinkedList<Veiculo> getListaVeiculos();
	
	public abstract boolean removerVeiculo(String placa);
}