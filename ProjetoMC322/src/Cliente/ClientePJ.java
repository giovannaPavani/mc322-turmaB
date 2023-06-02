package Cliente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;

import Frota.Frota;
import Veiculo.Veiculo;

public class ClientePJ extends Cliente {
	
	//Propriedades
	private final String cnpj;
	private LocalDate dataFundacao;
	private LinkedList<Frota> listaFrotas;
	private int qtdeFuncionarios;

	//Construtor
	public ClientePJ (String nome, String endereco, String telefone, String email, 
					  LinkedList<Frota> listaFrotas, String cnpj, LocalDate dataFundacao, int qtdeFuncionarios) {
		// chama o construtor da superclasse Cliente
		super (nome, endereco, telefone, email);
		
		this.cnpj = cnpj;
		this.dataFundacao = dataFundacao;
		this.qtdeFuncionarios = qtdeFuncionarios;
		this.listaFrotas = listaFrotas;
	}

	//Getters e Setters
	public String getCnpj() {
		return cnpj;
	}
	
	public LocalDate getDataFundacao() {
		return dataFundacao;
	}
	
	public void setDataFundacao(LocalDate dataFundacao) {
		this.dataFundacao = dataFundacao;
	}
	
	public LinkedList<Frota> getListaFrotas() {
		return listaFrotas;
	}
	
	public void setListaFrotas(LinkedList<Frota> listaFrotas) {
		this.listaFrotas = listaFrotas;
	}
	
	public int getQtdeFuncionarios() {
		return qtdeFuncionarios;
	}

	public void setQtdeFuncionarios(int qtdeFuncionarios) {
		this.qtdeFuncionarios = qtdeFuncionarios;
	}
	
	@Override
	public String toString () {
		String ret = "";
		// formatador para converter o objeto LocalDate em String do formato "dd/MM/yyyy"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ret += "CNPJ: " + this.cnpj + "\n"; 
		ret += "Data de Fundação: " + dataFundacao.format(formatter) + "\n";
		ret += "Qtde de Funcionarios: " + this.qtdeFuncionarios + "\n";
		ret += super.toString();
		
		if(listaFrotas != null && !listaFrotas.isEmpty()) {
			ret += "-----------------\n";
			ret += "Lista de Veículos\n";
			ret += "-----------------";
			for(Frota frota: listaFrotas)
				ret += "\n-\n" + frota.toString(); // ou veiculo.getPlaca() p/ ficar menos poluido
			ret += "\n-";
		}
		
		return ret;
	}
	
	@Override
	public String toStringSimples() {
		String ret = "";
		// formatador para converter o objeto LocalDate em String do formato "dd/MM/yyyy"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ret += "CNPJ: " + this.cnpj + "\n";
		ret += "Data de fundação: " + this.dataFundacao.format(formatter)+ "\n";
		ret += super.toStringSimples();
		
		return ret;
	}
	
	private Frota getFrotaByCode(String code) {
		for(Frota frota: listaFrotas) {
			if(frota.getCode().equals(code))
				return frota;
		}
		
		return null;
	}
	
	public Veiculo getVeiculoByPlaca(String placa) {
		for(Frota frota: listaFrotas) {
			for(Veiculo veiculo: frota.getListaVeiculos()) {
				if(veiculo.getPlaca().equals(placa))
					return veiculo;
			}
		}
		
		return null;
	}
	
	// TOTEST
	public boolean removerVeiculo(String placa) {
		for(Frota frota: listaFrotas) {
			for(Veiculo veiculo: frota.getListaVeiculos()) {
				if(veiculo.getPlaca().equals(placa))
					return frota.removerVeiculo(veiculo);
			}
		}
		
		// veiculo nao cadastrado
		return false;
	}
	
	/* =================
	 *  FUNÇÕES PEDIDAS
	 * ================= */
	
	// TOTEST
	public boolean cadastrarFrota(Frota frota) {
		return this.listaFrotas.add(frota);
	}
	
	// TOTEST add, remover e remover toda a frota
	public boolean atualizarFrota(Frota frota, Veiculo veiculo) {
		if(frota == null || this.listaFrotas == null)
			return false;
		
		if(this.listaFrotas.isEmpty() || !this.listaFrotas.contains(frota))
			return false;
		
		if(veiculo == null)
			return this.listaFrotas.remove(frota); // remove a frota inteira caso nao haja um veiculo especifico
			
		if(!frota.removerVeiculo(veiculo)) // se nao remover, isto é, veiculo nao existente -> add
			return frota.cadastrarVeiculo(veiculo);
		
		return true;
	}
	
	// sobrecarga do metodo acima apenas com atributos chave e nao o objeto inteiro
	public boolean atualizarFrota(String code, String placa) {
		if(code == null || code.equals(""))
			return false;
		
		Frota frota = getFrotaByCode(code);
		
		if(this.listaFrotas.isEmpty() || !this.listaFrotas.contains(frota))
			return false;
		
		Veiculo veiculo =frota.getVeiculoByPlaca(placa);
		
		if(veiculo == null)
			return this.listaFrotas.remove(frota); // remove a frota inteira caso nao haja um veiculo especifico
			
		if(!frota.removerVeiculo(veiculo)) // se nao remover, isto é, veiculo nao existente -> add
			return frota.cadastrarVeiculo(veiculo);
		
		return true;
	}
	
	// TOTEST
	// mudei para  LinkedList<Veiculo> ao inves de boolean
	public LinkedList<Veiculo> getVeiculosPorFrota(String code) {
		Frota frota = getFrotaByCode(code);
		
		if(frota == null)
			return null;
		
		return frota.getListaVeiculos();
	}

	// retorna todos os veiculos do cliente
	@Override
	public LinkedList<Veiculo> getListaVeiculos() {
		LinkedList<Veiculo> veiculos = new LinkedList<Veiculo>();
		
		for(Frota frota: listaFrotas) 
			veiculos.addAll(frota.getListaVeiculos());
		
		return veiculos;
	}
	
}