package codigo;

public class Cliente {


    private String nome;
    private int id;


	public Cliente(String nome, int id) {
		this.nome = nome;
		this.id = id;

        
	}

    public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}


    
}