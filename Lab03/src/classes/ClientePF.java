package classes;

import java.util.Date;
import java.util.List;
//3
public class ClientePF extends Cliente {
	
	//Propriedades
	private final String cpf ;
	private Date dataNascimento;

	//Construtor
	public ClientePF (String nome , String endereco , Date dataLicenca ,
				  String educacao , String genero , String classeEconomica ,
                  List < Veiculo > listaVeiculos , String cpf , Date dataNascimento ) {
		// chama o construtor da superclasse
		super (nome , endereco , dataLicenca , educacao , genero , classeEconomica , listaVeiculos);
		this.cpf = cpf ;
		this.dataNascimento = dataNascimento ;
	}

	//Getters e Setters
	public Date getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	@Override
	public String toString () {
		String ret = "";
		ret += "CPF: " + this.cpf + "\n";
		ret += "Nome: " + this.getNome() + "\n";
		ret += "Data de Nascimento: " + dataNascimento + "\n";
		ret += "Endereco: " + this.getEndereco() + "\n";
		ret += "Data da Licença: " + this.getDataLicenca() + "\n";
		ret += "Educação: " + this.getEducacao() + "\n";
		ret += "Gênero: " + this.getGenero() + "\n";
		ret += "Classe Econômica: " + this.getClasseEconomica() + "\n";

		return ret;
	}

	//Faz a soma das multplicações dos 8 primeiros dígitos do cpf (a partir do indiceInicio) e determina o digitoVerificador 
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
}