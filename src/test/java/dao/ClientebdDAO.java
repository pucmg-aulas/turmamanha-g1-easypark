package dao;

import Models.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientebdDAO {
    
    private static ClientebdDAO instance;
    private List<Cliente> clientes;
    private BancoDados bd;
    
    private ClientebdDAO(){
        this.bd = BancoDados.getInstancia();
        this.clientes = lerClientes();
        if(clientes == null){
            clientes = new ArrayList<>();
        }
    }
    
    public static ClientebdDAO getInstance(){
        if(instance == null){
            instance = new ClientebdDAO();
        }
        return instance;
    }
    
    private List<Cliente> lerClientes() {
        List<Cliente> listaCliente = new ArrayList<>();
        String comandoSQL = "SELECT * FROM cliente";
        
        try {
            Connection conn = BancoDados.getConexao();
            PreparedStatement ps = conn.prepareStatement(comandoSQL);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Cliente c = new Cliente(rs.getString("cpf"), rs.getString("nome"));
                listaCliente.add(c);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientebdDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaCliente;
    }
    
    public boolean cadastrarCliente(Cliente cliente){
        String comandoSQL = "INSERT INTO cliente (cpf, nome) VALUES (?,?)";
         try{
             Connection conn = BancoDados.getConexao();
             PreparedStatement ps = conn.prepareStatement(comandoSQL);
             ps.setString(1, cliente.getCpf());
             ps.setString(2, cliente.getNome());
             ps.executeUpdate();
             return true;
         }catch(SQLException ex) {
            Logger.getLogger(ClientebdDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
         return false;
    }
    
    public Cliente buscarClientePorCpf(String cpf) {
        String comandoSQL = "SELECT * FROM cliente WHERE cpf = ?";
        try {
            Connection conn = BancoDados.getConexao();
            PreparedStatement ps = conn.prepareStatement(comandoSQL);
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            Cliente c = null;
            
            if(rs.next()){
                String cpfAtual = rs.getString("cpf");
                String nome = rs.getString("nome");
                c = new Cliente(nome, cpfAtual);
                
                return c;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientebdDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
