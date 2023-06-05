package Seguro;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedList;

import Cliente.ClientePF;
import Condutor.Condutor;
import Seguradora.Seguradora;
import Sinistro.Sinistro;
import Veiculo.Veiculo;

public class SeguroPF extends Seguro {
	
	private Veiculo veiculo;
	private ClientePF cliente;
	
	public SeguroPF(LocalDate dataInicio, LocalDate dataFim, 
					Seguradora seguradora,LinkedList<Sinistro> listaSinistros, 
					LinkedList<Condutor> listacondutores, Veiculo veiculo, ClientePF cliente) {
		
		super(dataInicio, dataFim, seguradora, listaSinistros, listacondutores);
		
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

	// TOTEST
	@Override
	public void calcularValor() {
		// calcular idade
		LocalDate dataAtual = LocalDate.now();
		Period periodo = Period.between(cliente.getDataNascimento(), dataAtual);
		int idade = periodo.getYears();
		
		// pega valor do fator idade de acordo com a idade do cliente
		CalcSeguro FATOR_IDADE;
		if(idade < 30)
			FATOR_IDADE = CalcSeguro.FATOR_18_30;
		else if(idade <= 60)
			FATOR_IDADE = CalcSeguro.FATOR_30_60;
		else 
			FATOR_IDADE = CalcSeguro.FATOR_60_90;

		int qtdVeiculos = cliente.getListaVeiculos().size();
		int qtdSinistrosCliente = seguradora.getSinistrosPorCliente(cliente.getCpf()).size();
		int qtdSinistrosCondutor = getQtdSinistrosCondutores(); // TODO DUVIDA! da seguradora ou do seguro?
		
		double valor = CalcSeguro.VALOR_BASE.getFator() * FATOR_IDADE.getFator() * (1.0 + 1.0/(qtdVeiculos+2.0)) *
					   (2.0 + qtdSinistrosCliente/10.0) * (5.0 + qtdSinistrosCondutor/10.0); 

		this.valorMensal = valor;	
	}
	
	@Override
	public String toString() {
		String ret = super.toString();
		
		ret += "-------------------\n";
		ret += "Dados do Cliente\n";
		ret += "-------------------\n";
		ret += this.cliente.toStringSimples() + "\n";
		ret += "-------------------\n";
		ret += "Dados do Veiculo\n";
		ret += "-------------------\n";
		ret += this.veiculo.toString() + "\n";
		
		return ret;
	}
	
	@Override
	public String toStringSimples() {
		String ret = super.toStringSimples();
		
		ret += "-------------------\n";
		ret += "Dados do Cliente\n";
		ret += "-------------------\n";
		ret += this.cliente.toStringSimples() + "\n";
		ret += "-------------------\n";
		ret += "Dados do Veiculo\n";
		ret += "-------------------\n";
		ret += this.veiculo.toString() + "\n";
		
		return ret;
	}

}
