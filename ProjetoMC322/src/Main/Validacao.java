package Main;

import java.util.Scanner;

public class Validacao {

	//Faz a soma das multplicações dos 8 dígitos do cpf (a partir do indiceInicio) e determina o digitoVerificador 
	private static int calcularDigitoVerificadorCPF(int indiceInicio, String cpf) {
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
		if(Character.getNumericValue(numCpf.charAt(9)) != calcularDigitoVerificadorCPF(0, numCpf)|| 
			Character.getNumericValue(numCpf.charAt(10)) != calcularDigitoVerificadorCPF(1, numCpf))
			// pelo menos um dos digitos verificadores calculados nao é igual ao digito verificadore fornecido
			return false;
				
		return true;
	}
	
	// Determina o 1º ou 2º digitoVerificador 
	private static int calcularDigitoVerificadorCNPJ(int indiceFinal, String cnpj) {
		int soma = calcularSomaDigitos(indiceFinal, cnpj);
		
		int resto = soma % 11;
		
		// se o resto for 0 ou 1, o digitoVerificador é 0
		if(resto < 2)
			return 0;
		
		// se nao, o digitoVerificador é 11 - resto
		return 11 - resto;
	}
		
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
		
		return soma;
	}
		
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
		// nao ha digitos diferentes (todos iguais)
		if(!diferente)
			return false;

		// calculo dos digitos verificadores (os dois ultimos nas posicoes [12] e [13])
		if(Character.getNumericValue(numCnpj.charAt(12)) != calcularDigitoVerificadorCNPJ(11, numCnpj)|| 
			Character.getNumericValue(numCnpj.charAt(13)) != calcularDigitoVerificadorCNPJ(12, numCnpj))
			// pelo menos um dos digitos verificadores calculados nao é igual ao digito verificadore fornecido
			return false;
				
		return true;
	}
	
	/* ============================================================
	 *  FUNÇÕES PARA LIDAR COM A LEITURA DE INFORMAÇÕES DO CONSOLE
	 * ============================================================*/
	
	// programa fica em loop até que o usuário insira um CNPJ válido
	public static String getCnpjValido(Scanner leitor) {
		boolean valido = false;
		String cnpj;
		do { // continuamente é feito esse bloco de comandos até o usuário inserir um CNPJ válido
			System.out.print("CNPJ: ");
			cnpj = leitor.nextLine();
			valido = Validacao.validarCNPJ(cnpj);
			if(!valido) {
				System.out.println(" -------------------------------------------");
				System.out.println("| CNPJ inválido. Tente inserí-lo novamente. |");
				System.out.println(" -------------------------------------------\n");
			}
		} while(!valido);
		
		// retira caracteres não numéricos do CNPJ inserido
		return cnpj.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
	}
	
	// programa fica em loop até que o usuário insira um CPF válido
	public static String getCpfValido(Scanner leitor) {
		String cpf;
		boolean valido = false;
		do { // continuamente é feito esse bloco de comandos até o usuário inserir um CPF válido
			System.out.print("CPF: ");
			cpf = leitor.nextLine();
			valido = Validacao.validarCPF(cpf);
			if(!valido) {
				System.out.println(" ------------------------------------------");
				System.out.println("| CPF inválido. Tente inserí-lo novamente. |");
				System.out.println(" ------------------------------------------\n");
			}
		} while(!valido);
		
		// retira caracteres não numéricos do CPF inserido
		return cpf.replaceAll("\\.", "").replaceAll("-", "");
	}
	
	// programa fica em loop até que o usuário insira um tipo de cliente válido, isto é, [CPF] ou [CNPJ]
	public static String getTipoClienteValido(Scanner leitor) {
		String tipoCliente = "";
		do{ // continuamente é feito esse bloco de comandos até o usuário inserir um tipo de cliente válido
			System.out.println("\nO cliente é físico(CPF) ou jurídico(CNPJ)?");
			System.out.print("Digite [CPF] ou [CNPJ]: ");
			String tipoLeitor = leitor.nextLine().toUpperCase(); // coloca todo o texto digitado em caixa alta para facilitar inserção do usuário
			if(tipoLeitor.equals("CPF") || tipoLeitor.equals("CNPJ")) {
				tipoCliente = tipoLeitor;
				System.out.println(); // pula linha	
			}
			else {
				System.out.println(" --------------------------------------------------------------------");
				System.out.println("| Tipo de cliente inválido. Tente inserir [CPF] ou [CNPJ] novamente. |");
				System.out.println(" --------------------------------------------------------------------\n");
			}
		} while(tipoCliente.equals(""));
		
		return tipoCliente;
	}
}
