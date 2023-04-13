package classes;

public class Veiculo {

	// Propriedades
	private String placa;
	private String marca;
	private String modelo;
	private int anoFabricacao;
	
	// Construtor
	public Veiculo(String placa, String marca, String modelo, int anoFabricacao) {
		this.placa = placa;
		this.marca = marca;
		this.modelo = modelo;
		this.setAnoFabricacao(anoFabricacao);
	}
	
	// Getters e setters
	public String getPlaca() {
		return placa;
	}
	
	public String getMarca() {
		return marca;
	}
	
	public String getModelo() {
		return modelo;
	}
	
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getAnoFabricacao() {
		return anoFabricacao;
	}

	public void setAnoFabricacao(int anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}
	
	public String toString() {
		String ret = "";
		
		ret += "Placa: "+this.placa+"\n";
		ret += "Marca: "+this.marca+"\n";
		ret += "Modelo: "+this.modelo+"\n";
		ret += "Ano de Fabricação: "+this.anoFabricacao+"\n";
		
		return ret;
	}
}
