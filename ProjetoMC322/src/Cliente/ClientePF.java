package Cliente;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import Veiculo.Veiculo;

public class ClientePF extends Cliente {
	
	//Propriedades
	private final String cpf;
	private String genero;
	protected LocalDate dataLicenca;
	private String educacao;
	private LocalDate dataNascimento;
	private String classeEconomica;

	//Construtor
	public ClientePF (String nome, String endereco, LinkedList<Veiculo> listaVeiculos,
			          LocalDate dataLicenca, String cpf, LocalDate dataNascimento, 
					  String educacao , String genero , String classeEconomica) {
		// chama o construtor da superclasse Cliente
		super (nome , endereco, listaVeiculos);
		
		this.cpf = cpf;
		this.genero = genero;
		this.dataLicenca = dataLicenca;
		this.educacao = educacao;
		this.dataNascimento = dataNascimento;
		this.classeEconomica = classeEconomica;
	}

	//Getters e Setters
	public String getCpf() {
		return cpf;
	}
	
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getEducacao() {
		return educacao;
	}
	
	public void setEducacao(String educacao) {
		this.educacao = educacao;
	}
	
	public LocalDate getDataLicenca() {
		return dataLicenca;
	}
	
	public void setDataLicenca(LocalDate dataLicenca) {
		this.dataLicenca = dataLicenca;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getClasseEconomica() {
		return classeEconomica;
	}

	public void setClasseEconomica(String classeEconomica) {
		this.classeEconomica = classeEconomica;
	}
	
	@Override
	public String toString () {
		String ret = "";
		// formatador para converter o objeto LocalDate em String do formato "dd/MM/yyyy"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ret += "CPF: " + this.cpf + "\n";
		ret += "Gênero: " + this.genero + "\n";
		ret += "Data da Licença: " + dataLicenca.format(formatter)+"\n";
		ret += "Educação: " + this.educacao + "\n";
		ret += "Data de Nascimento: " + this.dataNascimento.format(formatter) + "\n";
		ret += "Classe Econômica: " + this.classeEconomica + "\n";
		ret += super.toString();
		
		return ret;
	}
	
	@Override
	public String toStringSimples() {
		String ret = "";
		// formatador para converter o objeto LocalDate em String do formato "dd/MM/yyyy"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ret += "CPF: " + this.cpf + "\n";
		ret += "Data de Nascimento: " + this.dataNascimento.format(formatter) + "\n";
		ret += super.toStringSimples();
		
		return ret;
	}

	/* =================
	 *  FUNÇÕES PEDIDAS
	 * ================= */
	
	//TOTEST
	@Override
	public double calculaScore() {
		// calcular idade
		LocalDate dataAtual = LocalDate.now();
		Period periodo = Period.between(dataNascimento, dataAtual);
		int idade = periodo.getYears();
		
		CalcSeguro FATOR_IDADE;
		if(idade <= 30)
			FATOR_IDADE = CalcSeguro.FATOR_18_30;
		else if(idade <= 60)
			FATOR_IDADE = CalcSeguro.FATOR_30_60;
		else 
			FATOR_IDADE = CalcSeguro.FATOR_60_90;

		//System.out.println(idade);
		//System.out.println(CalcSeguro.VALOR_BASE.getFator() + " * " + FATOR_IDADE.getFator() + " * " + this.listaVeiculos.size());
		//System.out.println(CalcSeguro.VALOR_BASE.getFator() * FATOR_IDADE.getFator() * this.listaVeiculos.size());
				
		return CalcSeguro.VALOR_BASE.getFator() * FATOR_IDADE.getFator() * this.listaVeiculos.size();
	}
	
}