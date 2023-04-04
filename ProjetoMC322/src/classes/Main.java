package classes;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
	
	public static void main(String [] args){ 
		
		Scanner leitor = new Scanner(System.in);
	    String operacao = "0";
	    Seguradora seguradora;
	    
	    System.out.println("=====================================");
		System.out.println("  Sistema de Seguradora de Veıculos");
		System.out.println("=====================================\n");
		
		System.out.println("Inicie suas operações criando uma seguradora.\n");
		esperarTecla(leitor);
		clearScreen();
		seguradora = criarSeguradora(leitor);
		esperarTecla(leitor);
		
	    // ========= MENU PRINCIPAL ========= // 
		do {
			clearScreen();
			System.out.println("=====================================");
			System.out.println("  Sistema de Seguradora de Veıculos");
			System.out.println("=====================================\n");
			System.out.println("1 - Cadastrar cliente");
			System.out.println("2 - Cadastrar veículo de um cliente");
			System.out.println("3 - Remover cliente");
			System.out.println("4 - Listar clientes da seguradora");
			System.out.println("5 - Gerar sinistro de um cliente");
			System.out.println("6 - Listar sinistros da seguradora\n");
			
			System.out.println("0 - Sair do programa\n");
			
			System.out.print("Digite o número da operação que deseja realizar: ");
			
			operacao = leitor.nextLine();
			
			switch(operacao) {
				case "1": 
					Cliente cliente = cadastrarCliente(leitor);
					seguradora.cadastrarCliente(cliente);
					break;
					
				case "2": 
					cadastrarVeiculo(leitor);
					break;
				
				case "3": 
					removerCliente(leitor, seguradora);
					break;
				
				case "4": 
					listarClientes(seguradora);
					break;
				
				case "5": 
					gerarSinistro(leitor, seguradora);
					break;
					
				case "6": 
					listarSinistrosCliente(seguradora);
					break;
					
				case "7": 
					listarSinistros(seguradora);
					break;
			}
			
			if(!operacao.equals("0"))
				esperarTecla(leitor);

		}while(!operacao.equals("0"));
		
	}
	
	public static void clearScreen() {  
	    //System.out.print("\033[H\033[2J");  
	    //System.out.flush();  
		for (int i = 0; i < 50; ++i) 
			System.out.println();
	}
	
	public static void esperarTecla(Scanner leitor) {  
		System.out.println("Aperte qualquer tecla para prosseguir!");
		leitor.nextLine();
	}
	
	public static Seguradora criarSeguradora(Scanner leitor) {
		System.out.println("----------------------");
		System.out.println(" 1 - Criar seguradora");
		System.out.println("----------------------\n");
		System.out.println("Digite as informações da nova seguradora.");
		
		System.out.print("Nome: ");
		String nome = leitor.nextLine();
		System.out.print("Telefone: ");
		String telefone = leitor.nextLine();
		System.out.print("Email: ");
		String email = leitor.nextLine();
		System.out.print("Endereco: ");
		String endereco = leitor.nextLine();
		
		Seguradora seguradora = new Seguradora(nome, telefone, email, endereco);
		
		System.out.println("\nNova seguradora criada!");
		
		return seguradora;
	}
	
	public static Cliente cadastrarCliente(Scanner leitor) {
		
		// ARRUMAR DATAS!!!
		
		System.out.println("-----------------------");
		System.out.println(" 2 - Cadastrar cliente");
		System.out.println("-----------------------\n");
		
		System.out.println("Digite as informações do novo cliente.\n");
		System.out.print("Nome: ");
		String nome = leitor.nextLine();
		System.out.print("Endereco: ");
		String endereco = leitor.nextLine();
		System.out.print("Data da licença(dd/mm/aaaa): ");
		String dataLicenca = leitor.nextLine();
		System.out.print("Educação: ");
		String educacao = leitor.nextLine();
		System.out.print("Gênero: ");
		String genero = leitor.nextLine();
		System.out.print("Classe econômica: ");
		String classeEconomica = leitor.nextLine();
		
		System.out.println("O cliente a ser cadastrado é físico(CPF) ou jurídico(CNPJ)?");
		System.out.println("Digite [CPF] ou [CNPJ].\n");
		String tipo = leitor.nextLine();
		
		Cliente cliente;
		
		if(tipo.equals("CPF")) {
			System.out.print("CPF: ");
			String cpf = leitor.nextLine();
			System.out.print("Data de Nascimento: ");
			String dataNascimento = leitor.nextLine();
			
			//cliente = new ClientePF(cpf, nome, endereco, dataLicenca,
			//		  educacao, genero, classeEconomica, dataNascimento, new ArrayList());
		
		} else if(tipo.equals("CNPJ")){
			System.out.print("CNPJ: ");
			String cnpj = leitor.nextLine();
			System.out.print("Data de Fundação: ");
			String dataFundacao = leitor.nextLine();
			
			//cliente = new ClientePJ(cnpj, nome, endereco, dataLicenca,
			//		   dataFundacao, new ArrayList());
		}
		
		System.out.println("\nNovo cliente cadastrado na seguradora!");
		
		return cliente;			
			
	}
	
	public static void cadastrarVeiculo(Scanner leitor) {
		
	}
	
	public static void removerCliente(Scanner leitor, Seguradora seguradora) {
		
	}
	
	public static void listarClientes(Seguradora seguradora) {
		
	}
	
	public static void gerarSinistro(Scanner leitor, Seguradora seguradora) {
		
	}
	
	public static void listarSinistrosCliente(Seguradora seguradora) {
		
	}
	
	public static void listarSinistros(Seguradora seguradora) {
		
	}
	
}
