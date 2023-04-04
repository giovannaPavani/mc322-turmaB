package classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientePJ extends Cliente {
	
	//Propriedades
	private final String cnpj;
	private Date dataFundacao;

	//Construtor
	public ClientePJ (String nome , String endereco , Date dataFundacao ,
				  String educacao , String genero , String classeEconomica ,
                  List < Veiculo > listaVeiculos , String cnpj , Date dataNascimento ) {
		// chama o construtor da superclasse
		super (nome , endereco , dataFundacao , educacao , genero , classeEconomica , listaVeiculos);
		this.cnpj = cnpj;
		this.dataFundacao = dataFundacao ;
	}

	//Getters e Setters
	public String getCnpj() {
		return cnpj;
	}
	
	public Date getDataFundacao() {
		return dataFundacao;
	}
	
	public void setDataNascimento(Date dataFundacao) {
		this.dataFundacao = dataFundacao;
	}
	
	@Override
	public String toString () {
		String ret = "";
		ret += "CNPJ: " + this.cnpj + "\n";
		ret += "Nome: " + this.nome + "\n";
		ret += "Data de Fundação: " + dataFundacao + "\n";
		ret += "Endereço: " + endereco + "\n";
		ret += "Data da Licença: " + this.dataLicenca + "\n";
		ret += "Educação: " + this.educacao + "\n";
		ret += "Gênero: " + this.genero + "\n";
		ret += "Classe Econômica: " + this.classeEconomica + "\n";

		return ret;
	}
	
	// Faz a soma das multplicações dos 14 primeiros dígitos do cnpj pelos respectivos vetores de multiplicadores
	private static int calcularSomaDigitos(int indiceFinal, String cnpj) {
		int soma =0;
		if(indiceFinal == 11) {
			// A soma será o produto escalar dos 11 primeiros algarismos do cnpj
			// e o vetor (5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
			for(int i=0; i<4; i++) {
				int digito = Character.getNumericValue(cnpj.charAt(i));
				soma += digito * (5-i);
			}
			for(int i=4; i<indiceFinal; i++) {
				int digito = Character.getNumericValue(cnpj.charAt(i));
				soma += digito * (13-i);
			}
		}
		else if(indiceFinal == 12) {
			// A soma será o produto escalar dos 12 primeiros algarismos do cnpj
			// e o vetor (6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
			for(int i=0; i<5; i++) {
				int digito = Character.getNumericValue(cnpj.charAt(i));
				soma += digito * (6-i);
			}
			for(int i=4; i<indiceFinal; i++) {
				int digito = Character.getNumericValue(cnpj.charAt(i));
				soma += digito * (14-i);
			}
		}
		return soma;
	}
	
	// Determina o 1º ou 2º digitoVerificador 
	private static int calcularDigitoVerificador(int indiceFinal, String cnpj) {
		int soma = calcularSomaDigitos(indiceFinal, cnpj);
		
		int resto = soma % 11;
		int digitoVerificador = 0;
		
		// se o resto for 0 ou 1, o digitoVerificador é 0
		if(resto >= 2)
			digitoVerificador = 11 - resto;
		
		return digitoVerificador;
	}
	
	
	/* CNJP:  11.222.333/0001-XX
	 * 0 a 7: nº incricao
	 * 8 a 11: nº filiais
	 * 12 e 13: verificadores
	 */
	public static boolean validarCNPJ(String cnpj) {
		// remove caracteres nao numericos ('.' e '-')
		String numCnpj = cnpj.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		
		// verifica se o cnpj tem 14 digitos
		if(numCnpj.length() != 14)
			return false;
		
		// verifica se todos os digitos sao iguais
		boolean diferente = false;
		for(int i=0; i < numCnpj.length()-1; i++)
			// se achar pelo menos um digito diferente (valido) -> break
			if(numCnpj.charAt(i) != numCnpj.charAt(i+1)) {
				diferente = true;
				break;
			}
		// nao ha digitos diferentes
		if(!diferente)
			return false;

		// calculo dos digitos verificadores
		if(Character.getNumericValue(numCnpj.charAt(12)) != calcularDigitoVerificador(11, numCnpj)|| 
			Character.getNumericValue(numCnpj.charAt(13)) != calcularDigitoVerificador(12, numCnpj))
			// pelo menos um dos digitos verificadores calculados nao sao iguais aos digitos verificadores fornecidos
			return false;
				
		return true;
	}
}