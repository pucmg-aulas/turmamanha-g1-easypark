package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BancoDados {
    private static String banco = "dbEasyPark";
    private static String usuario = "root";
    private static String senha = "";
    private static String url = "";
    private static BancoDados instancia = null;
    private static Connection conexao = null;
    
    private BancoDados(){}
    
    public static void conectar(){
        try {
            conexao = DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException ex) {
            Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static BancoDados getInstancia(){
        if(instancia == null){
            instancia = new BancoDados();
            conectar();
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
