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
					  String cnpj, LocalDate dataFundacao,  LinkedList<Frota> listaFrotas, int qtdeFuncionarios) {
		// chama o construtor da superclasse Cliente
		super (nome, endereco, telefone, email);
		
		this.cnpj = cnpj;
		this.dataFundacao = dataFundacao;
		this.listaFrotas = listaFrotas;
		this.qtdeFuncionarios = qtdeFuncionarios;
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
		ret += super.toString();
		ret += "Qtde de Funcionarios: " + this.qtdeFuncionarios;
		
		if(listaFrotas != null && !listaFrotas.isEmpty()) {
			ret += "\n-----------------\n";
			ret += "Lista de Frotas\n";
			ret += "-----------------";
			for(Frota frota: listaFrotas)
				ret += "\n\n" + frota.toString();
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
	
	/* =================
	 *  FUNÇÕES PEDIDAS
	 * ================= */
	
	// TOTEST
	public boolean cadastrarFrota(Frota frota) {
		if(frota == null || listaFrotas.contains(frota))
			return false;
		
		return this.listaFrotas.add(frota);
	}
	
	// TOTEST 
	// adiciona ou remove veiculo da frota (ambos fornecidos), ou remove toda a frota
	public boolean atualizarFrota(String code, Veiculo veiculo) {
		if(code == null || code.equals("") || listaFrotas == null || listaFrotas.isEmpty())
			return false;
		
		Frota frota = getFrotaByCode(code);
		
		if(frota == null) // !this.listaFrotas.contains(frota)
			return false;
		
		if(veiculo == null) // remove a frota inteira caso nao haja um veiculo especifico
			return this.listaFrotas.remove(frota); 
			
		// acha a frota do id fornecido
		for(Frota f: listaFrotas) {
			if(f.getCode().equals(frota.getCode())) {
				Veiculo v = f.getVeiculoByPlaca(veiculo.getPlaca());
				if(v == null) // veiculo nao cadastrado -> add
					return f.addVeiculo(veiculo);
				// veiculo cadastrado -> remove
				return f.addVeiculo(veiculo);				
			}
		}
		
		return false;
	}
	
	// TOTEST 
	// adiciona ou remove veiculo da frota (ambos fornecidos), ou remove toda a frota
	// (sobrecarga do metodo acima apenas com atributos chave e nao o objeto inteiro)
	public boolean atualizarFrota(String code, String placa) {
		if(code == null || code.equals("") || listaFrotas == null||listaFrotas.isEmpty())
			return false;
		
		if(placa == null || placa.equals("")) {
			Frota frota = getFrotaByCode(code);
			return this.listaFrotas.remove(frota); // remove a frota inteira caso nao haja um veiculo especifico
		}
		
		// acha a frota do id fornecido
		for(Frota f: listaFrotas) {
			if(f.getCode().equals(code)) {
				Veiculo veiculo = f.getVeiculoByPlaca(placa);
				if(veiculo == null) // veiculo nao cadastrado -> add
					return f.addVeiculo(veiculo);
				// veiculo cadastrado -> remove
				return f.addVeiculo(veiculo);				
			}
		}
		
		return false;
	}
	
	// TOTEST
	// mudei para  LinkedList<Veiculo> ao inves de boolean
	public LinkedList<Veiculo> getVeiculosPorFrota(String code) {
		if(code == null || code.equals(""))
			return null;
		
		Frota frota = getFrotaByCode(code);
		
		if(frota == null)
			return null;
		
		return frota.getListaVeiculos();
	}

	/* ====================
	 *  MÉTODOS AUXILIARES
	 * ==================== */
	
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
					return frota.removeVeiculo(veiculo);
			}
		}
		
		return false; // veiculo nao cadastrado
	}
	
	// retorna todos os veiculos do cliente
	@Override
	public LinkedList<Veiculo> getListaVeiculos() {
		LinkedList<Veiculo> veiculos = new LinkedList<Veiculo>();
		
		for(Frota frota: listaFrotas) 
			veiculos.addAll(frota.getListaVeiculos());
		
		return veiculos;
	}
	
	public Frota getFrotaByCode(String code) {
		for(Frota frota: listaFrotas) {
			if(frota.getCode().equals(code))
				return frota;
		}
		
		return null;
	}
	
}