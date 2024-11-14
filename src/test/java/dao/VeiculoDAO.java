package dao;

import Models.Cliente;
import Models.Veiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeiculoDAO {

    private List<Veiculo> veiculos;
    private ClientebdDAO clientes;
    private static VeiculoDAO instance;
    private BancoDados bancoDados;

    private VeiculoDAO() {
        bancoDados = BancoDados.getInstancia();
        clientes = ClientebdDAO.getInstance();
        veiculos = lerVeiculos();
        if (veiculos == null) {
            veiculos = new ArrayList<>();
        }
    }

    public static VeiculoDAO getInstance() {
        if (instance == null) {
            instance = new VeiculoDAO();
        }
        return instance;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public boolean cadastrarVeiculoPorCliente(String modelo, String placa, Cliente cliente) {
        Veiculo veiculoAtual = new Veiculo(placa, cliente, modelo);

        if (buscarVeiculoPorPlaca(placa) != null) {
            System.out.println("Veículo com Placa " + veiculoAtual.getPlaca() + " já cadastrado.");
            return false;
        }

        veiculos.add(veiculoAtual);
        salvarVeiculoBanco(veiculoAtual);
        return true;
    }

    public boolean cadastrarVeiculoPorCliente(Veiculo veiculo) {
        if (buscarVeiculoPorPlaca(veiculo.getPlaca()) != null) {
            System.out.println("Veículo com Placa " + veiculo.getPlaca() + " já cadastrado.");
            return false;
        }

        veiculos.add(veiculo);
        salvarVeiculoBanco(veiculo);
        return true;
    }

    private boolean salvarVeiculoBanco(Veiculo veiculo) {
        String sql = "INSERT INTO veiculo (placa, modelo, cpf_cliente) VALUES (?, ?, ?)";
        try (Connection conn = BancoDados.getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, veiculo.getPlaca());
            pstmt.setString(2, veiculo.getModelo());
            pstmt.setString(3, veiculo.getCliente().getCpf());

            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) {
        String sql = "SELECT * FROM veiculo WHERE placa = ?";

        try (Connection conn = BancoDados.getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, placa);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String modelo = rs.getString("modelo");
                String cpfCliente = rs.getString("cpf_cliente");

                Cliente clienteAtual = clientes.buscarClientePorCpf(cpfCliente);

                return new Veiculo(placa, clienteAtual, modelo);
            }
        } catch (SQLException e) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }

    public List<Veiculo> buscarVeiculosPorCliente(Cliente cliente) {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM veiculo WHERE cpf_cliente = ?";

        try (Connection conn = BancoDados.getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getCpf());
            String cpfCliente = cliente.getCpf();
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String placa = rs.getString("placa");
                String modelo = rs.getString("modelo");
                
                Cliente clienteAtual = clientes.buscarClientePorCpf(cpfCliente);
                
                veiculos.add(new Veiculo(placa, clienteAtual, modelo));
            }
        } catch (SQLException e) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return veiculos;
    }

    public List<Veiculo> lerVeiculos() {
        List<Veiculo> veiculosLista = new ArrayList<>();
        String sql = "SELECT * FROM veiculo";

        try (Connection conn = BancoDados.getConexao(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String placa = rs.getString("placa");
                String modelo = rs.getString("modelo");
                String cpfCliente = rs.getString("cpf_cliente");
                Cliente clienteAtual = clientes.buscarClientePorCpf(cpfCliente);
                
                Veiculo novoVeiculo = new Veiculo(placa, clienteAtual, modelo);
                veiculosLista.add(novoVeiculo);
            }
        } catch (SQLException e) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return veiculosLista;
    }
}

