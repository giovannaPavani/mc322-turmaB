package Frota;

import java.util.LinkedList;
import Veiculo.Veiculo;

public class Frota {

	String code;
	LinkedList<Veiculo> listaVeiculos;
	
	public Frota(String code, LinkedList<Veiculo> listaVeiculos) {
		this.code = code;
		this.listaVeiculos = listaVeiculos;
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
	public boolean cadastrarVeiculo(Veiculo veiculo) {
		if(this.listaVeiculos.contains(veiculo) || veiculo == null) // veiculo ja cadastrado ou nulo
			return false;
		
		return this.listaVeiculos.add(veiculo);
	}
	
	// TOTEST
	// remove (caso exista) o veiculo passado por parametro do cliente
	public boolean removerVeiculo(Veiculo veiculo) {
		if(veiculo == null) // veiculo nulo
			return false;
		return this.listaVeiculos.remove(veiculo);
	}
	
	// TOTEST
	public Veiculo getVeiculoByPlaca(String placa) {
		Veiculo ret = null;
		
		for(Veiculo veiculo: listaVeiculos)
			if (veiculo.getPlaca().equals(placa)) {
				ret = veiculo;	
				break;
			}
		
		return ret;
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
			ret += "-----------------\n";
			ret += "Lista de Veículos\n";
			ret += "-----------------";
			for(Veiculo veiculo: listaVeiculos)
				ret += "\n-\n" + veiculo.getPlaca();
			ret += "\n-";
		}
		
		return ret;
	}
}
