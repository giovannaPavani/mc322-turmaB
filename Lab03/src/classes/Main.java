package classes;

public class Main {

	public static void main(String [] args){ 
		
		// Testes - Cliente
		System.out.println("--- Cliente ---");
		//Cliente c = new Cliente("Giovanna Pavani", "502.289.268-58", "09/08/2004", "18", "Avenida 1, 78 - Campinas-SP");
	    /*System.out.println(c.toString());
	    if(Cliente.validarCPF(c.getCpf()))
	    	System.out.println("CPF válido\n");
	    else
	    	System.out.println("CPF inválido\n");
	    */
	    
	    // Testes - Carro
	    System.out.println("--- Carro ---");
	    Veiculo v = new Veiculo("BRA2E19", "Honda", "Civic");
	    System.out.println("Placa: "+v.getPlaca());
	    System.out.println("Marca: "+v.getMarca());
	    System.out.println("Modelo: "+v.getModelo()+"\n");
	    
	    // Testes - Seguradora
	    System.out.println("--- Seguradora ---");
	    Seguradora s = new Seguradora("Azul", "(19)3257-8965", "azul@seguros.com.br", "Av. Orosimbo Maia, 98 - Campinas-SP");;
	    System.out.println("Nome: "+s.getNome());
	    System.out.println("Telefone: "+s.getTelefone());
	    System.out.println("Email: "+s.getEmail());
	    System.out.println("Endereco: "+s.getEndereco()+"\n");
	    
	    // Testes - Sinistro
	    System.out.println("--- Sinistro ---");
	    //Sinistro sin = new Sinistro("16/02/2002", "Rua Martinho Santos, 441 - Campinas-SP");;
	    //System.out.println("Id: "+sin.getId());
	    //System.out.println("Data: "+sin.getData());
	    //System.out.println("Endereco: "+sin.getEndereco());
	}
}
