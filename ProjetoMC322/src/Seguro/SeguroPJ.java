package Seguro;

import java.time.LocalDate;
import java.time.Period;
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
					LinkedList<Sinistro> listacondutores,
					Frota frota, ClientePJ cliente) {
		
		super(id, dataInicio, dataFim, seguradora, listaSinistros, listacondutores);
		
		this.frota = frota;
		this.cliente = cliente;
		calcularValor();
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
		// calcular anosPosFundacao
		LocalDate dataAtual = LocalDate.now();
		Period periodo = Period.between(cliente.getDataFundacao(), dataAtual);
		int anosPosFundacao = periodo.getYears();
				
		int qtdVeiculos = cliente.getListaFrotas().size();
		int qtdSinistrosCliente = 2; // TODO aparentemente é na seguradora
		int qtdSinistrosCondutor = 3;
		
		double valor = CalcSeguro.VALOR_BASE.getFator() * (10.0 + cliente.getQtdeFuncionarios()/10.0) * 
					   (1.0 + 1.0/(qtdVeiculos + 2.0)) * (1.0 + 1.0/(anosPosFundacao + 2.0)) *
					   (2.0 + qtdSinistrosCliente/10.0) * (5.0 + qtdSinistrosCondutor/10.0);
		
		// TODO faz set ou deixa protected??
		this.setValorMensal(valor);	
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
