package Main;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Locale;
import java.text.NumberFormat;
import Cliente.Cliente;
import Cliente.ClientePF;
import Cliente.ClientePJ;
import Seguradora.Seguradora;
import Sinistro.Sinistro;
import Veiculo.Veiculo;

public class Main {
	
	private static void exibirMenuPrincipal() {
		MenuOperacoes menuOperacoes[] = MenuOperacoes.values();
		System.out.println("=================================================");
		System.out.println("        Sistema de Seguradora de Veículos");
		System.out.println("=================================================");
		System.out.println("		 Menu principal\n");
		for(MenuOperacoes op: menuOperacoes) {
			System.out.println(" "+(op.ordinal()+1) + " - " + op.getDescricao());
		}
	}
	
	/* exibir submenus
	 * se a lista de constantes do submenu for percorrida da mesma forma que o meu externo, a opção Voltar
	 * é printada com a posição que está na lista do enum (9 - Voltar). Por isso, a lista é percorrida 
	 * de forma diferente, tendo i como o inteiro correspondente. Assim, para listar o submenu de cadastros,
	 * por exemplo, vai ser printado "4 - Voltar".
	 */
	private static void exibirSubMenu(MenuOperacoes op) {
		limparTela();
		SubMenuOperacoes[] subMenu = op.getSubmenu();
		System.out.println(" " +(op.ordinal()+1) +") Menu "+ op.getDescricao()+"\n");
		for(int i=0; i<subMenu.length; i++) {
			System.out.println(" "+(i+1) +" - " + subMenu[i].getDescricao());
		}
	}
	
	//ler opções do menu externo
	private static MenuOperacoes lerOpcaoMenuPrincipal() {
		Scanner scanner = new Scanner(System.in);
		int opUsuario;
		MenuOperacoes opUsuarioConst;
		do {
			System.out.print("\nDigite o número da operação que deseja realizar: ");
			opUsuario = scanner.nextInt() - 1;
			// se o numero inserido não for válido
			if(opUsuario < 0 || opUsuario > MenuOperacoes.values().length-1) {
				System.out.println(" ---------------------------------------------------------------------------------");
				System.out.println("| A operação inserida não é válida! Reveja os índices do menu e insira um válido. |");
				System.out.println(" ---------------------------------------------------------------------------------\n");
			}	
		}while(opUsuario < 0 || opUsuario > MenuOperacoes.values().length - 1);
		opUsuarioConst = MenuOperacoes.values()[opUsuario];
		return opUsuarioConst;
	}
	
	//ler opção dos submenus
	private static SubMenuOperacoes lerOpcaoSubMenu(MenuOperacoes op) {
		Scanner scanner = new Scanner(System.in);
		int opUsuario;
		SubMenuOperacoes opUsuarioConst;
		do {
			System.out.print("\nDigite o número da operação que deseja realizar: ");
			opUsuario = scanner.nextInt() - 1;
			// se o numero inserido não for válido
			if(opUsuario < 0 || opUsuario > op.getSubmenu().length - 1) {
				System.out.println(" ---------------------------------------------------------------------------------");
				System.out.println("| A operação inserida não é válida! Reveja os índices do menu e insira um válido. |");
				System.out.println(" ---------------------------------------------------------------------------------\n");
			}
		}while(opUsuario < 0 || opUsuario > op.getSubmenu().length - 1);
		opUsuarioConst = op.getSubmenu()[opUsuario];
		return opUsuarioConst;
	}
	
	//executar opções do menu externo
	private static void executarOpcaoMenuExterno(MenuOperacoes op, Seguradora seguradora) {
		Scanner leitor = new Scanner(System.in);
		switch(op) {
			case CADASTRAR:
			case LISTAR:
			case EXCLUIR:
				executarSubMenu(op, seguradora); // executa em cadastrar, listar e/ou no excluir
				break;
				
			case GERAR_SINISTRO:
				if(gerarSinistro(leitor, seguradora))
					System.out.println("\nSinistro cadastrado com sucesso!");
				else
					System.out.println("\nERRO: Algo deu errado no cadastro do sinistro. Revise os dados e certifique-se que o cliente e o veículo envolvidos estão cadastrados na seguradora.");
				esperarEnter();
				break;
				
			case TRANSFERIR_SEGURO:
				if(transferirSeguro(leitor, seguradora))
					System.out.println("Transferência de seguro feita com sucesso!");
				else
					System.out.println("\nERRO: Algo deu errado na tranferêcia de seguro. Revise os dados e certifique-se que ambos os clientes estão cadastrados na seguradora.");
				esperarEnter();
				break;
				
			case CALCULAR_RECEITA:
				calcularReceita(seguradora);
				esperarEnter();
				break;
				
			case SAIR:
				break;
		}
	}
	
