package Main;

// define as constantes dos submenus
public enum SubMenuOperacoes {
	
	// constantes das opcoes de todos os submenus
	CADASTRAR_CLIENTE("Cadastrar cliente"),
	CADASTRAR_VEICULO("Cadastrar veiculo"),
	CADASTRAR_FROTA("Cadastrar frota"),
	CADASTRAR_CONDUTOR("Cadastrar condutor"),
	
	LISTAR_CLIENTES("Listar clientes"),
	LISTAR_SINISTROS_SEGURADORA("Listar sinistros seguradora"),
	LISTAR_SINISTROS_CLIENTE("Listar sinistros cliente"),
	LISTAR_VEICULOS_SEGURADORA("Listar veículos seguradora"),
	LISTAR_VEICULOS_CLIENTE("Listar veículos cliente"),
	LISTAR_SEGUROS_SEGURADORA("Listar seguros seguradora"),
	LISTAR_SEGUROS_CLIENTE("Listar seguros cliente"),
	
	EXCLUIR_CLIENTE("Excluir cliente"),
	EXCLUIR_VEICULO("Excluir veiculo"),
	EXCLUIR_SINISTRO("Excluir sininstro"),
	EXCLUIR_CONDUTOR("Excluir condutor"),
	
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
