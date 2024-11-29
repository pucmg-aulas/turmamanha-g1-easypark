/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UnitaryTests;


import Models.Estacionamento;
import dao.EstacionamentobdDAO;

import java.util.List;

public class EstacionamentobdDAOTest {

    public static void main(String[] args) {
        try {
            EstacionamentobdDAO dao = EstacionamentobdDAO.getInstance();

            // Teste 1: Singleton da Classe
            System.out.println("=== Teste 1: Singleton da Classe ===");
            EstacionamentobdDAO instance1 = EstacionamentobdDAO.getInstance();
            EstacionamentobdDAO instance2 = EstacionamentobdDAO.getInstance();
            System.out.println("Instances são iguais? " + (instance1 == instance2)); // Deve ser true

            // Teste 2: Cadastrar Estacionamento
            System.out.println("\n=== Teste 2: Cadastrar Estacionamento ===");
            Estacionamento estacionamento1 = new Estacionamento(1, "Estacionamento Centro", "Rua A", "Centro", 100, 50);
            boolean sucessoCadastro = dao.cadastrarEstacionamento(estacionamento1);
            System.out.println("Cadastro realizado com sucesso? " + sucessoCadastro);

            // Teste 3: Buscar Estacionamento por ID
            System.out.println("\n=== Teste 3: Buscar Estacionamento por ID ===");
            Estacionamento estacionamentoBuscado = dao.getEstacionamentoPorId(1);
            System.out.println("Estacionamento encontrado: " + estacionamentoBuscado);

            // Teste 4: Listar Estacionamentos
            System.out.println("\n=== Teste 4: Listar Estacionamentos ===");
            List<Estacionamento> listaEstacionamentos = dao.listarEstacionamentos();
            for (Estacionamento e : listaEstacionamentos) {
                System.out.println(e);
            }

            // Teste 5: Obter Maior ID
            System.out.println("\n=== Teste 5: Obter Maior ID ===");
            int maiorId = dao.obterMaiorId();
            System.out.println("Maior ID: " + maiorId);

            // Teste 6: Remover Estacionamento
            System.out.println("\n=== Teste 6: Remover Estacionamento ===");
            dao.removeEstacionamento(1);
            Estacionamento estacionamentoRemovido = dao.getEstacionamentoPorId(1);
            System.out.println("Estacionamento após remoção (deve ser null): " + estacionamentoRemovido);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
