package classes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
	
	public static void main(String [] args){ 
		
		Scanner leitor = new Scanner(System.in);
	    Seguradora seguradora;
	    
	    System.out.println("=================================================");
		System.out.println("        Sistema de Seguradora de Veıculos");
		System.out.println("=================================================\n");
		
		System.out.println("Inicie suas operações criando uma seguradora.\n");
		
		/* ====================
		 *    DADOS DE TESTE
		 * ==================== */
		///*
		seguradora = new Seguradora("Azul", "1932770378", "azul@g.com", "Av 1, 89", 
				                    new ArrayList<Sinistro>(), new ArrayList<Cliente>());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataL = LocalDate.parse("01/03/2023", formatter);
		LocalDate dataN = LocalDate.parse("09/08/2004", formatter);
		LocalDate dataF = LocalDate.parse("21/07/2010", formatter);
		LocalDate dataS = LocalDate.parse("14/04/2023", formatter);
				
		ClientePF clientePF = new ClientePF("MARIA", "AV 2, 89", new LinkedList<Veiculo>(), dataL,
				                            "90197003087", dataN, "GRADUANDA", "F", "MÉDIA");
		ClientePJ clientePJ = new ClientePJ("PADARIA SM", "AV 3, 140", new LinkedList<Veiculo>(),
				"79896457000186", dataF);
		
		Veiculo veiculo1 = new Veiculo("NCX-3134", "NISSAN", "KICKS", 2020);
		Veiculo veiculo2 = new Veiculo("MSM-8271", "HONDA", "FIT", 2019);
		Veiculo veiculo3 = new Veiculo("HZM-7159", "HONDA", "H-RV", 2022);
		
		clientePF.adicionarVeiculo(veiculo1);
		clientePJ.adicionarVeiculo(veiculo2);
		clientePJ.adicionarVeiculo(veiculo3);
		
		seguradora.cadastrarCliente(clientePF);
		seguradora.cadastrarCliente(clientePJ);
		
		seguradora.gerarSinistro("NCX-3134", "90197003087", dataS, "RUA CAOS");
		
		seguradora.gerarSinistro("MSM-8271", "79.896.457/0001-86", dataS, "RUA ACIDENTE");
		//*/
		/* ====================
		 *  FIM DADOS DE TESTE
		 * ==================== */
		
		esperarTecla(leitor);
		
		/* Caso queira rodar o programa adicionando sua propria seguradora, conforme foi planejado este programa,
		 * deixe as proximas duas linhas descomentadas e comente o bloco de dados de teste.
		 * 
		 * Caso contário, comente essas linhas e descomente o bloco de dados de teste */
		/*
		seguradora = criarSeguradora(leitor);
		esperarTecla(leitor);
		*/
		
		/* ================
		 *  MENU PRINCIPAL
		 * ================ */
		
		String operacao = "0";
		do {
			limparTela();
			System.out.println("=================================================");
			System.out.println("        Sistema de Seguradora de Veıculos");
			System.out.println("=================================================\n");
			System.out.println(" 1 - Cadastrar cliente");
			System.out.println(" 2 - Cadastrar veículo de um cliente");
			System.out.println(" 3 - Remover cliente");
			System.out.println(" 4 - Listar clientes");
			System.out.println(" 5 - Gerar sinistro de um cliente");
			System.out.println(" 6 - Listar sinistros da seguradora");
			System.out.println(" 7 - Listar sinistros de um cliente\n");
			
			System.out.println(" 0 - Sair do programa\n");
			
			System.out.print("Digite o número da operação que deseja realizar: ");
			
			operacao = leitor.nextLine();
			
			if(("01234567").indexOf(operacao) < 0) {
				System.out.println(" ---------------------------------------------------------------------------------");
				System.out.println("| A operação inserida não é válida! Reveja os índices do menu e insira um válido. |");
				System.out.println(" ---------------------------------------------------------------------------------\n");
				esperarTecla(leitor);
				continue;
			}	
			
			switch(operacao) {
				case "1": 
					Cliente cliente = cadastrarCliente(leitor);
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
					listarClientes(leitor, seguradora);
					break;
				
				case "5": 
					if(gerarSinistro(leitor, seguradora))
						System.out.println("\nSinistro cadastrado com sucesso!");
					else
						System.out.println("\nAlgo deu errado no cadastro do sinistro. Revise os dados e certifique-se que o cliente envolvido está cadastrado na seguradora.");
					break;
					
				case "6": 
					listarSinistros(seguradora);
					break;
					
				case "7": 
					listarSinistrosCliente(leitor, seguradora);
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
	 *  FUNÇÕES PARA LIDAR COM A LEITURA DE INFORMAÇÕES DO CONSOLE
	 * ============================================================*/
	
	private static String getCnpjValido(Scanner leitor) {
		boolean valido = false;
		String cnpj;
		do {
			System.out.print("CNPJ: ");
			cnpj = leitor.nextLine();
			valido = ClientePJ.validarCNPJ(cnpj);
			if(!valido) {
				System.out.println(" -------------------------------------------");
				System.out.println("| CNPJ inválido. Tente inserí-lo novamente. |");
				System.out.println(" -------------------------------------------\n");
			}
		} while(!valido);
		
		return cnpj.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
	}
	
	private static String getCpfValido(Scanner leitor) {
		String cpf;
		boolean valido = false;
		do {
			System.out.print("CPF: ");
			cpf = leitor.nextLine();
			valido = ClientePF.validarCPF(cpf);
			if(!valido) {
				System.out.println(" ------------------------------------------");
				System.out.println("| CPF inválido. Tente inserí-lo novamente. |");
				System.out.println(" ------------------------------------------\n");
			}
		} while(!valido);
		
		return cpf.replaceAll("\\.", "").replaceAll("-", "");
	}
	
	private static String getTipoClienteValido(Scanner leitor) {
		String tipoCliente = "";
		do{
			System.out.println("\nO cliente é físico(CPF) ou jurídico(CNPJ)?");
			System.out.print("Digite [CPF] ou [CNPJ]: ");
			String tipoLeitor = leitor.nextLine();
			if(tipoLeitor.equals("CPF") || tipoLeitor.equals("CNPJ")) {
				tipoCliente = tipoLeitor;
				System.out.println();				
			}
			else {
				System.out.println(" --------------------------------------------------------------------");
				System.out.println("| Tipo de cliente inválido. Tente inserir [CPF] ou [CNPJ] novamente. |");
				System.out.println(" --------------------------------------------------------------------\n");
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
		System.out.println("Digite as informações da nova seguradora.\n");
		
		System.out.print("Nome: ");
		String nome = leitor.nextLine();
		System.out.print("Telefone: ");
		String telefone = leitor.nextLine();
		System.out.print("Email: ");
		String email = leitor.nextLine();
		System.out.print("Endereco: ");
		String endereco = leitor.nextLine();
		
		Seguradora seguradora = new Seguradora(nome, telefone, email, endereco, 
                							   new ArrayList<Sinistro>(), new ArrayList<Cliente>());
		
		System.out.println("\nNova seguradora criada!\nVeja as informações cadastradas:");
		
		System.out.println("-------------------------");
		System.out.println(seguradora.toString());
		System.out.println("-------------------------\n");
		
		return seguradora;
	}
	
	public static Cliente cadastrarCliente(Scanner leitor) {
		limparTela();
		System.out.println("-------------------------------------------");
		System.out.println("           1 - Cadastrar cliente");
		System.out.println("-------------------------------------------\n");
		
		System.out.println("Digite as informações do novo cliente.");
		System.out.println("(Obs: Escreva datas no formato dd/mm/aaaa)\n");
		System.out.print("Nome: ");
		String nome = leitor.nextLine();
		System.out.print("Endereco: ");
		String endereco = leitor.nextLine();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		String tipo = getTipoClienteValido(leitor);
		
		Cliente cliente = null;
		
		if(tipo.equals("CPF")) {
			String cpf = getCpfValido(leitor);
			
			LocalDate dataLicenca = null;
			do {
				System.out.print("Data da licença: ");
				String dataLicencaLeitor = leitor.nextLine();
				try {
					dataLicenca = LocalDate.parse(dataLicencaLeitor, formatter);
				} catch(Exception e) {
					System.out.println(" --------------------------------------------------------------------------------");
					System.out.println("| Data da licença inválida! Tente inserí-la novamente, no formato [dd/mm/yyyy]. |");
					System.out.println(" --------------------------------------------------------------------------------\n");
				}
			} while (dataLicenca == null);
			
			LocalDate dataNascimento = null;
			do {
				System.out.print("Data de Nascimento: ");
				String dataNascimentoLeitor = leitor.nextLine();
				try {
					dataNascimento = LocalDate.parse(dataNascimentoLeitor, formatter);
				} catch(Exception e) {
					System.out.println(" ----------------------------------------------------------------------------------");
					System.out.println("| Data de nascimento inválida! Tente inserí-la novamente, no formato [dd/mm/aaaa]. |");
					System.out.println(" ----------------------------------------------------------------------------------\n");
				}
			} while (dataNascimento == null);
			
			System.out.print("Educação: ");
			String educacao = leitor.nextLine();
			
			String genero;
			boolean valido = false;
			do {
				System.out.print("Gênero (F/M/NB): ");
				genero = leitor.nextLine();
				if(genero.equals("F") || genero.equals("M") || genero.equals("NB"))
					valido = true;
				if(!valido) {
					System.out.println(" ---------------------------------------------------------------------------------------------------");
					System.out.println("| Gênero inválido. Tente inserí-lo novamente, sendo [F] Feminino, [M] Masculino e [NB] Não binário. |");
					System.out.println(" ---------------------------------------------------------------------------------------------------\n");
				}
			} while(!valido);
			
			System.out.print("Classe econômica: ");
			String classeEconomica = leitor.nextLine();
			
			cliente = new ClientePF(nome, endereco, new LinkedList<Veiculo>(), dataLicenca,
									cpf, dataNascimento, educacao, genero, classeEconomica);
		
		} else if(tipo.equals("CNPJ")){
			String cnpj = getCnpjValido(leitor);
			
			LocalDate dataFundacao = null;
			do {
				System.out.print("Data de fundação: ");
				String dataFundacaoLeitor = leitor.nextLine();
				try {
					dataFundacao = LocalDate.parse(dataFundacaoLeitor, formatter);
				} catch(Exception e) {
					System.out.println(" --------------------------------------------------------------------------------");
					System.out.println("| Data de fundação inválida! Tente inserí-la novamente, no formato [dd/mm/yyyy]. |");
					System.out.println(" --------------------------------------------------------------------------------\n");
				}
			} while (dataFundacao == null);
			
			cliente = new ClientePJ(nome, endereco, new LinkedList<Veiculo>(),
					                cnpj, dataFundacao);
		}
		
		return cliente;			
	}
	
	public static Veiculo cadastrarVeiculo(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("---------------------------------------");
		System.out.println("        2 - Cadastrar veículo");
		System.out.println("---------------------------------------\n");
		
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
				System.out.println(" ---------------------------------------------------------------");
				System.out.println("| Ano de fabricação inválido! Tente inserir o número novamente. |");
				System.out.println(" ---------------------------------------------------------------\n");
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
		System.out.println("--------------------------------------------");
		System.out.println("           3 - Remover cliente");
		System.out.println("--------------------------------------------");
		
		String tipoCliente = getTipoClienteValido(leitor);
		
		String keyCliente = "";
		if(tipoCliente.equals("CNPJ"))
			keyCliente = getCnpjValido(leitor);
		else if(tipoCliente.equals("CPF"))
			keyCliente = getCpfValido(leitor);
		
		return seguradora.removerCliente(keyCliente);
	}
	
	public static void listarClientes(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("-----------------------------------------------------------");
		System.out.println("            4 - Listar clientes da seguradora");
		System.out.println("-----------------------------------------------------------\n");
		
		if (seguradora.getListaClientes() == null || seguradora.getListaClientes().isEmpty()) {
			System.out.println("Nenhum cliente cadastrado na seguradora "+ seguradora.getNome() +". Para cadastrar novos cliente, digite a opção 1 no menu.\n");
			return;
		}
		
		String tipoCliente = "";
		do{
			System.out.println("Deseja a lista de clientes físicos(CPF) ou jurídicos(CNPJ)?");
			System.out.print("Digite [CPF] ou [CNPJ]: ");
			String tipoLeitor = leitor.nextLine();
			if(tipoLeitor.equals("CPF") || tipoLeitor.equals("CNPJ")) {
				tipoCliente = tipoLeitor;
				System.out.println();				
			}
			else {
				System.out.println(" --------------------------------------------------------------------");
				System.out.println("| Tipo de cliente inválido. Tente inserir [CPF] ou [CNPJ] novamente. |");
				System.out.println(" --------------------------------------------------------------------\n");
			}
		} while(tipoCliente.equals(""));
		
		if(tipoCliente.equals("CPF"))
			System.out.println("\nSegue a lista de clientes físicos cadastrados na seguradora:\n");
		else
			System.out.println("\nSegue a lista de clientes jurídicos cadastrados na seguradora:\n");
		
		System.out.println("-------------------------------\n");
		for(Cliente cliente: seguradora.listarClientes(tipoCliente)) {
			System.out.println(cliente.toString()+"\n");
			System.out.println("-------------------------------\n");
		}
		System.out.println();
	}
	
	public static boolean gerarSinistro(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println(" 5 -  Gerar sinistro de um cliente ");
		System.out.println("--------------------------------------------\n");
		
		System.out.println("Digite as informações que envolvem o sinistro.");
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate data = null;
		do {
			System.out.print("Data do sinistro: ");
			String dataLicencaLeitor = leitor.nextLine();
			try {
				data = LocalDate.parse(dataLicencaLeitor, formatter);
			} catch(Exception e) {
				System.out.println(" --------------------------------------------------------------------------------");
				System.out.println("| Data do sinistro inválido. Tente inserí-la novamente, no formato [dd/mm/yyyy]. |");
				System.out.println(" --------------------------------------------------------------------------------\n");
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
	
	public static void listarSinistros(Seguradora seguradora) {
		limparTela();
		System.out.println("-----------------------------------------------------------");
		System.out.println("            6 - Listar sinistros da seguradora");
		System.out.println("-----------------------------------------------------------\n");
		
		if (seguradora.listarSinistros() == null || seguradora.listarSinistros().isEmpty()) {
			System.out.println("Nenhum sinistro gerado na seguradora "+ seguradora.getNome() +". Para gerar novos sinistros, digite a opção 5 no menu.\n");
			return;
		}
		
		System.out.println("Segue a lista de sinistros registrados na seguradora:\n");
		
		System.out.println("----------------------------------\n");
		for(Sinistro sinistro: seguradora.listarSinistros()) {
			System.out.println(sinistro.toString()+"-\n");
			System.out.println("----------------------------------\n");
		}
	}
	
	public static void listarSinistrosCliente(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("-------------------------------------------");
		System.out.println("    7 - Listar sinistros de um cliente");
		System.out.println("-------------------------------------------");
		
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
		
		System.out.println("Segue a lista de sinistros do cliente informado registrados na seguradora: \n");
		
		System.out.println("----------------------------------\n");
		for(Sinistro sinistro: seguradora.listarSinistrosByKeyCliente(keyCliente)) {
			System.out.println(sinistro.toString()+"-\n");
			System.out.println("----------------------------------\n");
		}
	}
	
}
