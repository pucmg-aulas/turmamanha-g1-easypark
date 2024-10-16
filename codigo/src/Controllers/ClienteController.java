package Controllers;

import Models.Cliente;

import java.util.ArrayList;
import java.util.List;


public class ClienteController {
    private Cliente cliente;

    public ClienteController() {
        this.cliente = new Cliente();
    }

    public void cadastrarCliente(String nome, String cpf) {
        cliente.setNome(nome);
        cliente.setCpf(cpf);
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    
    
/*
    // Método para cadastrar um novo cliente
    public boolean cadastrarCliente(Cliente cliente) {
        // Verifica se o cliente já está cadastrado pelo CPF
        if (buscarClientePorCpf(cliente.getCpf()) != null) {
            System.out.println("Cliente com CPF " + cliente.getCpf() + " já cadastrado.");
            return false;
        }

        clientes.add(cliente);
        // Grava o cliente no arquivo
        return cliente.gravarEmArquivo();
    }

    // Método para buscar um cliente pelo CPF
    public Cliente buscarClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null; // Retorna null se o cliente não for encontrado
    }

    // Método para carregar clientes do arquivo
    private void carregarClientesDoArquivo() {
        List<String> clientesLidos = Cliente.lerClientesDoArquivo();
        for (String clienteInfo : clientesLidos) {
            String[] linhas = clienteInfo.split("\n");
            String cpf = linhas[0].replace("CPF: ", "").trim();
            String nome = linhas[1].replace("Nome: ", "").trim();
            Cliente cliente = new Cliente(nome, cpf);
            clientes.add(cliente);
        }
    }

    // Método para listar todos os clientes cadastrados
    public List<Cliente> listarClientes() {
        return clientes;
    }
*/
}
