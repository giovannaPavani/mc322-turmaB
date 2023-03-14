package classes;
import java.util.Random;

public class Sinistro {
	
	// Propriedades
	private int id;
	private String data;
	private String endereco;
	
	// Construtor
	public Sinistro(String data, String endereco) {
		this.id = gerarId();
		this.data = data;
		this.endereco = endereco;
	}
	
	// Função geradora de ids aleatórios
	private int gerarId() {
		Random gerador = new Random();
		int ret = gerador.nextInt();
		// caso seja negativo, arruma o sinal
		if(ret < 0)
			ret = -ret;
        return ret;
	}
	
	// Getters e setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
}
