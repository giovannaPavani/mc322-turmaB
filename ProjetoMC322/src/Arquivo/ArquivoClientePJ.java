package Arquivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.IllegalFormatConversionException;
import java.util.LinkedList;
import Cliente.ClientePJ;
import Frota.Frota;
import java.util.ArrayList;

public class ArquivoClientePJ implements I_Arquivo{

	@Override
	public boolean gravarArquivo(ArrayList<Object> lista) {
		File file = new File("../lab06-seguradora_arquivos_v2/clientesPJ.csv\"");
        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
           
            // escreve o cabeçalho
            bufferedWriter.write("CNPJ_CLIENTE,NOME_CLIENTE,TELEFONE,ENDERECO,EMAIL_CLIENTE,DATA_FUND,CODE_FROTA\n");
            
            // escreve os dados de um ClientePJ por linha, separados por virgula
            for(Object object: lista) {
            	String linha = ""; 
            	linha += ((ClientePJ)object).getCnpj()+",";
            	linha += ((ClientePJ)object).getNome()+",";
            	linha += ((ClientePJ)object).getTelefone()+",";
            	linha += ((ClientePJ)object).getEndereco()+",";
            	linha += ((ClientePJ)object).getEmail()+",";
            	linha += ((ClientePJ)object).getDataFundacao().toString()+","; // falta formatar certo
            	linha += ((ClientePJ)object).getListaFrotas().get(0).getCode()+"\n";
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
                System.out.println("Erro ao fechar o BufferedWriter ao salvar dados ClientePJ");
            }
        }
        return false;
	}

	@Override
	public ArrayList<Object> lerArquivo() {
		
		String arquivoCSV = "../lab06-seguradora_arquivos_v2/clientesPJ.csv";
	    BufferedReader br = null;
	    String linha = "";
	    String divisor = ",";
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    ArrayList<Object> listaClientesPJ = new ArrayList<Object>();
	    
	    try {

	        br = new BufferedReader(new FileReader(arquivoCSV));
	        // pula a primeira linha de cabeçalhos
	        linha = br.readLine();
	        while ((linha = br.readLine()) != null) {

	            String[] dados = linha.split(divisor);
	            
	            String cnpj = dados[0];
	            String nome = dados[1];
	            String telefone = dados[2];
	            String endereco = dados[3];
	            String email = dados[4];
	            LocalDate dataFundacao = LocalDate.parse(dados[5], formatter);
	            String codeFrota = dados[6]; // ARRUMAR - deveria pegar a frota do code lido armazenado no arquivo de frotas
	            
	            ClientePJ cliente = new ClientePJ(nome, endereco, telefone, email, cnpj, dataFundacao, new LinkedList<Frota>(), 0);
	            listaClientesPJ.add(cliente);
	        }

	    } catch (FileNotFoundException e) {
	    	System.err.println("Arquivo para leitura de ClientePJ não encontrado.\n"
	    					 + "Verifique se ele está localizado em <ProjetoMC322\\lab06-seguradora_arquivos_v2\\clientesPJ.csv> para utilizar o programa.");
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
	            	System.err.println("Ocorreu um erro ao fechar o arquivo de ClientePJ.\n"
	            					 + "Verifique se ele está localizado em <ProjetoMC322\\lab06-seguradora_arquivos_v2\\clientesPJ.csv> para utilizar o programa.");
	                e.printStackTrace();
	            }
	        }
	    }
		   
	    // retorna lista de objetos do tipo de objeto lido (no caso, ClientePJ)
	    return listaClientesPJ;
	}

}
