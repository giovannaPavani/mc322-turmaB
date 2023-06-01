package Seguradora;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import Cliente.Cliente;
import Cliente.ClientePF;
import Cliente.ClientePJ;
import Condutor.Condutor;
import Seguro.Seguro;
import Seguro.SeguroPF;
import Seguro.SeguroPJ;
import Sinistro.Sinistro;
import Veiculo.Veiculo;

public class Seguradora {

	// Propriedades
	private final String cnpj;
	private String nome;
	private String telefone;
	private String endereco;
	private String email;
	private ArrayList<Cliente> listaClientes;
	private ArrayList<Seguro> listaSeguros;
	
	// Construtor
	public Seguradora(String cnpj, String nome, String telefone, String endereco, 
					  String email, ArrayList<Cliente> listaClientes, ArrayList<Seguro> listaSeguros) 
	{
		this.cnpj = cnpj;
		this.nome = nome;
		this.telefone = telefone;
		this.endereco = endereco;
		this.email = email;
		this.listaClientes = listaClientes;
		this.listaSeguros = listaSeguros;
	}
	
	// Getters e setters
	public String getCnpj() {
		return cnpj;
	}
	
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

	public ArrayList<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(ArrayList<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}	
	
	public ArrayList<Seguro> getListaSeguros() {
		return listaSeguros;
	}

	public void setListaSeguros(ArrayList<Seguro> listaSeguros) {
		this.listaSeguros = listaSeguros;
	}
	
