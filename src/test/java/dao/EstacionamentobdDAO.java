package dao;

import Models.Estacionamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstacionamentobdDAO {

    private static EstacionamentobdDAO instance;  // Corrigido o tipo da instância para EstacionamentobdDAO
    private BancoDados bd;

    private EstacionamentobdDAO() {
        this.bd = BancoDados.getInstancia();
    }

    public static EstacionamentobdDAO getInstance() {
        if (instance == null) {
            instance = new EstacionamentobdDAO();
        }
        return instance;
    }

    public boolean cadastrarEstacionamento(Estacionamento estacionamento) throws SQLException {
        String sql = "INSERT INTO Estacionamento (id, nome, rua, bairro, numero, qntdVagas) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = bd.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, estacionamento.getId());
            ps.setString(2, estacionamento.getNome());
            ps.setString(3, estacionamento.getRua());
            ps.setString(4, estacionamento.getBairro());
            ps.setInt(5, estacionamento.getNumero());
            ps.setInt(6, estacionamento.getQntdVagas());
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new SQLException("Erro ao cadastrar estacionamento: " + e.getMessage());
        }
    }

    public void removeEstacionamento(int id) throws SQLException {
        String sql = "DELETE FROM Estacionamento WHERE id = ?";
        
        try (Connection conn = bd.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao remover estacionamento: " + e.getMessage());
        }
    }

    public Estacionamento getEstacionamentoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Estacionamento WHERE id = ?";
        
        try (Connection conn = bd.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    String rua = rs.getString("rua");
                    String bairro = rs.getString("bairro");
                    int numero = rs.getInt("numero");
                    int qntdVagas = rs.getInt("qntdVagas");

                    return new Estacionamento(id, nome, rua, bairro, numero, qntdVagas);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar estacionamento: " + e.getMessage());
        }

        return null;
    }

    public List<Estacionamento> listarEstacionamentos() throws SQLException {
        List<Estacionamento> estacionamentos = new ArrayList<>();
        String sql = "SELECT * FROM Estacionamento";

        try (Connection conn = bd.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String rua = rs.getString("rua");
                String bairro = rs.getString("bairro");
                int numero = rs.getInt("numero");
                int qntdVagas = rs.getInt("qntdVagas");

                Estacionamento e = new Estacionamento(id, nome, rua, bairro, numero, qntdVagas);
                estacionamentos.add(e);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar estacionamentos: " + e.getMessage());
        }

        return estacionamentos;
    }

    
}
