package classes;

public class Cliente {

	// Propriedades
	private String nome;
	private String cpf;
	private String dataNascimento;
	private String idade;
	private String endereco;
	
	// Construtor
	public Cliente(String nome, String cpf, String dataNascimento, String idade, String endereco) {
		this.setNome(nome);
		this.setCpf(cpf);
		this.dataNascimento = dataNascimento;
		this.idade = idade;
		this.endereco = endereco;
	}
	
	// Gera String com todas as informações do cliente
	public String toString() {
		String ret = "";
		ret += "Nome: " + nome + "\n";
		ret += "CPF: " + cpf + "\n";
		ret += "Data de Nascimento: " + dataNascimento + "\n";
		ret += "Idade: " + idade + "\n";
		ret += "Endereco: " + endereco + "\n";
		return ret;
	}
	
	// Faz a soma das multplicações dos 8 primeiros dígitos do cpf (a partir do indiceInicio) e determina o digitoVerificador 
	private static int calcularDigitoVerificador(int indiceInicio, String cpf) {
		int soma = 0;
		
		// Os nove primeiros algarismos são ordenadamente multiplicados pela sequência 10, 9, 8, 7, 6, 5, 4, 3, 2 
		// OBS: +indiceInicio serve para adequar as contagens considerando o indiceInicio como origem
		for(int i=indiceInicio; i<9+indiceInicio; i++) {
			int digito = Character.getNumericValue(cpf.charAt(i));
			soma += digito * (10-i+indiceInicio);
		}
		
		int resto = soma % 11;
		int digitoVerificador = 0;
		
		// se o resto for 0 ou 1, o digitoVerificador é 0
		if(resto != 0 && resto != 1)
			digitoVerificador = 11 - resto;
		
		return digitoVerificador;
	}
	
	public static boolean validarCPF(String cpf) {
		// remove caracteres nao numericos ('.' e '-')
		String numCpf = cpf.replaceAll("\\.", "").replaceAll("-", "");
		
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
		// nao ha digitos diferentes
		if(!diferente)
			return false;

		// calculo dos digitos verificadores
		if(Character.getNumericValue(numCpf.charAt(9)) != calcularDigitoVerificador(0, numCpf)|| 
			Character.getNumericValue(numCpf.charAt(10)) != calcularDigitoVerificador(1, numCpf))
			// pelo menos um dos digitos verificadores calculados nao sao iguais aos digitos verificadores fornecidos
			return false;
				
		return true;
	}
	
	// Getters e Setters
	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
