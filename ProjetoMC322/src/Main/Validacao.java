package Main;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	
	public static boolean validaCPF(String cpf) {
		
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
		
	public static boolean validaCNPJ(String cnpj) {
		
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
			valido = Validacao.validaCNPJ(cnpj);
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
			valido = Validacao.validaCPF(cpf);
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

	public static String getKeyClienteValida(Scanner leitor, String tipoCliente) {
		String keyCliente = "";
		if(tipoCliente.equals("CNPJ"))
			keyCliente = Validacao.getCnpjValido(leitor); 
		else if(tipoCliente.equals("CPF"))
			keyCliente = Validacao.getCpfValido(leitor);
		return keyCliente;
	}
	
	public static String validaNome(Scanner leitor) {
		String nome = "";
		boolean valido = true;
		do {
			System.out.print("Nome: ");
			nome = leitor.nextLine();
			
			int tam = nome.length();
			for (int i=0; i<tam; i++) {
				char c = nome.charAt(i);
				if (!(((c >= 'A') && (c <= 'Z')) ||
				    ((c >= 'a') && (c <= 'z')))) {
					System.out.println(" -----------------------------------------------------------------------------------------");
					System.out.println("| Nome inválido. Tente inserir novamente sem acentos e 'ç', apenas letras são permitidas. |");
					System.out.println(" -----------------------------------------------------------------------------------------\n");
					valido = false;
					break;
				}
			}
			
		} while(!valido);
			
		return nome;
	}
	
	// programa fica em loop ate que o usuário insira uma data no formato correto
	public static LocalDate getDataValida(Scanner leitor, String tipoData) {
		// formatador de String do formato "dd/MM/yyyy" para objeto LocalDate
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate data= null;
		
		do { // continuamente é feito esse bloco de comandos ate o usuario inserir uma data valida
			System.out.print(tipoData+": ");
			String dataLeitor = leitor.nextLine();
			try { // tenta converter String em LocalDate
				data = LocalDate.parse(dataLeitor, formatter);
			} catch(Exception e) {
				System.out.println(" --------------------------------------------------------------------------------");
				System.out.println("| "+tipoData+" inválida! Tente inserí-la novamente, no formato [dd/mm/yyyy]. |");
				System.out.println(" --------------------------------------------------------------------------------\n");
			}
		} while (data == null);
		
		return data;
	}
	
	// programa fica em loop até que o usuário insira um genero valido
	public static String getGeneroValido(Scanner leitor) {
		String genero;
		boolean valido = false;
		do { // continuamente é feito esse bloco de comandos até o usuário inserir um genero valido ("F", "M" ou "NB")
			System.out.print("Gênero (F/M/NB): ");
			genero = leitor.nextLine().toUpperCase(); // caixa alta para padronizacao e facilitar insercao
			if(genero.equals("F") || genero.equals("M") || genero.equals("NB"))
				valido = true;
			if(!valido) {
				System.out.println(" ---------------------------------------------------------------------------------------------------");
				System.out.println("| Gênero inválido. Tente inserí-lo novamente, sendo [F] Feminino, [M] Masculino e [NB] Não binário. |");
				System.out.println(" ---------------------------------------------------------------------------------------------------\n");
			}
		} while(!valido);
		
		return genero;
	}

	public static int getQtdValida(Scanner leitor, String tipoQtd) {
		int qtd = -1;
		do {
			System.out.print(tipoQtd+": ");
			String qtdLeitor = leitor.nextLine();
			try {
				qtd = Integer.parseInt(qtdLeitor);
				if(qtd < 0) // número natural
					throw new Exception();
			} catch(Exception e) {
				System.out.println(" --------------------------------------------------------------------------------");
				System.out.println("| "+tipoQtd+" inválida! Tente inserí-la novamente, no formato [dd/mm/yyyy]. |");
				System.out.println(" --------------------------------------------------------------------------------\n");
			}
		} while (qtd == -1);
		
		return qtd;
	}
	
	// programa fica em loop até que o usuário insira um número entre 1886 e 2023
	//																    \ (invencao do carro, sim eu pesquisei...)
	public static int getAnoValido(Scanner leitor) {
		int anoFabricacao = 0;
		
		do { // continuamente é feito esse bloco de comandos até o usuário inserir um ano valido
			System.out.print("Ano de fabricação: ");
			try {					
				String anoFabricacaoLeitor = leitor.nextLine();
				anoFabricacao = Integer.parseInt(anoFabricacaoLeitor); // tenta converter a String para int
				if(anoFabricacao < 1886 || anoFabricacao > 2023)
					throw new Exception(); // gera excecao caso o ano esteja fora do intervalo dado
			} catch(Exception e) { // ano invalido
				anoFabricacao = 0; // zera variavel para continuar em loop
				System.out.println(" ---------------------------------------------------------------");
				System.out.println("| Ano de fabricação inválido! Tente inserir o número novamente. |");
				System.out.println(" ---------------------------------------------------------------\n");
			}
		}while(anoFabricacao ==0);
		
		return anoFabricacao;
	}

	public static int getIDValido(Scanner leitor) {
		int id = 0;
		
		do { // continuamente é feito esse bloco de comandos até o usuário inserir um id valido
			System.out.print("ID: ");
			try {					
				String idLeitor = leitor.nextLine();
				id = Integer.parseInt(idLeitor); // tenta converter a String para int
				if(id <= 0)
					throw new Exception(); // gera excecao caso seja negativo ou 0
			} catch(Exception e) { // ano invalido
				id = 0; // zera variavel para continuar em loop
				System.out.println(" ------------------------------------------------------------");
				System.out.println("| ID inválido! Tente inserir o número novamente. |");
				System.out.println(" ------------------------------------------------------------\n");
			}
		}while(id == 0);
		
		return id;
	}
	
}