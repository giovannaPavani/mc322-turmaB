package Arquivo;

public interface I_Arquivo {

	public abstract boolean gravarArquivo(String nomeArquivo);
	
	public abstract String lerArquivo(String nomeArquivo);
}
