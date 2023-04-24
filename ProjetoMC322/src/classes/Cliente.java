package classes;
import java.util.LinkedList;

public class Cliente {

	// Propriedades
	protected String nome;
	protected String endereco;
	protected LinkedList<Veiculo> listaVeiculos;
	
	// Construtor
	public Cliente(String nome, String endereco, LinkedList<Veiculo> listaVeiculos) {
		this.nome = nome;
		this.endereco = endereco;
		this.listaVeiculos = listaVeiculos;
		//this.listaVeiculos = new ArrayList<Veiculo>();
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
	
	public LinkedList<Veiculo> getListaVeiculos() {
		return listaVeiculos;
	}

	public void setListaVeiculos(LinkedList<Veiculo> listaVeiculos) {
		this.listaVeiculos = listaVeiculos;
	}
	
	// Gera String com todas as informações do cliente
	public String toString() {
		String ret = "";
		
		ret += "Nome: " + nome + "\n";
		ret += "Endereco: " + endereco + "\n";
		if(listaVeiculos != null && !listaVeiculos.isEmpty()) {
			ret += "-----------------\n";
			ret += "Lista de Veículos\n";
			ret += "-----------------";
			for(Veiculo veiculo: listaVeiculos)
				ret += "\n-\n" + veiculo.toString(); // ou veiculo.getPlaca() p/ ficar menos poluido
			ret += "\n-";
		}
		return ret;
	}
	
	/* ====================
	 *  MÉTODOS AUXILIARES
	 * ==================== */
	
	// cadastra (se já nao o estiver) o veiculo passado por parametro no cliente
	public boolean adicionarVeiculo(Veiculo veiculo) {
		if(this.listaVeiculos.contains(veiculo) || veiculo == null) // veiculo ja cadastrado ou nulo
			return false;
		
		return this.listaVeiculos.add(veiculo);
	}
	
	// remove (caso exista) o veiculo passado por parametro do cliente
	public boolean removerVeiculo(Veiculo veiculo) {
		if(veiculo == null) // veiculo nulo
			return false;
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
	
	// Gera String mais simples com algumas informações do cliente
	public String toStringSimples() {
		String ret = "";
		
		ret += "Nome: " + nome + "\n";
		ret += "Endereco: " + endereco + "\n";

		return ret;
	}
}