	//executa os submenus: exibição do menu, leitura da opção e execução dos métodos
	private static void executarSubMenu(MenuOperacoes op, Seguradora seguradora) {
		SubMenuOperacoes opSubMenu;
		do {
			exibirSubMenu(op);
			opSubMenu = lerOpcaoSubMenu(op);
			executarOpcaoSubMenu(opSubMenu, seguradora);
		}while(opSubMenu != SubMenuOperacoes.VOLTAR);
	}
	
	private static void executarOpcaoSubMenu(SubMenuOperacoes opSubmenu, Seguradora seguradora) {
		Scanner leitor = new Scanner(System.in);
		
		switch(opSubmenu) {
			case CADASTRAR_CLIENTE:
				if(cadastrarCliente(leitor, seguradora))
					System.out.println("\nNovo cliente cadastrado na seguradora!");
				else
					System.out.println("\nERRO: Não foi possível realizar o cadastro do cliente. Revise os dados e tente novamente.");
				break;
				
			case CADASTRAR_VEICULO:
				if(cadastrarVeiculo(leitor, seguradora))
					System.out.println("\nNovo veículo cadastrado na seguradora!");
				else
					System.out.println("\nERRO: Não foi possível realizar o cadastro do veículo. Revise os dados e tente novamente.");
				break;
				
			case LISTAR_CLIENTES:
				listarClientes(leitor, seguradora);
				break;
				
			case LISTAR_SINISTROS_SEGURADORA:
				listarSinistros(seguradora);
				break;
				
			case LISTAR_SINISTROS_CLIENTE:
				listarSinistrosCliente(leitor, seguradora);
				break;
				
			case LISTAR_VEICULOS_CLIENTE: // FAZER!!!
				listarVeiculosCliente(leitor, seguradora);
				break;
			
			case LISTAR_VEICULOS_SEGURADORA: // FAZER!!!
				listarVeiculosSeguradora(leitor, seguradora);
				break;
				
			case EXCLUIR_CLIENTE:
				if(excluirCliente(leitor, seguradora))
					System.out.println("\nCliente excluído da seguradora com sucesso!");
				else
					System.out.println("\nERRO: O cliente informado não estava cadastrado na seguradora. Revise os dados.");
				break;
				
			case EXCLUIR_VEICULO: // FAZER!!!
				if(excluirVeiculo(leitor, seguradora))
					System.out.println("\nVeículo excluído da seguradora com sucesso!");
				else
					System.out.println("\nERRO: O Veículo informado não estava cadastrado na seguradora. Revise os dados.");
				break;

			case EXCLUIR_SINISTRO: // FAZER!!!
				if(excluirSinistro(leitor, seguradora))
					System.out.println("\nSinistro excluído da seguradora com sucesso!");
				else
					System.out.println("\nERRO: O sinistro informado não estava cadastrado na seguradora. Revise os dados.");
				break;
				
			case VOLTAR:
				break;
		}
		if(opSubmenu != SubMenuOperacoes.VOLTAR)
			esperarEnter();
	}
	
	/* =============================
	 *  COMANDOS BÁSICOS DO CONSOLE
	 * =============================*/
	
	// cria 50 linhas nulas e cria a ilusão de que a tela do console foi limpa
	private static void limparTela() {
		for (int i = 0; i < 50; ++i) 
			System.out.println();
	}
	
	// programa não prossegue enquanto o usuário não apertar [Enter]
	private static void esperarEnter() {  
		Scanner leitor = new Scanner(System.in);
		System.out.println("Aperte [Enter] para prosseguir.");
		leitor.nextLine();
	}
	
	
	/* =======================================
	 *  MÉTODOS/FUNÇÕES DE CADA OPÇÃO DO MENU
	 * =======================================*/
	
