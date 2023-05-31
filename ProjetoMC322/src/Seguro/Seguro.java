package Seguro;

import java.time.LocalDate;
import java.util.LinkedList;
import Seguradora.Seguradora;
import Sinistro.Sinistro;

public abstract class Seguro {

	private final int id;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	private Seguradora seguradora;
	private LinkedList<Sinistro> listaSinistros;
	private LinkedList<Sinistro> listacondutores;
	private double valorMensal;
	
	public Seguro(int id, LocalDate dataInicio, LocalDate dataFim,
				  Seguradora seguradora, LinkedList<Sinistro> listaSinistros,
				  LinkedList<Sinistro> listacondutores) {
		this.id = id;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.seguradora = seguradora;
		this.listaSinistros = listaSinistros;
		this.listacondutores = listacondutores;
		calcularValor();
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

	public LinkedList<Sinistro> getListacondutores() {
		return listacondutores;
	}

	public void setListacondutores(LinkedList<Sinistro> listacondutores) {
		this.listacondutores = listacondutores;
	}

	public double getValorMensal() {
		return valorMensal;
	}

	public void setValorMensal(double valorMensal) {
		this.valorMensal = valorMensal;
	}
	
	public abstract boolean desautorizarCondutor();
	
	public abstract boolean autorizarCondutor();
	
	public abstract void calcularValor();
	
	public abstract void gerarSinistro();
	
	public abstract String toString();
}
