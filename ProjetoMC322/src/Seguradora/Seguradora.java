package Seguradora;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

import Cliente.Cliente;
import Cliente.ClientePF;
import Cliente.ClientePJ;
import Sinistro.Sinistro;
import Veiculo.Veiculo;

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
		this.nome = nome;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
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
		// lista todos os sinistros da seguradora
		if(this.listaSinistros != null && !this.listaSinistros.isEmpty()) {
			ret += "\nLista de sinistros:";
			for(Sinistro sinistro: this.listaSinistros)
				ret += "\n- "+sinistro.toStringSimples();			
		}
		// lista todos os clientes da seguradora
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
	
	// 1 - Clientes
	
	public boolean cadastrarCliente(Cliente cliente) {
		if(this.listaClientes.contains(cliente) || cliente == null) // cliente já cadastrado ou nulo
			return false;
		
		if(listaClientes.add(cliente)) // calcula preco seguro se cliente foi adicionado na listaClientes
			this.calcularPrecoSeguroCliente(this.getKeyCliente(cliente));
		
		return false; 
	}
	
	// remove cliente da seguradora pelo seu CPF/CNPJ
	public boolean removerCliente(String cliente) {
		
		// remove caracteres nao numericos ('.', '-' e '/') do CPF/CNPJ
		cliente = cliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		
		// resgata cliente com o CPF/CNPJ dado cadastrado na seguradora
		Cliente clienteObj = getClienteByKey(cliente);
		
		// cliente nao cadastrado
		if(clienteObj == null)
			return false;
		
		// resgata sinistros envolvendo este cliente na seguradora
		ArrayList<Sinistro> sinistrosCliente = listarSinistrosByKeyCliente(cliente);

		// remove todos eles da seguradora
		for(Sinistro sinistro : sinistrosCliente)
			this.listaSinistros.remove(sinistro);

		// retorna se cliente foi removido da listaClientes
		return listaClientes.remove(clienteObj);
	}
	
	// lista os clientes da seguradora conforme o seu tipo (físico [CPF] ou jurídico [CNPJ])
	public ArrayList<Cliente> listarClientes(String tipoCliente){
		ArrayList<Cliente> pesquisa = new ArrayList<Cliente>();
		
		for(Cliente cliente : listaClientes) {
			// foi pedido lista de clientes físicos e o item da vez é um ClientePF
			if(tipoCliente.equals("CPF") && cliente instanceof ClientePF) { 
				pesquisa.add(cliente);
				   // foi pedido lista de clientes juridicos e o item da vez é um ClientePJ
			} else if (tipoCliente.equals("CNPJ") && cliente instanceof ClientePJ) {
				pesquisa.add(cliente);				
			}
		}
		return pesquisa;
	}

	// 2 - Sinistros
	
	public boolean gerarSinistro(String placa, String keyCliente, LocalDate data, String endereco) { 
		// resgata cliente com a keyCliente (CPF/CNPJ) fornecida cadastrado na seguradora
		keyCliente = keyCliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		Cliente cliente = getClienteByKey(keyCliente);
		if(cliente == null) // cliente nao cadastrado
			return false;
		
		// resgata veiculo do cliente com a placa fornecida
		Veiculo veiculo = cliente.getVeiculoByPlaca(placa);
		if(veiculo == null) // o cliente nao tem esse veiculo
			return false;
		
		Sinistro sinistro = new Sinistro(data, endereco, this, veiculo, cliente);
		
		boolean add = listaSinistros.add(sinistro); // retorna se o sinistro criado foi adicionado na listaSinistros
		
		if(add)
			this.calcularPrecoSeguroCliente(keyCliente); // atualiza valor do seguro do cliente
		
		return add;
	}
	
	public boolean removerSinistro(int id) {
		if(id < 0)
			return false;
		
		for(int i = 0; i < listaSinistros.size(); i++) {
			if(listaSinistros.get(i).getId() == id) {
				Sinistro removido = listaSinistros.remove(i);
				if(removido != null) {
					this.calcularPrecoSeguroCliente(getKeyCliente(removido.getCliente()));
					return true;
				}
			}
		}
		
		return false;
	}
	
	// retorna se existe algum sinistro envolvendo este cliente
	public boolean visualizarSinistro(String cliente){
		// pega o tipo de cliente de acordo com o CPF/CNPJ fornecido
		cliente = cliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		String tipoCliente = getTipoClienteByKey(cliente);
		
		// pesquisa na lista de sinistros se ha algum que envolve CPF/CNPJ fornecido
		for(Sinistro sinistro: listaSinistros) {
			// foi pedido sinistros de um cliente juridico e o item da vez envolve um ClientePJ
			if(tipoCliente.equals("PJ") && sinistro.getCliente() instanceof ClientePJ) {
				// CNPJ do cliente deste sinistro é igual ao fornecido
				if(((ClientePJ) sinistro.getCliente()).getCnpj().equals(cliente))
					return true;			
			// foi pedido sinistros de um cliente físico e o item da vez envolve um ClientePF
			} else if(tipoCliente.equals("PF") && sinistro.getCliente() instanceof ClientePF) {
				// CPF do cliente deste sinistro é igual ao fornecido
				if(((ClientePF) sinistro.getCliente()).getCpf().equals(cliente))
					return true;				
			}
		}
		
		// nao ha nenhum sinistro envolvendo o cliente com o CPF/CNPJ fornecido
		return false;
	}
	
	// retorna lista com todos os sinistros de um cliente de acordo com CPF/CNPJ fornecido
	public ArrayList<Sinistro> listarSinistrosByKeyCliente(String keyCliente){
		// pega o tipo de cliente de acordo com o CPF/CNPJ
		keyCliente = keyCliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");		
		String tipoCliente = getTipoClienteByKey(keyCliente);
		
		ArrayList<Sinistro> pesquisa = new ArrayList<Sinistro>();
		
		// pesquisa na lista de sinistros todos cujo CPF/CNPJ do cliente envolvido é igual ao fornecido
		for(Sinistro sinistro: this.listaSinistros) {
			// foi pedido um cliente juridico e o item da vez envolve um ClientePJ
			if(tipoCliente.equals("PJ") && sinistro.getCliente() instanceof ClientePJ) {
				// CNPJ do cliente envolvido é igual ao fornecido
				if(((ClientePJ) sinistro.getCliente()).getCnpj().equals(keyCliente))
					pesquisa.add(sinistro);
				   // foi pedido um cliente juridico e o item da vez envolve um ClientePJ
			} else if(tipoCliente.equals("PF") && sinistro.getCliente() instanceof ClientePF) {
				// CPF do cliente envolvido é igual ao fornecido
				if(((ClientePF) sinistro.getCliente()).getCpf().equals(keyCliente))
					pesquisa.add(sinistro);
			}			
		}
		
		return pesquisa;
	}
	
	// retorna todos os sinistros cadastrados na seguradora
	public ArrayList<Sinistro> listarSinistros(){
		return this.getListaSinistros();
	}
	
	// 3 - Veiculos
	
	public ArrayList<Veiculo> getVeiculos(){
		ArrayList<Veiculo> ret = new ArrayList<Veiculo>();
		
		for(Cliente cliente: listaClientes) {
			for(Veiculo veiculo: cliente.getListaVeiculos()) {
				ret.add(veiculo);
			}
		}
		
		return ret;
	}
	
	// remove (caso exista) o veiculo passado por parametro do cliente e atualiza seguro
	public boolean removerVeiculo(Cliente cliente, String placa) {
		if(placa == null || cliente == null) // veiculo nulo
			return false;
		
		Veiculo veiculo = null;
		for(Veiculo v: cliente.getListaVeiculos()) {
			if(v.getPlaca().equals(placa))
				veiculo = v;
		}
		
		if(veiculo == null)
			return false;
		
		boolean remove = cliente.removerVeiculo(veiculo);
		
		if(remove) {			
			String key = getKeyCliente(cliente);
			this.calcularPrecoSeguroCliente(key);
		}
		
		return remove;
	}
	
	// adiciona o veiculo ao cliente, ambos passados por parametro e atualiza o seguro
	public boolean adicionarVeiculo(String keyCliente, Veiculo veiculo) {
		if(keyCliente == null || veiculo == null) // cliente nulo
			return false;
		
		// busca o cliente do cpf/cpnj passado por parametro
		Cliente cliente = getClienteByKey(keyCliente);
		if(cliente == null)
			return false;
		
		// tenta adicionar veiculo, se der certo, atualiza o seguro do cliente
		boolean add = cliente.adicionarVeiculo(veiculo);
		if(add)
			this.calcularPrecoSeguroCliente(keyCliente);
		
		return add;
	}
	
	// 4 - Seguro e receita
	
	// calcula o valor e atualiza no cliente
	private void calcularPrecoSeguroCliente(String keyCliente) {
		// encontra cliente na lista da seguradora
		Cliente cliente = this.getClienteByKey(keyCliente);
		
		// se o cliente nao existir, para a execução do metodo
		if(cliente == null)
			return;
		
		// calcula score cliente
		double score  = cliente.calculaScore();
		// pega tamanho da lista de sinistros do cliente
		int qtdSinistros = this.listarSinistrosByKeyCliente(keyCliente).size();
		
		// atualiza o valor do seguro no objeto do cliente
		cliente.setValorSeguro(score * (1 + qtdSinistros));
	}
	
	// soma o valor do seguro de todos os cliente da seguradora
	public double calcularReceita() {
		double soma = 0.0;
		
		for(Cliente cliente : listaClientes)
			soma += cliente.getValorSeguro();
		
		return soma;
	}
	
	// tranfere todos os veículos do clienteFonte para o clienteDestino
	public boolean transferirSeguro(String keyClienteFonte, String keyClienteDestino) {
		Cliente clienteFonte, clienteDestino;
		
		// resgata os clientes das keys passadas por parametro na seguradora
		// se pelo menos um deles não estiver cadastrado na seguradora, retorna false
		clienteFonte = getClienteByKey(keyClienteFonte);
		if(clienteFonte == null)
			return false;
		
		clienteDestino = getClienteByKey(keyClienteDestino);
		if(clienteDestino == null)
			return false;
		
		LinkedList<Veiculo> veiculos = clienteFonte.getListaVeiculos();
		// se o cliente fonte não tiver nada no seu seguro, a transferência é trivial
		if(!veiculos.isEmpty()) {
			for(Veiculo veiculo: veiculos) {
				clienteFonte.removerVeiculo(veiculo);
				clienteDestino.adicionarVeiculo(veiculo);
			}
			
			this.calcularPrecoSeguroCliente(keyClienteFonte);
			this.calcularPrecoSeguroCliente(keyClienteDestino);			
		}
		
		return true;
	}
	
	/* ====================
	 *  MÉTODOS AUXILIARES 
	 * ===================*/
	
	// retorna o tipo de cliente de acordo com CPF/CNPJ fornecido
	private static String getTipoClienteByKey(String key) {
		if(key.length() == 14)
			return "PJ";
		else
			return "PF";
	}
	
	// retorna o cpf ou cnpj do cliente a depender do seu tipo
	private static String getKeyCliente(Cliente cliente) {
		if(cliente instanceof ClientePF) 
			return ((ClientePF) cliente).getCpf();
		
		return ((ClientePJ) cliente).getCnpj();	
	}
	
	// retorna o cliente cadastrado na seguradora cuja key (CPF/CNPJ) é passada por parâmetro
	public Cliente getClienteByKey(String key){
		// pega o tipo de cliente de acordo com o CPF/CNPJ
		key = key.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		String tipoCliente = getTipoClienteByKey(key);
		
		// pesquisa na lista de clientes se ha algum cujo CPF/CNPJ é igual ao fornecido
		for(Cliente cliente : listaClientes)
			// foi pedido um cliente juridico e o item da vez é um ClientePJ
			if(tipoCliente.equals("PJ") && cliente instanceof ClientePJ) {
				// CNPJ do cliente é igual ao fornecido
				if(((ClientePJ) cliente).getCnpj().equals(key))
					return cliente;
			}    // foi pedido um cliente fisico e o item da vez é um ClientePF
			else if(tipoCliente.equals("PF") && cliente instanceof ClientePF) {
				// CPF do cliente é igual ao fornecido
				if(((ClientePF) cliente).getCpf().equals(key))
					return cliente;
			}
		
		// o cliente com a key informada não cadastrado
		return null;
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