	// 1 - CADASTRAR
	
	// cria uma seguradora conforme os dados que o usuário inserir e a retorna
	private static Seguradora criarSeguradora(Scanner leitor) {
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
	private static boolean cadastrarCliente(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("-------------------------------------------");
		System.out.println("           1.1 - Cadastrar cliente");
		System.out.println("-------------------------------------------\n");
		
		System.out.println("Digite as informações do novo cliente.");
		System.out.println("(OBS: Escreva datas no formato dd/mm/aaaa)\n");
		System.out.print("Nome: ");
		String nome = leitor.nextLine();
		System.out.print("Endereco: ");
		String endereco = leitor.nextLine();
		
		String tipo = Validacao.getTipoClienteValido(leitor);
		
		// formatador de String do formato "dd/MM/yyyy" para objeto LocalDate
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Cliente cliente = null;
		
		// criando cliente físico
		if(tipo.equals("CPF")) {
			String cpf = Validacao.getCpfValido(leitor);
			
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
			String cnpj = Validacao.getCnpjValido(leitor);
			
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
	private static boolean cadastrarVeiculo(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("---------------------------------------");
		System.out.println("        1.2 - Cadastrar veículo");
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
		String tipoCliente = Validacao.getTipoClienteValido(leitor);
		String keyCliente = "";
		if(tipoCliente.equals("CNPJ"))
			keyCliente = Validacao.getCnpjValido(leitor); 
		else if(tipoCliente.equals("CPF"))
			keyCliente = Validacao.getCpfValido(leitor);
		
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

	// 2 - LISTAR
	
	// lista clientes físicos(CPF) ou juridicos (CNPJ) conforme o usuario inserir
	private static void listarClientes(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("-----------------------------------------------------------");
		System.out.println("            2.1 - Listar clientes da seguradora");
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
	
	// lista todos os sinistros gerados na seguradora
	private static void listarSinistros(Seguradora seguradora) {
		limparTela();
		System.out.println("-----------------------------------------------------------");
		System.out.println("            2.2 - Listar sinistros da seguradora");
		System.out.println("-----------------------------------------------------------\n");
		
		// resgata lista de todos os sinistros da seguradora
		ArrayList<Sinistro> lista = seguradora.listarSinistros();
		
		// nao ha nenhum sinistro gerado na seguradora
		if (lista == null || lista.isEmpty()) {
			System.out.println("ERRO: Nenhum sinistro gerado na seguradora.\n");
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
	private static void listarSinistrosCliente(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("-------------------------------------------");
		System.out.println("    2.3 - Listar sinistros de um cliente");
		System.out.println("-------------------------------------------");
		
		// pergunta ao usuário o tipo de cliente e seu CPF/CNPJ
		String tipoCliente = Validacao.getTipoClienteValido(leitor);
		String keyCliente = "";
		if(tipoCliente.equals("CNPJ"))
			keyCliente = Validacao.getCnpjValido(leitor);
		else if(tipoCliente.equals("CPF"))
			keyCliente = Validacao.getCpfValido(leitor);
		
		// nao ha nenhum sinistro gerado envolvendo o cliente informado ou o cliente nao esta cadastrado na seguradora
		if (!seguradora.visualizarSinistro(keyCliente)) {
			System.out.println("\nERRO: Este cliente não tem nenhum sinistro registrado na seguradora, ou não está cadastrado na seguradora.");
			return;
		}
		
		System.out.println("Segue a lista de sinistros do cliente informado registrados na seguradora:\n");
		
		// printa lista de sinistros, separando cada sinistro com esses hifens
		System.out.println("-------------------------------\n");
		for(Sinistro sinistro: seguradora.listarSinistrosByKeyCliente(keyCliente)) {
			System.out.print(sinistro.toString());
			System.out.println("-------------------------------\n");
		}
	}
	
	private static void listarVeiculosCliente(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("-------------------------------------------");
		System.out.println("    2.4 - Listar veículos de um cliente");
		System.out.println("-------------------------------------------");
		
		// pergunta ao usuário o tipo de cliente e seu CPF/CNPJ
		String tipoCliente = Validacao.getTipoClienteValido(leitor);
		String keyCliente = "";
		if(tipoCliente.equals("CNPJ"))
			keyCliente = Validacao.getCnpjValido(leitor);
		else if(tipoCliente.equals("CPF"))
			keyCliente = Validacao.getCpfValido(leitor);
		
		Cliente cliente = seguradora.getClienteByKey(keyCliente);
		if(cliente == null) {
			System.out.println("\nERRO: Este cliente não está registrado na seguradora.");
			return;
		}
		
		LinkedList<Veiculo> veiculosCliente = cliente.getListaVeiculos();
		if (veiculosCliente == null || veiculosCliente.isEmpty()) {
			System.out.println("Este cliente não tem nenhum veículo cadastrado.");
			return;
		}
		
		System.out.println("Segue a lista de veículos do cliente informado registrados na seguradora:\n");
		
		// printa lista de veiculos, separando cada veiculos com esses hifens
		System.out.println("-------------------------------\n");
		for(Veiculo veiculo: veiculosCliente) {
			System.out.print(veiculo.toString());
			System.out.println("\n\n-------------------------------\n");
		}
	}

	private static void listarVeiculosSeguradora(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("-------------------------------------------");
		System.out.println("    2.5 - Listar veículos de um cliente");
		System.out.println("-------------------------------------------");
		
		ArrayList<Veiculo> veiculosSeguradora = seguradora.getVeiculos();
		if (veiculosSeguradora == null || veiculosSeguradora.isEmpty()) {
			System.out.println("A seguradora não tem nenhum veículo cadastrado.");
			return;
		}
		
		System.out.println("\nSegue a lista de veículos registrados na seguradora:\n");
		
		// printa lista de veiculos, separando cada veiculos com esses hifens
		System.out.println("-------------------------------\n");
		for(Veiculo veiculo: veiculosSeguradora) {
			System.out.print(veiculo.toString());
			System.out.println("\n\n-------------------------------\n");
		}
	}
	
	// 3 - EXCLUIR 
	
	// remove cliente e seus sinistros (caso exista) da seguradora
	// retorna true se o cliente for removido e false caso o CPF/CNPJ informado
	// não pertenca a nenhum cliente cadastro na seguradora
	private static boolean excluirCliente(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println("           3.1 - Remover cliente");
		System.out.println("--------------------------------------------");
		
		// pergunta ao usuário o tipo de cliente e seu CPF/CNPJ
		String tipoCliente = Validacao.getTipoClienteValido(leitor);
		String keyCliente = "";
		if(tipoCliente.equals("CNPJ"))
			keyCliente = Validacao.getCnpjValido(leitor);
		else if(tipoCliente.equals("CPF"))
			keyCliente = Validacao.getCpfValido(leitor);
		
		// tenta remover cliente pelo CPF/CNPJ e retorna se deu certo
		return seguradora.removerCliente(keyCliente);
	}
	
	private static boolean excluirVeiculo(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println("           3.2 - Excluir veiculo");
		System.out.println("--------------------------------------------");
		
		System.out.println("Digite as informações do veículo a ser excluído.");
		// pergunta ao usuário o tipo de cliente e seu CPF/CNPJ
		String tipoCliente = Validacao.getTipoClienteValido(leitor);
		String keyCliente = "";
		if(tipoCliente.equals("CNPJ"))
			keyCliente = Validacao.getCnpjValido(leitor);
		else if(tipoCliente.equals("CPF"))
			keyCliente = Validacao.getCpfValido(leitor);
		
		Cliente cliente = seguradora.getClienteByKey(keyCliente);
		if(cliente == null) {
			System.out.print("\nERRO: O cliente inserido não está cadastrado na seguradora.");
			return false;
		}
		System.out.print("Placa do veículo: ");
		String placa = leitor.nextLine();
		
		// tenta remover cliente pelo CPF/CNPJ e retorna se deu certo
		return cliente.removerVeiculo(placa);
	}
	
	private static boolean excluirSinistro(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println("           3.3 - Excluir sinistro");
		System.out.println("--------------------------------------------");
		
		System.out.println("Digite o ID do sinistro a ser excluído.");
		
		int id = -1;
		do { // continuamente é feito esse bloco de comandos até o usuário inserir um id valido
			System.out.print("Ano de fabricação: ");
			try {					
				String idLeitor = leitor.nextLine();
				id = Integer.parseInt(idLeitor); // tenta converter a String para int
				if(id < 0)
					throw new Exception(); // gera excecao caso o ano esteja fora do intervalo dado
			} catch(Exception e) { // ano invalido
				id = -1; // zera variavel para continuar em loop
				System.out.println(" ------------------------------------------------------------");
				System.out.println("| ID do sinistro inválido! Tente inserir o número novamente. |");
				System.out.println(" ------------------------------------------------------------\n");
			}
		}while(id == -1);
		
		// tenta remover sinistro pelo CPF/CNPJ e retorna se deu certo
		return seguradora.removerSinistro(id);
	}
	
	// 4 - GERAR SINISTRO
	
	// gera um sinistro na seguradora envolvendo um cliente cadastrado na seguradora e algum 
	// veiculo nele cadastrado. retorna true se o cadastro der certo ou falso caso contrario
	// (cliente nao cadastrado na seguradora ou veiculo nao cadastrado no cliente)
	private static boolean gerarSinistro(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println("           4 -  Gerar sinistro ");
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
		String tipoCliente = Validacao.getTipoClienteValido(leitor);
		String keyCliente = "";
		if(tipoCliente.equals("CNPJ"))
			keyCliente = Validacao.getCnpjValido(leitor);
		else if(tipoCliente.equals("CPF"))
			keyCliente = Validacao.getCpfValido(leitor);
		
		System.out.print("Placa do veículo: ");
		String placa = leitor.nextLine();
		
		// gera sinistro na seguradora e retorna se deu certo
		return seguradora.gerarSinistro(placa, keyCliente, data, endereco);
	}

	// 5 - TRANSFERIR SEGURO
	
	private static boolean transferirSeguro(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println("           5 -  Tranferir seguro ");
		System.out.println("--------------------------------------------\n");
		
		System.out.println("Digite o CPF/CNPJ do cliente cujo seguro será transferido:");
		
		// pergunta ao usuário o tipo de cliente e seu CPF/CNPJ
		String tipoCliente = Validacao.getTipoClienteValido(leitor);
		String clienteFonte = "";
		if(tipoCliente.equals("CNPJ"))
			clienteFonte = Validacao.getCnpjValido(leitor);
		else if(tipoCliente.equals("CPF"))
			clienteFonte = Validacao.getCpfValido(leitor);
		
		System.out.println("Digite o CPF/CNPJ do cliente que receberá o seguro:");
		
		tipoCliente = Validacao.getTipoClienteValido(leitor);
		String clienteDestino = "";
		if(tipoCliente.equals("CNPJ"))
			clienteDestino = Validacao.getCnpjValido(leitor);
		else if(tipoCliente.equals("CPF"))
			clienteDestino = Validacao.getCpfValido(leitor);
		
		// transfere o seguro do clienteFonte para o clienteDestino e retorna se deu certo
		return seguradora.transferirSeguro(clienteFonte, clienteDestino);
	}
	
	// 6 - CALCULAR RECEITA
	
	private static void calcularReceita(Seguradora seguradora) {
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println("      6 -  Cacular receita da seguradora ");
		System.out.println("--------------------------------------------\n");
		
		Locale localBrasil = new Locale("pt", "BR");
		String receita = NumberFormat.getCurrencyInstance(localBrasil).format(seguradora.calcularReceita());
		
		System.out.println("A atual receita da seguradora é "+ receita+"\n");
	}
	
	
	public static void main(String [] args){ 
		
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
		
		seguradora.gerarSinistro("MSM-8271", "79.896.457/0001-86", dataS, "RUA ACIDENTE");
		
		//*/
		/* ====================
		 *  FIM DADOS DE TESTE
		 * ==================== */
		
		esperarEnter();
		
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
		
		MenuOperacoes op;
		do {
			limparTela();
			exibirMenuPrincipal();
			op = lerOpcaoMenuPrincipal();
			executarOpcaoMenuExterno(op, seguradora);
		}while(op != MenuOperacoes.SAIR);
		
		System.out.println("Programa encerado!");
	}
	
}
