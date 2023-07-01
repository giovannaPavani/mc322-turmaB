package Arquivo;

import java.util.ArrayList;

public interface I_Arquivo {

	public abstract boolean gravarArquivo(ArrayList<Object> lista);
	// 		O parametro ArrayList<Object> lista é a lista de todos as instancias do objeto na seguradora que serão gravadas
	
	public abstract ArrayList<Object> lerArquivo();
	// 		Optei por retornar um array list de objects, que pode armazenar objetos de qualquer classe de forma genérica
	// ao inves da string, que teria que passar por mais um processamento
	// 		Alem disso, optei por nao passar String nomeArquivo por parametro, pois este é constante para cada classe,
	// portanto, ele está settado dentro de cada metodo
}
