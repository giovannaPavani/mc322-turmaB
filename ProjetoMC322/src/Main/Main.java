package Main;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Locale;
import java.text.NumberFormat;
import Cliente.Cliente;
import Cliente.ClientePF;
import Cliente.ClientePJ;
import Condutor.Condutor;
import Frota.Frota;
import Seguradora.Seguradora;
import Seguro.Seguro;
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
			// i+1 para começar em 1, não em 0
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
			opUsuario = scanner.nextInt() - 1; // -1 pois o usuário está vendo os indices somados a 1 (como dito em exibirMenuPrincipal())
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
			opUsuario = scanner.nextInt() - 1; // -1 pois o usuário está vendo os indices somados a 1 (como dito em exibirMenuPrincipal())
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
				
			case GERAR_SEGURO:
				if(gerarSeguro(leitor, seguradora))
					System.out.println("\nSeguro gerado com sucesso!\n");
				else
					System.out.println("\nERRO: Algo deu errado no cadastro do seguro. Revise os dados e certifique-se que o cliente e o veículo/frota envolvidos estão cadastrados na seguradora.\n");
				esperarEnter();
				break;
				
			case CANCELAR_SEGURO:
				if(cancelarSeguro(leitor, seguradora))
					System.out.println("\nSeguro cancelado com sucesso!\n");
				else
					System.out.println("\nERRO: Algo deu errado no cancelamento do seguro. Revise os dados.");
				esperarEnter();
				break;
			
			case GERAR_SINISTRO:
				if(gerarSinistro(leitor, seguradora))
					System.out.println("\nSinistro gerado com sucesso!\n");
				else
					System.out.println("\nERRO: Algo deu errado no cadastro do sinistro. Revise os dados e certifique-se que o cliente e o veículo envolvidos estão cadastrados na seguradora.\n");
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
		
			// CADASTRAR
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
			
			case CADASTRAR_FROTA:
				if(cadastrarFrota(leitor, seguradora))
					System.out.println("\nNova frota cadastrada na seguradora!");
				else
					System.out.println("\nERRO: Não foi possível realizar o cadastro da frota. Revise os dados e tente novamente.");
				break;
				
			case CADASTRAR_CONDUTOR:
				if(cadastrarCondutor(leitor, seguradora))
					System.out.println("\nNovo condutor cadastrado na seguradora!");
				else
					System.out.println("\nERRO: Não foi possível realizar o cadastro do condutor. Revise os dados e tente novamente.");
				break;
			
			// LISTAR
			case LISTAR_CLIENTES:
				listarClientes(leitor, seguradora);
				break;
				
			case LISTAR_SINISTROS_SEGURADORA:
				listarSinistrosSeguradora(seguradora);
				break;
				
			case LISTAR_SINISTROS_CLIENTE:
				listarSinistrosCliente(leitor, seguradora);
				break;
			
			case LISTAR_VEICULOS_SEGURADORA:
				listarVeiculosSeguradora(leitor, seguradora);
				break;
				
			case LISTAR_VEICULOS_CLIENTE:
				listarVeiculosCliente(leitor, seguradora);
				break;
			
			case LISTAR_SEGUROS_SEGURADORA:
				listarSegurosSeguradora(seguradora);
				break;
				
			case LISTAR_SEGUROS_CLIENTE:
				listarSegurosCliente(leitor, seguradora);
				break;
			
			// EXCLUIR
			case EXCLUIR_CLIENTE:
				if(excluirCliente(leitor, seguradora))
					System.out.println("\nCliente excluído da seguradora com sucesso!");
				else
					System.out.println("\nERRO: O cliente informado não estava cadastrado na seguradora. Revise os dados.");
				break;
				
			case EXCLUIR_VEICULO:
				if(excluirVeiculo(leitor, seguradora))
					System.out.println("\nVeículo excluído da seguradora com sucesso!");
				break;

			case EXCLUIR_SINISTRO:
				if(excluirSinistro(leitor, seguradora))
					System.out.println("\nSinistro excluído da seguradora com sucesso!");
				else
					System.out.println("\nERRO: O sinistro informado não estava reigstrado na seguradora. Revise os dados.");
				break;
			
			case EXCLUIR_CONDUTOR:
				if(excluirCondutor(leitor, seguradora))
					System.out.println("\nCondutor excluído da seguradora com sucesso!");
				else
					System.out.println("\nERRO: O condutor informado não estava cadastrado na seguradora. Revise os dados.");
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
	private static Seguradora criarSeguradora() {
		limparTela();
		System.out.println("----------------------");
		System.out.println("   Criar seguradora   ");
		System.out.println("----------------------\n");
		System.out.println("Digite as informações da nova seguradora.\n");
		System.out.println("(OBS: Escreva o nome sem caracteres especiais (acentos e 'ç').\n");
		
		Scanner leitor = new Scanner(System.in);
		
		String cnpj = Validacao.getCnpjValido(leitor);
		String nome = Validacao.validaNome(leitor);
		System.out.print("Telefone: ");
		String telefone = leitor.nextLine();
		System.out.print("Endereco: ");
		String endereco = leitor.nextLine();
		System.out.print("Email: ");
		String email = leitor.nextLine();
		
		Seguradora seguradora = new Seguradora(cnpj, nome, telefone, endereco, email,
                							   new ArrayList<Cliente>(), new ArrayList<Seguro>());
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
		System.out.println("(OBS: Escreva o nome sem caracteres especiais (acentos e 'ç') e as datas no formato dd/mm/aaaa).\n");
		
		String nome = Validacao.validaNome(leitor);
		System.out.print("Endereco: ");
		String endereco = leitor.nextLine();
		System.out.print("Telefone: ");
		String telefone = leitor.nextLine();
		System.out.print("Email: ");
		String email = leitor.nextLine();
		
		String tipo = Validacao.getTipoClienteValido(leitor);

		Cliente cliente = null;
		if(tipo.equals("CPF")) { 
			// criando cliente físico
			String cpf = Validacao.getCpfValido(leitor);
			String genero = Validacao.getGeneroValido(leitor);
			System.out.print("Educação: ");
			String educacao = leitor.nextLine();
			LocalDate dataNascimento = Validacao.getDataValida(leitor, "Data de nascimento");
			
			// cria cliente com lista de veiculos instanciada e vazia
			cliente = new ClientePF(nome, endereco, telefone, email, cpf, genero, educacao,
									dataNascimento, new LinkedList<Veiculo>());
		
		} else if(tipo.equals("CNPJ")){ 
			// criando cliente jurídico
			String cnpj = Validacao.getCnpjValido(leitor);
			LocalDate dataFundacao = Validacao.getDataValida(leitor, "Data de fundação");
			int qtdeFuncionarios = Validacao.getQtdValida(leitor, "Quantidade de funcionários");
			
			// cria cliente com lista de veiculos instanciada e vazia
			cliente = new ClientePJ(nome, endereco, telefone, email, cnpj, dataFundacao,
									new LinkedList<Frota>() , qtdeFuncionarios);
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
		int anoFabricacao = Validacao.getAnoValido(leitor);
		
		// pergunta ao usuário o tipo de cliente e seu CPF/CNPJ
		String tipoCliente = Validacao.getTipoClienteValido(leitor);
		String keyCliente = Validacao.getKeyClienteValida(leitor, tipoCliente);
		
		// resgata cliente dentro da seguradora
		Cliente cliente = seguradora.getClienteByKey(keyCliente);
		if(cliente == null) {
			System.out.print("ERRO: O cliente cujo CPF/CNPJ foi dado não está cadastrado na seguradora.");
			return false;
		}
		
		String code = null;
		if(tipoCliente.equals("CNPJ")) {
			System.out.print("Code da frota: ");
			code = leitor.nextLine();
		}
		
		// cria veiculo com os dados inseridos pelo usuario
		Veiculo veiculo = new Veiculo(placa, marca, modelo, anoFabricacao);
		
		// cadastra veiculo criado no objeto do cliente dono, dentro da seguradora
		// e retorna se deu certo a insercao
		return seguradora.cadastrarVeiculo(keyCliente, veiculo, code);	
	}
	
	// cria uma frota com veiculos conforme os dados que o usuário inserir, adiciona em um cliente PJ da seguradora
	// e retorna true caso tenha dado certo, e false caso contrário (cliente inexistente ou erro na criacao da frota)
	private static boolean cadastrarFrota(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("---------------------------------------");
		System.out.println("        1.3 - Cadastrar frota");
		System.out.println("---------------------------------------\n");
		
		System.out.println("Digite as informações do novo frota.");
		
		int qtd = Validacao.getQtdValida(leitor, "Quantidade de veículos da nova frota");
		LinkedList<Veiculo> listaVeiculos = new LinkedList<Veiculo>();
		
		for(int i=0; i<qtd; i++) {
			System.out.print("\nInformações do "+i+"º veículo: \n");
			System.out.print("Placa: ");
			String placa = leitor.nextLine();
			System.out.print("Marca: ");
			String marca = leitor.nextLine();
			System.out.print("Modelo: ");
			String modelo = leitor.nextLine();
			int anoFabricacao = Validacao.getAnoValido(leitor);
			
			// cria veiculo com os dados inseridos pelo usuario
			Veiculo veiculo = new Veiculo(placa, marca, modelo, anoFabricacao);
			// o add na lista de veiculos da nova frota
			listaVeiculos.add(veiculo);
		}
		
		// pergunta ao usuário o tipo de cliente seu CNPJ
		String cnpj = Validacao.getCnpjValido(leitor);
		
		// resgata cliente dentro da seguradora
		Cliente cliente = seguradora.getClienteByKey(cnpj);
		if(cliente == null) {
			System.out.print("ERRO: O cliente cujoCNPJ foi dado não está cadastrado na seguradora.");
			return false;
		}
		
		Frota frota = new Frota(listaVeiculos);
		
		// cadastra frota criada no objeto do cliente dono, dentro da seguradora
		// e retorna se deu certo a insercao
		return seguradora.cadastrarFrota(cnpj, frota);	
	}
	
	// cria um condutor conforme os dados que o usuário inserir, adiciona em um seguro da seguradora
	// e retorna true caso tenha dado certo, e false caso contrário
	private static boolean cadastrarCondutor(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("-------------------------------------------");
		System.out.println("           1.4 - Cadastrar condutor");
		System.out.println("-------------------------------------------\n");
		
		System.out.println("Digite as informações do novo condutor.");
		System.out.println("(OBS: Escreva o nome sem caracteres especiais (acentos e 'ç') e as datas no formato dd/mm/aaaa).\n");
		
		String cpf = Validacao.getCpfValido(leitor);
		String nome = Validacao.validaNome(leitor);
		System.out.print("Telefone: ");
		String telefone = leitor.nextLine();
		System.out.print("Endereco: ");
		String endereco = leitor.nextLine();
		System.out.print("Email: ");
		String email = leitor.nextLine();
		LocalDate dataNascimento = Validacao.getDataValida(leitor, "Data de nascimento");

		Condutor condutor = new Condutor(cpf, nome, telefone, endereco, email,
								dataNascimento, new LinkedList<Sinistro>());
		
		System.out.println("Digite o ID do seguro do novo condutor:");
		int idSeguro = Validacao.getIDValido(leitor);
		
		// adiciona o cliente criado acima na seguradora
		// e retorna se deu certo
		return seguradora.autorizarCondutor(idSeguro, condutor); // true: condutor cadastrado, false: condutor ja autorizado ou seguro nao cadastrado
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
			System.out.println("ERRO: Nenhum cliente cadastrado na seguradora.\n");
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
			System.out.println("ERRO: Nenhum cliente com ["+tipoCliente+"] cadastrado na seguradora.\n");
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
	private static void listarSinistrosSeguradora(Seguradora seguradora) {
		limparTela();
		System.out.println("-----------------------------------------------------------");
		System.out.println("            2.2 - Listar sinistros da seguradora");
		System.out.println("-----------------------------------------------------------\n");
		
		// resgata lista de todos os sinistros da seguradora
		ArrayList<Sinistro> sinistrosSeguradora = seguradora.getSinistros();
		
		// nao ha nenhum sinistro gerado na seguradora
		if (sinistrosSeguradora == null || sinistrosSeguradora.isEmpty()) {
			System.out.println("ERRO: Nenhum sinistro gerado na seguradora.\n");
			return;
		}
		
		System.out.println("Segue a lista de sinistros registrados na seguradora:\n");
		
		// printa lista de sinistros, separando cada sinistro com esses hifens
		System.out.println("-------------------------------\n");
		for(Sinistro sinistro: sinistrosSeguradora) {
			System.out.print(sinistro.toString());
			System.out.println("-------------------------------\n");
		}
	}
	
	// lista todos os sinistros na seguradora envolvendo o cliente inserido
	private static void listarSinistrosCliente(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println("    2.3 - Listar sinistros de um cliente");
		System.out.println("--------------------------------------------\n");
		
		// pergunta ao usuário o tipo de cliente e seu CPF/CNPJ
		String tipoCliente = Validacao.getTipoClienteValido(leitor);
		String keyCliente = Validacao.getKeyClienteValida(leitor, tipoCliente);
		
		ArrayList<Sinistro> sinistrosCliente = seguradora.getSinistrosPorCliente(keyCliente);
		
		// nao ha nenhum sinistro gerado envolvendo o cliente informado ou o cliente nao esta cadastrado na seguradora
		if (sinistrosCliente == null || sinistrosCliente.isEmpty()) {
			System.out.println("ERRO: Este cliente não tem nenhum sinistro registrado na seguradora, ou não está cadastrado na seguradora.\n");
			return;
		}
		
		System.out.println("Segue a lista de sinistros do cliente informado registrados na seguradora:\n");
		
		// printa lista de sinistros, separando cada sinistro com esses hifens
		System.out.println("-------------------------------\n");
		for(Sinistro sinistro: sinistrosCliente) {
			System.out.print(sinistro.toString());
			System.out.println("-------------------------------\n");
		}
	}
	
	// lista todos os veiculos cadastrados na seguradora
	private static void listarVeiculosSeguradora(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("-------------------------------------------");
		System.out.println("    2.4 - Listar veículos de um cliente");
		System.out.println("-------------------------------------------\n");
		
		ArrayList<Veiculo> veiculosSeguradora = seguradora.getVeiculos();
		if (veiculosSeguradora == null || veiculosSeguradora.isEmpty()) {
			System.out.println("ERRO: A seguradora não tem nenhum veículo cadastrado.\n");
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
	
	// lista todos os sinistros de um cliente informado que esteja cadastrado na seguradora
	private static void listarVeiculosCliente(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("-------------------------------------------");
		System.out.println("    2.5 - Listar veículos de um cliente");
		System.out.println("-------------------------------------------\n");
		
		// pergunta ao usuário o tipo de cliente e seu CPF/CNPJ
		String tipoCliente = Validacao.getTipoClienteValido(leitor);
		String keyCliente = Validacao.getKeyClienteValida(leitor, tipoCliente);
		
		Cliente cliente = seguradora.getClienteByKey(keyCliente);
		if(cliente == null) {
			System.out.println("ERRO: Este cliente não está registrado na seguradora.\n");
			return;
		}
		
		LinkedList<Veiculo> veiculosCliente = cliente.getListaVeiculos();
		if (veiculosCliente == null || veiculosCliente.isEmpty()) {
			System.out.println("\nEste cliente não tem nenhum veículo cadastrado.");
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
	
	// lista todos os seguros gerados na seguradora
	private static void listarSegurosSeguradora(Seguradora seguradora) {
		limparTela();
		System.out.println("-----------------------------------------------------------");
		System.out.println("            2.6 - Listar seguros da seguradora");
		System.out.println("-----------------------------------------------------------\n");
		
		// resgata lista de todos os seguros da seguradora
		ArrayList<Seguro> segurosSeguradora = seguradora.getListaSeguros();
		
		// nao ha nenhum seguro gerado na seguradora
		if (segurosSeguradora == null || segurosSeguradora.isEmpty()) {
			System.out.println("ERRO: Nenhum seguro gerado na seguradora.\n");
			return;
		}
		
		System.out.println("Segue a lista de seguros registrados na seguradora:\n");
		
		// printa lista de seguros, separando cada seguro com esses hifens
		System.out.println("-------------------------------\n");
		for(Seguro seguro: segurosSeguradora) {
			System.out.print(seguro.toString());
			System.out.println("-------------------------------\n");
		}
	}

	// lista todos os seguros na seguradora envolvendo o cliente inserido
	private static void listarSegurosCliente(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println("    2.7 - Listar seguros de um cliente");
		System.out.println("--------------------------------------------\n");
		
		// pergunta ao usuário o tipo de cliente e seu CPF/CNPJ
		String tipoCliente = Validacao.getTipoClienteValido(leitor);
		String keyCliente = Validacao.getKeyClienteValida(leitor, tipoCliente);
		
		ArrayList<Seguro> segurosCliente = seguradora.getSegurosPorCliente(keyCliente);
		
		// nao ha nenhum seguro gerado envolvendo o cliente informado ou o cliente nao esta cadastrado na seguradora
		if (segurosCliente == null || segurosCliente.isEmpty()) {
			System.out.println("ERRO: Este cliente não tem nenhum seguro registrado na seguradora, ou não está cadastrado na seguradora.");
			return;
		}
		
		System.out.println("Segue a lista de seguros do cliente informado registrados na seguradora:\n");
		
		// printa lista de seguros, separando cada seguro com esses hifens
		System.out.println("-------------------------------\n");
		for(Seguro seguro: segurosCliente) {
			System.out.print(seguro.toString());
			System.out.println("-------------------------------\n");
		}
	}
		
	// 3 - EXCLUIR 
	
	// exclui cliente e seus seguros (caso exista) da seguradora
	// retorna true se o cliente for excluido e false caso o CPF/CNPJ informado
	// não pertenca a nenhum cliente cadastro na seguradora
	private static boolean excluirCliente(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println("           3.1 - Excluir cliente");
		System.out.println("--------------------------------------------");
		
		// pergunta ao usuário o tipo de cliente e seu CPF/CNPJ
		String tipoCliente = Validacao.getTipoClienteValido(leitor);
		String keyCliente = Validacao.getKeyClienteValida(leitor, tipoCliente);
		
		// tenta remover cliente pelo CPF/CNPJ e retorna se deu certo
		return seguradora.removerCliente(keyCliente);
	}
	
	// exclui veiculo de um cliente, ambos informados pelo usuário
	// retorna true se deu certo e false caso o carro nao exista
	private static boolean excluirVeiculo(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println("           3.2 - Excluir veiculo");
		System.out.println("--------------------------------------------");
		
		System.out.println("Digite as informações do veículo a ser excluído.");
		// pergunta ao usuário o tipo de cliente e seu CPF/CNPJ
		String tipoCliente = Validacao.getTipoClienteValido(leitor);
		String keyCliente = Validacao.getKeyClienteValida(leitor, tipoCliente);
		
		Cliente cliente = seguradora.getClienteByKey(keyCliente);
		if(cliente == null) {
			System.out.print("\nERRO: O cliente inserido não está cadastrado na seguradora.\n");
			return true; // a exclusao nao deu certo, mas o erro foi lidado, por isso retorna true
		}
		System.out.print("Placa do veículo: ");
		String placa = leitor.nextLine();
		
		// tenta remover cliente pelo CPF/CNPJ e retorna se deu certo
		boolean remove = seguradora.removerVeiculo(keyCliente, placa);
		
		if(!remove)
			System.out.println("\nERRO: O Veículo informado não estava cadastrado na seguradora ou a placa do veículo não estava cadastrada no cliente informado. Revise os dados.\n");
		
		return remove;
	}
	
	// exclui sinistro de um cliente da seguradora
	// retorna true se deu certo e false caso o sinistro nao exista
	private static boolean excluirSinistro(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println("           3.3 - Excluir sinistro");
		System.out.println("--------------------------------------------");
		
		System.out.println("Digite o ID do sinistro a ser excluído.");
		
		int id = Validacao.getIDValido(leitor);
		
		// tenta remover sinistro pelo CPF/CNPJ e retorna se deu certo
		return seguradora.removerSinistro(id);
	}
	
	// exclui condutor e seus sinistros (caso exista) de um seguro da seguradora
	// retorna true se o condutor for excluido e false caso o seguro com id fornecido
	// nao exista ou o CPF informado não pertenca a nenhum condutor cadastro no seguro
	private static boolean excluirCondutor(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println("           3.4 - Excluir condutor");
		System.out.println("--------------------------------------------");
		
		System.out.println("Digite o ID do seguro do condutor a ser excluído.");
		int idSeguro = Validacao.getIDValido(leitor);
		// pergunta ao usuário o CPF do condutor
		System.out.println("Digite o CPF do condutor a ser excluído.");
		String cpf = Validacao.getCpfValido(leitor);
		
		// tenta remover cliente pelo CPF/CNPJ e retorna se deu certo
		return seguradora.desautorizarCondutor(idSeguro, cpf);
	}
	
	// 4 - GERAR SEGURO
	
	// gera um novo seguro para o cliente ja cadastrado com as informações inseridas
	// retorna true se deu certo e false caso o cliente com o CPF/CNPJ inserido nao 
	// exista ou alguma informaçao importante não é inserida
	private static boolean gerarSeguro(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println("           4 -  Gerar seguro ");
		System.out.println("--------------------------------------------\n");
		
		System.out.println("Digite as informações do novo seguro.");
		System.out.println("(OBS: Escreva as datas no formato dd/mm/aaaa).\n");
		//cnpj codeFrota dataInicio, dataFim, null, null, 
		// cpf placaVeiculo
		
		String tipo = Validacao.getTipoClienteValido(leitor);

		if(tipo.equals("CPF")) {
			// gerando seguro de cliente físico
			String cpf = Validacao.getCpfValido(leitor);
			LocalDate dataFim = Validacao.getDataValida(leitor, "Data fim");
			System.out.print("Placa do veículo: ");
			String placa = leitor.nextLine();
			
			return seguradora.gerarSeguro(cpf, dataFim, placa, null);
		
		} else if(tipo.equals("CNPJ")){ 
			// gerando seguro de  cliente jurídico
			
			String cnpj = Validacao.getCnpjValido(leitor);
			LocalDate dataFim = Validacao.getDataValida(leitor, "Data fim");
			System.out.print("Code da frota: ");
			String code = leitor.nextLine();
			
			return seguradora.gerarSeguro(cnpj, dataFim, null, code);
		}
		
		return false;
	}
	
	// 5 - CANCELAR SEGURO
	
	// remove o seguro cujo id é informado da seguradora, caso ele exista
	// retorna true se deu certo e false caso o seguro nao exista
	private static boolean cancelarSeguro(Scanner leitor, Seguradora seguradora){
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println("           5 -  Cancelar seguro ");
		System.out.println("--------------------------------------------\n");
		
		System.out.println("Digite o ID do seguro que deseja cancelar.");
		int id = Validacao.getIDValido(leitor);
		
		// gera sinistro na seguradora e retorna se deu certo
		return seguradora.cancelarSeguro(id);
	}
	
	// 6 - GERAR SINISTRO
	
	// gera um sinistro na seguradora envolvendo um cliente cadastrado na seguradora e algum 
	// veiculo nele cadastrado. retorna true se o cadastro der certo ou falso caso contrario
	// (cliente nao cadastrado na seguradora, veiculo nao cadastrado no cliente ou condutor nao
	// cadastrado no seguro do cliente e veiculo informados)
	private static boolean gerarSinistro(Scanner leitor, Seguradora seguradora) {
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println("           6 -  Gerar sinistro ");
		System.out.println("--------------------------------------------\n");
		
		System.out.println("Digite as informações que envolvem o sinistro.");
		
		LocalDate data = Validacao.getDataValida(leitor, "Data do sinistro");
		System.out.print("Endereço do sinistro: ");
		String endereco = leitor.nextLine();
		System.out.print("Placa do veículo: ");
		String placa = leitor.nextLine();
		
		// pergunta ao usuário o tipo de cliente e seu CPF/CNPJ
		String tipoCliente = Validacao.getTipoClienteValido(leitor);
		String keyCliente = Validacao.getKeyClienteValida(leitor, tipoCliente);
		
		// pergunta ao usuário o CPF do condutor
		System.out.println("Digite o CPF do condutor envolvido no sinistro.");
		String cpfCondutor = Validacao.getCpfValido(leitor);
		
		// gera sinistro na seguradora e retorna se deu certo
		return seguradora.gerarSinistro(placa, keyCliente, data, endereco, cpfCondutor);
	}
	
	// 7 - CALCULAR RECEITA
	
	// calcula a receita da seguradora
	private static void calcularReceita(Seguradora seguradora) {
		limparTela();
		System.out.println("--------------------------------------------");
		System.out.println("      7 -  Cacular receita da seguradora ");
		System.out.println("--------------------------------------------\n");
		
		// formata valor da receita em uma String no formato de moeda brasileira
		Locale localBrasil = new Locale("pt", "BR");
		String receita = NumberFormat.getCurrencyInstance(localBrasil).format(seguradora.calcularReceita());
		
		System.out.println("A atual receita da seguradora é "+ receita+"\n");
	}

	/* ==========
	 *    MAIN
	 * ==========*/
	
	public static void main(String [] args){ 
		
		// Apenas uma seguradora é criada e será nela que todas as operações serão feitas
	    Seguradora seguradora;
	    
	    System.out.println("=================================================");
		System.out.println("        Sistema de Seguradora de Veıculos");
		System.out.println("=================================================\n");
		
		System.out.println("Inicie suas operações carregando os arquivos\n");
		
		esperarEnter();

		// cria seguradora apenas com cnpj, nome, telefone, endereço e email (são arbitrários)
	    seguradora = new Seguradora("22542453000182", "Azul", "1932770378", "Av 1, 89", "azul@g.com",
		                    		new ArrayList<Cliente>(), new ArrayList<Seguro>());
	    
	    // setta informações relevantes da seguradora registradas nos arquivos
	    seguradora.lerDados();
	
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
		
		// grava todas as alterações feitas durante a execução nos arquivos .csv
		seguradora.gravarDados();
		
		System.out.println("\nPrograma encerado!");
	}
}
