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
	public Seguradora(String nome , String telefone , String email , String endereco) 
	{
		this.nome = nome ;
		this.telefone = telefone ;
		this.email = email ;
		this.endereco = endereco;
		this.listaSinistros = new ArrayList<Sinistro>();
		this.listaClientes = new ArrayList<Cliente>();
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
	
	public String toStringSimples() {
		String ret = "";
		
		ret += "Nome: " + this.nome+"\n";
		ret += "Telefone: " + this.telefone+"\n";
		ret += "Email: " + this.email+"\n";
		ret += "Endereço: " + this.endereco;
		
		return ret;
	}
	
	/**
	 *  Cadastrar cliente
	 * @param cliente a adicionar
	 * @return true se o cliente ja estava na lista, ou falso caso contrario
	 */
	boolean cadastrarCliente(Cliente cliente) {
		return listaClientes.add(cliente);
	}
	
	/**
	 *  Cadastrar cliente
	 * @param cliente a remover
	 * @return true se o cliente ja estava na lista e foi removido, ou falso caso contrario
	 */
	boolean removerCliente(String keyCliente) {
		keyCliente = keyCliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		
		Cliente cliente = getClienteByKey(keyCliente);
		
		if(cliente == null)
			return false;
		
		ArrayList<Sinistro> sinistrosCliente = listarSinistrosByKeyCliente(keyCliente);

		for(Sinistro sinistro : sinistrosCliente)
			this.listaSinistros.remove(sinistro);

		return listaClientes.remove(cliente);
	}
	
	List<Cliente> listarClientes(String tipoCliente){
		List<Cliente> pesquisa = new ArrayList<Cliente>();
		for(Cliente cliente : listaClientes) {
			if(tipoCliente.equals("PF") && cliente instanceof ClientePF) {
				pesquisa.add(cliente);
			} else if (tipoCliente.equals("PJ") && cliente instanceof ClientePJ) {
				pesquisa.add(cliente);				
			}
		}
		return pesquisa;
	}
	
	private String getTipoClienteByKey(String key) {
		if(key.length() == 14)
			return "PJ";
		else
			return "PF";
	}
	
	Cliente getClienteByKey(String key){
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
		
		return null;
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
	boolean visualizarSinistro(String keyCliente){
		keyCliente = keyCliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		String tipoCliente = getTipoClienteByKey(keyCliente);
		
		for(Sinistro sinistro: listaSinistros) {
			if(tipoCliente.equals("PJ") && sinistro.getCliente() instanceof ClientePJ) {
				if(((ClientePJ) sinistro.getCliente()).getCnpj().equals(keyCliente))
					return true;				
			} else if(tipoCliente.equals("PF") && sinistro.getCliente() instanceof ClientePF) {
				if(((ClientePF) sinistro.getCliente()).getCpf().equals(keyCliente))
					return true;				
			}
		}
		return false;
	}
	
	ArrayList<Sinistro> listarSinistros(){
		return this.getListaSinistros();
	}
	
	ArrayList<Sinistro> listarSinistrosByKeyCliente(String keyCliente){
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
	
}
