package classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ClientePF extends Cliente {
	
	//Propriedades
	private final String cpf;
	private String genero;
	protected LocalDate dataLicenca;
	private String educacao;
	private LocalDate dataNascimento;
	private String classeEconomica;

	//Construtor
	public ClientePF (String nome, String endereco, LinkedList<Veiculo> listaVeiculos,
			          LocalDate dataLicenca, String cpf, LocalDate dataNascimento, 
					  String educacao , String genero , String classeEconomica) {
		// chama o construtor da superclasse Cliente
		super (nome , endereco, listaVeiculos);
		
		this.cpf = cpf;
		this.genero = genero;
		this.dataLicenca = dataLicenca;
		this.educacao = educacao;
		this.dataNascimento = dataNascimento;
		this.classeEconomica = classeEconomica;
	}

	//Getters e Setters
	public String getCpf() {
		return cpf;
	}
	
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getEducacao() {
		return educacao;
	}
	
	public void setEducacao(String educacao) {
		this.educacao = educacao;
	}
	
	public LocalDate getDataLicenca() {
		return dataLicenca;
	}
	
	public void setDataLicenca(LocalDate dataLicenca) {
		this.dataLicenca = dataLicenca;
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
	
	@Override
	public String toString () {
		String ret = "";
		// formatador para converter o objeto LocalDate em String do formato "dd/MM/yyyy"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ret += "CPF: " + this.cpf + "\n";
		ret += "Gênero: " + this.genero + "\n";
		ret += "Data da Licença: " + dataLicenca.format(formatter)+"\n";
		ret += "Educação: " + this.educacao + "\n";
		ret += "Data de Nascimento: " + this.dataNascimento.format(formatter) + "\n";
		ret += "Classe Econômica: " + this.classeEconomica + "\n";
		ret += super.toString();
		
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
	
	public static boolean validarCPF(String cpf) {
		
		// remove caracteres nao numericos ('.' e '-')
		String numCpf = cpf.replaceAll("\\D+","");
		
		// verifica se o cpf tem 11 digitos
		if(numCpf.length() != 11)
			return false;
		
		// verifica se todos os digitos sao iguais
		boolean diferente = false;
		for(int i=0; i < numCpf.length()-1; i++)
			// se achar pelo menos um digito diferente (valido) -> break
			if(numCpf.charAt(i) != numCpf.charAt(i+1)) {
				diferente = true;
				break;
			}
		// nao ha digitos diferentes (todos iguais)
		if(!diferente)
			return false;

		// calculo dos digitos verificadores (os dois ultimos nas posicoes [9] e [10])
		if(Character.getNumericValue(numCpf.charAt(9)) != calcularDigitoVerificador(0, numCpf)|| 
			Character.getNumericValue(numCpf.charAt(10)) != calcularDigitoVerificador(1, numCpf))
			// pelo menos um dos digitos verificadores calculados nao é igual ao digito verificadore fornecido
			return false;
				
		return true;
	}
	
	/* ====================
	 *  MÉTODOS AUXILIARES
	 * ==================== */
	
	//Faz a soma das multplicações dos 8 dígitos do cpf (a partir do indiceInicio) e determina o digitoVerificador 
	private static int calcularDigitoVerificador(int indiceInicio, String cpf) {
		int soma = 0;
		
		// Os nove primeiros algarismos são ordenadamente multiplicados pela sequência 10, 9, 8, 7, 6, 5, 4, 3, 2 
		// OBS: +indiceInicio serve para adequar as contagens considerando o indiceInicio como origem
		for(int i=indiceInicio; i<9+indiceInicio; i++) {
			int digito = Character.getNumericValue(cpf.charAt(i));
			soma += digito * (10-i+indiceInicio);
		}
		
		int resto = soma % 11;
		
		// se o resto for 0 ou 1, o digitoVerificador é 0
		if(resto < 2)
			return 0;
		
		// se nao, o digitoVerificador é 11 - resto
		return 11 - resto;
	}
	
}