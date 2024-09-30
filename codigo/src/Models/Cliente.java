package Models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Cliente {

	private String nome;
	private String cpf;
	private int id;
	private static int nextId = 1;

	public Cliente(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
		this.id = nextId;
		nextId++;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return this.cpf;
	}

	File clientes = new File("./src/Models/Archives/Clientes.txt");

	// Método para gravar os dados do cliente em um arquivo de texto
	public boolean gravarEmArquivo() {

		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(clientes))) {
			escritor.write("Cliente ID: " + this.cpf + "\n");
			escritor.write("Nome: " + this.nome + "\n");
			escritor.write("----------------------------------------");
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	//metodo para ler arquivos
	public boolean lerClientePorId(int clienteId) {
		try (BufferedReader leitor = new BufferedReader(new FileReader(clientes))) {
			String linha;
			boolean clienteEncontrado = false;
			while ((linha = leitor.readLine()) != null) {
				if (linha.contains("Cliente ID: " + clienteId)) {
					clienteEncontrado = true;
					System.out.println(linha);
					System.out.println(leitor.readLine());
					System.out.println(leitor.readLine());
					return true;
				}
			}

		} catch (IOException e) {
			return false;
		}
		return false;
	}
}
