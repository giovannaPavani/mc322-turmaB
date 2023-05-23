package Cliente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import Veiculo.Veiculo;

public class ClientePJ extends Cliente {
	
	//Propriedades
	private final String cnpj;
	private LocalDate dataFundacao;
	private LinkedList<Veiculo> listaFrota;


	//Construtor
	public ClientePJ (String nome, String endereco, String telefone, String email, 
					  LinkedList<Veiculo> listaFrota, String cnpj, LocalDate dataFundacao) {
		// chama o construtor da superclasse Cliente
		super (nome, endereco, telefone, email);
		
		this.cnpj = cnpj;
		this.dataFundacao = dataFundacao;
		this.listaFrota = listaFrota;
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
	
	public LinkedList<Veiculo> getListaFrota() {
		return listaFrota;
	}
	
	public void setListaFrota(LinkedList<Veiculo> listaFrota) {
		this.listaFrota = listaFrota;
	}
	
	@Override
	public String toString () {
		String ret = "";
		// formatador para converter o objeto LocalDate em String do formato "dd/MM/yyyy"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ret += "CNPJ: " + this.cnpj + "\n";
		ret += "Data de Fundação: " + dataFundacao.format(formatter) + "\n";
		ret += super.toString();
		
		if(listaFrota != null && !listaFrota.isEmpty()) {
			ret += "-----------------\n";
			ret += "Lista de Veículos\n";
			ret += "-----------------";
			for(Veiculo veiculo: listaFrota)
				ret += "\n-\n" + veiculo.toString(); // ou veiculo.getPlaca() p/ ficar menos poluido
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
	
	/* =================
	 *  FUNÇÕES PEDIDAS
	 * ================= */
	
	// TODO
	public boolean cadastrarFrota() {
		return true;
	}
	
	// TODO
	public boolean atualizarFrota() {
		// add, remover e remover toda a frota
		return true;
	}
	
	// TODO
	public boolean getVeiculosPorFrota() {
		return true;
	}
	
}