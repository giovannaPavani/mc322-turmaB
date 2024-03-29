package Arquivo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.IllegalFormatConversionException;

import Frota.Frota;
import Veiculo.Veiculo;
import java.util.ArrayList;

public class ArquivoVeiculo implements I_Arquivo{

	@Override
	public boolean gravarArquivo(ArrayList<Object> lista) {
		File file = new File("../lab06-seguradora_arquivos_v2/veiculos.csv\"");
        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
           
            // escreve o cabeçalho
            bufferedWriter.write("PLACA,MARCA,MODELO,ANO_FAB\n");
            
            // escreve os dados de um ClientePF por linha, separados por virgula
            for(Object object: lista) {
            	String linha = ""; 
            	linha += ((Veiculo)object).getPlaca()+",";
            	linha += ((Veiculo)object).getModelo()+",";
            	linha += ((Veiculo)object).getMarca()+",";
            	linha += ((Veiculo)object).getAnoFabricacao()+"\n";
            	bufferedWriter.write(linha);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();

        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                    return true;
                }
            } catch (Exception ex) {
                System.out.println("Erro ao fechar o BufferedWriter ao salvar dados Veiculo");
            }
        }
        return false;
	}

	@Override
	public ArrayList<Object> lerArquivo() {
		
		String arquivoCSV = "../lab06-seguradora_arquivos_v2/veiculos.csv";
	    BufferedReader br = null;
	    String linha = "";
	    String divisor = ",";
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    ArrayList<Object> listaVeiculos = new ArrayList<Object>();
	    
	    try {

	        br = new BufferedReader(new FileReader(arquivoCSV));
	        // pula a primeira linha de cabeçalhos
	        linha = br.readLine();
	        while ((linha = br.readLine()) != null) {

	            String[] dados = linha.split(divisor);
	            
	            String placa = dados[0];
	            String marca = dados[1];
	            String modelo = dados[2];
	            int anoFab = Integer.parseInt(dados[3]);
	            
	            Veiculo veiculo = new Veiculo(placa, marca, modelo, anoFab);
	            listaVeiculos.add(veiculo);
	        }

	    } catch (FileNotFoundException e) {
	    	System.err.println("Arquivo para leitura de Veiculo não encontrado.\n"
	    					 + "Verifique se ele está localizado em <ProjetoMC322\\lab06-seguradora_arquivos_v2\\veiculos.csv> para utilizar o programa.");
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
	            	System.err.println("Ocorreu um erro ao fechar o arquivo de Veiculo.\n"
	            					 + "Verifique se ele está localizado em <ProjetoMC322\\lab06-seguradora_arquivos_v2\\veiculos.csv> para utilizar o programa.");
	                e.printStackTrace();
	            }
	        }
	    }
		   
	    // retorna lista de objetos do tipo de objeto lido (no caso, Veiculo)
	    return listaVeiculos;
	}

}
