package Main;

// define as constantes do menu principal
// (essa classe só serve para acessar as constantes)
public enum MenuOperacoes {
	
	// constantes das opcoes do menu principal
	CADASTRAR("Cadastrar", new SubMenuOperacoes[] {
			SubMenuOperacoes.CADASTRAR_CLIENTE,
			SubMenuOperacoes.CADASTRAR_VEICULO,
			SubMenuOperacoes.CADASTRAR_CONDUTOR,
			SubMenuOperacoes.VOLTAR
	}),
	
	LISTAR("Listar", new SubMenuOperacoes[] {
			SubMenuOperacoes.LISTAR_CLIENTES,
			SubMenuOperacoes.LISTAR_SINISTROS_SEGURADORA,
			SubMenuOperacoes.LISTAR_SINISTROS_CLIENTE,
			SubMenuOperacoes.LISTAR_VEICULOS_SEGURADORA,
			SubMenuOperacoes.LISTAR_VEICULOS_CLIENTE,
			SubMenuOperacoes.LISTAR_SEGUROS_SEGURADORA,
			SubMenuOperacoes.LISTAR_SEGUROS_CLIENTE,
			SubMenuOperacoes.VOLTAR
	}),
	
	EXCLUIR("Excluir", new SubMenuOperacoes[] {
			SubMenuOperacoes.EXCLUIR_CLIENTE,
			SubMenuOperacoes.EXCLUIR_VEICULO,
			SubMenuOperacoes.EXCLUIR_SINISTRO,
			SubMenuOperacoes.EXCLUIR_CONDUTOR,
			SubMenuOperacoes.VOLTAR}),
	
	GERAR_SEGURO("Gerar Seguro", new SubMenuOperacoes[] {SubMenuOperacoes.VOLTAR}),
	
	CANCELAR_SEGURO("Cancelar Seguro", new SubMenuOperacoes[] {SubMenuOperacoes.VOLTAR}),
	
	GERAR_SINISTRO("Gerar Sinistro", new SubMenuOperacoes[] {SubMenuOperacoes.VOLTAR}),
	
	//TRANSFERIR_SEGURO("Transferir Seguro", new SubMenuOperacoes[] {SubMenuOperacoes.VOLTAR}),
	
	CALCULAR_RECEITA("Calcular Receita", new SubMenuOperacoes[] {SubMenuOperacoes.VOLTAR}),
	
	SAIR("Sair", new SubMenuOperacoes[] {});
	
	// atributos
	private final String descricao;
	private final SubMenuOperacoes[] submenu;
	
	// construtor
	MenuOperacoes(String descricao, SubMenuOperacoes[] submenu){
		this.descricao = descricao;
		this.submenu = submenu;
	}
	
	// getters
	public String getDescricao() {
		return descricao;
	}
	
	public SubMenuOperacoes[] getSubmenu() {
		return submenu;
	}
}
