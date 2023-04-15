package classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
// VARIAS DUVIDAS
public class Seguradora {

	// Propriedades
	private String nome;
	private String telefone;
	private String email;
	private String endereco;
	private ArrayList<Sinistro> listaSinistros;
	private ArrayList<Cliente> listaClientes;
	
	// Construtor
	public Seguradora(String nome , String telefone , String email , String endereco,
			ArrayList<Sinistro> listaSinistros, ArrayList<Cliente> listaClientes) 
	{
		this.nome = nome ;
		this.telefone = telefone ;
		this.email = email ;
		this.endereco = endereco;
		this.listaSinistros = listaSinistros;
		this.listaClientes = listaClientes;
	}
	
	// Getters e setters
	public String getNome() {
		return nome ;
	}
	
	public void setNome(String nome) {
		this . nome = nome ;
	}
	
	public String getTelefone() {
		return telefone ;
	}
	
	public void setTelefone( String telefone ) {
		this.telefone = telefone ;
	}
	
	public String getEmail() {
		return email ;
	}
	
	public void setEmail(String email) {
		this.email = email ;
	}
	
	public String getEndereco() {
		return endereco ;
	}
	
	public void setEndereco( String endereco ) {
		this.endereco = endereco ;
	}
	
	public ArrayList<Sinistro> getListaSinistros() {
		return listaSinistros;
	}

	public void setListaSinistros(ArrayList<Sinistro> listaSinistros) {
		this.listaSinistros = listaSinistros;
	}

	public ArrayList<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(ArrayList<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}
	
	public String toString() {
		String ret = "";
		
		ret += "Nome: " + this.nome+"\n";
		ret += "Telefone: " + this.telefone+"\n";
		ret += "Email: " + this.email+"\n";
		ret += "Endereço: " + this.endereco;
		if(this.listaSinistros != null && !this.listaSinistros.isEmpty()) {
			ret += "\nLista de sinistros:";
			for(Sinistro sinistro: this.listaSinistros)
				ret += "\n- "+sinistro.toStringSimples();			
		}
		if(this.listaClientes != null && !this.listaClientes.isEmpty()) {
			ret += "Lista de clientes:";
			for(Cliente cliente: this.listaClientes)
				ret += "\n- "+cliente.toStringSimples();
		}
		
		return ret;
	}
	
	/* =================
	 *  FUNÇÕES PEDIDAS
	 * ================= */
	
	/**
	 *  Cadastrar cliente
	 * @param cliente a adicionar
	 * @return true se o cliente ja estava na lista, ou falso caso contrario
	 */
	boolean cadastrarCliente(Cliente cliente) {
		if(this.listaClientes.contains(cliente)) // cliente já cadastrado
			return false;
		
		return listaClientes.add(cliente);
	}
	
	/**
	 *  Cadastrar cliente
	 * @param cliente a remover
	 * @return true se o cliente ja estava na lista e foi removido, ou falso caso contrario
	 */
	boolean removerCliente(String cliente) {
		cliente = cliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		
		Cliente clienteObj = getClienteByKey(cliente);
		
		if(clienteObj == null)
			return false;
		
		ArrayList<Sinistro> sinistrosCliente = listarSinistrosByKeyCliente(cliente);

		for(Sinistro sinistro : sinistrosCliente)
			this.listaSinistros.remove(sinistro);

		return listaClientes.remove(clienteObj);
	}
	
	
	// lista os clientes da seguradora conforme o seu tipo (físico [CPF] ou jurídico [CNPJ])
	List<Cliente> listarClientes(String tipoCliente){
		List<Cliente> pesquisa = new ArrayList<Cliente>();
		for(Cliente cliente : listaClientes) {
			if(tipoCliente.equals("CPF") && cliente instanceof ClientePF) {
				pesquisa.add(cliente);
			} else if (tipoCliente.equals("CNPJ") && cliente instanceof ClientePJ) {
				pesquisa.add(cliente);				
			}
		}
		return pesquisa;
	}

	boolean gerarSinistro(String placa, String keyCliente, LocalDate data, String endereco) { 
		// acha cliente
		keyCliente = keyCliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		Cliente cliente = getClienteByKey(keyCliente);
		if(cliente == null) // cliente nao existe
			return false;
		
		// acha veiculo do cliente
		Veiculo veiculo = cliente.getVeiculoByPlaca(placa);
		if(veiculo == null) // o cliente nao tem esse veiculo
			return false;
		
		Sinistro sinistro = new Sinistro(data, endereco, this, veiculo, cliente);
		
		return listaSinistros.add(sinistro);
	}
	
	// Existe sinistro deste cliente
	boolean visualizarSinistro(String cliente){
		cliente = cliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		String tipoCliente = getTipoClienteByKey(cliente);
		
		for(Sinistro sinistro: listaSinistros) {
			if(tipoCliente.equals("PJ") && sinistro.getCliente() instanceof ClientePJ) {
				if(((ClientePJ) sinistro.getCliente()).getCnpj().equals(cliente))
					return true;				
			} else if(tipoCliente.equals("PF") && sinistro.getCliente() instanceof ClientePF) {
				if(((ClientePF) sinistro.getCliente()).getCpf().equals(cliente))
					return true;				
			}
		}
		return false;
	}
	
	ArrayList<Sinistro> listarSinistros(){
		return this.getListaSinistros();
	}
	
	
	/* ====================
	 *  MÉTODOS AUXILIARES 
	 * ===================*/
	
	// retorna o tipo de cliente de acordo com sua key, isto é, se tem CPF ou CNPJ
	private String getTipoClienteByKey(String key) {
		if(key.length() == 14)
			return "PJ";
		else
			return "PF";
	}
	
	// retorna o cliente cadastrado na seguradora cuja key (CPF OU CNPJ) é passada por parâmetro
	public Cliente getClienteByKey(String key){
		key = key.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		String tipoCliente = getTipoClienteByKey(key);
		
		for(Cliente cliente : listaClientes)
			if(tipoCliente.equals("PJ") && cliente instanceof ClientePJ) {
				if(((ClientePJ) cliente).getCnpj().equals(key))
					return cliente;
			}
			else { // tipoCliente == "PF"
				if(((ClientePF) cliente).getCpf().equals(key))
					return cliente;
			}
		
		// caso o cliente com a chave informada não for encontrado
		return null;
	}
	
	public ArrayList<Sinistro> listarSinistrosByKeyCliente(String keyCliente){
		keyCliente = keyCliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		ArrayList<Sinistro> pesquisa = new ArrayList<Sinistro>();
		
		String tipoCliente = getTipoClienteByKey(keyCliente);
		
		for(Sinistro sinistro: this.listaSinistros) {
			if(tipoCliente.equals("PJ") && sinistro.getCliente() instanceof ClientePJ) {
				if(((ClientePJ) sinistro.getCliente()).getCnpj().equals(keyCliente))
					pesquisa.add(sinistro);				
			} else {
				if(tipoCliente.equals("PF") && sinistro.getCliente() instanceof ClientePF) {
					if(((ClientePF) sinistro.getCliente()).getCpf().equals(keyCliente))
						pesquisa.add(sinistro);												
				}
			}			
		}
		
		return pesquisa;
	}
	
	public String toStringSimples() {
		String ret = "";
		
		ret += "Nome: " + this.nome+"\n";
		ret += "Telefone: " + this.telefone+"\n";
		ret += "Email: " + this.email+"\n";
		ret += "Endereço: " + this.endereco;
		
		return ret;
    }
	
}
