package classes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
	
	public static void main(String [] args){ 
		
		Scanner leitor = new Scanner(System.in);
	    Seguradora seguradora;
	    
	    System.out.println("=====================================");
		System.out.println("  Sistema de Seguradora de Veıculos");
		System.out.println("=====================================\n");
		
		System.out.println("Inicie suas operações criando uma seguradora.\n");
		esperarTecla(leitor);
		seguradora = criarSeguradora(leitor);
		esperarTecla(leitor);
		
		/* ================
		 *  MENU PRINCIPAL
		 * ================*/
		String operacao = "0";
		do {
			limparTela();
			System.out.println("=====================================");
			System.out.println("  Sistema de Seguradora de Veıculos");
			System.out.println("=====================================\n");
			System.out.println("1 - Cadastrar cliente");
			System.out.println("2 - Cadastrar veículo de um cliente");
			System.out.println("3 - Remover cliente");
			System.out.println("4 - Listar clientes");
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
					if(removerCliente(leitor, seguradora))
						System.out.println("\nCliente removido da seguradora com sucesso!");
					else
						System.out.println("\nO cliente informado não estava cadastrado na seguradora. Revise os dados.");
					break;
				
				case "4": 
					listarClientes(seguradora);
					break;
				
				case "5": 
					if(gerarSinistro(leitor, seguradora))
						System.out.println("\nSinistro cadastrado com sucesso!");
					else
						System.out.println("\nAlgo deu errado no cadastro do sinistro. Revise os dados e certifique-se que o cliente envolvido está cadastrado na seguradora.");
					break;
					
				case "6": 
					listarSinistrosCliente(leitor, seguradora);
					break;
					
				case "7": 
					listarSinistros(seguradora);
					break;
			}
			
			if(!operacao.equals("0"))
				esperarTecla(leitor);

		}while(!operacao.equals("0"));
		
	}
	
	/* =============================
	 *  COMANDOS BÁSICOS DO CONSOLE
	 * =============================*/
	
	public static void limparTela() {  
	    //System.out.print("\033[H\033[2J");  
	    //System.out.flush();  
		for (int i = 0; i < 50; ++i) 
			System.out.println();
	}
	
	public static void esperarTecla(Scanner leitor) {  
		System.out.println("Aperte qualquer tecla para prosseguir.");
		leitor.nextLine();
	}
	
	/* ============================================================
	 *  FUNÇÕES PARA LIDAR COM A LEITURA DE INFORMAÇÕES DO USUÁRIO
	 * ============================================================*/
	
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
	
	/* =======================================
	 *  MÉTODOS/FUNÇÕES DE CADA OPÇÃO DO MENU
	 * =======================================*/
	
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
		
		int anoFabricacao = 0;
		do {
			System.out.print("Ano de fabricação: ");
			try {
				String anoFabricacaoLeitor = leitor.nextLine();
				anoFabricacao = Integer.parseInt(anoFabricacaoLeitor);			
			} catch(Exception e) {
				System.out.println("----------------------------------------------------------------");
				System.out.println("| Ano de fabricação inválido. Tente inserir o número novamente. |");
				System.out.println("----------------------------------------------------------------\n");
			}
		}while(anoFabricacao ==0);
		
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
		/*// try catch
		if(veiculo == null) {
			System.out.print("ERRO: Os dados do veículo não são condizentes e não foi possível cadastrá-lo.");
			return null;
		}*/
					
		cliente.adicionarVeiculo(veiculo);
		
		return veiculo;			
	}
	
	public static boolean removerCliente(Scanner leitor, Seguradora seguradora) {
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
		
		return seguradora.removerCliente(keyCliente);
	}
	
	public static void listarClientes(Seguradora seguradora) {
		limparTela();
		System.out.println("-------------------------------------");
		System.out.println(" 4 - Listar clientes da seguradora");
		System.out.println("-------------------------------------\n");
		
		if (seguradora.getListaClientes() == null) {
			System.out.println("Nenhum cliente cadastrado na seguradora "+ seguradora.getNome() +". Para cadastrar novos cliente, digite a opção 1 no menu.\n");
			return;
		}
		
		System.out.println("Segue a lista de clientes da seguradora "+ seguradora.getNome() +"\n");
		
		for(Cliente cliente: seguradora.getListaClientes())
			System.out.println(cliente.toString());
	}
	
	public static boolean gerarSinistro(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("-------------------------------------");
		System.out.println(" 5 -  Gerar sinistro de um cliente ");
		System.out.println("-------------------------------------\n");
		
		System.out.println("Digite as informações que envolvem o sinistro.");
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate data = null;
		do {
			System.out.print("Data do sinistro: ");
			String dataLicencaLeitor = leitor.nextLine();
			try {
				data = LocalDate.parse(dataLicencaLeitor, formatter);
			} catch(Exception e) {
				System.out.println("---------------------------------------------------------------------------------");
				System.out.println("| Data do sinistro inválido. Tente inserí-la novamente, no formato [dd/mm/yyyy]. |");
				System.out.println("---------------------------------------------------------------------------------\n");
			}
		} while (data == null);
		
		System.out.print("Endereço do sinistro: ");
		String endereco = leitor.nextLine();
		
		String tipoCliente = getTipoClienteValido(leitor);
		
		String keyCliente = "";
		if(tipoCliente.equals("CNPJ"))
			keyCliente = getCnpjValido(leitor);
		else if(tipoCliente.equals("CPF"))
			keyCliente = getCpfValido(leitor);
		
		System.out.print("Placa do veículo: ");
		String placa = leitor.nextLine();
		
		
		return seguradora.gerarSinistro(placa, keyCliente, data, endereco);
	}
	
	public static void listarSinistrosCliente(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("-------------------------------------");
		System.out.println(" 5 - Listar sinistros de um cliente");
		System.out.println("-------------------------------------\n");
		
		String tipoCliente = getTipoClienteValido(leitor);
		
		String keyCliente = "";
		if(tipoCliente.equals("CNPJ"))
			keyCliente = getCnpjValido(leitor);
		else if(tipoCliente.equals("CPF"))
			keyCliente = getCpfValido(leitor);
		
		if (!seguradora.visualizarSinistro(keyCliente)) {
			System.out.println("Este cliente não tem nenhum sinistro gerado na seguradora, ou não está cadastrado na seguradora. Você pode verificar se o cliente informado está cadastrado na seguradora digitando a opção 4 no menu\n");
			return;
		}
		
		System.out.println("Segue a lista de sinistros do cliente informado registrados na seguradora "+ seguradora.getNome() +"\n");
		
		for(Sinistro sinistro: seguradora.listarSinistrosByKeyCliente(keyCliente))
			System.out.println(sinistro.toString());
	}
	
	public static void listarSinistros(Seguradora seguradora) {
		limparTela();
		System.out.println("-------------------------------------");
		System.out.println(" 6 - Listar sinistros da seguradora");
		System.out.println("-------------------------------------\n");
		
		if (seguradora.listarSinistros() == null) {
			System.out.println("Nenhum sinistro gerado na seguradora "+ seguradora.getNome() +". Para gerar novos sinistros, digite a opção 5 no menu.\n");
			return;
		}
		
		System.out.println("Segue a lista de sinistros registrados na seguradora "+ seguradora.getNome() +"\n");
		
		for(Sinistro sinistro: seguradora.listarSinistros())
			System.out.println(sinistro.toString());
	}
	
}
