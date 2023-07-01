package Arquivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.IllegalFormatConversionException;
import java.util.LinkedList;

import Condutor.Condutor;
import Frota.Frota;
import Veiculo.Veiculo;

import java.util.ArrayList;

public class ArquivoFrota implements I_Arquivo{

	@Override
	public boolean gravarArquivo(ArrayList<Object> lista) {
		File file = new File("../lab06-seguradora_arquivos_v2/frotas.csv\"");
        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
           
            // escreve o cabeçalho
            bufferedWriter.write("ID_FROTA,PLACA_VEICULO1,PLACA_VEICULO2,PLACA_VEICULO3\n");
            
            // escreve os dados de um ClientePF por linha, separados por virgula
            for(Object object: lista) {
            	String linha = ""; 
            	linha += ((Frota)object).getCode()+",";
            	// supondo que sempre há exatamente 3 carros na frota, como no arquivo frotas.csv dado
            	linha += ((Frota)object).getListaVeiculos().get(0).getPlaca()+",";
            	linha += ((Frota)object).getListaVeiculos().get(1).getPlaca()+",";
            	linha += ((Frota)object).getListaVeiculos().get(2).getPlaca()+"\n";
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
                System.out.println("Erro ao fechar o BufferedWriter ao salvar dados Frota");
            }
        }
        return false;
	}

	@Override
	public ArrayList<Object> lerArquivo() {
		
		String arquivoCSV = "../lab06-seguradora_arquivos_v2/frotas.csv";
	    BufferedReader br = null;
	    String linha = "";
	    String divisor = ",";
	    ArrayList<Object> listaFrotas = new ArrayList<Object>();
	    
	    try {

	        br = new BufferedReader(new FileReader(arquivoCSV));
	        // pula a primeira linha de cabeçalhos
	        linha = br.readLine();
	        while ((linha = br.readLine()) != null) {

	            String[] dados = linha.split(divisor);
	            
	            String id = dados[0];
	            String placaVeiculo1 = dados[1];
	            String placaVeiculo2 = dados[2];
	            String placaVeiculo3 = dados[3];
	            // ARRUMAR - deveria pegar os veiculo das placas lidas armazenado no arquivo de veiculos
	            // add na lista de veiculos e aí sim instanciar e add na lista de frotas
	            
	            Frota frota = new Frota(id, new LinkedList<Veiculo>());
	            listaFrotas.add(frota);
	        }

	    } catch (FileNotFoundException e) {
	    	System.err.println("Arquivo para leitura de Frota não encontrado.\n"
	    					 + "Verifique se ele está localizado em <ProjetoMC322\\lab06-seguradora_arquivos_v2\\frotas.csv> para utilizar o programa.");
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
	            	System.err.println("Ocorreu um erro ao fechar o arquivo de Frota.\n"
	            					 + "Verifique se ele está localizado em <ProjetoMC322\\lab06-seguradora_arquivos_v2\\frotas.csv> para utilizar o programa.");
	                e.printStackTrace();
	            }
	        }
	    }
		   
	    // retorna lista de objetos do tipo de objeto lido (no caso, Frota)
	    return listaFrotas;
	}

}
