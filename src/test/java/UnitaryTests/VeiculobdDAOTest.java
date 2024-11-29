/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UnitaryTests;

import Models.Cliente;
import Models.Veiculo;
import dao.VeiculoDAO;
import dao.ClientebdDAO;
import java.sql.SQLException;
import java.util.List;

public class VeiculobdDAOTest {

    public static void main(String[] args) {
        // Instância do DAO
        VeiculoDAO veiculoDAO = VeiculoDAO.getInstance();
        ClientebdDAO clienteDAO = ClientebdDAO.getInstance();

        // Testando cadastro de um novo cliente
        Cliente cliente1 = new Cliente("12345678900", "Carlos Silva");
        clienteDAO.cadastrarCliente(cliente1);

        // Testando cadastro de veículo por cliente
        Veiculo veiculo1 = new Veiculo("ABC1234", cliente1, "Fusca");
        boolean veiculoCadastrado = veiculoDAO.cadastrarVeiculoPorCliente(veiculo1);
        if (veiculoCadastrado) {
            System.out.println("Veículo cadastrado com sucesso: " + veiculo1.getPlaca());
        } else {
            System.out.println("Falha ao cadastrar veículo: " + veiculo1.getPlaca());
        }

        // Testando cadastro de veículo com placa já existente
        Veiculo veiculo2 = new Veiculo("ABC1234", cliente1, "Civic");
        veiculoCadastrado = veiculoDAO.cadastrarVeiculoPorCliente(veiculo2); // Placa repetida
        if (!veiculoCadastrado) {
            System.out.println("Falha ao cadastrar veículo (placa duplicada): " + veiculo2.getPlaca());
        }

        // Testando busca de veículo por placa
        Veiculo veiculoBuscado = veiculoDAO.buscarVeiculoPorPlaca("ABC1234");
        if (veiculoBuscado != null) {
            System.out.println("Veículo encontrado: " + veiculoBuscado.getPlaca() + ", Modelo: " + veiculoBuscado.getModelo());
        } else {
            System.out.println("Veículo não encontrado com a placa: ABC1234");
        }

        // Testando atualização de proprietário de veículo
        String placaParaAtualizar = "ABC1234";
        String novoCpfProprietario = "98765432100";  // Um novo CPF
        String novoModelo = "Fusca 1978";

        boolean sucessoAtualizacao = veiculoDAO.atualizarProprietario(placaParaAtualizar, novoCpfProprietario, novoModelo);
        if (sucessoAtualizacao) {
            Veiculo veiculoAtualizado = veiculoDAO.buscarVeiculoPorPlaca(placaParaAtualizar);
            System.out.println("Veículo atualizado: " + veiculoAtualizado.getPlaca() + ", Novo Modelo: " + veiculoAtualizado.getModelo());
        } else {
            System.out.println("Falha ao atualizar o veículo.");
        }

        // Testando leitura de todos os veículos
        List<Veiculo> listaVeiculos = veiculoDAO.lerVeiculos();
        if (!listaVeiculos.isEmpty()) {
            System.out.println("\nVeículos cadastrados:");
            for (Veiculo v : listaVeiculos) {
                System.out.println("Placa: " + v.getPlaca() + ", Modelo: " + v.getModelo() + ", Proprietário: " + v.getCliente().getNome());
            }
        } else {
            System.out.println("Nenhum veículo encontrado.");
        }

        // Testando exclusão de veículo
        try {
            String placaParaExcluir = "ABC1234";
            veiculoDAO.excluirVeiculoPorPlaca(placaParaExcluir);
            System.out.println("Veículo com placa " + placaParaExcluir + " excluído com sucesso.");
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir veículo: " + ex.getMessage());
        }
    }
}
