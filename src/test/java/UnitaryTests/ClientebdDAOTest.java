/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UnitaryTests;

import Models.Cliente;
import dao.BancoDados;
import dao.ClientebdDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ClientebdDAOTest {

    public static void main(String[] args) {
        ClientebdDAO dao = ClientebdDAO.getInstance();

        // Testando cadastro de cliente
        Cliente cliente1 = new Cliente("12345678900", "João da Silva");
        boolean sucessoCadastro = dao.cadastrarCliente(cliente1);
        if (sucessoCadastro) {
            System.out.println("Cliente cadastrado com sucesso: " + cliente1.getNome());
        } else {
            System.out.println("Falha ao cadastrar o cliente: " + cliente1.getNome());
        }

        // Testando busca de cliente existente
        Cliente clienteBuscado = dao.buscarClientePorCpf("12345678900");
        if (clienteBuscado != null) {
            System.out.println("Cliente encontrado: " + clienteBuscado.getNome());
        } else {
            System.out.println("Cliente não encontrado.");
        }

        // Testando busca de cliente inexistente
        Cliente clienteInexistente = dao.buscarClientePorCpf("98765432100");
        if (clienteInexistente != null) {
            System.out.println("Cliente encontrado: " + clienteInexistente.getNome());
        } else {
            System.out.println("Cliente com CPF 98765432100 não encontrado.");
        }

        // Testando leitura de todos os clientes
        System.out.println("\n--- Testando leitura de todos os clientes ---");
        List<Cliente> listaClientes = dao.getInstance().lerClientes();
        if (!listaClientes.isEmpty()) {
            for (Cliente c : listaClientes) {
                System.out.println("Cliente encontrado: CPF=" + c.getCpf() + ", Nome=" + c.getNome());
            }
        } else {
            System.out.println("Nenhum cliente encontrado no banco de dados.");
        }

        // Testando atualização de dados do cliente (simulação sem alteração do DAO)
        System.out.println("\n--- Testando atualização de dados ---");
        String cpfAtualizar = "12345678900";
        String novoNome = "João da Silva Atualizado";

        boolean sucessoAtualizacao = atualizarClienteDirect(cpfAtualizar, novoNome); // Usando método local
        if (sucessoAtualizacao) {
            Cliente clienteAtualizado = dao.buscarClientePorCpf(cpfAtualizar);
            System.out.println("Cliente atualizado com sucesso. Novo nome: " + clienteAtualizado.getNome());
        } else {
            System.out.println("Falha ao atualizar o cliente com CPF: " + cpfAtualizar);
        }
    }

    /**
     * Método local para atualizar cliente diretamente no banco
     * sem alterar o DAO original.
     */
    private static boolean atualizarClienteDirect(String cpf, String novoNome) {
        String comandoSQL = "UPDATE cliente SET nome = ? WHERE cpf = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement ps = conn.prepareStatement(comandoSQL)) {
            ps.setString(1, novoNome);
            ps.setString(2, cpf);
            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0; // Retorna true se pelo menos uma linha foi atualizada
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
