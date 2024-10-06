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


        while (true) {
            System.out.println("\n=== Sistema de Estacionamentos ===");
            System.out.println("(1) Cadastrar Estacionamento");
            System.out.println("(2) Cadastrar Cliente");
            System.out.println("(3) Selecionar Cliente");
            System.out.println("(4) Selecionar Estacionamento");
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

                    Estacionamento estacionamento = new Estacionamento(nome, rua, bairro, numero);
                    if (estacionamento.gravarEstacionamentosEmArquivo()) {
                        System.out.println("---------------------------------------");
                        System.out.println("Estacionamento cadastrado com sucesso!");
                        System.out.println("ID do Estacionamento: " + estacionamento.getId()); // Mostra o ID do estacionamento
                        System.out.println("---------------------------------------");
                    }
                    break;

                case 2:
                    // Cadastrar cliente
                    System.out.print("Digite o nome do cliente: ");
                    String nomeCliente = scanner.nextLine();
                    System.out.println("Digite o cpf do cliente: ");
                    String cpfCliente = scanner.nextLine();
                    Cliente novoCliente = new Cliente(nomeCliente, cpfCliente);
                    if(novoCliente.gravarEmArquivo()){
                        System.out.println("---------------------------------------");
                        System.out.println("Cliente cadastrado com sucesso!");
                        System.out.println("Cliente: " + novoCliente.getNome()); //
                        System.out.println("---------------------------------------");
                    }
                    break;

                case 3:
                    // Selecionar Cliente
                    System.out.print("Digite o CPF do cliente que deseja selecionar: ");
                    String cpfAtual = scanner.nextLine();

                    List<String> clientes = Cliente.lerClientesDoArquivo();

                    Cliente clienteAtual = null;

                    for(String c : clientes) {
                        if (c.contains("CPF: " + cpfAtual)) {
                            String[] linhas = c.split("\n");
                            String cpfClienteAtual = linhas[0].replace("CPF: ", "").trim();  // CPF na primeira linha
                            String nomeClienteAtual = linhas[1].replace("Nome: ", "").trim(); // Nome na segunda linha

                            clienteAtual = new Cliente(nomeClienteAtual, cpfAtual);

                            System.out.println("Cliente encontrado: " + clienteAtual.getNome());
                            break;
                        }
                    }
                        if(clienteAtual == null){
                            System.out.println("Cliente com CPF " + cpfAtual + " não encontrado!");
                            break;
                        }else{
                            System.out.println("\n=== CPF: " + clienteAtual.getCpf() + "===");
                            System.out.println("(1) Exibir Detalhes do Cliente");
                            System.out.println("(2) Cadastrar Veículo do Cliente");
                            System.out.println("(3) Exibir veículos do Cliente");
                            System.out.println("(0) Voltar");
                            System.out.print(">");

                            int opcaoCliente = scanner.nextInt();
                            scanner.nextLine();

                            if (opcaoCliente == 0) {
                                System.out.println("Voltando...");
                                break;
                            }
                            switch (opcaoCliente) {
                                case 1:
                                    // Exibir detalhes do cliente
                                    System.out.println("\n=== Detalhes do Cliente ===");
                                    System.out.println("Nome: " + clienteAtual.getNome());
                                    System.out.println("CPF: " + clienteAtual.getCpf());
                                    break;
                                case 2:
                                    System.out.println("\nDigite os dados do veículo para adicionar:");
                                    System.out.print("Placa: ");
                                    String placa = scanner.nextLine();
                                    System.out.print("Modelo: ");
                                    String modelo = scanner.nextLine();

                                    // Cria o veículo e associa ao cliente
                                    Veiculo novoVeiculo = new Veiculo(placa, clienteAtual, modelo);

                                    // Grava o veículo no arquivo
                                    if (novoVeiculo.gravarEmArquivo()) {
                                        System.out.println("Veículo adicionado com sucesso para o cliente " + clienteAtual.getNome());
                                    } else {
                                        System.out.println("Erro ao adicionar o veículo.");
                                    }
                                    break;
                                case 3:
                                    // Exibir veículos do cliente
                                    System.out.println("\nVeículos cadastrados para o cliente " + clienteAtual.getNome() + ":");

                                    List<String> todosVeiculos = Veiculo.lerVeiculosDoArquivo();
                                    boolean veiculosEncontrados = false;

                                    // Itera sobre todos os veículos para filtrar pelo CPF do cliente
                                    for (String veiculoInfo : todosVeiculos) {
                                        if (veiculoInfo.contains("CPF: " + cpfAtual)) {
                                            String[] linhas = veiculoInfo.split("\n");
                                            String placaVeiculo = linhas[0].replace("Placa: ", "").trim();
                                            String modeloVeiculo = linhas[1].replace("Modelo: ", "").trim();

                                            // Exibe apenas a placa e o modelo do veículo
                                            System.out.println("Placa: " + placaVeiculo);
                                            System.out.println("Modelo: " + modeloVeiculo);
                                            System.out.println("----------------------------------------------");
                                            veiculosEncontrados = true;
                                        }
                                    }

                                    // Caso nenhum veículo seja encontrado para o CPF fornecido
                                    if (!veiculosEncontrados) {
                                        System.out.println("Nenhum veículo encontrado para o CPF " + cpfAtual);
                                        break;
                                    }
                                    break;

                                default:
                                    System.out.println("Opção Inválida!");
                                    break;
                            }
                        }
                    break;
                case 4:
                    // Selecionar Estacionamento
                    System.out.println("Digite o ID do estacionamento que deseja selecionar: ");

                    List<String> estacionamentos = Estacionamento.lerEstacionamentosDoArquivo();

                    for (String e : estacionamentos) {
                        System.out.println(e);
                    }

                    System.out.print(">");
                    int idAtual = scanner.nextInt();
                    scanner.nextLine();

                    Estacionamento estacionamentoAtual = null;

                    for (String e: estacionamentos) {
                        if (e.contains("ID: " + idAtual)) {
                            String[] linhas = e.split("\n");
                            String nomeEstacionamento = linhas[0].replace("Estacionamento: ", "");
                            String ruaEstacionamento = linhas[2].split(", ")[0].replace("Endereço: ", "");
                            int numeroEstacionamento = Integer.parseInt(linhas[2].split(", ")[1].split(" - ")[0]);
                            String bairroEstacionamento = linhas[2].split(", ")[1].split(" - ")[1];

                            estacionamentoAtual = new Estacionamento(nomeEstacionamento, ruaEstacionamento, bairroEstacionamento, numeroEstacionamento);
                            estacionamentoAtual.setId(idAtual);

                            System.out.println("Estacionamento selecionado: " + estacionamentoAtual.getNome());
                        }
                    }
                        if(estacionamentoAtual == null){
                            System.out.println("Estacionamento com ID " + idAtual + " não encontrado!");
                        }else{
                            boolean done = true;
                            while (done) {
                                System.out.println("(1) Exibir Detalhes do Estacionamento");
                                System.out.println("(2) Cadastrar Vaga");
                                System.out.println("(3) Reservar Vaga");
                                System.out.println("(4) Liberar Vaga");
                                System.out.println("(5) Gerar Cobrança");
                                System.out.println("(0) Voltar");
                                System.out.print(">");
                                int escolha = scanner.nextInt();
                                scanner.nextLine();

                                if(escolha == 0){
                                    System.out.println("Encerrando sistema...");
                                    done = false;
                                }

                                switch (escolha) {
                                    case 1:
                                        //Exibir informacoes do Estacionamento
                                        System.out.println("===Informações do Estacionamento===");
                                        System.out.println("Estacionamento: " + estacionamentoAtual.getNome());
                                        System.out.println("Endereço: " + estacionamentoAtual.getRua() + " - " + estacionamentoAtual.getBairro() + ", " + estacionamentoAtual.getNumero());
                                        break;
                                    case 2:
                                        //Cadastrar Vaga
                                        System.out.println("Escolha o tipo de vaga:");
                                        System.out.println("(1) Vaga Padrão");
                                        System.out.println("(2) Vaga Idoso");
                                        System.out.println("(3) Vaga PCD");
                                        System.out.println("(4) Vaga VIP");
                                        System.out.print("> ");
                                        int tipoVaga = scanner.nextInt();
                                        scanner.nextLine();

                                        Vaga novaVaga = null;

                                        //Cadatrar Tipo da Vaga
                                        switch (tipoVaga) {
                                            case 1:
                                                novaVaga = new Vaga(); // Vaga Padrão
                                                break;
                                            case 2:
                                                novaVaga = new VagaIdoso(); // Vaga Idoso
                                                break;
                                            case 3:
                                                novaVaga = new VagaPCD(); // Vaga PCD
                                                break;
                                            case 4:
                                                novaVaga = new VagaVIP(); // Vaga VIP
                                                break;
                                            default:
                                                System.out.println("Tipo de vaga inválido!");
                                                return;
                                        }

                                        estacionamentoAtual.adicionarVaga(novaVaga);
                                        break;

                                    case 3:
                                        // Reservar Vaga
                                        System.out.print("Digite o ID da vaga a ser reservada: ");
                                        int idVaga = scanner.nextInt();
                                        scanner.nextLine();


                                        if (estacionamentoAtual.reservarVagaPorId(idVaga)) {
                                            System.out.println("Vaga ID " + idVaga + " reservada com sucesso!");
                                        } else {
                                            System.out.println("Falha ao reservar a vaga. Verifique se a vaga está disponível.");
                                        }
                                        break;

//                                    case 4:
//                                        // Liberar vaga
//                                        System.out.print("Digite o ID da vaga a ser liberada: ");
//                                        int idVagaLiberar = scanner.nextInt();
//                                        scanner.nextLine();
//                                        Vaga vagaLiberar = estacionamento.getVagaPorId(idVagaLiberar);
//
//                                        if (vagaLiberar != null && vagaLiberar.liberarVaga()) {
//                                            System.out.println("Vaga ID " + idVagaLiberar + " liberada com sucesso!");
//                                        } else {
//                                            System.out.println("Falha ao liberar a vaga. Verifique se a vaga está ocupada.");
//                                        }
//                                        break;

//                                    case 5:
//                                        // Gerar cobrança
//
//                                        System.out.print("Digite o ID da vaga para cobrança: ");
//                                        int idVagaCobranca = scanner.nextInt();
//                                        scanner.nextLine();
//
//                                        Vaga vagaCobranca = estacionamento.getVagaPorId(idVagaCobranca);
//                                        if (vagaCobranca == null) {
//                                            System.out.println("Vaga não encontrada!");
//                                            break;
//                                        }
//
//                                        System.out.print("Digite a placa do veículo: ");
//                                        String placaVeiculo = scanner.nextLine();
//                                        Veiculo veiculoCobranca = new Veiculo(placaVeiculo);
//
//                                        Cobranca cobranca = new Cobranca(idVagaCobranca, estacionamento, veiculoCobranca);
//                                        cobranca.setHoraSaida(LocalTime.now());
//                                        cobranca.calcularTempoFinal();
//                                        cobranca.calcularValorTotal();
//                                        cobranca.gravarEmArquivo(); // -> Grava a cobranca registrada nos arquivos
//                                        System.out.println("Cobrança gerada com sucesso! Valor total: R$ " + cobranca.getValorTotal());
//
//                                        if (cobranca.pagar()) {
//                                            System.out.println("Vaga liberada após pagamento.");
//                                        }
//                                        break;

                                    default:
                                        System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
                                        break;
                                }
                            }
                        }
                    }
            }
        scanner.close();

        }

}