	public String toString() {
		String ret = "";
		
		ret += "CNPJ: " + this.cnpj+"\n";
		ret += "Nome: " + this.nome+"\n";
		ret += "Telefone: " + this.telefone+"\n";
		ret += "Email: " + this.email+"\n";
		ret += "Endereço: " + this.endereco;

		// lista todos os clientes da seguradora
		if(this.listaClientes != null && !this.listaClientes.isEmpty()) {
			ret += "Lista de clientes:";
			for(Cliente cliente: this.listaClientes)
				ret += "\n- "+cliente.toStringSimples();
		}
		
		// lista todos os seguros da seguradora
		if(this.listaSeguros != null && !this.listaSeguros.isEmpty()) {
			ret += "\nLista de Seguros:";
			for(Seguro seguro: this.listaSeguros)
				ret += "\n- "+seguro.toString(); // TODO toStringSimples			
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
		
		if(listaClientes.add(cliente)) {
			// calcula preco seguro se cliente foi adicionado na listaClientes
			// TODO em q criar seguro??
			//this.calcularPrecoSeguroCliente(getKeyCliente(cliente));
			return true;
		}
		
		return false; 
	}
	
	// TOTEST
	// remove cliente da seguradora pelo seu CPF/CNPJ
	public boolean removerCliente(String cliente) {
		
		// remove caracteres nao numericos ('.', '-' e '/') do CPF/CNPJ
		cliente = cliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		
		// resgata cliente com o CPF/CNPJ dado cadastrado na seguradora
		Cliente clienteObj = getClienteByKey(cliente);
		
		// cliente nao cadastrado
		if(clienteObj == null)
			return false;
		
		// resgata seguros envolvendo este cliente na seguradora
		ArrayList<Seguro> segurosCliente = listarSegurosByKeyCliente(cliente);

		// remove todos eles da seguradora
		for(Seguro seguro : segurosCliente)
			this.listaSeguros.remove(seguro);

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
	
	 public boolean gerarSinistro(String placa, String keyCliente, LocalDate data, String endereco, Condutor condutor) { 
		String tipoCliente = getTipoClienteByKey(keyCliente);
		
		// resgata cliente com a keyCliente (CPF/CNPJ) fornecida cadastrado na seguradora
		keyCliente = keyCliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		Cliente cliente = getClienteByKey(keyCliente);
		if(cliente == null) // cliente nao cadastrado
			return false;
		
		// resgata veiculo do cliente com a placa fornecida
		Veiculo veiculo = cliente.getVeiculoByPlaca(placa);
		if(veiculo == null) // o cliente nao tem esse veiculo
			return false;
		
		Seguro seguro = null;
		for(Seguro s: listaSeguros) {
			if(tipoCliente.equals("PF") && s instanceof SeguroPF) {
				if(((SeguroPF)s).getCliente().getCpf().equals(keyCliente) &&
					((SeguroPF)s).getVeiculo().getPlaca().equals(placa)) {
					seguro = s;
					break;
				}
			}
			
			if(tipoCliente.equals("PJ") && s instanceof SeguroPJ) {
				if(((SeguroPJ)s).getCliente().getCnpj().equals(keyCliente) &&
					((SeguroPJ)s).getFrota().getVeiculoByPlaca(placa) != null) {
					seguro = s;
					break;
				}
			}
		}
		
		if(seguro == null)
			return false;
		
		// ver se existe o condutor no seguro
		// se nao, retorna falso
		
		return seguro.gerarSinistro(data, endereco, condutor);
	} 
	/*
	public boolean removerSinistro(int id) {
		if(id < 0)
			return false;
		
		// remove sinistro da lista da seguradora e atualiza seguro cliente
		for(int i = 0; i < listaSinistros.size(); i++) {
			if(listaSinistros.get(i).getId() == id) { // acha o sinistro com id informado na lista de sinistros da seguradora
					Sinistro removido = listaSinistros.remove(i); // remove e guarda objeto removido na variavel
					this.calcularPrecoSeguroCliente(getKeyCliente(removido.getCliente()));
					return true;
				}
		}
		
		// sinistro nao existe
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
	} */
	
	// TOTEST
	public ArrayList<Seguro> listarSegurosByKeyCliente(String keyCliente){
		// pega o tipo de cliente de acordo com o CPF/CNPJ
		keyCliente = keyCliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");		
		String tipoCliente = getTipoClienteByKey(keyCliente);
		
		ArrayList<Seguro> pesquisa = new ArrayList<Seguro>();
		
		// pesquisa na lista de sinistros todos cujo CPF/CNPJ do cliente envolvido é igual ao fornecido
		for(Seguro seguro: this.listaSeguros) {
			
			// foi pedido um cliente fisico e o item da vez é um SeguroPF
			if(tipoCliente.equals("PF") && seguro instanceof SeguroPF) {
				// CPF do cliente envolvido é igual ao fornecido
				if(((SeguroPF)seguro).getCliente().getCpf().equals(keyCliente))
					pesquisa.add(seguro);
			}    // foi pedido um cliente juridico e o item da vez é um SeguroPJ
			else if (tipoCliente.equals("PJ") && seguro instanceof SeguroPJ) {
				// CNPJ do cliente envolvido é igual ao fornecido
				if(((SeguroPJ)seguro).getCliente().getCnpj().equals(keyCliente))
					pesquisa.add(seguro);
			}
		}
		
		return pesquisa;
	}
	
	/*
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
	}*/

	
	// 3 - Veiculos
	
	// TOTEST
	// retorna todos os veiculos cadastrados na seguradora
	public ArrayList<Veiculo> getVeiculos(){
		ArrayList<Veiculo> ret = new ArrayList<Veiculo>();
		
		for(Cliente cliente: listaClientes)
			ret.addAll(cliente.getListaVeiculos());
		
		return ret;
	}
	
	// TODO
	// remove (caso exista) o veiculo passado por parametro do cliente e atualiza seguro
	public boolean removerVeiculo(Cliente cliente, String placa) {
		if(placa == null || cliente == null) // veiculo ou cliente nulo
			return false;
		
		Veiculo veiculo = cliente.getVeiculoByPlaca(placa);
		
		if(veiculo == null) // veiculo nao cadastrado
			return false;
		
		boolean remove = cliente.removerVeiculo(veiculo);
		
		// se deu certo a remoção
		if(remove) {
			// remove sinsitros envolvendo este carro e o cliente
			ArrayList<Sinistro> listaRemover = new ArrayList<Sinistro>();
			for(Sinistro s: listaSinistros) {
				if(s.getVeiculo().equals(veiculo) && s.getCliente().equals(cliente))
					listaRemover.add(s);
			}
			for(Sinistro sinistro : listaRemover)
				this.listaSinistros.remove(sinistro);
			
			// atualiza seguro cliente
			String key = getKeyCliente(cliente);
			this.calcularPrecoSeguroCliente(key);
		}
		
		return remove;
	}
	
	//TODO
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
	
	// 4 - Seguro
	
	// TODO
	public boolean gerarSeguro() {
		return true;
	}
	
	// TODO
	public boolean cancelarSeguro() {
		return true;
	}
	
	// TOTEST
	public ArrayList<Seguro> getSegurosPorCliente(String keyCliente){
		ArrayList<Seguro> ret = new ArrayList<Seguro>();
		
		String tipoCliente = getTipoClienteByKey(keyCliente);
		
		for(Seguro seguro: listaSeguros) {
			if(tipoCliente.equals("PF") && seguro instanceof SeguroPF) {
				if(((SeguroPF)seguro).getCliente().getCpf().equals(keyCliente))
						ret.add(seguro);
			}
			else if(tipoCliente.equals("PJ") && seguro instanceof SeguroPJ) {
				if(((SeguroPJ)seguro).getCliente().getCnpj().equals(keyCliente))
						ret.add(seguro);
			}
				
		}
		return ret;
	}
	
	// 5 - Receita
	
	// calcula o valor e atualiza no cliente
	/*private void calcularPrecoSeguroCliente(String keyCliente) {
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
	}*/
	
	// TOTEST
	// soma o valor do seguro de todos os seguros da seguradora
	public double calcularReceita() {
		double soma = 0.0;
		
		for(Seguro seguro : listaSeguros)
			soma += seguro.getValorMensal();
		
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
				// remove todos os veiculos da fonte e add no destino
				clienteFonte.removerVeiculo(veiculo);
				clienteDestino.adicionarVeiculo(veiculo);
			}
			// atualiza seguros de ambos os clientes
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
		
		ret += "CNPJ: " + this.cnpj+"\n";
		ret += "Nome: " + this.nome+"\n";
		ret += "Telefone: " + this.telefone+"\n";
		ret += "Email: " + this.email+"\n";
		ret += "Endereço: " + this.endereco;
		
		return ret;
    }

}
