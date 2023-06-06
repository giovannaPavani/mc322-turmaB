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

	private Frota frota;
	private ClientePJ cliente;
	
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

	// TOTEST
	@Override
	public void calcularValor() {
		// calcula anosPosFundacao
		LocalDate dataAtual = LocalDate.now();
		Period periodo = Period.between(cliente.getDataFundacao(), dataAtual);
		int anosPosFundacao = periodo.getYears();
				
		int qtdVeiculos = cliente.getListaFrotas().size();
		int qtdSinistrosCliente = this.seguradora.getSinistrosPorCliente(this.cliente.getCnpj()).size();
		int qtdSinistrosCondutor =  getQtdSinistrosCondutores(); // TODO DUVIDA! da seguradora ou do seguro?
		
		double valor = CalcSeguro.VALOR_BASE.getFator() * (10.0 + cliente.getQtdeFuncionarios()/10.0) * 
					   (1.0 + 1.0/(qtdVeiculos+2.0)) * (1.0 + 1.0/(anosPosFundacao+2.0)) *
					   (2.0 + qtdSinistrosCliente/10.0) * (5.0 + qtdSinistrosCondutor/10.0);
		
		this.valorMensal = valor;	
	}

	// TOTEST
	// remove sinistros envolvendo o veiculo dos condutores do seguro, remove veiculo da frota e atualiza valor
	public boolean removerVeiculo(String placa) {
		if(placa == null || placa.equals(""))
			return false;
		
		// remove sinistros dos condutores
		for(Sinistro sinistro: listaSinistros) {
			for(int i = 0; i < listaCondutores.size(); i++) {
				Condutor condutor = listaCondutores.get(i);
				if(sinistro.getCondutor().getCpf().equals(condutor.getCpf())) {
					if(!condutor.removerSinistro(sinistro))
						return false;
					else 
						listaCondutores.set(i, condutor); // atualiza listaCondutores
				}				
			}
		}
		
		if(frota.removerVeiculo(placa)) { // remove veiculo da frota
			calcularValor(); // atualiza valor do seguro
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		String ret = super.toString();
		
		ret += "--------------------\n";
		ret += "  Dados do Cliente\n";
		ret += "--------------------\n";
		ret += this.cliente.toStringSimples() + "\n";
		ret += "--------------------\n";
		ret += "   Dados da Frota\n";
		ret += "--------------------\n";
		ret += this.frota.toStringSimples() + "\n";

		return ret;
	}

	@Override
	public String toStringSimples() {
		String ret = super.toStringSimples();
		
		ret += "--------------------\n";
		ret += "  Dados do Cliente\n";
		ret += "--------------------\n";
		ret += this.cliente.toStringSimples() + "\n";
		ret += "--------------------\n";
		ret += "   Dados da Frota\n";
		ret += "--------------------\n";
		ret += this.frota.toStringSimples() + "\n";

		return ret;
	}
}
