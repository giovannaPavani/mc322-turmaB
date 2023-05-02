package Main;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import Cliente.Cliente;
import Cliente.ClientePF;
import Cliente.ClientePJ;
import Seguradora.Seguradora;
import Sinistro.Sinistro;
import Veiculo.Veiculo;

public class Main {
	
	public static void main(String [] args){ 
		
		// Objeto responsavel por fazer a leitura do console 
		Scanner leitor = new Scanner(System.in);
		// Apenas uma seguradora é criada e será nela que todas as operações serão feitas
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
				                            "79896457000186", dataF, 4);
		
		Veiculo veiculo1 = new Veiculo("NCX-3134", "NISSAN", "KICKS", 2020);
		Veiculo veiculo2 = new Veiculo("MSM-8271", "HONDA", "FIT", 2019);
		Veiculo veiculo3 = new Veiculo("HZM-7159", "HONDA", "H-RV", 2022);
		
		clientePF.adicionarVeiculo(veiculo1);
		clientePJ.adicionarVeiculo(veiculo2);
		clientePJ.adicionarVeiculo(veiculo3);
		
		seguradora.cadastrarCliente(clientePF);
		seguradora.cadastrarCliente(clientePJ);
		
		seguradora.gerarSinistro("NCX-3134", "90197003087", dataS, "RUA CAOS");
		
		//seguradora.gerarSinistro("MSM-8271", "79.896.457/0001-86", dataS, "RUA ACIDENTE");
		//*/
		/* ====================
		 *  FIM DADOS DE TESTE
		 * ==================== */
		
		esperarEnter(leitor);
		
		/* Caso queira rodar o programa adicionando sua propria seguradora, conforme foi planejado este programa,
		 * deixe as proximas duas linhas descomentadas e comente o bloco de dados de teste.
		 * 
		 * Caso contário, comente essas linhas e descomente o bloco de dados de teste */
		/*
		seguradora = criarSeguradora(leitor);
		esperarEnter(leitor);
		*/
		
		/* ================
		 *  MENU PRINCIPAL
		 * ================ */
		
