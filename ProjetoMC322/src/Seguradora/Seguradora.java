package Seguradora;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import Cliente.Cliente;
import Cliente.ClientePF;
import Cliente.ClientePJ;
import Condutor.Condutor;
import Frota.Frota;
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
		ret += "Endereço: " + this.endereco+"\n";
		ret += "Email: " + this.email;

		// lista todos os clientes da seguradora
		if(this.listaClientes != null && !this.listaClientes.isEmpty()) {
			ret += "\nLista de clientes:";
			for(Cliente cliente: this.listaClientes)
				ret += "\n- "+cliente.toStringSimples();
		}
		
		// lista todos os seguros da seguradora
		if(this.listaSeguros != null && !this.listaSeguros.isEmpty()) {
			ret += "\nLista de Seguros:";
			for(Seguro seguro: this.listaSeguros)
				ret += "\n- "+seguro.toStringSimples();
		}
		
		return ret;
	}
	
	/* =================
	 *  FUNÇÕES PEDIDAS
	 * ================= */
	
	// 1 - Clientes
	
	// TOTEST
	// add cliente na listaClientes
	public boolean cadastrarCliente(Cliente cliente) {
		if(this.listaClientes.contains(cliente) || cliente == null) // cliente já cadastrado ou nulo
			return false;
		
		if(getClienteByKey(getKeyCliente(cliente)) != null) // cpf/cnpj já cadastrado
			return false;
		
		return listaClientes.add(cliente);
	}
	
	// TOTEST
	// remove cliente da seguradora pelo seu CPF/CNPJ
	public boolean removerCliente(String keyCliente) {
		// remove caracteres nao numericos ('.', '-' e '/') do CPF/CNPJ
		keyCliente = keyCliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		
		// resgata cliente com o CPF/CNPJ dado cadastrado na seguradora
		Cliente cliente = getClienteByKey(keyCliente);
		
		// cliente nao cadastrado
		if(cliente == null)
			return false;
		
		// resgata seguros envolvendo este cliente na seguradora
		ArrayList<Seguro> segurosCliente = getSegurosPorCliente(keyCliente);

		// remove todos eles da seguradora
		for(Seguro seguro : segurosCliente)
			this.listaSeguros.remove(seguro);

		// retorna se cliente foi removido da listaClientes
		return listaClientes.remove(cliente);
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
	
	// TOTEST
	public boolean gerarSinistro(String placa, String keyCliente, LocalDate data, String endereco, String cpfCondutor) { 
		// resgata cliente com a keyCliente (CPF/CNPJ) fornecida cadastrado na seguradora
		keyCliente = keyCliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		Cliente cliente = getClienteByKey(keyCliente);
		if(cliente == null) // cliente nao cadastrado
			return false;
		
		// resgata veiculo do cliente com a placa fornecida
		Veiculo veiculo = cliente.getVeiculoByPlaca(placa);
		if(veiculo == null) // o cliente nao tem esse veiculo
			return false;
		
		// resgata seguro envolvendo o cliente e o veiculo fornecidos
		Seguro seguro = getSeguro(keyCliente, placa);
		if(seguro == null) // nao existe
			return false;
		
		// retorna se deu certo o cadastro do sinistro
		return seguro.gerarSinistro(data, endereco, cpfCondutor);
	} 
	
	// TOTEST
	public boolean removerSinistro(int id) {
		if(id < 0)
			return false; // ID invalido
		
		for(Seguro seguro: listaSeguros) {
			if(seguro.removerSinistro(id))
				return true;
		}
		
		// sinistro nao existe
		return false;
	} 
	
	// TOTEST
	// retorna todos os sinistros registrados em seguros de um cliente de acordo com CPF/CNPJ fornecido
	public ArrayList<Sinistro> getSinistrosPorCliente(String keyCliente){
		if(keyCliente == null || keyCliente.equals(""))
			return null;
		
		ArrayList<Sinistro> ret = new ArrayList<Sinistro>();
		
		keyCliente = keyCliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		ArrayList<Seguro> segurosCliente = getSegurosPorCliente(keyCliente);
		
		 // add todos os sinistros de todos os seguros do cliente fornecido no ret
		for(Seguro seguro: segurosCliente)
			ret.addAll(seguro.getListaSinistros());
		
		return ret;
	}
	
	// TOTEST
	// retorna todos os sinistros registrados em seguros da seguradora
	public ArrayList<Sinistro> getSinistros(){
		ArrayList<Sinistro> ret = new ArrayList<Sinistro>();
		
		 // add todos os sinistros de todos os seguros
		for(Seguro seguro: listaSeguros)
			ret.addAll(seguro.getListaSinistros());
		
		return ret;
	}
	
	// 3 - Veiculos
	
	// TOTEST
	// adiciona o veiculo ao cliente, ambos passados por parametro e atualiza o seguro
	public boolean cadastrarVeiculo(String keyCliente, Veiculo veiculo, String code) {
		if(keyCliente == null || keyCliente.equals("") || veiculo == null) // cliente nulo
			return false;
		
		// busca o cliente do cpf/cpnj passado por parametro
		keyCliente = keyCliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		Cliente cliente = getClienteByKey(keyCliente);
		if(cliente == null)
			return false;
		
		// tenta adicionar veiculo, se der certo, atualiza o seguro do cliente
		String tipoCliente = getTipoClienteByKey(keyCliente);
		
		boolean add = false;
		if(tipoCliente.equals("PF")){
			add = ((ClientePF)cliente).cadastrarVeiculo(veiculo);
		
		} else if (tipoCliente.equals("PJ")){
			if(code == null || code.equals(""))
				return false;
			add = ((ClientePJ)cliente).atualizarFrota(code, veiculo);
		}
		
		if(add) { // se cadastrou novo veiculo, atualiza valor de todos os seguros do cliente
			for(Seguro seguro: getSegurosPorCliente(keyCliente)) {
				seguro.calcularValor();
			}
			return true;
		}
		
		return false;
	}
	
	// TOTEST
	// adiciona frota ao clientePJ, ambos passados por parametro e atualiza o seguro
	public boolean cadastrarFrota(String cnpj, Frota frota) {
		if(cnpj == null || cnpj.equals("") || frota == null) // cliente nulo
			return false;
		
		// cliente precisa ser PJ
		String tipoCliente = getTipoClienteByKey(cnpj);
		if(!tipoCliente.equals("PJ"))
			return false;
		
		// busca o cliente do cpf/cpnj passado por parametro
		cnpj = cnpj.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		ClientePJ cliente = (ClientePJ)getClienteByKey(cnpj);
		if(cliente == null)
			return false;

		if(cliente.cadastrarFrota(frota)) { // se cadastrou nova frota, atualiza valor de todos os seguros do cliente
			for(Seguro seguro: getSegurosPorCliente(cnpj)) {
				seguro.calcularValor();
			}
			return true;
		}
		
		return false;
	}
	
	// TOTEST
	// remove (caso exista) o veiculo passado por parametro do cliente
	public boolean removerVeiculo(String keyCliente, String placa) {
		if(placa == null || placa.equals("") || keyCliente == null || keyCliente.equals("")) // veiculo ou cliente nulos
			return false;
		
		keyCliente = keyCliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		Cliente cliente = getClienteByKey(keyCliente);
		if(cliente == null) // cliente nao cadastrado
			return false;
		
		Veiculo veiculo = cliente.getVeiculoByPlaca(placa);
		if(veiculo == null) // veiculo nao cadastrado no cliente
			return false;
		
		if(cliente.removerVeiculo(placa)) {// se deu certo a remoção no cliente
			
			String tipoCliente = getTipoClienteByKey(keyCliente);
			
			for(Seguro seguro: listaSeguros) {
				if(tipoCliente.equals("PF") && seguro instanceof SeguroPF) { // CPF - remove o seguro do veiculo
					if(((SeguroPF)seguro).getCliente().getCpf().equals(keyCliente) &&
					   ((SeguroPF)seguro).getVeiculo().getPlaca().equals(placa)) {
						return listaSeguros.remove(seguro);
					}
					
				}
				else if (tipoCliente.equals("PJ") && seguro instanceof SeguroPJ) { 
					// CNPJ - remove sinistros envolvendo o veiculo dos condutores do seguro
					if(((SeguroPJ)seguro).getCliente().getCnpj().equals(keyCliente)) {
						if(((SeguroPJ)seguro).removerVeiculo(placa)) {
							if(((SeguroPJ)seguro).getFrota().getListaVeiculos().isEmpty()) { // se a frota do seguro não tem mais carros, este é cancelado
								listaSeguros.remove(seguro);
								break;
							}
						}
					}
					
				}
			}
			
			if(tipoCliente.equals("PJ")) { // se removeu veiculo de algum clientePJ
				for(Seguro seguro: listaSeguros) 
					if (seguro instanceof SeguroPJ)
						seguro.calcularValor();
				return true;
			}
		}

		return false; // alguma remoção deu errado
	}
	
	// TOTEST
	// retorna todos os veiculos cadastrados em clientes da seguradora
	public ArrayList<Veiculo> getVeiculos(){
		ArrayList<Veiculo> ret = new ArrayList<Veiculo>();
		
		for(Cliente cliente: listaClientes)
			ret.addAll(cliente.getListaVeiculos());
		
		return ret;
	}
	
	// TOTEST
	// retorna todos os veiculos cadastrados em clientes da seguradora
	public LinkedList<Veiculo> getVeiculosByKeyCliente(String keyCliente){
		if(keyCliente == null || keyCliente.equals(""))
			return null;
		
		keyCliente = keyCliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		Cliente cliente = getClienteByKey(keyCliente);

		return cliente.getListaVeiculos();
	}
	
	// 4 - Seguros
	
	// TOTEST
	// gera seguro de um cliente com as informações fornecidas
	public boolean gerarSeguro(String keyCliente, LocalDate dataFim, String placa, String code) {
		
		if(keyCliente == null || keyCliente.equals("") || dataFim == null )
			return false;
		
		// tira caracteres nao numeros do cpf
		keyCliente = keyCliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		// pega cliente cadastrado
		Cliente cliente = (Cliente)getClienteByKey(keyCliente);
		if(cliente == null)
			return false; // cliente nao cadastrado
		LocalDate dataInicio = LocalDate.now();
		
		if(getTipoClienteByKey(keyCliente).equals("PF")){
			if(placa == null || placa.equals(""))
				return false;
			Veiculo veiculo = cliente.getVeiculoByPlaca(placa);
			SeguroPF seguro = new SeguroPF(dataInicio, dataFim, this, new LinkedList<Sinistro>(), new LinkedList<Condutor>(), veiculo, (ClientePF)cliente);
			return listaSeguros.add(seguro);
			
		} else if(getTipoClienteByKey(keyCliente).equals("PJ")){
			if(code == null || code.equals(""))
				return false;
			Frota frota = ((ClientePJ)cliente).getFrotaByCode(code).clone();
			SeguroPJ seguro = new SeguroPJ(dataInicio, dataFim, this, new LinkedList<Sinistro>(), new LinkedList<Condutor>(), frota, (ClientePJ)cliente);
			return listaSeguros.add(seguro);
		}
		
		return false;
	}
	
	// TOTEST
	// remove seguro cujo id é fornecido e retorna true; caso contrario, retorna false
	public boolean cancelarSeguro(int id) {
		if(id < 0)
			return false;
		
		for(Seguro seguro: listaSeguros) {
			if(seguro.getId() == id)
				return listaSeguros.remove(seguro);
		}
		
		return false;
	}
	
	// TOTEST
	/*public boolean tranferirSeguro(String keyClienteFonte, String keyClienteDestino) {
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
				clienteFonte.removerVeiculo(veiculo.getPlaca());
				clienteDestino.(veiculo);
			}
			// atualiza seguros de ambos os clientes
			this.calcularPrecoSeguroCliente(keyClienteFonte);
			this.calcularPrecoSeguroCliente(keyClienteDestino);			
		}
		
		return true;
	}*/
	
	// TOTEST
	// retorna todos os seguros registrados com a key do cliente na seguradora
	public ArrayList<Seguro> getSegurosPorCliente(String keyCliente){
		ArrayList<Seguro> ret = new ArrayList<Seguro>();
		
		// tira caracteres '.', '/' e '-' do CPF/CNPJ e pega o tipo de cliente
		keyCliente = keyCliente.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		String tipoCliente = getTipoClienteByKey(keyCliente);
		
		// pesquisa na lista de seguros todos cujo cliente envolvido tem CPF/CNPJ igual ao fornecido
		for(Seguro seguro: listaSeguros) {
			// foi pedido um cliente fisico e o item da vez é um SeguroPF
			if(tipoCliente.equals("PF") && seguro instanceof SeguroPF) {
				// CPF do cliente envolvido é igual ao fornecido
				if(((SeguroPF)seguro).getCliente().getCpf().equals(keyCliente))
					ret.add(seguro);
			} // o mesmo que o if acima, porem com cliente juridico
			else if(tipoCliente.equals("PJ") && seguro instanceof SeguroPJ) {
				if(((SeguroPJ)seguro).getCliente().getCnpj().equals(keyCliente))
					ret.add(seguro);
			}
		}
		
		return ret;
	}
	
	// TOTEST
	// no seguro fornecido, adiciona o condutor fornecido, caso ele ainda nao esteja cadastrado
	public boolean autorizarCondutor(int idSeguro, Condutor condutor) {
		if(idSeguro < 0 || condutor == null)
			return false;
		
		for(Seguro seguro: listaSeguros) {
			if(seguro.getId() == idSeguro)
				return seguro.autorizarCondutor(condutor);
		}
		
		return false; // seguro nao cadastrado
	}
	
	// TOTEST
	// no seguro fornecido, adiciona o condutor fornecido, caso ele ainda nao esteja cadastrado
	public boolean desautorizarCondutor(int idSeguro, String cpfCondutor) {
		if(idSeguro < 0 || cpfCondutor == null || cpfCondutor.equals(""))
			return false;
		
		cpfCondutor = cpfCondutor.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");		
		
		for(Seguro seguro: listaSeguros) {
			if(seguro.getId() == idSeguro)
				return seguro.desautorizarCondutor(cpfCondutor);
		}
		
		return false; // seguro nao cadastrado
	}

	// 5 - Receita
	
	// TOTEST
	// soma o valor do seguro de todos os seguros da seguradora
	public double calcularReceita() {
		double soma = 0.0;
		
		for(Seguro seguro : listaSeguros)
			soma += seguro.getValorMensal();
		
		return soma;
	}
	
	/* ====================
	 *  MÉTODOS AUXILIARES 
	 * ===================*/
	
	public String toStringSimples() {
		String ret = "";
		
		ret += "CNPJ: " + this.cnpj+"\n";
		ret += "Nome: " + this.nome+"\n";
		ret += "Telefone: " + this.telefone+"\n";
		ret += "Email: " + this.email+"\n";
		ret += "Endereço: " + this.endereco;
		
		return ret;
    }
	
	// retorna o tipo de cliente de acordo com CPF/CNPJ fornecido
	private static String getTipoClienteByKey(String key) {
		key = key.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
		
		if(key.length() == 14)
			return "PJ";
		else
			return "PF";
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
	
	// retorna seguro que envolve o cliente e o veiculo fornecidos; se nao houver, retorna nulo
	private Seguro getSeguro(String keyCliente, String placa) {
		String tipoCliente = getTipoClienteByKey(keyCliente);
		
		for(Seguro seguro: listaSeguros) {
			if(tipoCliente.equals("PF") && seguro instanceof SeguroPF) {
				if(((SeguroPF)seguro).getCliente().getCpf().equals(keyCliente) &&
					((SeguroPF)seguro).getVeiculo().getPlaca().equals(placa)) {
					return seguro;
				}
			}
			
			if(tipoCliente.equals("PJ") && seguro instanceof SeguroPJ) {
				if(((SeguroPJ)seguro).getCliente().getCnpj().equals(keyCliente) &&
					((SeguroPJ)seguro).getFrota().getVeiculoByPlaca(placa) != null) {
					return seguro;
				}
			}
		}
		
		return null;
	}
	
	// retorna o cpf ou cnpj do cliente a depender do seu tipo
	private static String getKeyCliente(Cliente cliente) {
		if(cliente instanceof ClientePF) 
			return ((ClientePF) cliente).getCpf();
		
		return ((ClientePJ) cliente).getCnpj();	
	}

}
