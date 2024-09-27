package Models;

import Models.*;

import java.util.Scanner;

public class EstacionamentoApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Estacionamento estacionamento = new Estacionamento("Estacionamento Central", "Rua A", "Centro", 123);
        int opcao;

        do {
            System.out.println("=== | Sistema de Estacionamento | ===");
            System.out.println("Seja bem-vindo ao nosso sistema de Auto-Atendimento!");
            System.out.println("Menu Principal:");
            System.out.println("(1) Informações do Estacionamento");
            System.out.println("(2) Cadastrar Cliente");
            System.out.println("(3) Cadastrar Veículo");
            System.out.println("(4) Cadastrar Vaga");
            System.out.println("(5) Reservar Vaga");
            System.out.println("(6) Liberar Vaga");
            System.out.println("(7) Realizar Cobrança");
            System.out.println("(8) Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Informações sobre o Estacionamento:");
                    estacionamento.gravarEmArquivo(); // Grava informações do estacionamento no arquivo
                    break;
                case 2:
                    System.out.println("Cadastrar novo cliente:");
                    System.out.print("Digite o nome do cliente: ");
                    scanner.nextLine(); // Consumir o newline
                    String nomeCliente = scanner.nextLine();
                    Cliente cliente = new Cliente(nomeCliente);
                    cliente.gravarEmArquivo(); // Grava informações do cliente no arquivo
                    break;
                case 3:
                    System.out.println("Cadastrar novo veículo:");
                    System.out.print("Digite a placa do veículo: ");
                    scanner.nextLine(); // Consumir o newline
                    String placaVeiculo = scanner.nextLine();
                    System.out.print("Digite o nome do cliente proprietário: ");
                    String nomeProprietario = scanner.nextLine();
                    Cliente clienteVeiculo = new Cliente(nomeProprietario);
                    Veiculo veiculo = new Veiculo(placaVeiculo, clienteVeiculo);
                    veiculo.gravarEmArquivo(); // Grava informações do veículo no arquivo
                    break;
                case 4:
                    System.out.println("Cadastrar nova vaga:");
                    Vaga vaga = new Vaga();
                    estacionamento.adicionarVaga(vaga);
                    vaga.gravarEmArquivo(); // Grava informações da vaga no arquivo
                    break;
                case 5:
                    System.out.println("Reservar uma vaga:");
                    System.out.print("Digite o ID da vaga: ");
                    int idVagaReserva = scanner.nextInt();
                    Vaga vagaReserva = estacionamento.getVagaPorId(idVagaReserva);
                    if (vagaReserva != null && vagaReserva.ocuparVaga()) {
                        System.out.println("Vaga reservada com sucesso.");
                    } else {
                        System.out.println("Falha ao reservar a vaga.");
                    }
                    break;
                case 6:
                    System.out.println("Liberar uma vaga:");
                    System.out.print("Digite o ID da vaga: ");
                    int idVagaLiberar = scanner.nextInt();
                    Vaga vagaLiberar = estacionamento.getVagaPorId(idVagaLiberar);
                    if (vagaLiberar != null && vagaLiberar.liberarVaga()) {
                        System.out.println("Vaga liberada com sucesso.");
                    } else {
                        System.out.println("Falha ao liberar a vaga.");
                    }
                    break;
                case 7:
                    System.out.println("Realizar cobrança:");
                    System.out.print("Digite o ID da vaga: ");
                    int idVagaCobranca = scanner.nextInt();
                    Vaga vagaCobranca = estacionamento.getVagaPorId(idVagaCobranca);
                    if (vagaCobranca != null && !vagaCobranca.getStatus()) {
                        System.out.print("Digite a placa do veículo: ");
                        scanner.nextLine(); // Consumir o newline
                        String placaCobranca = scanner.nextLine();
                        Veiculo veiculoCobranca = new Veiculo(placaCobranca, null);
                        Cobranca cobranca = new Cobranca(idVagaCobranca, estacionamento, veiculoCobranca);
                        cobranca.setHoraSaida(java.time.LocalTime.now()); // Exemplo de saída automática
                        cobranca.calcularTempoFinal();
                        cobranca.calcularValorTotal();
                        System.out.println("Valor total a pagar: R$ " + cobranca.getValorTotal());
                    } else {
                        System.out.println("Vaga não está ocupada ou não encontrada.");
                    }
                    break;
                case 8:
                    System.out.println("Saindo do sistema.");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

            System.out.println();

        } while (opcao != 8);

        scanner.close();
    }
}
