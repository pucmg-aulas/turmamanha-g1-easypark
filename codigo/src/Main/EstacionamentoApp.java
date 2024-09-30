import src.Models.Cobranca;
import src.Models.Cliente;
import src.Models.Veiculo;
import src.Models.Estacionamento;
import src.Models.Vaga;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class EstacionamentoApp {
}

public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int opcao;

    Estacionamento estacionamento;
    estacionamento = new Estacionamento("a", "a", "a", 123);

    while (true) {
        System.out.println("\n=== Sistema de Estacionamentos ===");
        System.out.println("(1) Cadastrar Estacionamento");
        System.out.println("(2) Selecionar Estacionamento");
        System.out.println("(3) Cadastrar Veículo");
        System.out.print(">");

        opcao = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha pendente após a entrada numérica

        switch (opcao) {
            case 1:
                // Cadastro de Estacionamento
                System.out.print("Digite o nome do estacionamento: ");
                String nome = scanner.nextLine();

                System.out.print("Digite a rua do estacionamento: ");
                String rua = scanner.nextLine();

                System.out.print("Digite o bairro do estacionamento: ");
                String bairro = scanner.nextLine();

                System.out.print("Digite o número do estacionamento: ");
                int numero = scanner.nextInt();
                scanner.nextLine();

                estacionamento = new Estacionamento(nome, rua, bairro, numero);
                System.out.println("Estacionamento cadastrado com sucesso!");

                break;

            case 2:
                // Selecionar Estacionamento
                System.out.print("Digite o ID do estacionamento que deseja selecionar: ");

                int idEstacionamento = scanner.nextInt();
                scanner.nextLine();

                Estacionamento estacionamentoAtual = null;

                List<Estacionamento> estacionamentos = Estacionamento.lerEstacionamentosDeArquivo();
                for (Estacionamento est : estacionamentos) {
                    if (est.getId() == idEstacionamento) {
                       Estacionamento estacionamentoSelecionado = est;
                        System.out.println("Estacionamento " + est.getNome() + " selecionado.");
                        break;
                    }
                }

                if (estacionamentoAtual == null) {
                    System.out.println("Estacionamento com ID " + idEstacionamento + " não encontrado.");
                }
                break;

            default:
                break;
        }

        System.out.println("(4) Cadastrar Vaga");
        System.out.println("(5) Reservar Vaga");
        System.out.println("(6) Liberar Vaga");
        System.out.println("(7) Gerar Cobrança");
        System.out.println("(8) Exibir Detalhes do Estacionamento");
        System.out.println("(9) Sair");
        System.out.print("Selecione uma opção: ");

        if (opcao == 9) {
            System.out.println("Encerrando o sistema...");
            break;
        }

        switch (opcao) {

            case 2:
                // Cadastrar cliente
                System.out.print("Digite o nome do cliente: ");
                String nomeCliente = scanner.nextLine();
                System.out.print("Digite o cpf do cliente: ");
                String cpfCliente = scanner.nextLine();
                Cliente novoCliente = new Cliente(nomeCliente, cpfCliente);
                novoCliente.gravarEmArquivo();
                System.out.println("Cliente cadastrado com sucesso! Cpf: " + novoCliente.getCpf());
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
                System.out.println("Escolha o tipo de vaga:");
                System.out.println("1 - REGULAR");
                System.out.println("2 - IDOSO");
                System.out.println("3 - PCD");
                System.out.println("4 - VIP");

                int escolha = scanner.nextInt();

                Vaga.TipoVaga tipoDeVaga = null;

                switch (escolha) {
                    case 1:
                        tipoDeVaga = Vaga.TipoVaga.REGULAR;
                        break;
                    case 2:
                        tipoDeVaga = Vaga.TipoVaga.IDOSO;
                        break;
                    case 3:
                        tipoDeVaga = Vaga.TipoVaga.PCD;
                        break;
                    case 4:
                        tipoDeVaga = Vaga.TipoVaga.VIP;
                        break;
                    default:
                        System.out.println("Escolha inválida! O tipo padrão será REGULAR.");
                        tipoDeVaga = Vaga.TipoVaga.REGULAR;
                        break;
                }
                Vaga novaVaga = new Vaga(tipoDeVaga);
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

            case 8:
                // Exibir detalhes do estacionamento
                System.out.println("=== Informações do Estacionamento ===");
                System.out.println("Nome: " + estacionamento.getNome());
                System.out.println("Endereço: " + estacionamento.getRua() + ", " + estacionamento.getNumero() + " - "
                        + estacionamento.getBairro());
                System.out.println("Vagas Disponíveis: " + estacionamento.getVagas().size());
                break;

            default:
                System.out.println("Opção inválida! Por favor, escolha uma opção válida.");

        }
    }
    scanner.close();

}
