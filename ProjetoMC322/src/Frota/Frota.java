package Frota;

import java.util.LinkedList;
import java.util.Random;

import Veiculo.Veiculo;

public class Frota {

	private String code;
	private LinkedList<Veiculo> listaVeiculos;
	
	public Frota(LinkedList<Veiculo> listaVeiculos) {
		this.code = gerarId();
		this.listaVeiculos = listaVeiculos;
	}
	
	private Frota(String code, LinkedList<Veiculo> listaVeiculos) {
		this.code = code;
		this.listaVeiculos = listaVeiculos;
	}
	
	private String gerarId() {
		Random gerador = new Random();
		int ret = gerador.nextInt();
		
		// caso seja negativo, arruma o sinal
		if(ret < 0)
			ret = -ret;
		
        return ret+""; // converte para string
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public LinkedList<Veiculo> getListaVeiculos() {
		return listaVeiculos;
	}
	
	public void setListaVeiculos(LinkedList<Veiculo> listaVeiculos) {
		this.listaVeiculos = listaVeiculos;
	}
	
	// TOTEST
	// cadastra (se já nao o estiver) o veiculo passado por parametro no cliente
	public boolean addVeiculo(Veiculo veiculo) {
		if(this.listaVeiculos.contains(veiculo) || veiculo == null) // veiculo ja cadastrado ou nulo
			return false;
		
		return this.listaVeiculos.add(veiculo);
	}
	
	// TOTEST
	// remove (caso exista) o veiculo passado por parametro e seus sinistros do cliente
	public boolean removeVeiculo(Veiculo veiculo) {
		if(veiculo == null) // veiculo nulo
			return false;
		
		return this.listaVeiculos.remove(veiculo);
	}
	
	// TOTEST
	// remove (caso exista) o veiculo cuja placa é passada por parametro e seus sinistros do cliente
	public boolean removerVeiculo(String placa) {
		if(placa == null || placa.equals("")) // placa nula
			return false;
		
		Veiculo veiculo = getVeiculoByPlaca(placa);
		if(veiculo == null)
			return false;
		
		return this.listaVeiculos.remove(veiculo);
	}
	
	// TOTEST
	public Veiculo getVeiculoByPlaca(String placa) {
		for(Veiculo veiculo: listaVeiculos)
			if(veiculo.getPlaca().equals(placa))
				return veiculo;	
		
		return null;
	}
	
	public Frota clone() {
		LinkedList<Veiculo> lista = new LinkedList<Veiculo>();
		for(Veiculo v: listaVeiculos)
			lista.add(v);
		
		return new Frota(this.code, lista);
	}
	
	// TOTEST
	public String toString() {
		String ret = "";
		ret += "Code: " + this.code + "\n";
		if(listaVeiculos != null && !listaVeiculos.isEmpty()) {
			ret += "-----------------\n";
			ret += "Lista de Veículos\n";
			ret += "-----------------";
			for(Veiculo veiculo: listaVeiculos)
				ret += "\n-\n" + veiculo.toString();
			ret += "\n-";
		}
		
		return ret;
	}
	
	// TOTEST
	public String toStringSimples() {
		String ret = "";
		ret += "Code: " + this.code + "\n";
		if(listaVeiculos != null && !listaVeiculos.isEmpty()) {
			ret += "Lista de Veículos:\n";
			for(Veiculo veiculo: listaVeiculos)
				ret += " " + veiculo.getPlaca() + "\n";
		}
		
		return ret;
	}
}
