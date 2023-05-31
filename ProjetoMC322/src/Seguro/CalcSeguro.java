package Seguro;

public enum CalcSeguro {
	
	// constantes de valor base e fator de idade
	VALOR_BASE (10.0),
	FATOR_18_30 (1.25),
	FATOR_30_60 (1.0),
	FATOR_60_90 (1.5);
	
	// atributo
	public final double fator;
	
	// construtor
	CalcSeguro(double fator){
		this.fator = fator;
	}
	
	// getter
	public double getFator() {
		return this.fator;
	}
}
