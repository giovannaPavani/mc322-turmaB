package classes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
		seguradora = criarSeguradora(leitor);
		esperarTecla(leitor);
		
	    // ========= MENU PRINCIPAL ========= // 
		do {
			limparTela();
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
			Cliente cliente;
			
			switch(operacao) {
				case "1": 
					cliente = cadastrarCliente(leitor);
					if(cliente != null) {
						System.out.println("\nNovo cliente cadastrado na seguradora!");
						seguradora.cadastrarCliente(cliente);
					}
					break;
					
				case "2": 
					Veiculo veiculo = cadastrarVeiculo(leitor, seguradora);
					if(veiculo != null)
						System.out.println("\nNovo veículo cadastrado na seguradora!");
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
	
	public static void limparTela() {  
	    //System.out.print("\033[H\033[2J");  
	    //System.out.flush();  
		for (int i = 0; i < 50; ++i) 
			System.out.println();
	}
	
	public static void esperarTecla(Scanner leitor) {  
		System.out.println("Aperte qualquer tecla para prosseguir!");
		leitor.nextLine();
	}
	
	private static String getCnpjValido(Scanner leitor) {
		boolean valido = false;
		String cnpj;
		do {
			if(!valido) {
				System.out.println("--------------------------------------------");
				System.out.println("| CNPJ inválido. Tente inserí-lo novamente. |");
				System.out.println("--------------------------------------------\n");
			}
			System.out.print("CNPJ: ");
			cnpj = leitor.nextLine();
			valido = ClientePJ.validarCNPJ(cnpj);
		} while(!valido);
		
		return cnpj;
	}
	
	private static String getCpfValido(Scanner leitor) {
		String cpf;
		boolean valido = false;
		do {
			if(!valido)
				System.out.println("-------------------------------------------");
				System.out.println("| CPF inválido. Tente inserí-lo novamente. |");
				System.out.println("-------------------------------------------\n");
			System.out.print("CPF: ");
			cpf = leitor.nextLine();
			valido = ClientePF.validarCPF(cpf);
		} while(!valido);
		
		return cpf;
	}
	
	private static String getTipoClienteValido(Scanner leitor) {
		String tipoCliente = "";
		do{
			System.out.println("O cliente é físico(CPF) ou jurídico(CNPJ)?");
			System.out.println("Digite [CPF] ou [CNPJ].\n");
			String tipoLeitor = leitor.nextLine();
			if(tipoLeitor.equals("CPF") || tipoLeitor.equals("CNPJ"))
				tipoCliente = tipoLeitor;
			else {
				System.out.println("---------------------------------------------------------------------");
				System.out.println("| Tipo de cliente inválido. Tente inserir [CPF] ou [CNPJ] novamente. |");
				System.out.println("---------------------------------------------------------------------\n");
			}
		} while(tipoCliente.equals(""));
		
		return tipoCliente;
	}
	
	public static Seguradora criarSeguradora(Scanner leitor) {
		limparTela();
		System.out.println("----------------------");
		System.out.println("   Criar seguradora   ");
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
		limparTela();
		System.out.println("-------------------------");
		System.out.println(" 1 - Cadastrar cliente");
		System.out.println("-------------------------\n");
		
		System.out.println("Digite as informações do novo cliente.");
		System.out.println("(Obs: Escreva a data no formato dd/mm/aaaa.)\n");
		System.out.print("Nome: ");
		String nome = leitor.nextLine();
		System.out.print("Endereco: ");
		String endereco = leitor.nextLine();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataLicenca = null;
		do {
			System.out.print("Data da licença: ");
			String dataLicencaLeitor = leitor.nextLine();
			try {
				dataLicenca = LocalDate.parse(dataLicencaLeitor, formatter);
			} catch(Exception e) {
				System.out.println("---------------------------------------------------------------------------------");
				System.out.println("| Data da licença inválida. Tente inserí-la novamente, no formato [dd/mm/yyyy]. |");
				System.out.println("---------------------------------------------------------------------------------\n");
			}
		} while (dataLicenca == null);
		
		//System.out.println("Certo! " + dataLicenca.format(formatter));
		
		System.out.print("Educação: ");
		String educacao = leitor.nextLine();
		System.out.print("Gênero: ");
		String genero = leitor.nextLine();
		System.out.print("Classe econômica: ");
		String classeEconomica = leitor.nextLine();
		
		String tipo = getTipoClienteValido(leitor);
		
		Cliente cliente = null;
		
		
		if(tipo.equals("CPF")) {
			String cpf = getCpfValido(leitor);
				
			LocalDate dataNascimento = null;
			do {
				System.out.print("Data de Nascimento: ");
				String dataNascimentoLeitor = leitor.nextLine();
				try {
					dataNascimento = LocalDate.parse(dataNascimentoLeitor, formatter);
				} catch(Exception e) {
					System.out.println("-----------------------------------------------------------------------------------");
					System.out.println("| Data de nascimento inválida. Tente inserí-la novamente, no formato [dd/mm/aaaa]. |");
					System.out.println("-----------------------------------------------------------------------------------\n");
				}
			} while (dataNascimento == null);
				
				cliente = new ClientePF(nome, endereco, dataLicenca, new ArrayList<Veiculo>(),
										cpf, dataNascimento, educacao, genero, classeEconomica);
		
		} else if(tipo.equals("CNPJ")){
			String cnpj = getCnpjValido(leitor);
			
			LocalDate dataFundacao = null;
			do {
				System.out.print("Data de funndação: ");
				String dataFundacaoLeitor = leitor.nextLine();
				try {
					dataFundacao = LocalDate.parse(dataFundacaoLeitor, formatter);
				} catch(Exception e) {
					System.out.println("---------------------------------------------------------------------------------");
					System.out.println("| Data de fundação inválida. Tente inserí-la novamente, no formato [dd/mm/yyyy]. |");
					System.out.println("---------------------------------------------------------------------------------\n");
				}
			} while (dataFundacao == null);
			
			cliente = new ClientePJ(nome, endereco, dataLicenca, 
					                new ArrayList<Veiculo>(),cnpj, dataFundacao );
		}
		
		return cliente;			
	}
	
	public static Veiculo cadastrarVeiculo(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("-------------------------");
		System.out.println(" 2 - Cadastrar veículo");
		System.out.println("-------------------------\n");
		
		System.out.println("Digite as informações do novo veículo.");
		
		System.out.print("Placa: ");
		String placa = leitor.nextLine();
		System.out.print("Marca: ");
		String marca = leitor.nextLine();
		System.out.print("Modelo: ");
		String modelo = leitor.nextLine();
		System.out.print("Ano de fabricaçao: ");
		String anoFabricacaoLeitor = leitor.nextLine();
		int anoFabricacao = Integer.parseInt(anoFabricacaoLeitor);
		
		String tipoCliente = getTipoClienteValido(leitor);
		
		String keyCliente = "";
		if(tipoCliente.equals("CNPJ"))
			keyCliente = getCnpjValido(leitor);
		else if(tipoCliente.equals("CPF"))
			keyCliente = getCpfValido(leitor);
		
		// acha cliente dentro da seguradora
		Cliente cliente = seguradora.getClienteByKey(keyCliente);
		if(cliente == null) {
			System.out.print("ERRO: O cliente cujo CPF/CNPJ foi dado não está cadastrado na seguradora.");
			return null;
		}
		// cria e cadastra veiculo no objeto do cliente dono
		Veiculo veiculo = new Veiculo(placa, marca, modelo, anoFabricacao);
		if(veiculo == null) {
			System.out.print("ERRO: Os dados do veículo não são condizentes e não foi possível cadastrá-lo.");
			return null;
		}
					
		cliente.adicionarVeiculo(veiculo);
		
		return veiculo;			
	}
	
	public static void removerCliente(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("------------------------");
		System.out.println(" 3 - Remover cliente");
		System.out.println("------------------------\n");
		
		String tipoCliente = getTipoClienteValido(leitor);
		
		String keyCliente = "";
		if(tipoCliente.equals("CNPJ"))
			keyCliente = getCnpjValido(leitor);
		else if(tipoCliente.equals("CPF"))
			keyCliente = getCpfValido(leitor);
		
		seguradora.removerCliente(keyCliente);
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
