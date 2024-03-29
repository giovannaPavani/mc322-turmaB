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
import Veiculo.Veiculo;

import java.util.ArrayList;

public class ArquivoClientePF implements I_Arquivo{

	@Override
	public boolean gravarArquivo(ArrayList<Object> lista) {
		File file = new File("../lab06-seguradora_arquivos_v2/clientesPF.csv\"");
        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
           
            // escreve o cabeçalho
            bufferedWriter.write("CPF_CLIENTE,NOME_CLIENTE,TELEFONE_CLIENTE,ENDERECO_CLIENTE,EMAIL_CLIENTE,SEXO,ENSINO,DATA_NASCIMENTO,PLACA_VEICULO_CLIENTE1\n");
            
            // escreve os dados de um ClientePF por linha, separados por virgula
            for(Object object: lista) {
            	String linha = ""; 
            	linha += ((ClientePF)object).getCpf()+",";
            	linha += ((ClientePF)object).getNome()+",";
            	linha += ((ClientePF)object).getTelefone()+",";
            	linha += ((ClientePF)object).getEndereco()+",";
            	linha += ((ClientePF)object).getEmail()+",";
            	linha += ((ClientePF)object).getGenero()+",";
            	linha += ((ClientePF)object).getEducacao()+",";
            	linha += ((ClientePF)object).getDataNascimento()+",";
            	linha += ((ClientePF)object).getListaVeiculos().get(0).getPlaca()+"\n";
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
                System.out.println("Erro ao fechar o BufferedWriter ao salvar dados ClientePF");
            }
        }
        return false;
	}

	@Override
	public ArrayList<Object> lerArquivo() {
		String arquivoCSV = "../lab06-seguradora_arquivos_v2/clientesPF.csv";
	    BufferedReader br = null;
	    String linha = "";
	    String divisor = ",";
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    ArrayList<Object> listaClientesPF = new ArrayList<Object>();
	    
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
	            String placaVeiculo = dados[8]; // ARRUMAR - deveria pegar o veiculo da placa lida armazenado no arquivo de veiculos
	            
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
		   
	    // retorna lista de objetos do tipo de objeto lido (no caso, ClientePF)
	    return listaClientesPF;
	}

}