		String operacao = "0";
		do {
			limparTela();
			// implementar menu
			// Exibir menu
			//System.out.print(MenuOperacoes.CADASTRAR.ordinal()+1 + " - " +
			//				MenuOperacoes.CADASTRAR.getDescricao());
			// pode ser getname tbm, mas dai seria o nome da constante (ex: CADASTRAR)
			int opUsuario;
			MenuOperacoes opUsuarioConst;
			do {
				do {
					for(MenuOperacoes opcao: MenuOperacoes.values()) {
						System.out.print(opcao.ordinal() + " - " + opcao.getDescricao());
					}
					
					System.out.print("Digite o número da operação que deseja realizar: ");
					opUsuario = leitor.nextInt();
					
				} while(opUsuario > 0 && opUsuario < MenuOperacoes.values().length);
				
				System.out.print("Executa opcao "+ opUsuario);
				
				opUsuarioConst = MenuOperacoes.values()[opUsuario]; // pega a constante da enum
				
			} while(opUsuarioConst != MenuOperacoes.SAIR);
			
			// metodo pra exibir menu, metodo pra ler opcao, metodo para exec menu externo
			// e metodos para executar operacoes
			
			/*
			private static void executaOpcaoMenuExterno(MenuOperacoes op) {
			
				switch (op) {
					case CADASTRAR:
						// chama submenu
						System.out.println("Executa cadastrar");
						break;
						
					case LISTAR:
						// chama submenu
						System.out.println("Executa listar");
						break;
						
					case EXCLUIR:
						// chama submenu
						System.out.println("Executa excluir");
						break;
						
					case GERAR_SINISTRO:
						// chama aq o metodo
						System.out.println("Executa GERAR_SINISTRO");
						break;
					
					case TRANSFERIR_SEGURO:
						// chama aq o metodo
						System.out.println("Executa TRANSFERIR_SEGURO");
						break;
				}
			}
			*/
			
			// MAIN
			/*
			MenuOPeracoes opUsuarioConst;
			do {
				exibirMenuExterno();
				opUsuarioConst = lerOpMenuExterno();
				// executar operacao usuario escolheu
				executaOpcaoMenuExterno(opUsuarioConst);
				//System.out.println("Executa opcao "+opUsuarioConst.getDescricao());
			} while(opUsuarioConst != MenuOperacoes.SAIR);
			*/
			
			// fim enum
			
			
			
			System.out.println("=================================================");
			System.out.println("        Sistema de Seguradora de Veículos");
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
			
			// se o numero inserido não for válido (isto é, entre 0 e 7)
			if(("01234567").indexOf(operacao) < 0) {
				System.out.println(" ---------------------------------------------------------------------------------");
				System.out.println("| A operação inserida não é válida! Reveja os índices do menu e insira um válido. |");
				System.out.println(" ---------------------------------------------------------------------------------\n");
				esperarEnter(leitor);
				continue; // volta para o início do loop do menu principal
			}	
			
			switch(operacao) {
				case "1": 
					if(cadastrarCliente(leitor, seguradora))
						System.out.println("\nNovo cliente cadastrado na seguradora!");
					else
						System.out.println("\nERRO: Não foi possível realizar o cadastro do cliente. Revise os dados e tente novamente.");
					break;
					
				case "2": 
					if(cadastrarVeiculo(leitor, seguradora))
						System.out.println("\nNovo veículo cadastrado na seguradora!");
					else
						System.out.println("\nERRO: Não foi possível realizar o cadastro do veículo. Revise os dados e tente novamente.");
					break;
				
				case "3": 
					if(removerCliente(leitor, seguradora))
						System.out.println("\nCliente removido da seguradora com sucesso!");
					else
						System.out.println("\nERRO: O cliente informado não estava cadastrado na seguradora. Revise os dados.");
					break;
				
				case "4": 
					listarClientes(leitor, seguradora);
					break;
				
				case "5": 
					if(gerarSinistro(leitor, seguradora))
						System.out.println("\nSinistro cadastrado com sucesso!");
					else
						System.out.println("\nERRO: Algo deu errado no cadastro do sinistro. Revise os dados e certifique-se que o cliente e o veículo envolvidos estão cadastrados na seguradora.");
					break;
					
				case "6": 
					listarSinistros(seguradora);
					break;
					
				case "7": 
					listarSinistrosCliente(leitor, seguradora);
					break;
			}
			
			// confirma ou cancela saída do programa
			if(operacao.equals("0")) {
				limparTela();
				System.out.println("----------------");
				System.out.println("Sair do Programa");
				System.out.println("----------------");
				System.out.println("Aperte [Enter] para confirmar.\nPara cancelar, aperte [C].\n");
				String confirma = leitor.nextLine();
				if(confirma.equals("c") || confirma.equals("C")) {
					operacao = "8"; // colocar operacao != 0 para não sair do do-while
					continue;  // cancela a saída e volta para o início do loop do menu principal
				}
			}
			else
				esperarEnter(leitor);

		}while(!operacao.equals("0"));
		
