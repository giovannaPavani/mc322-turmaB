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

import Cliente.ClientePF;
import Condutor.Condutor;
import Sinistro.Sinistro;

import java.util.ArrayList;

public class ArquivoCondutor implements I_Arquivo{

	@Override
	public boolean gravarArquivo(ArrayList<Object> lista) {
		File file = new File("../lab06-seguradora_arquivos_v2/condutores.csv\"");
        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
           
            // escreve o cabeçalho
            bufferedWriter.write("CPF_CONDUTOR,NOME_CONDUTOR,TELEFONE,ENDERECO,EMAIL,DATA_NASCIMENTO\n");
            
            // escreve os dados de um ClientePF por linha, separados por virgula
            for(Object object: lista) {
            	String linha = ""; 
            	linha += ((Condutor)object).getCpf()+",";
            	linha += ((Condutor)object).getNome()+",";
            	linha += ((Condutor)object).getTelefone()+",";
            	linha += ((Condutor)object).getEndereco()+",";
            	linha += ((Condutor)object).getEmail()+",";
            	linha += ((Condutor)object).getDataNascimento()+"\n";
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
                System.out.println("Erro ao fechar o BufferedWriter ao salvar dados Condutor");
            }
        }
        return false;
	}

	@Override
	public ArrayList<Object> lerArquivo() {
		
		String arquivoCSV = "../lab06-seguradora_arquivos_v2/condutores.csv";
	    BufferedReader br = null;
	    String linha = "";
	    String divisor = ",";
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    ArrayList<Object> listaCondutores = new ArrayList<Object>();
	    
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
	            LocalDate dataNascimento = LocalDate.parse(dados[5], formatter);
	            
	            Condutor condutor = new Condutor(cpf, nome, telefone, endereco, email, dataNascimento, new LinkedList<Sinistro>());
	            listaCondutores.add(condutor);
	        }

	    } catch (FileNotFoundException e) {
	    	System.err.println("Arquivo para leitura de Condutor não encontrado.\n"
	    					 + "Verifique se ele está localizado em <ProjetoMC322\\lab06-seguradora_arquivos_v2\\condutores.csv> para utilizar o programa.");
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
	            	System.err.println("Ocorreu um erro ao fechar o arquivo de Condutor.\n"
	            					 + "Verifique se ele está localizado em <ProjetoMC322\\lab06-seguradora_arquivos_v2\\condutores.csv> para utilizar o programa.");
	                e.printStackTrace();
	            }
	        }
	    }
		   
	    // retorna lista de objetos do tipo de objeto lido (no caso, Condutor)
	    return listaCondutores;
	}

}
