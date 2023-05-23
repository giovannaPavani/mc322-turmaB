package Seguro;

import java.time.LocalDate;
import java.util.LinkedList;
import Cliente.ClientePJ;
import Frota.Frota;
import Seguradora.Seguradora;
import Sinistro.Sinistro;

public class SeguroPJ extends Seguro{

	Frota frota;
	ClientePJ cliente;
	
	public SeguroPJ(int id, LocalDate dataInicio, LocalDate dataFim, 
			Seguradora seguradora,LinkedList<Sinistro> listaSinistros, 
			LinkedList<Sinistro> listacondutores, double valorMensal,
			Frota frota, ClientePJ cliente) {
		
		super(id, dataInicio, dataFim, seguradora, listaSinistros, listacondutores, valorMensal);
		
		this.frota = frota;
		this.cliente = cliente;
	}
	
	public Frota getFrota() {
		return frota;
	}

	public void setFrota(Frota frota) {
		this.frota = frota;
	}

	public ClientePJ getCliente() {
		return cliente;
	}

	public void setCliente(ClientePJ cliente) {
		this.cliente = cliente;
	}

	@Override
	public boolean desautorizarCondutor() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean autorizarCondutor() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void calcularValor() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gerarSinistro() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
