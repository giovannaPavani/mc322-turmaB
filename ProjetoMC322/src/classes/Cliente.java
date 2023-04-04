package classes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cliente {

	// Propriedades
	// é mais certo protected?
	protected String nome;
	protected String endereco;
	protected Date dataLicenca;
	protected List<Veiculo> listaVeiculos;
	
	// Construtor
	public Cliente(String nome, String endereco, Date dataLicenca, List<Veiculo> listaVeiculos) {
		this.nome = nome;
		this.endereco = endereco;
		this.dataLicenca = dataLicenca;
		this.listaVeiculos = listaVeiculos;
		//this.listaVeiculos = new ArrayList<Veiculo>();
	}
	
	// Gera String com todas as informações do cliente
	public String toString() {
		String ret = "";
		ret += "Nome: " + nome + "\n";
		ret += "Endereco: " + endereco + "\n";
		ret += "Data da Licença: " + dataLicenca + "\n";

		return ret;
	}
	
	
	// Getters e Setters
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public Date getDataLicenca() {
		return dataLicenca;
	}

	public void setDataLicenca(Date dataLicenca) {
		this.dataLicenca = dataLicenca;
	}


	public List<Veiculo> getListaVeiculos() {
		return listaVeiculos;
	}

	public void setListaVeiculos(List<Veiculo> listaVeiculos) {
		this.listaVeiculos = listaVeiculos;
	}
}