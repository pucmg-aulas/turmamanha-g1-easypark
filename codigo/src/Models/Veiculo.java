package Models;

import java.io.*;
import Models.Cliente;

public class Veiculo {
    private String placa;
    private Cliente cliente;

    public Veiculo(String placa) {
        this.placa = placa;
        this.cliente = cliente;
    }

    public void adicionarCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getPlaca() {
        return placa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    File veiculo = new File("./src/Models/Archives/Veiculo.txt");

    // Método para gravar os dados do veículo em um arquivo de texto
    public boolean gravarEmArquivo() {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(veiculo))) {
            escritor.write("Veículo Placa: " + this.placa + "\n");
            escritor.write("Cliente CPF: " + this.cliente.getCpf() + "\n");
            escritor.write("Cliente Nome: " + this.cliente.getNome() + "\n");
            escritor.write("----------------------------------------------");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    //metodo para ler arquivos
	public boolean lerveiculoPorPlaca(String placaCarro) {
        try (BufferedReader leitor = new BufferedReader(new FileReader(veiculo))) {
            String linha;
            boolean veiculoEncontrado = false;
            while ((linha = leitor.readLine()) != null) {
                if (linha.contains("Placa: " + placaCarro)) {
                    veiculoEncontrado = true;
                    System.out.println(linha);
                    System.out.println(leitor.readLine());
                    break;
                }
            }
            if (!veiculoEncontrado) {
                System.out.println("placa " + placaCarro + " não encontrado.");
            }
			return true;
        } catch (IOException e) {
			return false;
        }
    }
  
}
