package src.Models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Cliente {

	private String nome;
	private String cpf;

	public Cliente(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
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

	File clientes = new File("codigo/src/Models/Archives/Clientes.txt");
	// Método para gravar os dados do cliente em um arquivo de texto
	public boolean gravarEmArquivo() {
		
		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(clientes))) {
			escritor.write("Cliente ID: " + this.id + "\n");
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
                    break;
                }
            }
            if (!clienteEncontrado) {
                System.out.println("Cliente com ID " + clienteId + " não encontrado.");
            }
			return true;
        } catch (IOException e) {
			return false;
        }
    }
}
