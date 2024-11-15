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
    
    private VagabdDAO(){
        this.vagas = lerVagas();
        if(this.vagas == null){
            this.vagas = new ArrayList();
        }
        
        this.bd = BancoDados.getInstancia();

    }
    
    public static VagabdDAO getInstance(){
        if(instance == null){
            instance = new VagabdDAO();
        }
        return instance;
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
                vagas.add(vagaAtual);
                vagaLista.add(vagaAtual); 
            }
            
            for (int i = 0; i < vagasIdoso; i++) {
                Vaga vagaAtual = new VagaIdoso();
                ps.setString(1, vagaAtual.getTipo());
                ps.setInt(2, idEstacionamento);
                ps.setBoolean(3, true);
                vagas.add(vagaAtual);
                vagaLista.add(vagaAtual);
            }
            
            for (int i = 0; i < vagasPCD; i++) {
                Vaga vagaAtual = new VagaPCD();
                ps.setString(1, vagaAtual.getTipo());
                ps.setInt(2, idEstacionamento);
                ps.setBoolean(3, true);
                vagas.add(vagaAtual);
                vagaLista.add(vagaAtual);
            }
            
            for (int i = 0; i < vagasVIP; i++) {
                Vaga vagaAtual = new VagaVIP();
                ps.setString(1, vagaAtual.getTipo());
                ps.setInt(2, idEstacionamento);
                ps.setBoolean(3, true);
                vagas.add(vagaAtual);
                vagaLista.add(vagaAtual);
            }
            
            
        }catch(SQLException e){
            Logger.getLogger(VagabdDAO.class.getName()).log(Level.SEVERE, null, e);        }
          
        
    }
    
    private List<Vaga> lerVagas() throws SQLException{
        String sql = "SELECT * FROM vaga";
        
        try{
            Connection conn = BancoDados.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                String tipo = rs.getString("tipo");
                int idEstacionamento = rs.getInt("idestacionamento");
                boolean status = rs.getBoolean("status");
            }
            
            
        }
    }
    
}
