package Controllers;

import Models.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ClienteController {

    private Cliente cliente;
    private static final String ARQUIVO = "./src/test/java/Archives/Clientes.txt";

    public ClienteController() {
    }

    public ClienteController(String nome, String cpf) {
        this.cliente = new Cliente(nome, cpf);
    }

    // Método para cadastrar um novo cliente
    public boolean cadastrarCliente(String nome, String cpf) throws IOException {

        Cliente cliente = new Cliente(nome, cpf);

        if (buscarClientePorCpf(cliente.getCpf()) != null) {
            System.out.println("Cliente com CPF " + cliente.getCpf() + " já cadastrado.");
            return false;
        }

        salvarClienteArquivo(cliente);
        return true;
    }

    private boolean salvarClienteArquivo(Cliente cliente) throws IOException {
        File arquivo = new File(ARQUIVO);

        try {
            File diretorio = arquivo.getParentFile();
            if (diretorio != null && !diretorio.exists()) {
                diretorio.mkdir();
            }

            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            bw.write(cliente.getCpf() + ";" + cliente.getNome());
            bw.newLine();
            bw.flush();
            return true;
        }
    }catch (IOException e) {
        throw new IOException(e.getMessage());
        }
    }

    public Cliente buscarClientePorCpf(String cpf) {
        try(BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))){
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setCliente(String nome, String cpf) {
        this.cliente = new Cliente(nome, cpf);
    }

    public String getNomeClientePorCpf(String cpf) {
        Cliente cliente = buscarClientePorCpf(cpf);
        return cliente.getNome();
    }

    //    // Método para carregar clientes do arquivo
//    private void carregarClientesDoArquivo() {
//        List<String> clientesLidos = Cliente.lerClientesDoArquivo();
//        for (String clienteInfo : clientesLidos) {
//            String[] linhas = clienteInfo.split("\n");
//            String cpf = linhas[0].replace("CPF: ", "").trim();
//            String nome = linhas[1].replace("Nome: ", "").trim();
//            Cliente cliente = new Cliente(nome, cpf);
//            clientes.add(cliente);
//        }
//    }

    // Método para listar todos os clientes cadastrados
   // public List<Cliente> listarClientes() {
      //  return clientes;
  //  }

}
