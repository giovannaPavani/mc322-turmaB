package classes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class Cliente {

	// Propriedades
	// é mais certo protected?
	protected String nome;
	protected String endereco;
	protected LocalDate dataLicenca;
	protected LinkedList<Veiculo> listaVeiculos;
	
	// Construtor
	public Cliente(String nome, String endereco, LocalDate dataLicenca, LinkedList<Veiculo> listaVeiculos) {
		this.nome = nome;
		this.endereco = endereco;
		this.dataLicenca = dataLicenca;
		this.listaVeiculos = listaVeiculos;
		//this.listaVeiculos = new ArrayList<Veiculo>();
	}
	
	// Gera String com todas as informações do cliente
	public String toString() {
		String ret = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ret += "Nome: " + nome + "\n";
		ret += "Endereco: " + endereco + "\n";
		ret += "Data da Licença: " + dataLicenca.format(formatter);
		if(listaVeiculos != null && !listaVeiculos.isEmpty()) {
			ret += "\nLista de Veículos:";
			for(Veiculo veiculo: listaVeiculos)
				ret += "\n-\n"+ veiculo.toString(); // ou veiculo.getPlaca() p/ ficar menos poluido
		}
		
		return ret;
	}
	
	public String toStringSimples() {
		String ret = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ret += "Nome: " + nome + "\n";
		ret += "Endereco: " + endereco + "\n";
		ret += "Data da Licença: " + dataLicenca.format(formatter) + "\n";

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
	
	public LocalDate getDataLicenca() {
		return dataLicenca;
	}

	public void setDataLicenca(LocalDate dataLicenca) {
		this.dataLicenca = dataLicenca;
	}

	public LinkedList<Veiculo> getListaVeiculos() {
		return listaVeiculos;
	}

	public void setListaVeiculos(LinkedList<Veiculo> listaVeiculos) {
		this.listaVeiculos = listaVeiculos;
	}
	
	public boolean adicionarVeiculo(Veiculo veiculo) {
		return this.listaVeiculos.add(veiculo);
	}
	
	public boolean removerVeiculo(Veiculo veiculo) {
		return this.listaVeiculos.remove(veiculo);
	}
	
	public Veiculo getVeiculoByPlaca(String placa) {
		Veiculo ret = null;
		for(Veiculo veiculo: listaVeiculos)
			if (veiculo.getPlaca().equals(placa)) {
				ret = veiculo;	
				break;
			}
		return ret;
	}
}