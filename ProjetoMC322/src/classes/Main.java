package classes;
import java.util.Scanner;

public class Main {
	
	public static void main(String [] args){ 
		
		Scanner leitor = new Scanner(System.in);
	    String operacao = "0";
	    // ========= MENU PRINCIPAL ========= // 
		do {
			clearScreen();
			System.out.println("---------------------------------");
			System.out.println("Sistema de Seguradora de Veıculos");
			System.out.println("---------------------------------\n");
			System.out.println("1 - Cliente");
			System.out.println("2 - Veiculo");
			System.out.println("3 - Sinistro");
			System.out.println("4 - Seguradora\n");
			System.out.println("0 - Sair do programa\n");
			
			System.out.println("Digite o numero do objeto com o qual deseja realizar uma operação: ");
			
			operacao = leitor.nextLine();
			switch(operacao) {
				case "1": abrirMenuCliente(leitor);
				case "2": abrirMenuVeiculo(leitor);
				case "3": abrirMenuSinistro(leitor);
				case "4": abrirMenuSeguradora(leitor);
			}
			
		}while(!operacao.equals("0"));
	}
	
	public static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	}  
	
	// ========= MINI MENUS ========= //
	private static void abrirMenuCliente(Scanner leitor) {
		String operacao = "0";
		do{
			clearScreen();
			System.out.println("		Clientes");
			System.out.println("------------------------\n");
			System.out.println("1.1 - Criar cliente físico");
			System.out.println("1.2 - Criar cliente jurídico");
			System.out.println("1.3 - Verificar CPF");
			System.out.println("1.4 - Verificar CNPJ\n");
			System.out.println("0 - Voltar para o menu principal\n");
			
			System.out.println("Digite o numero da operação que deseja realizar: ");
			leitor.next(operacao);
			
			switch(operacao) {
				case "1.1": System.out.print("oi");
			}
		} while(!operacao.equals("0"));
	}
	
	private static void abrirMenuVeiculo(Scanner leitor) {
		String operacao = "0";
		do{
			System.out.flush();
			System.out.println("		Veiculos");
			System.out.println("------------------------\n");
			System.out.println("Digite o numero da operação que deseja realizar:\n");
			System.out.println("2.1 - Criar veiculo");
			System.out.println("0 - Voltar para o menu principal\n\n");
			leitor.next(operacao);
			
			switch(operacao) {
				case "2.1":
			}
		} while(!operacao.equals("0"));
	}
	
	private static void abrirMenuSinistro(Scanner leitor) {
		String operacao = "0";
		do{
			System.out.flush();
			System.out.println("		Sinistros");
			System.out.println("-------------------------\n");
			System.out.println("Digite o numero da operação que deseja realizar:\n");
			System.out.println("3.1 - Criar sinistro");
			System.out.println("0 - Voltar para o menu principal\n\n");
			leitor.next(operacao);
			
			switch(operacao) {
				case "3.1":
			}
		} while(!operacao.equals("0"));
	}
	
	private static void abrirMenuSeguradora(Scanner leitor) {
		String operacao = "0";
		do{
			System.out.flush();
			System.out.println("		Seguradora");
			System.out.println("--------------------------\n");
			System.out.println("Digite o numero da operação que deseja realizar:\n");
			System.out.println("4.1 - Criar seguradora");
			System.out.println("4.2 - Cadastrar cliente");
			System.out.println("4.3 - Remover cliente");
			System.out.println("4.4 - Listar clientes da seguradora");
			System.out.println("4.5 - Gerar sinistro");
			System.out.println("4.6 - Visualizar sinistros de um cliente");
			System.out.println("4.7 - Listar sinistros da seguradora");
			System.out.println("0 - Voltar para o menu principal\n\n");
			leitor.next(operacao);
			
			switch(operacao) {
				case "4.1":
			}
		} while(!operacao.equals("0"));
	}
}
