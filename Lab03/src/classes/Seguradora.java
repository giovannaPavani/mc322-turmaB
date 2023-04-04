package classes;

import java.util.ArrayList;
import java.util.List;
//3
// VARIAS DUVIDAS
public class Seguradora {

	// Propriedades
	private String nome;
	private String telefone;
	private String email;
	private String endereco;
	private List<Sinistro> listaSinistros;
	private List<Cliente> listaClientes;
	
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
	
	public List<Sinistro> getListaSinistros() {
		return listaSinistros;
	}

	public void setListaSinistros(List<Sinistro> listaSinistros) {
		this.listaSinistros = listaSinistros;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
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
	 * @return true se o cliente ja estava na lista, ou falso caso contrario
	 */
	boolean removerCliente(Cliente cliente) {
		return listaClientes.remove(cliente);
	}
	
	List<Cliente> listarClientes(String tipoCliente){
		List<Cliente> pesquisa = new ArrayList<Cliente>();
		for(Cliente item : listaClientes)
			//if(tipoCliente == "PF" && item.instanceOf(ClientePF))
				pesquisa.add(item);
			//else if (tipoCliente == "PJ" && item.instanceOf(ClientePJ))
		 		// pesquisa.add(item);
		return pesquisa;
	}
	
	// ???????????
	boolean gerarSinistro() {
		return true;
	}
	
	// boolean???
	// se existe?
	boolean visualizarSinistro(String cliente){
		return true;
	}
	
	// ???????????
	List<Sinistro> listarSinistros(){
		return this.listaSinistros;
	}
}
