package Controllers;

import Models.Veiculo;
import Models.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoController {

    private Cliente cliente;
    private Veiculo veiculo;
    private static final String ARQUIVO = "./src/test/java/Archives/Veiculos.txt";

    public VeiculoController() {
    }

    public boolean cadastrarVeiculoPorCliente(String modelo, String placa, Cliente cliente) throws IOException {
        Veiculo veiculoAtual = new Veiculo(placa, cliente, modelo);

        if (buscarVeiculoPorPlaca(placa) != null) {
            System.out.println("Veículo com Placa " + veiculoAtual.getPlaca() + " já cadastrado.");
            return false;
        }

        salvarVeiculoArquivo(veiculoAtual);
        return true;
    }

    private boolean salvarVeiculoArquivo(Veiculo veiculo) throws IOException {
        File arquivo = new File(ARQUIVO);
        try {
            File diretorio = arquivo.getParentFile();
            if (diretorio != null && !diretorio.exists()) {
                diretorio.mkdir();
            }

            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
                bw.write(veiculo.getPlaca() + ";" + veiculo.getModelo() + ";" + veiculo.getCliente().getCpf() + ";" + veiculo.getCliente().getNome());
                bw.newLine();
                bw.flush();
                return true;
            }
        }catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) throws FileNotFoundException {
        try(BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))){
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                String placaVeiculo = dados[0];
                String modelo = dados[1];
                String cpfCliente = dados[2];
                String nomeCliente = dados[3];

                if (placa.equals(placaVeiculo)) {
                    return new Veiculo(placa, new Cliente(nomeCliente, cpfCliente), modelo);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<Veiculo> buscarVeiculosPorCliente(Cliente cliente) throws IOException {

        List<Veiculo> veiculos = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))){
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                String placaVeiculo = dados[0];
                String modelo = dados[1];
                String cpfCliente = dados[2];
                String nomeCliente = dados[3];

                if(cpfCliente.equals(cliente.getCpf())){
                    veiculos.add(new Veiculo(placaVeiculo, new Cliente(nomeCliente, cpfCliente), modelo));
                }
            }
        }catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }

        return veiculos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
}
