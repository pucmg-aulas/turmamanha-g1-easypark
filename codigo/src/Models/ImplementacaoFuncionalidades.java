import java.util.Scanner;
import java.time.LocalTime;

public class EstacionamentoApp {
    private static Estacionamento estacionamento = new Estacionamento("Estacionamento Central", "Rua Principal", "Centro", 123);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        while (true) {
            System.out.println("\n=== Sistema de Estacionamento ===");
            System.out.println("(1) Exibir Detalhes do Estacionamento");
            System.out.println("(2) Cadastrar Cliente");
            System.out.println("(3) Cadastrar Veículo");
            System.out.println("(4) Cadastrar Vaga");
            System.out.println("(5) Reservar Vaga");
            System.out.println("(6) Liberar Vaga");
            System.out.println("(7) Gerar Cobrança");
            System.out.println("(8) Sair");
            System.out.print("Selecione uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha pendente após a entrada numérica

            if (opcao == 8) {
                System.out.println("Encerrando o sistema...");
                break;
            }

            switch (opcao) {
                case 1:
                    // Exibir detalhes do estacionamento
                    System.out.println("=== Informações do Estacionamento ===");
                    System.out.println("Nome: " + estacionamento.getNome());
                    System.out.println("Endereço: " + estacionamento.getRua() + ", " + estacionamento.getNumero() + " - " + estacionamento.getBairro());
                    System.out.println("Vagas Disponíveis: " + estacionamento.getVagas().size());
                    estacionamento.gravarEmArquivo();
                    break;

                case 2:
                    // Cadastrar cliente
                    System.out.print("Digite o nome do cliente: ");
                    String nomeCliente = scanner.nextLine();
                    Cliente novoCliente = new Cliente(nomeCliente);
                    novoCliente.gravarEmArquivo();
                    System.out.println("Cliente cadastrado com sucesso! ID: " + novoCliente.getId());
                    break;

                case 3:
                    // Cadastrar veículo
                    System.out.print("Digite a placa do veículo: ");
                    String placa = scanner.nextLine();
                    Veiculo veiculo = new Veiculo(placa);
                    veiculo.gravarEmArquivo();
                    System.out.println("Veículo cadastrado com sucesso!");
                    break;

                case 4:
                    // Cadastrar vaga
                    Vaga novaVaga = new Vaga();
                    estacionamento.adicionarVaga(novaVaga);
                    novaVaga.gravarEmArquivo();
                    System.out.println("Nova vaga cadastrada. ID: " + novaVaga.getId());
                    break;

                case 5:
                    // Reservar vaga
                    System.out.print("Digite o ID da vaga a ser reservada: ");
                    int idVaga = scanner.nextInt();
                    scanner.nextLine(); // Consumir a nova linha
                    Vaga vaga = estacionamento.getVagaPorId(idVaga);

                    if (vaga != null && vaga.ocuparVaga()) {
                        System.out.println("Vaga ID " + idVaga + " reservada com sucesso!");
                    } else {
                        System.out.println("Falha ao reservar a vaga. Verifique se a vaga está disponível.");
                    }
                    break;

                case 6:
                    // Liberar vaga
                    System.out.print("Digite o ID da vaga a ser liberada: ");
                    int idVagaLiberar = scanner.nextInt();
                    scanner.nextLine(); // Consumir a nova linha
                    Vaga vagaLiberar = estacionamento.getVagaPorId(idVagaLiberar);

                    if (vagaLiberar != null && vagaLiberar.liberarVaga()) {
                        System.out.println("Vaga ID " + idVagaLiberar + " liberada com sucesso!");
                    } else {
                        System.out.println("Falha ao liberar a vaga. Verifique se a vaga está ocupada.");
                    }
                    break;

                case 7:
                    // Gerar cobrança
                    System.out.print("Digite o ID da vaga para cobrança: ");
                    int idVagaCobranca = scanner.nextInt();
                    scanner.nextLine(); // Consumir a nova linha

                    Vaga vagaCobranca = estacionamento.getVagaPorId(idVagaCobranca);
                    if (vagaCobranca == null) {
                        System.out.println("Vaga não encontrada!");
                        break;
                    }

                    System.out.print("Digite a placa do veículo: ");
                    String placaVeiculo = scanner.nextLine();
                    Veiculo veiculoCobranca = new Veiculo(placaVeiculo);

                    Cobranca cobranca = new Cobranca(idVagaCobranca, estacionamento, veiculoCobranca);
                    cobranca.setHoraSaida(LocalTime.now());
                    cobranca.calcularTempoFinal();
                    cobranca.calcularValorTotal();
                    cobranca.gravarEmArquivo();
                    System.out.println("Cobrança gerada com sucesso! Valor total: R$ " + cobranca.getValorTotal());

                    if (cobranca.pagar()) {
                        System.out.println("Vaga liberada após pagamento.");
                    }
                    break;

                default:
                    System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
            }
        }
        scanner.close();
    }
}
