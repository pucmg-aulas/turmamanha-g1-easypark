package Models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	public String getCpf() {
		return this.cpf;
	}

	File clientes = new File("./codigo/src/Archives/Clientes.txt");

	// Método para gravar os dados do cliente em um arquivo de texto
	public boolean gravarEmArquivo() {

		File arquivo = new File("./codigo/src/Archives/Clientes.txt");

		try {

			File diretorio = arquivo.getParentFile();
			if (diretorio != null && !diretorio.exists()) {
				diretorio.mkdirs();  // Cria o diretório se ele não existir
			}

			// Cria o arquivo se ele não existir
			if (!arquivo.exists()) {
				arquivo.createNewFile(); // Cria o arquivo
			}

			try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, true))) {
				escritor.write("CPF: " + this.cpf + "\n");
				escritor.write("Nome: " + this.nome + "\n");
				escritor.write("----------------------------------------\n");
			}
				return true;
		} catch (IOException e) {
				e.printStackTrace();
				return false;
		}
	}

	//metodo para ler arquivos
	public static List<String> lerClientesDoArquivo() {
		File arquivo = new File("./codigo/src/Archives/Clientes.txt");
		List<String> clientes = new ArrayList<>();

		try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
			String linha;
			StringBuilder clienteAtual = new StringBuilder();

			// Lê cada linha do arquivo
			while ((linha = leitor.readLine()) != null) {
				clienteAtual.append(linha).append("\n");

				// Verifica se o final do cliente foi encontrado
				if (linha.equals("----------------------------------------")) {
					clientes.add(clienteAtual.toString());
					clienteAtual.setLength(0); // Limpa o builder para o próximo cliente
				}
			}

		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
		}

		return clientes;
	}
}