		System.out.println("Programa encerado!");
	}
	
	/* =============================
	 *  COMANDOS BÁSICOS DO CONSOLE
	 * =============================*/
	
	// cria 50 linhas nulas e cria a ilusão de que a tela do console foi limpa
	public static void limparTela() {
		for (int i = 0; i < 50; ++i) 
			System.out.println();
	}
	
	// programa não prossegue enquanto o usuário não apertar [Enter]
	public static void esperarEnter(Scanner leitor) {  
		
		System.out.println("Aperte [Enter] para prosseguir.");
		leitor.nextLine();
	}
	
	/* ============================================================
	 *  FUNÇÕES PARA LIDAR COM A LEITURA DE INFORMAÇÕES DO CONSOLE
	 * ============================================================*/
	
	// programa fica em loop até que o usuário insira um CNPJ válido
	private static String getCnpjValido(Scanner leitor) {
		boolean valido = false;
		String cnpj;
		do { // continuamente é feito esse bloco de comandos até o usuário inserir um CNPJ válido
			System.out.print("CNPJ: ");
			cnpj = leitor.nextLine();
			valido = ClientePJ.validarCNPJ(cnpj);
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
	private static String getCpfValido(Scanner leitor) {
		String cpf;
		boolean valido = false;
		do { // continuamente é feito esse bloco de comandos até o usuário inserir um CPF válido
			System.out.print("CPF: ");
			cpf = leitor.nextLine();
			valido = ClientePF.validarCPF(cpf);
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
	private static String getTipoClienteValido(Scanner leitor) {
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
	
	/* =======================================
	 *  MÉTODOS/FUNÇÕES DE CADA OPÇÃO DO MENU
	 * =======================================*/
	
	// cria uma seguradora conforme os dados que o usuário inserir e a retorna
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
		// cria seguradora com lista de sinistros e de clientes instanciadas e vazias
		
		System.out.println("\nNova seguradora criada!\nVeja as informações cadastradas:");
		
		// printa as informacoes da seguradora para o usuário revisar
		System.out.println("-------------------------");
		System.out.println(seguradora.toString());
		System.out.println("-------------------------\n");
		
		return seguradora;
	}

	// cria um cliente conforme os dados que o usuário inserir, adiciona na seguradora
	// e retorna true caso tenha dado certo, e false caso contrário
	public static boolean cadastrarCliente(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("-------------------------------------------");
		System.out.println("           1 - Cadastrar cliente");
		System.out.println("-------------------------------------------\n");
		
		System.out.println("Digite as informações do novo cliente.");
		System.out.println("(OBS: Escreva datas no formato dd/mm/aaaa)\n");
		System.out.print("Nome: ");
		String nome = leitor.nextLine();
		System.out.print("Endereco: ");
		String endereco = leitor.nextLine();
		
		String tipo = getTipoClienteValido(leitor);
		
		// formatador de String do formato "dd/MM/yyyy" para objeto LocalDate
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Cliente cliente = null;
		
		// criando cliente físico
		if(tipo.equals("CPF")) {
			String cpf = getCpfValido(leitor);
			
			// programa fica em loop ate que o usuário insira uma data no formato correto
			LocalDate dataLicenca = null;
			do { // continuamente é feito esse bloco de comandos ate o usuario inserir uma data valida
				System.out.print("Data da licença: ");
				String dataLicencaLeitor = leitor.nextLine();
				try { // tenta converter String em LocalDate
					dataLicenca = LocalDate.parse(dataLicencaLeitor, formatter);
				} catch(Exception e) {
					System.out.println(" --------------------------------------------------------------------------------");
					System.out.println("| Data da licença inválida! Tente inserí-la novamente, no formato [dd/mm/yyyy]. |");
					System.out.println(" --------------------------------------------------------------------------------\n");
				}
			} while (dataLicenca == null);
			
			// mesma lógica da dataLicenca
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
			
			// programa fica em loop até que o usuário insira um genero valido
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
			
			System.out.print("Classe econômica: ");
			String classeEconomica = leitor.nextLine();
			
			// cria cliente com lista de veiculos instanciada e vazia
			cliente = new ClientePF(nome, endereco, new LinkedList<Veiculo>(), dataLicenca,
									cpf, dataNascimento, educacao, genero, classeEconomica);
		
		} else if(tipo.equals("CNPJ")){ 
			// criando cliente jurídico
			String cnpj = getCnpjValido(leitor);
			
			// mesma lógica da dataLicenca
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
			
			int qtdeFuncionarios = -1;
			do {
				System.out.print("Quantidade de funcionários: ");
				String qtdeFuncionariosLeitor = leitor.nextLine();
				try {
					qtdeFuncionarios = Integer.parseInt(qtdeFuncionariosLeitor);
					if(qtdeFuncionarios < 0) // número natural
						throw new Exception();
				} catch(Exception e) {
					System.out.println(" --------------------------------------------------------------------------------");
					System.out.println("| Data de fundação inválida! Tente inserí-la novamente, no formato [dd/mm/yyyy]. |");
					System.out.println(" --------------------------------------------------------------------------------\n");
				}
			} while (qtdeFuncionarios == -1);
			
			// cria cliente com lista de veiculos instanciada e vazia
			cliente = new ClientePJ(nome, endereco, new LinkedList<Veiculo>(),
					                cnpj, dataFundacao, qtdeFuncionarios);
		}
		
		// adiciona o cliente criado acima na seguradora
		// e retorna se deu certo
		return seguradora.cadastrarCliente(cliente);
	}
	
	// cria um veiculo conforme os dados que o usuário inserir, adiciona em um cliente da seguradora
	// e retorna true caso tenha dado certo, e false caso contrário (cliente inexistente ou erro na criacao do veiculo)
	public static boolean cadastrarVeiculo(Scanner leitor, Seguradora seguradora) {
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
		
		// programa fica em loop até que o usuário insira um número entre 1886 e 2023
		//																    \ (invencao do carro, sim eu pesquisei...)
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
		
		// pergunta ao usuário o tipo de cliente e seu CPF/CNPJ
		String tipoCliente = getTipoClienteValido(leitor);
		String keyCliente = "";
		if(tipoCliente.equals("CNPJ"))
			keyCliente = getCnpjValido(leitor); 
		else if(tipoCliente.equals("CPF"))
			keyCliente = getCpfValido(leitor);
		
		// resgata cliente dentro da seguradora
		Cliente cliente = seguradora.getClienteByKey(keyCliente);
		if(cliente == null) {
			System.out.print("ERRO: O cliente cujo CPF/CNPJ foi dado não está cadastrado na seguradora.");
			return false;
		}
		
		// cria veiculo com os dados inseridos pelo usuario
		Veiculo veiculo = new Veiculo(placa, marca, modelo, anoFabricacao);
		
		// cadastra veiculo criado no objeto do cliente dono, dentro da seguradora
		// e retorna se deu certo a insercao
		return cliente.adicionarVeiculo(veiculo);		
	}

	// remove cliente e seus sinistros (caso exista) da seguradora
	// retorna true se o cliente for removido e false caso o CPF/CNPJ informado
	// não pertenca a nenhum cliente cadastro na seguradora
	public static boolean removerCliente(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println("           3 - Remover cliente");
		System.out.println("--------------------------------------------");
		
		// pergunta ao usuário o tipo de cliente e seu CPF/CNPJ
		String tipoCliente = getTipoClienteValido(leitor);
		String keyCliente = "";
		if(tipoCliente.equals("CNPJ"))
			keyCliente = getCnpjValido(leitor);
		else if(tipoCliente.equals("CPF"))
			keyCliente = getCpfValido(leitor);
		
		// tenta remover cliente pelo CPF/CNPJ e retorna se deu certo
		return seguradora.removerCliente(keyCliente);
	}
	
	// lista clientes físicos(CPF) ou juridicos (CNPJ) conforme o usuario inserir
	public static void listarClientes(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("-----------------------------------------------------------");
		System.out.println("            4 - Listar clientes da seguradora");
		System.out.println("-----------------------------------------------------------\n");
		
		// nao ha nenhum cliente (fisico ou juridico) cadastrado na seguradora
		if (seguradora.getListaClientes() == null || seguradora.getListaClientes().isEmpty()) {
			System.out.println("Nenhum cliente cadastrado na seguradora. Para cadastrar novos cliente, digite a opção 1 no menu.\n");
			return;
		}
		
		// pergunta o tipo de cliente do qual o usuario deseja a lista
		// (aqui nao usei a funcao getTipoClienteValido() pois a mensagem de pergunta é diferente)
		String tipoCliente = "";
		do{
			System.out.println("Deseja a lista de clientes físicos(CPF) ou jurídicos(CNPJ)?");
			System.out.print("Digite [CPF] ou [CNPJ]: ");
			String tipoLeitor = leitor.nextLine().toUpperCase();
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
		
		// resgata lista de clientes de tipoCliente cadastrados na seguradora
		ArrayList<Cliente> lista = seguradora.listarClientes(tipoCliente);
		
		// nao ha clientes do tipoCliente cadastrados na seguradora
		if(lista == null || lista.isEmpty()) {
			System.out.println("ERRO: Nenhum cliente com ["+tipoCliente+"] gerado na seguradora. Para gerar novos sinistros, digite a opção 5 no menu.\n");
			return;
		}
		
		if(tipoCliente.equals("CPF"))
			System.out.println("\nSegue a lista de clientes físicos cadastrados na seguradora:\n");
		else
			System.out.println("\nSegue a lista de clientes jurídicos cadastrados na seguradora:\n");
		
		// printa lista de clientes de tipoCliente, separando cada cliente com esses hifens
		System.out.println("-------------------------------\n");
		for(Cliente cliente: lista) {
			System.out.println(cliente.toString()+"\n");
			System.out.println("-------------------------------\n");
		}
		System.out.println(); // pula linha
	}
	
	// gera um sinistro na seguradora envolvendo um cliente cadastrado na seguradora e algum 
	// veiculo nele cadastrado. retorna true se o cadastro der certo ou falso caso contrario
	// (cliente nao cadastrado na seguradora ou veiculo nao cadastrado no cliente)
	public static boolean gerarSinistro(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println(" 5 -  Gerar sinistro de um cliente ");
		System.out.println("--------------------------------------------\n");
		
		System.out.println("Digite as informações que envolvem o sinistro.");
		
		// mesma logica da dataLicenca
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
		
		// pergunta ao usuário o tipo de cliente e seu CPF/CNPJ
		String tipoCliente = getTipoClienteValido(leitor);
		String keyCliente = "";
		if(tipoCliente.equals("CNPJ"))
			keyCliente = getCnpjValido(leitor);
		else if(tipoCliente.equals("CPF"))
			keyCliente = getCpfValido(leitor);
		
		System.out.print("Placa do veículo: ");
		String placa = leitor.nextLine();
		
		// gera sinistro na seguradora e retorna se deu certo
		return seguradora.gerarSinistro(placa, keyCliente, data, endereco);
	}

	// lista todos os sinistros gerados na seguradora
	public static void listarSinistros(Seguradora seguradora) {
		limparTela();
		System.out.println("-----------------------------------------------------------");
		System.out.println("            6 - Listar sinistros da seguradora");
		System.out.println("-----------------------------------------------------------\n");
		
		// resgata lista de todos os sinistros da seguradora
		ArrayList<Sinistro> lista = seguradora.listarSinistros();
		
		// nao ha nenhum sinistro gerado na seguradora
		if (lista == null || lista.isEmpty()) {
			System.out.println("ERRO: Nenhum sinistro gerado na seguradora. Para gerar novos sinistros, digite a opção 5 no menu.\n");
			return;
		}
		
		System.out.println("Segue a lista de sinistros registrados na seguradora:\n");
		
		// printa lista de sinistros, separando cada sinistro com esses hifens
		System.out.println("-------------------------------");
		for(Sinistro sinistro: lista) {
			System.out.print(sinistro.toString());
			System.out.println("-------------------------------\n");
		}
	}
	
	// lista todos os sinistros na seguradora envolvendo o cliente inserido
	public static void listarSinistrosCliente(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("-------------------------------------------");
		System.out.println("    7 - Listar sinistros de um cliente");
		System.out.println("-------------------------------------------");
		
		// pergunta ao usuário o tipo de cliente e seu CPF/CNPJ
		String tipoCliente = getTipoClienteValido(leitor);
		String keyCliente = "";
		if(tipoCliente.equals("CNPJ"))
			keyCliente = getCnpjValido(leitor);
		else if(tipoCliente.equals("CPF"))
			keyCliente = getCpfValido(leitor);
		
		// nao ha nenhum sinistro gerado envolvendo o cliente informado ou o cliente nao esta cadastrado na seguradora
		if (!seguradora.visualizarSinistro(keyCliente)) {
			System.out.println("\nERRO: Este cliente não tem nenhum sinistro gerado na seguradora, ou não está cadastrado na seguradora."
					+ "\nVocê pode verificar se o cliente informado está cadastrado na seguradora digitando a opção 4 no menu.\n");
			return;
		}
		
		System.out.println("Segue a lista de sinistros do cliente informado registrados na seguradora:\n");
		
		// printa lista de sinistros, separando cada sinistro com esses hifens
		System.out.println("-------------------------------");
		for(Sinistro sinistro: seguradora.listarSinistrosByKeyCliente(keyCliente)) {
			System.out.print(sinistro.toString());
			System.out.println("-------------------------------\n");
		}
	}
	
}
