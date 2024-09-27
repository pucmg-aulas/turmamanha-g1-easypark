package src.Models;

public class Cliente {

    private String nome;
    private int id;

	private static int nextId = 1;

	public Cliente(String nome) {
		this.nome = nome;
		this.id = nextId;
		nextId++;

        
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