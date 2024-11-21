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
