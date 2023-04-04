package classes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
// 3
// faltam heranças

public class Cliente {

	// Propriedades
	// é mais certo protected?
	private String nome;
	private String endereco;
	private Date dataLicenca;
	private String educacao;
	private String genero;
	private String classeEconomica;
	private List<Veiculo> listaVeiculos;
	
	// Construtor
	public Cliente(String nome, String endereco, Date dataLicenca, String educacao, String genero, String classeEconomica, List<Veiculo> listaVeiculos) {
		this.setNome(nome);
		this.setNome(endereco);
		this.setDataLicenca(dataLicenca);
		this.setEducacao(educacao);
		this.setGenero(genero);
		this.setClasseEconomica(classeEconomica);
		this.listaVeiculos = listaVeiculos;
		//this.listaVeiculos = new ArrayList<Veiculo>();
	}
	
	// Gera String com todas as informações do cliente
	public String toString() {
		String ret = "";
		ret += "Nome: " + nome + "\n";
		ret += "Endereco: " + endereco + "\n";
		ret += "Data da Licença: " + dataLicenca + "\n";
		ret += "Educação: " + educacao + "\n";
		ret += "Gênero: " + genero + "\n";
		ret += "Classe Econômica: " + classeEconomica + "\n";

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

	
	public String getEducacao() {
		return educacao;
	}

	public void setEducacao(String educacao) {
		this.educacao = educacao;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getClasseEconomica() {
		return classeEconomica;
	}

	public void setClasseEconomica(String classeEconomica) {
		this.classeEconomica = classeEconomica;
	}

	public List<Veiculo> getListaVeiculos() {
		return listaVeiculos;
	}

	public void setListaVeiculos(List<Veiculo> listaVeiculos) {
		this.listaVeiculos = listaVeiculos;
	}
}
