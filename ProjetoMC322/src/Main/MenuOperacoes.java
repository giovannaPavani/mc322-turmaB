package Main;

// essa classe só serve para acessar as constantes
public enum MenuOperacoes {
	/*CADASTRAR("Cadastrar", new SubMenu[] {
			SubMenu.CADASTRAR_CLIENTE,
			CADASTRAR_VEICULO("Cadastrar veículo")
	}), - IDEIA*/ 
	LISTAR("Listar"),
	EXCLUIR("Excluir"), 
	GERAR_SINISTRO ("Gerar Sinistro"),
	TRANSFERIR_SEGURO ("Transferir Seguro"),
	CALCULAR_RECEITA ("Calcular Receita da Seguradora"),
	SAIR ("Sair");
	
	private final String descricao;
	private final SubMenu[] submenu;
	// nessa enum submenu teriamos todos os cadastrar
	// e o VOLTAR; (vazio)
	
	MenuOperacoes (String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao () {
		return this.descricao;
	}
}
