package Seguro;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Random;

import Condutor.Condutor;
import Seguradora.Seguradora;
import Sinistro.Sinistro;

public abstract class Seguro {

	protected final int id;
	protected LocalDate dataInicio;
	protected LocalDate dataFim;
	protected Seguradora seguradora;
	protected LinkedList<Sinistro> listaSinistros;
	protected LinkedList<Condutor> listaCondutores;
	protected double valorMensal;
	
	public Seguro(LocalDate dataInicio, LocalDate dataFim,
				  Seguradora seguradora, LinkedList<Sinistro> listaSinistros,
				  LinkedList<Condutor> listacondutores) {
		this.id = gerarId();
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.seguradora = seguradora;
		this.listaSinistros = listaSinistros;
		this.listaCondutores = listacondutores;
		calcularValor();
	}
	
	private int gerarId() {
		Random gerador = new Random();
		int ret = gerador.nextInt();
		// caso seja negativo, arruma o sinal
		if(ret < 0)
			ret = -ret;
        return ret;
	}
	
	public int getId() {
		return id;
	}
	
	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public Seguradora getSeguradora() {
		return seguradora;
	}

	public void setSeguradora(Seguradora seguradora) {
		this.seguradora = seguradora;
	}

	public LinkedList<Sinistro> getListaSinistros() {
		return listaSinistros;
	}

	public void setListaSinistros(LinkedList<Sinistro> listaSinistros) {
		this.listaSinistros = listaSinistros;
	}

	public LinkedList<Condutor> getListaCondutores() {
		return listaCondutores;
	}

	public void setListaCondutores(LinkedList<Condutor> listaCondutores) {
		this.listaCondutores = listaCondutores;
	}

	public double getValorMensal() {
		return valorMensal;
	}

	public void setValorMensal(double valorMensal) {
		this.valorMensal = valorMensal;
	}
	
	/* =================
	 *  FUNÇÕES PEDIDAS
	 * ================= */
	
	// OBS: Optei por colocar autorizarCondutor e desautorizarCondutor na classe 
	// principal, pois as implementações nas classes filhas estavam idênticas
	
	// TOTEST
	// adiciona condutor, se nao já cadastrado, na listaCondutores
	public boolean autorizarCondutor(Condutor condutor) {
		if(condutor == null)
			return false;
		
		// retorna falso se o condutor com mesmo cpf já esta autorizado
		for(Condutor c: listaCondutores) {
			if(c.getCpf().equals(condutor.getCpf()))
				return false;
		}
			
		if(listaCondutores.add(condutor)) {
			calcularValor();
			return true;
		}
		
		return false;
	}
	
	// TOTEST
	// remove, se já cadastrado, o condutor cujo cpf é fornecido da listaCondutores
	public boolean desautorizarCondutor(String cpf) {
		if(cpf == null || cpf.equals(""))
			return false;
		
		for(Condutor c: listaCondutores) {
			if(c.getCpf().equals(cpf))
				if(listaCondutores.remove(c)) {
					calcularValor();
					return true;
				}
					
		}
		
		return false;
	}
	
	// TOTEST
	public boolean gerarSinistro(LocalDate data, String endereco, String cpfCondutor) {
		if(data == null || endereco == null || endereco.equals("") || cpfCondutor == null || cpfCondutor.equals("") )
			return false;
		
		Condutor condutor = getCondutor(cpfCondutor);
		if(condutor == null)
			return false;
		
		Sinistro sinistro = new Sinistro(data, endereco, condutor, this);
		
		if(condutor.adicionarSinistro(sinistro)) { // add sinistro no condutor
			if(listaSinistros.add(sinistro)) { // add sinistro no seguro
				calcularValor(); // atualiza valor do seguro
				return true;
			}
		}
		
		return false; // se algum add der errado, retorna falso
	}
	
	public String toString() {
		String ret = "";
		ret += "ID: " + id + "\n";
		ret += "Data início: " + dataInicio + "\n";
		ret += "Data fim: " + dataFim + "\n";
		ret += "Valor mensal: " + valorMensal + "\n";
		
		ret += "-------------------\n";
		ret += "Dados da Seguradora\n";
		ret += "-------------------\n";
		ret += this.seguradora.toStringSimples() + "\n";
		
		if(listaSinistros != null && !listaSinistros.isEmpty()) {
			ret += "-----------------\n";
			ret += "Lista de Sinistros\n";
			ret += "-----------------";
			for(Sinistro sinistro: listaSinistros)
				ret += "\n-\n" + sinistro.toStringSimples();
			ret += "\n-";
		}
		
		if(listaCondutores != null && !listaCondutores.isEmpty()) {
			ret += "-----------------\n";
			ret += "Lista de Condutores\n";
			ret += "-----------------";
			for(Condutor condutor: listaCondutores)
				ret += "\n-\n" + condutor.toStringSimples();
			ret += "\n-";
		}
		
		return ret;
	}
	
	public abstract void calcularValor();
	
	/* ====================
	 *  MÉTODOS AUXILIARES 
	 * ===================*/
	
	// 	TOTEST
	public boolean removerSinistro(int id) {
		for(Sinistro sinistro: listaSinistros) { 
			if(sinistro.getId() == id) { // achou o sinistro cujo ID é informado
				
				// remove sinistro dos condutores
				for(Condutor condutor: listaCondutores) {
					if(condutor.getCpf().equals(sinistro.getCondutor().getCpf())) {
						if(!condutor.removerSinistro(sinistro))
							return false; // caso alguma remocao de sinistro de um condutor de errado
					}
				}
				
				// remove sinistro do seguro
				if(listaSinistros.remove(sinistro)) {
					calcularValor(); // atualiza valor do seguro
					return true;				
				}
				
				return false; // remoção deu errado
			}
		}
		
		return false; // o sinistro nao está cadastrado no seguro
	}
	
	// TOTEST
	public Condutor getCondutor(String cpfCondutor) {
		if(cpfCondutor == null || cpfCondutor.equals(""))
			return null;
		
		for(Condutor c: listaCondutores) {
			if(c.getCpf().equals(cpfCondutor))
				return c;
		}
			
		return null;
	}
	
	protected int getQtdSinistrosCondutores() {
		int qtd = 0;
		
		for(Condutor condutor: listaCondutores)
			qtd += condutor.getListaSinistros().size();
		
		return qtd;
	}
	
	public String toStringSimples() {
		String ret = "";
		
		ret += "ID: " + id + "\n";
		ret += "Valor mensal: " + valorMensal + "\n";
		
		return ret;
	}

}
