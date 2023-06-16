package Arquivo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.IllegalFormatConversionException;
import java.util.LinkedList;

import Cliente.ClientePF;
import Veiculo.Veiculo;

import java.util.ArrayList;

public class ArquivoClientePF implements I_Arquivo{

	@Override
	public boolean gravarArquivo(String nomeArquivo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String lerArquivo(String nomeArquivo) {
		// C:\Users\Usuario\Documents\eclipse-workspace\mc322-turmaB\ProjetoMC322\src\Main
		// C:\Users\Usuario\Documents\eclipse-workspace\mc322-turmaB\ProjetoMC322\lab06-seguradora_arquivos_v2
		String arquivoCSV = "../lab06-seguradora_arquivos_v2/clientesPF.csv";
	    BufferedReader br = null;
	    String linha = "";
	    String divisor = ",";
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    ArrayList<ClientePF> listaClientesPF = new ArrayList<ClientePF>();
	    
	    try {

	        br = new BufferedReader(new FileReader(arquivoCSV));
	        // pula a primeira linha de cabeçalhos
	        linha = br.readLine();
	        while ((linha = br.readLine()) != null) {

	            String[] dados = linha.split(divisor);
	            
	            String cpf = dados[0];
	            String nome = dados[1];
	            String telefone = dados[2];
	            String endereco = dados[3];
	            String email = dados[4];
	            String sexo = dados[5];
	            String ensino = dados[6];
	            LocalDate dataNascimento = LocalDate.parse(dados[7], formatter);
	            String placaVeiculo = dados[8]; // ARRUMAR
	            
	            ClientePF cliente = new ClientePF(nome, endereco, telefone, email, cpf, sexo, ensino,
	            								  dataNascimento, new LinkedList<Veiculo>());
	            listaClientesPF.add(cliente);
	        }

	    } catch (FileNotFoundException e) {
	    	System.err.println("Arquivo para leitura de ClientePF não encontrado.\n"
	    					 + "Verifique se ele está localizado em <ProjetoMC322\\lab06-seguradora_arquivos_v2\\clientesPF.csv> para utilizar o programa.");
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }  catch(IllegalFormatConversionException e){
	    	System.err.println("Erro de conversão de tipo: algum dado não foi registrado de forma correta");
	    	e.printStackTrace();
	    }  finally {
	        if (br != null) {
	            try {
	                br.close();
	            } catch (IOException e) {
	            	System.err.println("Ocorreu um erro ao fechar o arquivo de ClientePF.\n"
	            					 + "Verifique se ele está localizado em <ProjetoMC322\\lab06-seguradora_arquivos_v2\\clientesPF.csv> para utilizar o programa.");
	                e.printStackTrace();
	            }
	        }
	    }
		    
	    return listaClientesPF;
	}

}