//package dao;
//
//import Models.Cliente;
//import Models.Veiculo;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class VeiculoDAO {
//    private List<Veiculo> veiculos;
//    private static VeiculoDAO instance;
//    private final String Arquivo = "./src/test/java/Archives/Veiculos.txt";
//    
//    private VeiculoDAO(){
//        veiculos = lerVeiculos();
//        if(veiculos == null){
//            veiculos = new ArrayList<>();
//        }
//    }
//    
//    public static VeiculoDAO getInstance(){
//        if(instance == null){
//            instance = new VeiculoDAO();
//        }
//        return instance;
//    }
//    
//    public List<Veiculo> getVeiculos() {
//    return veiculos; 
//}
//    
//    public boolean cadastrarVeiculoPorCliente(String modelo, String placa, Cliente cliente) throws IOException {
//        Veiculo veiculoAtual = new Veiculo(placa, cliente, modelo);
//
//        if (buscarVeiculoPorPlaca(placa) != null) {
//            System.out.println("Veículo com Placa " + veiculoAtual.getPlaca() + " já cadastrado.");
//            return false;
//        }
//        
//        veiculos.add(veiculoAtual);
//        salvarVeiculoArquivo(veiculos);
//        return true;
//    }
//    
//    public boolean cadastrarVeiculoPorCliente(Veiculo veiculo) throws IOException {
//        if (buscarVeiculoPorPlaca(veiculo.getPlaca()) != null) {
//            System.out.println("Veículo com Placa " + veiculo.getPlaca() + " já cadastrado.");
//            return false;
//        }
//        
//        veiculos.add(veiculo);
//        salvarVeiculoArquivo(veiculos);
//        return true;
//    }
//
//    private boolean salvarVeiculoArquivo(List<Veiculo> listaVeiculo) throws IOException {
//        File arquivo = new File(Arquivo);
//        try {
//            File diretorio = arquivo.getParentFile();
//            if (diretorio != null && !diretorio.exists()) {
//                diretorio.mkdir();
//            }
//
//            if (!arquivo.exists()) {
//                arquivo.createNewFile();
//            }
//
//            try (BufferedWriter bw = new BufferedWriter(new FileWriter(Arquivo))) {
//                for(Veiculo veiculo : listaVeiculo){
//                    bw.write(veiculo.getPlaca() + ";" + veiculo.getModelo() + ";" + veiculo.getCliente().getCpf() + ";" + veiculo.getCliente().getNome());
//                    bw.newLine();
//                    bw.flush();
//                }
//                
//                return true;
//            }
//        }catch (IOException ex) {
//            throw new IOException(ex.getMessage());
//        }
//    }
//
//    public Veiculo buscarVeiculoPorPlaca(String placa) throws FileNotFoundException {
//        try(BufferedReader br = new BufferedReader(new FileReader(Arquivo))){
//            String linha;
//
//            while ((linha = br.readLine()) != null) {
//                String[] dados = linha.split(";");
//                
//                if(dados.length < 4){
//                    continue;
//                }
//                
//                String placaVeiculo = dados[0];
//                String modelo = dados[1];
//                String cpfCliente = dados[2];
//                String nomeCliente = dados[3];
//
//                if (placa.equals(placaVeiculo)) {
//                    return new Veiculo(placa, new Cliente(nomeCliente, cpfCliente), modelo);
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return null;
//    }
//
//    public List<Veiculo> buscarVeiculosPorCliente(Cliente cliente) throws IOException {
//
//        List<Veiculo> veiculos = new ArrayList<>();
//        try(BufferedReader br = new BufferedReader(new FileReader(Arquivo))){
//            String linha;
//
//            while ((linha = br.readLine()) != null) {
//                linha = linha.trim();
//                String[] dados = linha.split(";");
//                if(dados.length < 4){
//                    continue;
//                }
//                String placaVeiculo = dados[0];
//                String modelo = dados[1];
//                String cpfCliente = dados[2];
//                String nomeCliente = dados[3];
//                
//                if(cpfCliente.equals(cliente.getCpf())){
//                    veiculos.add(new Veiculo(placaVeiculo, new Cliente(nomeCliente, cpfCliente), modelo));
//                }
//            }
//        }catch (IOException ex) {
//            throw new IOException(ex.getMessage());
//        }
//
//        return veiculos;
//    }
//    
//   public List<Veiculo> lerVeiculos() {
//        List<Veiculo> veiculosLista = new ArrayList<>();
//
//        try (BufferedReader br = new BufferedReader(new FileReader(Arquivo))) {
//            String linha;
//
//            while ((linha = br.readLine()) != null) {
//                String[] dados = linha.split(";");
//                if (dados.length < 4) {
//                    System.out.println("Linha inválida no arquivo de veículos: " + linha);
//                    continue;
//                }
//                String placa = dados[0];
//                String modelo = dados[1];
//                String cpfCliente = dados[2];
//                String nomeCliente = dados[3];
//                Veiculo novoVeiculo = new Veiculo(placa, new Cliente(nomeCliente, cpfCliente), modelo);
//                veiculosLista.add(novoVeiculo);
//            }
//
//            return veiculosLista;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    
//}
