package dao;

import Models.Vaga;
import Models.VagaIdoso;
import Models.VagaPCD;
import Models.VagaRegular;
import Models.VagaVIP;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VagabdDAO {
    
    private static VagabdDAO instance;
    private BancoDados bd;
    private List<Vaga> vagas;
    private int idEstacionamento;
    
    private VagabdDAO() throws SQLException, IOException {
        this.vagas = new ArrayList<>();
        this.bd = BancoDados.getInstancia();
    }
    
    public static VagabdDAO getInstance() throws SQLException, IOException{
        if(instance == null){
            instance = new VagabdDAO();
        }
        return instance;
    }
    
    public void setIdEstacionamento(int idEstacionamento) {
        this.idEstacionamento = idEstacionamento;
    }
    
    public void instanciarVagas(int qntdVagas, int idEstacionamento) throws IOException, SQLException{
        String sql = "INSERT INTO vaga (tipo, idestacionamento, status) VALUES (?,?,?)";
        
        int vagasRegulares = (int) (qntdVagas * 0.5);
        int vagasIdoso = (int) (qntdVagas * 0.2);
        int vagasPCD = (int) (qntdVagas * 0.2);
        int vagasVIP = (int) (qntdVagas * 0.1);

        int totalInstanciadas = vagasRegulares + vagasIdoso + vagasPCD + vagasVIP;
        List<Vaga> vagaLista = new ArrayList<>();
        
        while (totalInstanciadas < qntdVagas) {
            vagasRegulares++;
            totalInstanciadas++;
        }
        
        try{
            Connection conn = BancoDados.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            for (int i = 0; i < vagasRegulares; i++) {
                Vaga vagaAtual = new VagaRegular();
                ps.setString(1, vagaAtual.getTipo());
                ps.setInt(2, idEstacionamento);
                ps.setBoolean(3, true);
                ps.addBatch();
                vagas.add(vagaAtual);
                vagaLista.add(vagaAtual); 
            }
            
            for (int i = 0; i < vagasIdoso; i++) {
                Vaga vagaAtual = new VagaIdoso();
                ps.setString(1, vagaAtual.getTipo());
                ps.setInt(2, idEstacionamento);
                ps.setBoolean(3, true);
                ps.addBatch();
                vagas.add(vagaAtual);
                vagaLista.add(vagaAtual);
            }
            
            for (int i = 0; i < vagasPCD; i++) {
                Vaga vagaAtual = new VagaPCD();
                ps.setString(1, vagaAtual.getTipo());
                ps.setInt(2, idEstacionamento);
                ps.setBoolean(3, true);
                ps.addBatch();
                vagas.add(vagaAtual);
                vagaLista.add(vagaAtual);
            }
            
            for (int i = 0; i < vagasVIP; i++) {
                Vaga vagaAtual = new VagaVIP();
                ps.setString(1, vagaAtual.getTipo());
                ps.setInt(2, idEstacionamento);
                ps.setBoolean(3, true);
                ps.addBatch();
                vagas.add(vagaAtual);
                vagaLista.add(vagaAtual);
            }
            
            ps.executeBatch();
            
        }catch(SQLException e){
            Logger.getLogger(VagabdDAO.class.getName()).log(Level.SEVERE, null, e);        }
          
    }
    
    private List<Vaga> lerVagas(int idEstacionamento) throws SQLException, IOException {
        String sql = "SELECT * FROM vaga WHERE idestacionamento = ?";
        List<Vaga> vagasLidas = new ArrayList<>();

        try (Connection conn = BancoDados.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEstacionamento);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String tipo = rs.getString("tipo");
                    Vaga vaga = null;

                    switch (tipo) {
                        case "regular":
                            vaga = new VagaRegular();
                            break;
                        case "idoso":
                            vaga = new VagaIdoso();
                            break;
                        case "pcd":
                            vaga = new VagaPCD();
                            break;
                        case "vip":
                            vaga = new VagaVIP();
                            break;
                        default:
                            break;
                    }

                    if (vaga != null) {
                        vaga.setId(rs.getInt("id"));
                        vaga.setStatus(rs.getBoolean("status"));
                        vaga.setIdEstacionamento(rs.getInt("idestacionamento"));
                        vagasLidas.add(vaga);
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VagabdDAO.class.getName()).log(Level.SEVERE, "Erro ao ler vagas", e);
        }
        return vagasLidas;
    }
    
    public boolean cadastrarVaga(Vaga vaga, int idEstacionamento) throws SQLException {
        String sql = "INSERT INTO vaga (tipo, idestacionamento, status) VALUES (?,?,?)";
        try (Connection conn = BancoDados.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vaga.getTipo());
            ps.setInt(2, idEstacionamento);
            ps.setBoolean(3, vaga.getStatus());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(VagabdDAO.class.getName()).log(Level.SEVERE, "Erro ao cadastrar vaga", e);
            return false;
        }
    }
    
    private Vaga getVagaPorId(int idVaga, int idEstacionamento) {
        for (Vaga vaga : this.vagas) {
            if (vaga.getId() == idVaga && vaga.getIdEstacionamento() == idEstacionamento) {
                return vaga;
            }
        }
        return null;
    }

    
    public List<Vaga> getVagasDisponiveis(int idEstacionamento) throws SQLException {
        List<Vaga> vagasDisponiveis = new ArrayList<>();
        String sql = "SELECT * FROM vaga WHERE idestacionamento = ? AND status = true";

        try (Connection conn = BancoDados.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEstacionamento);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    boolean status = rs.getBoolean("status");
                    int idEstacionamentoDb = rs.getInt("idestacionamento");

                    Vaga vaga = getVagaPorId(id, idEstacionamentoDb);

                    if (vaga != null && vaga.getStatus()) {
                        vagasDisponiveis.add(vaga);
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VagabdDAO.class.getName()).log(Level.SEVERE, "Erro ao buscar vagas disponíveis", e);
            throw e;
        }

        return vagasDisponiveis;
    }
    
    public List<Vaga> getVagasOcupadas(int idEstacionamento) throws SQLException {
        List<Vaga> vagasOcupadas = new ArrayList<>();
        String sql = "SELECT * FROM vaga WHERE idestacionamento = ? AND status = false";

        try (Connection conn = BancoDados.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEstacionamento);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    boolean status = rs.getBoolean("status");
                    int idEstacionamentoDb = rs.getInt("idestacionamento");

                    Vaga vaga = getVagaPorId(id, idEstacionamentoDb);

                    if (vaga != null && !vaga.getStatus()) {
                        vagasOcupadas.add(vaga);
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VagabdDAO.class.getName()).log(Level.SEVERE, "Erro ao buscar vagas ocupadas", e);
            throw e;
        }

        return vagasOcupadas;
    }
    
    public boolean liberarVaga(int idVaga) throws SQLException {
        String sql = "UPDATE vaga SET status = true WHERE id = ?";

        try (Connection conn = BancoDados.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idVaga);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            Logger.getLogger(VagabdDAO.class.getName()).log(Level.SEVERE, "Erro ao liberar vaga", e);
            return false;
        }
    }

}
