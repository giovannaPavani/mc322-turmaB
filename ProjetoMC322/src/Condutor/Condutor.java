package Condutor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import Sinistro.Sinistro;
import Veiculo.Veiculo;

public class Condutor {
	
	private final String cpf;
	private String nome; 
	private String telefone; 
	private String endereco; 
	private String email;
	private LocalDate dataNascimento;
	private LinkedList<Sinistro> listaSinistros;
	
	// Construtor
	public Condutor(String cpf, String nome, String telefone, String endereco, 
			        String email, LocalDate dataNascimento, LinkedList<Sinistro> listaSinistros) {
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.endereco = endereco;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.listaSinistros = listaSinistros;
	}

	// getters e setters
	public String getCpf() {
		return cpf;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public LinkedList<Sinistro> getListaSinistros() {
		return listaSinistros;
	}

	public void setListaSinistros(LinkedList<Sinistro> listaSinistros) {
		this.listaSinistros = listaSinistros;
	}

	// TOTEST
	public boolean adicionarSinistro(Sinistro sinistro) {
		if(sinistro == null)
			return false;
		
		return listaSinistros.add(sinistro);
	}
	
	// TOTEST
	public boolean removerSinistro(Sinistro sinistro) {
		if(sinistro == null)
			return false;
		
		return listaSinistros.remove(sinistro);
	}
	
	public String toString() {
		String ret = "";
		// formatador para converter o objeto LocalDate em String do formato "dd/MM/yyyy"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ret += "CPF: "+this.cpf+"\n";
		ret += "Nome: "+this.nome+"\n";
		ret += "Telefone: "+this.telefone+"\n";
		ret += "Endereço: "+this.endereco+"\n";
		ret += "Email: "+this.email+"\n";
		ret += "Data de nascimento: "+this.dataNascimento.format(formatter)+"\n";
		
		if(listaSinistros != null && !listaSinistros.isEmpty()) {
			ret += "-----------------\n";
			ret += "Lista de Sinistros\n";
			ret += "-----------------";
			for(Sinistro sinistro: listaSinistros)
				ret += "\n-\n" + sinistro.toString();
			ret += "\n-";
		}
		
		return ret;
	}
	
	public String toStringSimples() {
		String ret = "";
		// formatador para converter o objeto LocalDate em String do formato "dd/MM/yyyy"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ret += "CPF: "+this.cpf+"\n";
		ret += "Nome: "+this.nome+"\n";
		ret += "Telefone: "+this.telefone+"\n";
		ret += "Data de nascimento: "+this.dataNascimento.format(formatter)+"\n";
		
		return ret;
	}
}
