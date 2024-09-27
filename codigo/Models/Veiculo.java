package Models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    // Método para gravar os dados do veículo em um arquivo de texto
    public void gravarEmArquivo() {
        File veiculo = new File("veiculo.txt");

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(veiculo))) {
            escritor.write("Veículo Placa: " + this.placa + "\n");
            escritor.write("Cliente ID: " + this.cliente.getId() + "\n");
            escritor.write("Cliente Nome: " + this.cliente.getNome() + "\n");
            System.out.println("Dados do veículo gravados em " + veiculo.getName());
        } catch (IOException e) {
            System.out.println("Erro ao gravar no arquivo: " + e.getMessage());
        }
    }
}
