package Models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import Models.Cliente;

public class Veiculo{
    private String placa;
    private Cliente cliente;
    private String modelo;
    File veiculoArquivo = new File("./codigo/src/Archives/Veiculo.txt");

    public Veiculo(String placa, Cliente cliente, String modelo) {
        this.placa = placa;
        this.cliente = cliente;
        this.modelo = modelo;
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

    public String getModelo() {
        return modelo;  // Método para obter o modelo
    }

    // Método para gravar os dados do veículo em um arquivo de texto
    public boolean gravarEmArquivo() {
        try {
            // Cria o arquivo se não existir e escreve em modo de apêndice
            File diretorio = veiculoArquivo.getParentFile();
            if (diretorio != null && !diretorio.exists()) {
                diretorio.mkdirs(); // Cria o diretório se ele não existir
            }

            if (!veiculoArquivo.exists()) {
                veiculoArquivo.createNewFile(); // Cria o arquivo se ele não existir
            }

            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(veiculoArquivo, true))) {
                escritor.write("Placa: " + this.placa + "\n");
                escritor.write("Modelo: " + this.modelo + "\n");
                escritor.write("CPF: " + this.cliente.getCpf() + "\n");
                escritor.write("Cliente: " + this.cliente.getNome() + "\n");
                escritor.write("----------------------------------------------\n");
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //metodo para ler arquivos
    public boolean lerVeiculoPorPlaca(String placaCarro) {
        try (BufferedReader leitor = new BufferedReader(new FileReader(veiculoArquivo))) {
            String linha;
            boolean veiculoEncontrado = false;
            while ((linha = leitor.readLine()) != null) {
                if (linha.contains("Placa: " + placaCarro)) {
                    veiculoEncontrado = true;
                    System.out.println(linha);
                    System.out.println(leitor.readLine());  // CPF do cliente
                    System.out.println(leitor.readLine());  // Nome do cliente
                    break;
                }
            }
            if (!veiculoEncontrado) {
                System.out.println("Placa " + placaCarro + " não encontrada.");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> lerVeiculosDoArquivo() {
        File arquivo = new File("./codigo/src/Archives/Veiculo.txt");
        List<String> veiculos = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            StringBuilder veiculoAtual = new StringBuilder();

            // Lê cada linha do arquivo e armazena em um StringBuilder até encontrar a separação
            while ((linha = leitor.readLine()) != null) {
                veiculoAtual.append(linha).append("\n");

                // Verifica se chegou no final de um veículo (linha de separação)
                if (linha.equals("----------------------------------------------")) {
                    veiculos.add(veiculoAtual.toString()); // Adiciona o veículo à lista
                    veiculoAtual.setLength(0); // Limpa o builder para o próximo veículo
                }
            }

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
        }

        return veiculos;
    }

    public static void exibirVeiculosPorCpf(String cpf) {
        File arquivo = new File("./codigo/src/Archives/Veiculo.txt");

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean veiculoEncontrado = false;

            // Lê cada linha do arquivo para encontrar veículos pelo CPF
            while ((linha = leitor.readLine()) != null) {
                if (linha.contains("CPF: " + cpf)) {
                    veiculoEncontrado = true;

                    // Avança para a linha anterior que contém a placa e o modelo
                    String placa = leitor.readLine().replace("Placa: ", "").trim();
                    String modelo = leitor.readLine().replace("Modelo: ", "").trim();

                    // Exibe apenas a placa e o modelo do veículo
                    System.out.println("Placa: " + placa);
                    System.out.println("Modelo: " + modelo);
                    System.out.println("----------------------------------------------");

                    // Ignora as próximas linhas (Cliente CPF e Cliente Nome)
                    leitor.readLine(); // Nome do cliente
                    leitor.readLine(); // Linha de separação
                }
            }

            if (!veiculoEncontrado) {
                System.out.println("Nenhum veículo encontrado para o CPF: " + cpf);
            }

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
        }
    }

}
