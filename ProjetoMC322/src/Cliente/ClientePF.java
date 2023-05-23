package Cliente;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import Veiculo.Veiculo;

public class ClientePF extends Cliente {
	
	//Propriedades
	private final String cpf;
	private String genero;
	private String educacao;
	private LocalDate dataNascimento;
	private LinkedList<Veiculo> listaVeiculos;

	//Construtor
	public ClientePF (String nome, String endereco, String telefone, String email,
					  String cpf, String genero, String educacao,
					  LocalDate dataNascimento, LinkedList<Veiculo> listaVeiculos) {
		// chama o construtor da superclasse Cliente
		super (nome, endereco, telefone, email);
		
		this.cpf = cpf;
		this.genero = genero;
		this.educacao = educacao;
		this.dataNascimento = dataNascimento;
		this.listaVeiculos = listaVeiculos;
	}

	//Getters e Setters
	public String getCpf() {
		return cpf;
	}
	
	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	public String getEducacao() {
		return educacao;
	}
	
	public void setEducacao(String educacao) {
		this.educacao = educacao;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public LinkedList<Veiculo> getListaVeiculos() {
		return listaVeiculos;
	}
	
	public void setListaVeiculos(LinkedList<Veiculo> listaVeiculos) {
		this.listaVeiculos = listaVeiculos;
	}

	
	@Override
	public String toString () {
		String ret = "";
		// formatador para converter o objeto LocalDate em String do formato "dd/MM/yyyy"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ret += "CPF: " + this.cpf + "\n";
		ret += "Gênero: " + this.genero + "\n";
		ret += "Educação: " + this.educacao + "\n";
		ret += "Data de Nascimento: " + this.dataNascimento.format(formatter) + "\n";
		ret += super.toString();
		
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
	
	@Override
	public String toStringSimples() {
		String ret = "";
		// formatador para converter o objeto LocalDate em String do formato "dd/MM/yyyy"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ret += "CPF: " + this.cpf + "\n";
		ret += "Data de Nascimento: " + this.dataNascimento.format(formatter) + "\n";
		ret += super.toStringSimples();
		
		return ret;
	}

	/* =================
	 *  FUNÇÕES PEDIDAS
	 * ================= */
	
	// TODO +/-
	// cadastra (se já nao o estiver) o veiculo passado por parametro no cliente
	public boolean cadastrarVeiculo(Veiculo veiculo) {
		if(this.listaVeiculos.contains(veiculo) || veiculo == null) // veiculo ja cadastrado ou nulo
			return false;
		
		return this.listaVeiculos.add(veiculo);
	}
	
	// TODO +/-
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
	
}