package classes;

public class Main {

	public static void main(String [] args){ 
		
		// Testes - Cliente
		System.out.println("--- Cliente ---");
		Cliente c = new Cliente("Giovanna Pavani", "502.289.268-58", "09/08/2004", "18", "Avenida 1, 78 - Campinas-SP");
	    System.out.println(c.toString());
	    if(Cliente.validarCPF(c.getCpf()))
	    	System.out.println("CPF válido");
	    else
	    	System.out.println("CPF inválido");
	    
	    // Testes - Carro
	    System.out.println("--- Carro ---");
	    Veiculo v = new Veiculo("BRA2E19", "Honda", "Civic");
	    System.out.println(v.getPlaca());
	    System.out.println(v.getMarca());
	    System.out.println(v.getModelo());
	    
	    // Testes - Seguradora
	    System.out.println("--- Seguradora ---");
	    Seguradora s = new Seguradora("Azul", "(19)3257-8965", "azul@seguros.com.br", "Av. Orosimbo Maia, 98 - Campinas-SP");;
	    System.out.println(s.getNome());
	    System.out.println(s.getTelefone());
	    System.out.println(s.getEmail());
	    System.out.println(s.getEndereco());
	    
	    // Testes - Sinistro
	    System.out.println("--- Sinistro ---");
	    Sinistro sin = new Sinistro("16/02/2002", "Rua Martinho Santos, 441 - Campinas-SP");;
	    System.out.println(sin.getId());
	    System.out.println(sin.getData());
	    System.out.println(sin.getEndereco());
	}
}
