package Sinistro;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import Cliente.Cliente;
import Condutor.Condutor;
import Seguradora.Seguradora;
import Seguro.Seguro;
import Veiculo.Veiculo;

public class Sinistro {
	
	// Propriedades
	private final int id;
	private LocalDate data;
	private String endereco;
	private Condutor condutor;
	private Seguro seguro;
	
	// Construtor
	public Sinistro(LocalDate data, String endereco, Condutor condutor, Seguro seguro) {
		this.id = gerarId();
		this.data = data;
		this.endereco = endereco;
		this.condutor = condutor;
		this.seguro = seguro;
	}
	
	// Função geradora de ids aleatórios
	private int gerarId() {
		Random gerador = new Random();
		int ret = gerador.nextInt();
		// caso seja negativo, arruma o sinal
		if(ret < 0)
			ret = -ret;
        return ret;
	}
	
	// Getters e setters
	public int getId() {
		return id;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public Condutor getCondutor() {
		return condutor;
	}

	public void setCondutor(Condutor condutor) {
		this.condutor = condutor;
	}
	
	public Seguro getSeguro() {
		return seguro;
	}

	public void setSeguro(Seguro seguro) {
		this.seguro = seguro;
	}
	
	public String toString() {
		String ret = "";
		// formatador para converter o objeto LocalDate em String do formato "dd/MM/yyyy"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ret += "ID: " + this.id+"\n";
		ret += "Data: " + this.data.format(formatter)+"\n";
		ret += "Endereço: "+this.endereco+"\n";
		ret += "---------------------\n";
		ret += "  Dados do Condutor\n";
		ret += "---------------------\n";
		ret += this.condutor.toStringSimples()+"\n";
		ret += "-----------------------\n";
		ret += "    Dados do Seguro\n";
		ret += "-----------------------\n";
		ret += this.seguro.toStringSimples();
		
		return ret;
	}
	
	// TOTEST
	// toString mais simples, com menos informacoes do que o oficial
	public String toStringSimples() {
		String ret = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ret += "ID: "+this.id+"\n";
		ret += "Data: "+this.data.format(formatter)+"\n";
		ret += "Endereço: "+this.endereco+"\n";

		return ret;
	}
}
