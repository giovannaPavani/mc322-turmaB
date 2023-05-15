package Cliente;
import java.util.LinkedList;

import Veiculo.Veiculo;

public class Cliente {

	// Propriedades
	protected String nome;
	protected String endereco;
	protected LinkedList<Veiculo> listaVeiculos;
	protected double valorSeguro;
	
	// Construtor
	public Cliente(String nome, String endereco, LinkedList<Veiculo> listaVeiculos) {
		this.nome = nome;
		this.endereco = endereco;
		this.listaVeiculos = listaVeiculos;
		this.valorSeguro = 0.0;
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
	
	public LinkedList<Veiculo> getListaVeiculos() {
		return listaVeiculos;
	}

	public void setListaVeiculos(LinkedList<Veiculo> listaVeiculos) {
		this.listaVeiculos = listaVeiculos;
	}
	
	public double getValorSeguro() {
		return valorSeguro;
	}

	public void setValorSeguro(double valorSeguro) {
		this.valorSeguro = valorSeguro;
	}

	
	// Gera String com todas as informações do cliente
	public String toString() {
		String ret = "";
		
		ret += "Nome: " + this.nome + "\n";
		ret += "Endereco: " + this.endereco + "\n";
		ret += "Valor do seguro: "+ this.valorSeguro +"\n";
		if(listaVeiculos != null && !listaVeiculos.isEmpty()) {
			ret += "-----------------\n";
			ret += "Lista de Veículos\n";
			ret += "-----------------";
			for(Veiculo veiculo: listaVeiculos)
				ret += "\n-\n" + veiculo.toString(); // ou veiculo.getPlaca() p/ ficar menos poluido
			ret += "\n-";
		}
		return ret;
	}
	
	// define score como 0
	public double calculaScore() {
		return 0.0;
	}
	
	/* ====================
	 *  MÉTODOS AUXILIARES
	 * ==================== */
	
	// cadastra (se já nao o estiver) o veiculo passado por parametro no cliente
	public boolean adicionarVeiculo(Veiculo veiculo) {
		if(this.listaVeiculos.contains(veiculo) || veiculo == null) // veiculo ja cadastrado ou nulo
			return false;
		
		return this.listaVeiculos.add(veiculo);
	}
	
	// remove (caso exista) o veiculo passado por parametro do cliente
	public boolean removerVeiculo(Veiculo veiculo) {
		if(veiculo == null) // veiculo nulo
			return false;
		return this.listaVeiculos.remove(veiculo);
	}
	
	// remove (caso exista) o veiculo passado por parametro do cliente
	public boolean removerVeiculo(String placa) {
		if(placa == null) // veiculo nulo
			return false;
		
		Veiculo veiculo = null;
		for(Veiculo v: listaVeiculos) {
			if(v.getPlaca().equals(placa))
				veiculo = v;
		}
		
		if(veiculo == null)
			return false;
		
		return this.listaVeiculos.remove(veiculo);
	}
	
	public Veiculo getVeiculoByPlaca(String placa) {
		Veiculo ret = null;
		for(Veiculo veiculo: listaVeiculos)
			if (veiculo.getPlaca().equals(placa)) {
				ret = veiculo;	
				break;
			}
		return ret;
	}
	
	// Gera String mais simples com algumas informações do cliente
	public String toStringSimples() {
		String ret = "";
		
		ret += "Nome: " + nome + "\n";
		ret += "Endereco: " + endereco + "\n";

		return ret;
	}
}