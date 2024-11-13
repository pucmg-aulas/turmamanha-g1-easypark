package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BancoDados {
    private static String banco = "easyPark";
    private static String usuario = "postgres.lgxszdtbintlycfivosh";
    private static String senha = "PmEasyPark%";
    private static String url = "jdbc:postgresql://aws-0-us-east-2.pooler.supabase.com:6543/postgres";
    private static BancoDados instancia = null;
    private static Connection conexao = null;
    
    private BancoDados(){}
    
    public static void conectar() throws SQLException{
        conexao = DriverManager.getConnection(url, usuario, senha);
    }
    
    public static BancoDados getInstancia() {
        if(instancia == null){
            instancia = new BancoDados();
            try {
                conectar();
            } catch (SQLException ex) {
                Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return instancia;
    }
    
    public static Connection getConexao(){
        return conexao;
    }
    
    public void desconectar(){
        try{
            conexao.close();
            System.out.println("Conexão Encerrada!");
        }catch (SQLException ex){
            Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}