package Models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

	// Método para gravar os dados do cliente em um arquivo de texto
	public void gravarEmArquivo() {
		File clientes = new File("Clientes.txt");

		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(clientes))) {
			escritor.write("Cliente ID: " + this.id + "\n");
			escritor.write("Nome: " + this.nome + "\n");
			System.out.println("Dados do cliente gravados em " + clientes.getName());
		} catch (IOException e) {
			System.out.println("Erro ao gravar no arquivo: " + e.getMessage());
		}
	}
}
