package Main;

import Models.Cobranca;
import Models.Cliente;
import Models.Veiculo;
import Models.Estacionamento;
import Models.Vaga;
import Models.VagaIdoso;
import Models.VagaVIP;
import Models.VagaPCD;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class EstacionamentoApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;


        Estacionamento estacionamento = new Estacionamento("a", "a", "a", 123);

        while (true) {
            System.out.println("\n=== Sistema de Estacionamentos ===");
            System.out.println("(1) Cadastrar Estacionamento");
            System.out.println("(2) Cadastrar Veículo");
            System.out.println("(3) Selecionar Estacionamento");
            System.out.println("(0) Sair");
            System.out.print(">");

            opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 0) {
                System.out.println("Encerrando o sistema...");
                break;
            }

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

                    Estacionamento estacionamento1 = new Estacionamento(nome, rua, bairro, numero);
                    if(estacionamento1.gravarEstacionamentosEmArquivo()){
                        System.out.println("---------------------------------------");
                        System.out.println("Estacionamento cadastrado com sucesso!");
                        System.out.println("ID do Estacionamento: " + estacionamento.getId()); // Mostra o ID do estacionamento
                        System.out.println("---------------------------------------");
                    }
                    break;

                case 2:
                    // Cadastrar veículo
                    System.out.print("Digite a placa do veículo: ");
                    String placa = scanner.nextLine();
                    Veiculo veiculo = new Veiculo(placa);
                    veiculo.gravarEmArquivo();
                    System.out.println("Veículo cadastrado com sucesso!");
                    break;

                case 3:
                    // Selecionar Estacionamento
                    System.out.print("Digite o ID do estacionamento que deseja selecionar: ");

                    int idEstacionamento = scanner.nextInt();
                    scanner.nextLine();

                    Estacionamento estacionamentoAtual = null;

                    List<Estacionamento> estacionamentos = Estacionamento.lerEstacionamentosDeArquivo(); //metodo deve ler todos os estacionamentos ja cadastrados e comparar se o Id digitado bate
                    for (Estacionamento est : estacionamentos) {
                        if (est.getId() == idEstacionamento) {
                            Estacionamento estacionamentoSelecionado = est; // Aqui é feito o vínculo do estacionamento encontrado
                            System.out.println("Estacionamento " + est.getNome() + " selecionado.");
                            break;
                        }
                    }

                    if (estacionamentoAtual == null) {
                        System.out.println("Estacionamento com ID " + idEstacionamento + " não encontrado.");
                    } else {
                        if (estacionamentoAtual != null) {
                            while (true) {
                                System.out.println("(1) Cadastrar Cliente");
                                System.out.println("(2) Cadastrar Vaga");
                                System.out.println("(3) Reservar Vaga");
                                System.out.println("(4) Liberar Vaga");
                                System.out.println("(5) Gerar Cobrança");
                                System.out.println("(6) Exibir Detalhes do Estacionamento");
                                System.out.println("(0) Sair");
                                System.out.print("Selecione uma opção: ");

                                int opcao1;
                                opcao1 = scanner.nextInt(); // Atualiza a opção aqui
                                scanner.nextLine(); // Consumir a nova linha

                                if (opcao1 == 0) {
                                    System.out.println("Encerrando o sistema...");
                                    scanner.close();
                                    return; // Saia do programa
                                }
                                switch (opcao1) {
                                    case 1:
                                        // Cadastrar cliente
                                        System.out.print("Digite o nome do cliente: ");
                                        String nomeCliente = scanner.nextLine();
                                        Cliente novoCliente = new Cliente(nomeCliente, "115325425");
                                        novoCliente.gravarEmArquivo(); // -> Cliente cadastrado deve ser registrado no arquivo
                                        System.out.println("Cliente cadastrado com sucesso! ID: " + novoCliente.getId());
                                        break;

                                    case 2:

                                        // Cadastrar vaga

                                        System.out.println("Escolha o tipo de vaga:");
                                        System.out.println("(1) Regular");
                                        System.out.println("(2) Idoso");
                                        System.out.println("(3) PCD");
                                        System.out.println("(4) VIP");
                                        System.out.print("Digite a opção: ");
                                        int opcaoVaga = scanner.nextInt();
                                        scanner.nextLine(); // Consumir nova linha

                                        Vaga novaVaga;

                                        switch (opcaoVaga) {
                                            case 1:
                                                novaVaga = new Vaga(); // Propria classe Vaga para vaga Regular
                                                System.out.println("Vaga Regular cadastrada.");
                                                break;

                                            case 2:
                                                novaVaga = new VagaIdoso(); // Subtipo para vaga de Idoso
                                                System.out.println("Vaga Idoso cadastrada.");
                                                break;

                                            case 3:
                                                novaVaga = new VagaPCD(); // Subtipo para vaga de PCD
                                                System.out.println("Vaga PCD cadastrada.");
                                                break;

                                            case 4:
                                                novaVaga = new VagaVIP(); // Subtipo para vaga VIP
                                                System.out.println("Vaga VIP cadastrada.");
                                                break;

                                            default:
                                                System.out.println("Opção inválida! Selecione um tipo válido.");
                                                return;
                                        }

                                        //Precisa gravar a vaga cadastrada no arquivo e o tipo dela (nao sei como faz)
                                        // Adiciona a vaga ao estacionamento e salva no arquivo

                                        estacionamento.adicionarVaga(novaVaga);
                                        novaVaga.gravarEmArquivo(); // Vaga cadastrada deve ser registrada no arquivo

                                        System.out.println("Nova vaga cadastrada. ID: " + novaVaga.getId());

                                        break;

                                    case 3:
                                        // Reservar vaga
                                        // Precisa indicar que a vaga mudou de status no arquivo??
                                        System.out.print("Digite o ID da vaga a ser reservada: ");
                                        int idVaga = scanner.nextInt();
                                        scanner.nextLine();
                                        Vaga vaga = estacionamento.getVagaPorId(idVaga);

                                        if (vaga != null && vaga.ocuparVaga()) {
                                            System.out.println("Vaga ID " + idVaga + " reservada com sucesso!");
                                        } else {
                                            System.out.println("Falha ao reservar a vaga. Verifique se a vaga está disponível.");
                                        }
                                        break;

                                    case 4:
                                        // Liberar vaga
                                        // Precisa indicar que a vaga mudou de status no arquivo??
                                        System.out.print("Digite o ID da vaga a ser liberada: ");
                                        int idVagaLiberar = scanner.nextInt();
                                        scanner.nextLine();
                                        Vaga vagaLiberar = estacionamento.getVagaPorId(idVagaLiberar);

                                        if (vagaLiberar != null && vagaLiberar.liberarVaga()) {
                                            System.out.println("Vaga ID " + idVagaLiberar + " liberada com sucesso!");
                                        } else {
                                            System.out.println("Falha ao liberar a vaga. Verifique se a vaga está ocupada.");
                                        }
                                        break;

                                    case 5:
                                        // Gerar cobrança

                                        System.out.print("Digite o ID da vaga para cobrança: ");
                                        int idVagaCobranca = scanner.nextInt();
                                        scanner.nextLine();

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
                                        cobranca.gravarEmArquivo(); // -> Grava a cobranca registrada nos arquivos
                                        System.out.println("Cobrança gerada com sucesso! Valor total: R$ " + cobranca.getValorTotal());

                                        if (cobranca.pagar()) {
                                            System.out.println("Vaga liberada após pagamento.");
                                        }

                                    case 6:
                                        // Exibir detalhes do estacionamento
                                        System.out.println("=== Informações do Estacionamento ===");
                                        System.out.println("Nome: " + estacionamento.getNome());
                                        System.out.println("Endereço: " + estacionamento.getRua() + ", " + estacionamento.getNumero() + " - " + estacionamento.getBairro());
                                        System.out.println("Vagas Disponíveis: " + estacionamento.getVagas().size());
                                        break;

                                    default:
                                        System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
                                        break;
                                }
                            }
                        }
                    }

                default:
                    System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
                    break;


            }

        }
        scanner.close();
    }
}

