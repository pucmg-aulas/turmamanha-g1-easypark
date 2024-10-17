//package Main;
//
//import Models.Cobranca;
//import Models.Cliente;
//import Models.Veiculo;
//import Models.Estacionamento;
//import Models.Vaga;
//
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.List;
//import java.util.Scanner;
//
//public class EstacionamentoApp {
//
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int opcao;
//
//
//        while (true) {
//            System.out.println("\n=== Sistema de Estacionamentos ===");
//            System.out.println("(1) Cadastrar Estacionamento");
//            System.out.println("(2) Cadastrar Cliente");
//            System.out.println("(3) Selecionar Cliente");
//            System.out.println("(4) Selecionar Estacionamento");
//            System.out.println("(0) Sair");
//            System.out.print(">");
//
//            opcao = scanner.nextInt();
//            scanner.nextLine();
//
//            if (opcao == 0) {
//                System.out.println("Encerrando o sistema...");
//                break;
//            }
//
//            switch (opcao) {
//                case 1:
//
//                    // Cadastro de Estacionamento
//                    System.out.print("Digite o nome do estacionamento: ");
//                    String nome = scanner.nextLine();
//
//                    System.out.print("Digite a rua do estacionamento: ");
//                    String rua = scanner.nextLine();
//
//                    System.out.print("Digite o bairro do estacionamento: ");
//                    String bairro = scanner.nextLine();
//
//                    System.out.print("Digite o número do estacionamento: ");
//                    int numero = scanner.nextInt();
//                    scanner.nextLine();
//
//                    System.out.println("Digite a quantidade de vagas do estacionamento: ");
//                    int qntdVagas = scanner.nextInt();
//                    scanner.nextLine();
//
//                    Estacionamento estacionamento = new Estacionamento(nome, rua, bairro, numero, qntdVagas);
//                    if (estacionamento.gravarEstacionamentosEmArquivo()) {
//                        System.out.println("---------------------------------------");
//                        System.out.println("Estacionamento cadastrado com sucesso!");
//                        System.out.println("ID do Estacionamento: " + estacionamento.getId()); // Mostra o ID do estacionamento
//                        System.out.println("Total de Vagas: " + qntdVagas);
//
//                        // Exibir vagas criadas
//                        for (Vaga vaga : estacionamento.getVagas()) {
//                            System.out.println("Vaga ID: " + vaga.getId() + " - Tipo: " + vaga.getTipo());
//                        }
//
//                        System.out.println("---------------------------------------");;
//                    }
//                    break;
//
//                case 2:
//
//                    // Cadastrar cliente
//                    System.out.print("Digite o nome do cliente: ");
//                    String nomeCliente = scanner.nextLine();
//                    System.out.println("Digite o cpf do cliente: ");
//                    String cpfCliente = scanner.nextLine();
//                    Cliente novoCliente = new Cliente(nomeCliente, cpfCliente);
//                    if (novoCliente.gravarEmArquivo()) {
//                        System.out.println("---------------------------------------");
//                        System.out.println("Cliente cadastrado com sucesso!");
//                        System.out.println("Cliente: " + novoCliente.getNome()); //
//                        System.out.println("---------------------------------------");
//                    }
//                    break;
//
//                case 3:
//                    // Selecionar Cliente
//                    System.out.print("Digite o CPF do cliente que deseja selecionar: ");
//                    String cpfAtual = scanner.nextLine();
//
//                    List<String> clientes = Cliente.lerClientesDoArquivo();
//
//                    Cliente clienteAtual = null;
//
//                    for (String c : clientes) {
//                        if (c.contains("CPF: " + cpfAtual)) {
//                            String[] linhas = c.split("\n");
//                            String cpfClienteAtual = linhas[0].replace("CPF: ", "").trim();  // CPF na primeira linha
//                            String nomeClienteAtual = linhas[1].replace("Nome: ", "").trim(); // Nome na segunda linha
//
//                            clienteAtual = new Cliente(nomeClienteAtual, cpfAtual);
//
//                            System.out.println("Cliente encontrado: " + clienteAtual.getNome());
//                            break;
//                        }
//                    }
//                    if (clienteAtual == null) {
//                        System.out.println("Cliente com CPF " + cpfAtual + " não encontrado!");
//                        break;
//                    } else {
//                        System.out.println("\n=== CPF: " + clienteAtual.getCpf() + "===");
//                        System.out.println("(1) Exibir Detalhes do Cliente");
//                        System.out.println("(2) Cadastrar Veículo do Cliente");
//                        System.out.println("(3) Exibir veículos do Cliente");
//                        System.out.println("(0) Voltar");
//                        System.out.print(">");
//
//                        int opcaoCliente = scanner.nextInt();
//                        scanner.nextLine();
//
//                        if (opcaoCliente == 0) {
//                            System.out.println("Voltando...");
//                            break;
//                        }
//                        switch (opcaoCliente) {
//                            case 1:
//                                // Exibir detalhes do cliente
//                                System.out.println("\n=== Detalhes do Cliente ===");
//                                System.out.println("Nome: " + clienteAtual.getNome());
//                                System.out.println("CPF: " + clienteAtual.getCpf());
//                                break;
//                            case 2:
//                                System.out.println("\nDigite os dados do veículo para adicionar:");
//                                System.out.print("Placa: ");
//                                String placa = scanner.nextLine();
//                                System.out.print("Modelo: ");
//                                String modelo = scanner.nextLine();
//
//                                // Cria o veículo e associa ao cliente
//                                Veiculo novoVeiculo = new Veiculo(placa, clienteAtual, modelo);
//
//                                // Grava o veículo no arquivo
//                                if (novoVeiculo.gravarEmArquivo()) {
//                                    System.out.println("Veículo adicionado com sucesso para o cliente " + clienteAtual.getNome());
//                                } else {
//                                    System.out.println("Erro ao adicionar o veículo.");
//                                }
//                                break;
//                            case 3:
//                                // Exibir veículos do cliente
//                                System.out.println("\nVeículos cadastrados para o cliente " + clienteAtual.getNome() + ":");
//
//                                List<String> todosVeiculos = Veiculo.lerVeiculosDoArquivo();
//                                boolean veiculosEncontrados = false;
//
//                                // Itera sobre todos os veículos para filtrar pelo CPF do cliente
//                                for (String veiculoInfo : todosVeiculos) {
//                                    if (veiculoInfo.contains("CPF: " + cpfAtual)) {
//                                        String[] linhas = veiculoInfo.split("\n");
//                                        String placaVeiculo = linhas[0].replace("Placa: ", "").trim();
//                                        String modeloVeiculo = linhas[1].replace("Modelo: ", "").trim();
//
//                                        // Exibe apenas a placa e o modelo do veículo
//                                        System.out.println("Placa: " + placaVeiculo);
//                                        System.out.println("Modelo: " + modeloVeiculo);
//                                        System.out.println("----------------------------------------------");
//                                        veiculosEncontrados = true;
//                                    }
//                                }
//
//                                // Caso nenhum veículo seja encontrado para o CPF fornecido
//                                if (!veiculosEncontrados) {
//                                    System.out.println("Nenhum veículo encontrado para o CPF " + cpfAtual);
//                                    break;
//                                }
//                                break;
//
//                            default:
//                                System.out.println("Opção Inválida!");
//                                break;
//                        }
//                    }
//                    break;
//                case 4:
//                    // Selecionar Estacionamento
//                    System.out.println("Digite o ID do estacionamento que deseja selecionar: ");
//
//                    List<String> estacionamentos = Estacionamento.lerEstacionamentosDoArquivo();
//
//                    for (String e : estacionamentos) {
//                        System.out.println(e);
//                    }
//
//                    System.out.print(">");
//                    int idAtual = scanner.nextInt();
//                    scanner.nextLine();
//
//                    Estacionamento estacionamentoAtual = null;
//
//                    for (String e : estacionamentos) {
//                        if (e.contains("ID: " + idAtual)) {
//                            String[] linhas = e.split("\n");
//                            String nomeEstacionamento = linhas[0].replace("Estacionamento: ", "");
//                            String ruaEstacionamento = linhas[2].split(", ")[0].replace("Endereço: ", "");
//                            int numeroEstacionamento = Integer.parseInt(linhas[2].split(", ")[1].split(" - ")[0]);
//                            String bairroEstacionamento = linhas[2].split(", ")[1].split(" - ")[1];
//                            int qntdVagasEstacionamento = Integer.parseInt(linhas[3].replace("Vagas: ", ""));
//
//                            estacionamentoAtual = new Estacionamento(nomeEstacionamento, ruaEstacionamento, bairroEstacionamento, numeroEstacionamento, qntdVagasEstacionamento);
//                            estacionamentoAtual.setId(idAtual);
//
//                            System.out.println("Estacionamento selecionado: " + estacionamentoAtual.getNome());
//                        }
//                    }
//                    if (estacionamentoAtual == null) {
//                        System.out.println("Estacionamento com ID " + idAtual + " não encontrado!");
//                    } else {
//                        boolean done = true;
//                        while (done) {
//                            System.out.println("(1) Exibir Detalhes do Estacionamento");
//                            System.out.println("(2) Cadastrar Vaga");
//                            System.out.println("(3) Reservar Vaga");
//                            System.out.println("(4) Gerar Cobrança");
//                            System.out.println("(5) Pagar Cobrança");
//                            System.out.println("(0) Voltar");
//                            System.out.print(">");
//                            int escolha = scanner.nextInt();
//                            scanner.nextLine();
//
//                            if (escolha == 0) {
//                                System.out.println("Encerrando sistema...");
//                                done = false;
//                            }
//
//                            switch (escolha) {
//                                case 1:
//                                    //Exibir informacoes do Estacionamento
//                                    System.out.println("===Informações do Estacionamento===");
//                                    System.out.println("Estacionamento: " + estacionamentoAtual.getNome());
//                                    System.out.println("Endereço: " + estacionamentoAtual.getRua() + " - " + estacionamentoAtual.getBairro() + ", " + estacionamentoAtual.getNumero());
//                                    break;
//
////                                case 2:
////                                    //Cadastrar Vaga
////                                    System.out.println("Escolha o tipo de vaga:");
////                                    System.out.println("(1) Vaga Padrão");
////                                    System.out.println("(2) Vaga Idoso");
////                                    System.out.println("(3) Vaga PCD");
////                                    System.out.println("(4) Vaga VIP");
////                                    System.out.print("> ");
////                                    int tipoVaga = scanner.nextInt();
////                                    scanner.nextLine();
////
////                                    Vaga novaVaga = null;
////
////                                    //Cadatrar Tipo da Vaga
////                                    switch (tipoVaga) {
////                                        case 1:
////                                            novaVaga = new Vaga(idAtual); // Vaga Padrão
////                                            break;
////                                        case 2:
////                                            novaVaga = new VagaIdoso(idAtual); // Vaga Idoso
////                                            break;
////                                        case 3:
////                                            novaVaga = new VagaPCD(idAtual); // Vaga PCD
////                                            break;
////                                        case 4:
////                                            novaVaga = new VagaVIP(idAtual); // Vaga VIP
////                                            break;
////                                        default:
////                                            System.out.println("Tipo de vaga inválido!");
////                                            return;
////                                    }
////
////                                    estacionamentoAtual.adicionarVaga(novaVaga);
////                                    break;
//
//                                case 3:
//                                    // Reservar Vaga
//                                    System.out.print("Digite o ID da vaga a ser reservada: ");
//                                    int idVaga = scanner.nextInt();
//                                    scanner.nextLine();
//
//
//                                    if (estacionamentoAtual.reservarVagaPorId(idVaga)) {
//                                        System.out.println("Vaga ID " + idVaga + " reservada com sucesso!");
//                                    } else {
//                                        System.out.println("Falha ao reservar a vaga. Verifique se a vaga está disponível.");
//                                    }
//                                    break;
//
//                                case 4:
//                                    // Gerar cobrança
//
//                                    // Listar as vagas disponiveis
//                                    System.out.println("=== Vagas Disponíveis ===");
//                                    List<Vaga> vagasDisponiveis = estacionamentoAtual.getVagasDisponiveis();
//
//                                    if (vagasDisponiveis.isEmpty()) {
//                                        System.out.println("Não há vagas disponíveis no momento.");
//                                        break;
//                                    }
//
//                                    for (Vaga vaga : vagasDisponiveis) {
//                                        System.out.println("ID da Vaga: " + vaga.getId() + " - Tipo: " + vaga.getClass().getSimpleName());
//                                    }
//
//                                    ///////////////////////////////////
//
//                                    System.out.print("Digite o ID da vaga para reservar: ");
//                                    int idVagaCobranca = scanner.nextInt();
//                                    scanner.nextLine();
//
//                                    Vaga vagaCobranca = estacionamentoAtual.getVagaPorId(idVagaCobranca);
//                                    if (vagaCobranca == null || !vagaCobranca.isDesocupada()) {
//                                        System.out.println("Vaga não encontrada ou não disponível!");
//                                        break;
//                                    }
//
//                                    System.out.print("Digite a placa do veículo: ");
//                                    String placaVeiculo = scanner.nextLine();
//                                    Veiculo veiculoCobranca = Veiculo.getVeiculoPorPlaca(placaVeiculo);
//
//                                    if (veiculoCobranca == null) {
//                                        System.out.println("Veículo não encontrado!");
//                                        break;
//                                    }
//
//                                    vagaCobranca.setStatus(false); // Marca a vaga como ocupada
//                                    vagaCobranca.atualizarStatusNoArquivo("Ocupada"); // Atualiza o status no arquivo
//
//                                    Cobranca cobranca = new Cobranca(idVagaCobranca, estacionamentoAtual, veiculoCobranca);
//
//                                    cobranca.gravarEmArquivo();
//                                    System.out.println("Cobrança gerada para o veiculo: " + veiculoCobranca.getPlaca());
//                                    break;
//                                case 5:
//                                    // Listar as vagas ocupadas
//                                    System.out.println("=== Vagas Ocupadas ===");
//                                    List<Vaga> vagasOcupadas = estacionamentoAtual.getVagasOcupadas();
//
//                                    if (vagasOcupadas.isEmpty()) {
//                                        System.out.println("Não há vagas ocupadas no momento.");
//                                        break;
//                                    }
//
//                                    for (Vaga vaga : vagasOcupadas) {
//                                        System.out.println("ID da Vaga: " + vaga.getId() + " - Tipo: " + vaga.getClass().getSimpleName());
//                                    }
//
//                                    // Solicita o ID da vaga para pagamento
//                                    System.out.print("Digite o ID da vaga para pagamento: ");
//                                    int idVagaPagamento = scanner.nextInt();
//                                    scanner.nextLine(); // Consome a nova linha
//
//                                    // Obtém a vaga correspondente
//                                    Vaga vagaPagamento = estacionamentoAtual.getVagaPorId(idVagaPagamento);
//                                    vagaPagamento.setStatus(false);
//                                    if (vagaPagamento == null) {
//                                        System.out.println("Vaga não encontrada!");
//                                        break;
//                                    }
//
//                                    // Verifica se a vaga já está ocupada
//                                    if (vagaPagamento.isDesocupada()) {
//                                        System.out.println("Esta vaga não está ocupada.");
//                                        break;
//                                    }
//
//                                    System.out.print("Digite a placa do veículo: ");
//                                    String placaVeiculoPagamento = scanner.nextLine();
//                                    Veiculo veiculoCobrancaPagamento = Veiculo.getVeiculoPorPlaca(placaVeiculoPagamento);
//
//                                    LocalTime horaSaida = LocalTime.now();
//                                    Cobranca cobrancaPagamento = new Cobranca(idVagaPagamento, estacionamentoAtual, veiculoCobrancaPagamento); // Cria a instância de cobrança
//                                    LocalDateTime horaEntrada = Cobranca.lerHoraEntradaDeArquivo("./codigo/src/Archives/Cobrancas.txt", idVagaPagamento);
//
//                                    if (horaEntrada != null) {
//                                        cobrancaPagamento.setHoraSaida(horaSaida); // Define a hora de saída na cobrança
//                                        cobrancaPagamento.setHoraEntrada(horaEntrada); // Define a hora de entrada na cobrança
//
//                                        // Calcula o tempo e o valor
//                                        cobrancaPagamento.calcularTempoFinal(); // Calcula a duração
//                                        cobrancaPagamento.calcularValor(); // Calcula o valor total com base nas regras
//
//                                        // Aqui você pode gravar a cobrança no arquivo
//                                        cobrancaPagamento.gravarEmArquivo();
//
//                                        // Realiza o pagamento
//                                        if (cobrancaPagamento.pagar()) {
//                                            System.out.println("Pagamento realizado com sucesso!");
//                                            System.out.println("Valor total: R$ " + cobrancaPagamento.getValorTotal());
//
//                                            // Libera a vaga
//                                            vagaPagamento.liberarVaga();
//                                            System.out.println("Vaga liberada.");
//
//                                            // Remove a cobrança do arquivo
//                                            Cobranca.removerCobrancaDoArquivo("./codigo/src/Archives/Cobrancas.txt", idVagaPagamento);
//                                        } else {
//                                            System.out.println("Erro ao processar o pagamento.");
//                                        }
//                                    } else {
//                                        System.out.println("Erro: Não foi possível ler a hora de entrada para a vaga " + idVagaPagamento);
//                                    }
//                                    break;
//                            }
//                        }
//                    }
//            }
//        }
//        scanner.close();
//
//    }
//
//}
//
