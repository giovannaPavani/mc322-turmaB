package Seguro;

import java.time.LocalDate;
import java.util.LinkedList;

import Cliente.ClientePF;
import Seguradora.Seguradora;
import Sinistro.Sinistro;
import Veiculo.Veiculo;

public class SeguroPF extends Seguro{
	Veiculo veiculo;
	ClientePF cliente;
	
	public SeguroPF(int id, LocalDate dataInicio, LocalDate dataFim, 
			Seguradora seguradora,LinkedList<Sinistro> listaSinistros, 
			LinkedList<Sinistro> listacondutores, double valorMensal,
			Veiculo veiculo, ClientePF cliente) {
		
		super(id, dataInicio, dataFim, seguradora, listaSinistros, listacondutores, valorMensal);
		
		this.veiculo = veiculo;
		this.cliente = cliente;
	}
	
	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public ClientePF getCliente() {
		return cliente;
	}

	public void setCliente(ClientePF cliente) {
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
