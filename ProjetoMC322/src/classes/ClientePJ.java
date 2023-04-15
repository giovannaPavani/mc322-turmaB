package classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ClientePJ extends Cliente {
	
	//Propriedades
	private final String cnpj;
	private LocalDate dataFundacao;

	//Construtor
	public ClientePJ (String nome , String endereco, LinkedList<Veiculo> listaVeiculos, 
			          String cnpj, LocalDate dataFundacao) {
		// chama o construtor da superclasse
		super (nome , endereco , listaVeiculos);
		this.cnpj = cnpj;
		this.dataFundacao = dataFundacao ;
	}

	//Getters e Setters
	public String getCnpj() {
		return cnpj;
	}
	
	public LocalDate getDataFundacao() {
		return dataFundacao;
	}
	
	public void setDataFundacao(LocalDate dataFundacao) {
		this.dataFundacao = dataFundacao;
	}
	
	@Override
	public String toString () {
		String ret = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ret += "CNPJ: " + this.cnpj + "\n";
		ret += "Data de Fundação: " + dataFundacao.format(formatter) + "\n";
		ret += super.toString();
		return ret;
	}
	
	@Override
	public String toStringSimples() {
		String ret = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ret += "CNPJ: " + this.cnpj + "\n";
		ret += "Data de fundação: " + this.dataFundacao.format(formatter)+ "\n";
		ret += super.toStringSimples();
		
		return ret;
	}
	
	
	/* CNJP:  11.222.333/0001-XX
	 * -------------------------
	 * 0 a 7: nº incricao
	 * 8 a 11: nº filiais
	 * 12 e 13: verificadores
	 */
	public static boolean validarCNPJ(String cnpj) {
		// remove caracteres nao numericos ('.', '-' e '/')
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
	
	/* ====================
	 *  MÉTODOS AUXILIARES
	 * ==================== */
	
	// Faz a soma das multplicações dos 12 dígitos do cnpj pelos respectivos vetores de multiplicadores
	private static int calcularSomaDigitos(int indiceFinal, String cnpj) {
		int soma =0;
		if(indiceFinal == 11) {
			// A soma será o produto escalar dos 11 primeiros algarismos do cnpj
			// e o vetor (5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
			for(int i=0; i<=3; i++) {
				int digito = Character.getNumericValue(cnpj.charAt(i));
				soma += digito * (5-i);
			}
			for(int i=4; i<=indiceFinal; i++) {
				int digito = Character.getNumericValue(cnpj.charAt(i));
				soma += digito * (13-i);
			}
		}
		else if(indiceFinal == 12) {
			// A soma será o produto escalar dos 12 primeiros algarismos do cnpj
			// e o vetor (6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
			for(int i=0; i<=4; i++) {
				int digito = Character.getNumericValue(cnpj.charAt(i));
				soma += digito * (6-i);
			}
			for(int i=5; i<=indiceFinal; i++) {
				int digito = Character.getNumericValue(cnpj.charAt(i));
				soma += digito * (14-i);
			}
		}
		//System.out.println("soma: "+soma);
		return soma;
	}
	
	// Determina o 1º ou 2º digitoVerificador 
	private static int calcularDigitoVerificador(int indiceFinal, String cnpj) {
		int soma = calcularSomaDigitos(indiceFinal, cnpj);
		
		int resto = soma % 11;
		int digitoVerificador = 0;
		
		// se o resto for 0 ou 1, o digitoVerificador é 0
		if(resto < 2)
			digitoVerificador = 0;
		else
			digitoVerificador = 11 - resto;
		
		return digitoVerificador;
	}
}