package Seguro;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedList;
import Cliente.ClientePJ;
import Condutor.Condutor;
import Frota.Frota;
import Seguradora.Seguradora;
import Sinistro.Sinistro;

public class SeguroPJ extends Seguro{

	Frota frota;
	ClientePJ cliente;
	
	public SeguroPJ(LocalDate dataInicio, LocalDate dataFim, 
					Seguradora seguradora,LinkedList<Sinistro> listaSinistros, 
					LinkedList<Condutor> listacondutores,
					Frota frota, ClientePJ cliente) {
		
		super(dataInicio, dataFim, seguradora, listaSinistros, listacondutores);
		
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
		String ret = super.toString();
		
		ret += "-------------------\n";
		ret += "Dados do Cliente\n";
		ret += "-------------------\n";
		ret += this.cliente.toStringSimples() + "\n";
		ret += "-------------------\n";
		ret += "Dados da Frota\n";
		ret += "-------------------\n";
		ret += this.frota.toStringSimples() + "\n";

		return ret;
	}

}
