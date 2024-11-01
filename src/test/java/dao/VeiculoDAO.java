package dao;

import Models.Cliente;
import Models.Veiculo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {
    private List<Veiculo> veiculos;
    private static VeiculoDAO instance;
    private final String Arquivo = "./src/test/java/Archives/Veiculos.txt";
    
    private VeiculoDAO(){
        veiculos = lerVeiculos();
        if(veiculos == null){
            veiculos = new ArrayList<>();
        }
    }
    
    public static VeiculoDAO getInstance(){
        if(instance == null){
            instance = new VeiculoDAO();
        }
        return instance;
    }
    
    public boolean cadastrarVeiculoPorCliente(String modelo, String placa, Cliente cliente) throws IOException {
        Veiculo veiculoAtual = new Veiculo(placa, cliente, modelo);

        if (buscarVeiculoPorPlaca(placa) != null) {
            System.out.println("Veículo com Placa " + veiculoAtual.getPlaca() + " já cadastrado.");
            return false;
        }
        
        veiculos.add(veiculoAtual);
        salvarVeiculoArquivo(veiculos);
        return true;
    }
    
    public boolean cadastrarVeiculoPorCliente(Veiculo veiculo) throws IOException {
        if (buscarVeiculoPorPlaca(veiculo.getPlaca()) != null) {
            System.out.println("Veículo com Placa " + veiculo.getPlaca() + " já cadastrado.");
            return false;
        }
        
        veiculos.add(veiculo);
        salvarVeiculoArquivo(veiculos);
        return true;
    }

    private boolean salvarVeiculoArquivo(List<Veiculo> listaVeiculo) throws IOException {
        File arquivo = new File(Arquivo);
        try {
            File diretorio = arquivo.getParentFile();
            if (diretorio != null && !diretorio.exists()) {
                diretorio.mkdir();
            }

            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(Arquivo))) {
                for(Veiculo veiculo : listaVeiculo){
                    bw.write(veiculo.getPlaca() + ";" + veiculo.getModelo() + ";" + veiculo.getCliente().getCpf() + ";" + veiculo.getCliente().getNome());
                    bw.newLine();
                    bw.flush();
                }
                
                return true;
            }
        }catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) throws FileNotFoundException {
        try(BufferedReader br = new BufferedReader(new FileReader(Arquivo))){
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
        try(BufferedReader br = new BufferedReader(new FileReader(Arquivo))){
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
    
   public List<Veiculo> lerVeiculos() {
        List<Veiculo> veiculosLista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(Arquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length < 4) {
                    System.out.println("Linha inválida no arquivo de veículos: " + linha);
                    continue;
                }
                String placa = dados[0];
                String modelo = dados[1];
                String cpfCliente = dados[2];
                String nomeCliente = dados[3];
                Veiculo novoVeiculo = new Veiculo(placa, new Cliente(nomeCliente, cpfCliente), modelo);
                veiculosLista.add(novoVeiculo);
            }

            return veiculosLista;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
