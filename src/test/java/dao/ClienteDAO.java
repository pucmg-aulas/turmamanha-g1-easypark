/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Models.Cliente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
    private List<Cliente> clientes;
    private static ClienteDAO instance;
    private final String Arquivo = "./src/test/java/Archives/Clientes.txt";
    
    private ClienteDAO() throws IOException{
        clientes = lerClientes();
        if(clientes == null){
            clientes = new ArrayList<>();
        }
        
    }
    
    public static ClienteDAO getInstance() throws IOException{
        if(instance == null){
            instance = new ClienteDAO();
        }
        return instance;
    }
    
    public Cliente buscarClientePorCpf(String cpf) {
        try(BufferedReader br = new BufferedReader(new FileReader(Arquivo))){
            String linha;

            while((linha = br.readLine()) != null){
                String[] dados = linha.split(";");
                String cpfCliente = dados[0];
                String nomeCliente = dados[1];

                if(cpf.equals(cpfCliente)){
                    return new Cliente(nomeCliente, cpfCliente);
                }
            }

        }catch (IOException e){
            throw new RuntimeException(e);
        }
            return null;
    }
    
    public boolean cadastrarCliente(Cliente cliente) throws IOException{
        clientes.add(cliente);
        return salvarClienteArquivo(clientes);
    } 
    
    private boolean salvarClienteArquivo(List<Cliente> Listaclientes) throws IOException {
        File arquivo = new File(Arquivo);

        try {
            File diretorio = arquivo.getParentFile();
            if (diretorio != null && !diretorio.exists()) {
                diretorio.mkdir();
            }

            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }
            
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(Arquivo))) {
            for(Cliente cliente : Listaclientes){
                bw.write(cliente.getCpf() + ";" + cliente.getNome());
                bw.newLine();
                bw.flush();
            }
            return true;
        }
    }catch (IOException e) {
        throw new IOException(e.getMessage());
        }
    }
   
    public List<Cliente> lerClientes() throws FileNotFoundException, IOException{
        List<Cliente> clientesLista = new ArrayList();
        
        try(BufferedReader br = new BufferedReader(new FileReader(Arquivo))){
            String linha;
            
            while((linha = br.readLine()) != null){
                String[] dados = linha.split(";");
                String cpfCliente = dados[0];
                String nomeCliente = dados[1];
                clientesLista.add(new Cliente(nomeCliente, cpfCliente));
            }
            
            return clientesLista;
        }catch(IOException e){
           throw new RuntimeException(e);
       }
    }
}
