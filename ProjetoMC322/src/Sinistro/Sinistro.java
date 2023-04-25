package Sinistro;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import Cliente.Cliente;
import Seguradora.Seguradora;
import Veiculo.Veiculo;

public class Sinistro {
	
	// Propriedades
	private final int id;
	private LocalDate data;
	private String endereco;
	private Seguradora seguradora;
	private Veiculo veiculo;
	private Cliente cliente;
	
	// Construtor
	public Sinistro(LocalDate data, String endereco, Seguradora seguradora, Veiculo veiculo, Cliente cliente) {
		this.id = gerarId();
		this.data = data;
		this.endereco = endereco;
		this.seguradora = seguradora;
		this.veiculo = veiculo;
		this.cliente = cliente;
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
	
	public Seguradora getSeguradora() {
		return seguradora;
	}

	public void setSeguradora(Seguradora seguradora) {
		this.seguradora = seguradora;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public String toString() {
		String ret = "";
		// formatador para converter o objeto LocalDate em String do formato "dd/MM/yyyy"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ret += "ID: "+this.id+"\n";
		ret += "Data: "+this.data.format(formatter)+"\n";
		ret += "Endereço: "+this.endereco+"\n";
		ret += "-------------------\n";
		ret += "Dados da Seguradora\n";
		ret += "-------------------\n";
		ret += this.seguradora.toStringSimples()+"\n";
		ret += "----------------\n";
		ret += "Dados do Veículo\n";
		ret += "----------------\n";
		ret += this.veiculo.toString()+"\n";
		ret += "----------------\n";
		ret += "Dados do cliente\n";
		ret += "----------------\n";
		ret += this.cliente.toString()+"\n";
		return ret;
	}
	
	// toString mais simples, com menos informacoes do que o oficial
	public String toStringSimples() {
		String ret = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ret += "ID: "+this.id+"\n";
		ret += "Data: "+this.data.format(formatter)+"\n";
		ret += "Endereço: "+this.endereco+"\n";
		ret += "Seguradora: "+this.seguradora.getNome()+"\n";
		ret += "----------------\n";
		ret += "Dados do Veículo\n";
		ret += "----------------\n";
		ret += this.veiculo.toString()+"\n";
		ret += "----------------\n";
		ret += "Dados do cliente:\n";
		ret += "----------------\n";
		ret += this.cliente.toStringSimples();
		return ret;
	}
}
