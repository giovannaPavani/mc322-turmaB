package Main;

// define as constantes dos submenus
public enum SubMenuOperacoes {
	
	// constantes das opcoes de todos os submenus
	CADASTRAR_CLIENTE("Cadastrar cliente"),
	CADASTRAR_VEICULO("Cadastrar veiculo"),
	
	LISTAR_CLIENTES("Listar clientes"),
	LISTAR_SINISTROS_SEGURADORA("Listar sinistros seguradora"),
	LISTAR_SINISTROS_CLIENTE("Listar sinistros cliente"),
	LISTAR_VEICULOS_CLIENTE("Listar veículos cliente"),
	LISTAR_VEICULOS_SEGURADORA("Listar veículos seguradora"),
	
	EXCLUIR_CLIENTE("Excluir cliente"),
	EXCLUIR_VEICULO("Excluir veiculo"),
	EXCLUIR_SINISTRO("Excluir sininstro"),
	
	VOLTAR("Voltar");
	
	// atributo
	private final String descricao;
	
	// construtor
	SubMenuOperacoes(String descricao){
		this.descricao = descricao;
	}
	
	// getter
	public String getDescricao() {
		return descricao;
	}
}
