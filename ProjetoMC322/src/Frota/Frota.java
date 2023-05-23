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
	
	// TODO
	// cadastra (se já nao o estiver) o veiculo passado por parametro no cliente
	public boolean cadastrarVeiculo(Veiculo veiculo) {
		if(this.listaVeiculos.contains(veiculo) || veiculo == null) // veiculo ja cadastrado ou nulo
			return false;
		
		return this.listaVeiculos.add(veiculo);
	}
	
	// TODO
	// remove (caso exista) o veiculo passado por parametro do cliente
	public boolean removerVeiculo(Veiculo veiculo) {
		if(veiculo == null) // veiculo nulo
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
	
	// TODO
	public String toString() {
		String ret = "";
		return ret;
	}
		
}
