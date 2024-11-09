package dao;

import Models.Cliente;
import Models.Cobranca;
import Models.Estacionamento;
import Models.HistoricoUso;
import Models.ITipo;
import Models.Pagamento;
import Models.Vaga;
import Models.VagaIdoso;
import Models.VagaPCD;
import Models.VagaRegular;
import Models.VagaVIP;
import Models.Veiculo;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {
    private static final String ARQUIVO = "./src/test/java/Archives/Pagamentos.txt";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private List<Pagamento> pagamentos;
    private static PagamentoDAO instance;
    private VeiculoDAO veiculos;
    private ClienteDAO clientes;
    private EstacionamentoDAO estacionamentos;
    private VagaDAO vagas;
    
    private PagamentoDAO() throws IOException{
        this.clientes = ClienteDAO.getInstance();
        this.veiculos = VeiculoDAO.getInstance();
        this.estacionamentos = EstacionamentoDAO.getInstance();

        pagamentos = listarPagamentos();
        if(pagamentos == null){
            pagamentos = new ArrayList<>();
        }
    }
    
    public static PagamentoDAO getInstance() throws IOException{
        if(instance == null){
            instance = new PagamentoDAO();
        }
        return instance;
    }
    
    public void salvarPagamento(Cobranca cobranca) throws IOException {
    Pagamento pagamento = new Pagamento();
    int idEstacionamento = cobranca.getIdEstacionamento();
    this.vagas = VagaDAO.getInstance(idEstacionamento);
    double valorTotal = cobranca.getValorTotal();
    String placaVeiculo = cobranca.getPlacaVeiculo();
    int idVaga = cobranca.getIdVaga();
    Vaga vagaAtual = vagas.getVagaPorId(idVaga);
    String tipoVaga = vagaAtual.getTipo();
    int tempoTotal = cobranca.getTempoTotal();

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
        writer.write(pagamento.getIdPagamento() + ";" + idEstacionamento + ";" + valorTotal + ";"
                + tipoVaga + ";" + placaVeiculo + ";" + tempoTotal + ";" 
                + cobranca.getHoraEntrada().format(FORMATTER) + ";"
                + pagamento.getDataPagamento().format(FORMATTER) + "\n");
    }
}

    public List<Pagamento> getPagamentosPorCpf(String cpf) throws IOException {
    List<Pagamento> pagamentos = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
        String linha;
        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(";");
            
            try {
                int idPagamento = Integer.parseInt(dados[0]);
                int idEstacionamento = Integer.parseInt(dados[1]);
                double valorTotal = Double.parseDouble(dados[2]);
                
                String tipoVaga = dados[3];
                
                Vaga vagaAtual = new Vaga();
                vagaAtual.setIdEstacionamento(idEstacionamento);
                if("Idoso".equals(tipoVaga)){
                    vagaAtual.setTipo(new VagaIdoso());
                }else if("PCD".equals(tipoVaga)){
                   vagaAtual.setTipo(new VagaPCD());
                }else if("VIP".equals(tipoVaga)){
                     vagaAtual.setTipo(new VagaVIP());
                }else if("Regular".equals(tipoVaga)){
                      vagaAtual.setTipo(new VagaRegular());
                }
                
                String placaVeiculo = dados[4];
                int tempoTotal = Integer.parseInt(dados[5]);
                
                Veiculo veiculoAtual = veiculos.buscarVeiculoPorPlaca(placaVeiculo);
                Cliente clienteAtual = veiculoAtual.getCliente();
                
                LocalDateTime dataEntrada = LocalDateTime.parse(dados[6], FORMATTER);
                LocalDateTime dataSaida = LocalDateTime.parse(dados[7], FORMATTER);
                
                
                
                if (cpf.equals(clienteAtual.getCpf())) {
                    Pagamento pagamento = new Pagamento();
                    pagamento.setIdEstacionamento(idEstacionamento);
                    pagamento.setValorPago(valorTotal);
                    pagamento.setIdPagamento(idPagamento);
                    pagamento.setDataPagamento(dataSaida);
                    pagamento.setDataEntrada(dataEntrada);
                    pagamento.setPlacaVeiculo(placaVeiculo);
                    pagamento.setTipoVaga(vagaAtual.getTipoVaga());
                    pagamento.setTempoTotal(tempoTotal);
                    pagamentos.add(pagamento);
                }

            } catch (NumberFormatException | DateTimeParseException e) {
                System.out.println("Erro ao processar a linha: " + linha + " - " + e.getMessage());
            }
        }
    } catch (IOException e) {
        System.out.println("Erro ao ler o arquivo de pagamentos: " + e.getMessage());
        throw e; // Repassa a exceção para ser tratada externamente, se necessário
    }

    return pagamentos;
}

    
    public List<Pagamento> listarPagamentos() throws IOException {
        List<Pagamento> pagamentos = new ArrayList<>();
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            System.out.println("Arquivo de pagamentos não encontrado.");
            return pagamentos; // Retorna lista vazia se o arquivo não existir
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");

                // Verifica se a linha tem o número correto de dados
                if (dados.length < 8 || dados[0].isEmpty() || dados[1].isEmpty() || dados[2].isEmpty() || dados[3].isEmpty() || dados[4].isEmpty() || dados[5].isEmpty() || dados[6].isEmpty()|| dados[7].isEmpty()) {
                    System.out.println("Linha inválida ou incompleta: " + linha);
                    continue;
                }

                try {
                    int idPagamento = Integer.parseInt(dados[0]);
                    int idEstacionamento = Integer.parseInt(dados[1]);
                    double valorPago = Double.parseDouble(dados[2]);
                    String tipoVaga = dados[3];
                    String placaVeiculo = dados[4];
                    int tempoTotal = Integer.parseInt(dados[5]);
                    String dataEntrada = dados[6];
                    String dataSaida = dados[7];
                    
                    Vaga vagaAtual = new Vaga();
                    vagaAtual.setIdEstacionamento(idEstacionamento);
                    if("Idoso".equals(tipoVaga)){
                        vagaAtual.setTipo(new VagaIdoso());
                    }else if("PCD".equals(tipoVaga)){
                        vagaAtual.setTipo(new VagaPCD());
                    }else if("VIP".equals(tipoVaga)){
                        vagaAtual.setTipo(new VagaVIP());
                    }else if("Regular".equals(tipoVaga)){
                        vagaAtual.setTipo(new VagaRegular());
                    }
                    
                    Pagamento pagamento = new Pagamento();
                    pagamento.setIdEstacionamento(idEstacionamento);
                    pagamento.setValorPago(valorPago);
                    pagamento.setIdPagamento(idPagamento);
                    
                    LocalDateTime dataPagamentoFormatada = LocalDateTime.parse(dataSaida, FORMATTER);
                    pagamento.setDataPagamento(dataPagamentoFormatada);
                    
                    LocalDateTime dataEntradaFormatada = LocalDateTime.parse(dataEntrada, FORMATTER);
                    pagamento.setDataEntrada(dataEntradaFormatada);
                    
                    pagamento.setTempoTotal(tempoTotal);
                    pagamento.setPlacaVeiculo(placaVeiculo);
                    pagamento.setTipoVaga(vagaAtual.getTipoVaga());

                    pagamentos.add(pagamento);
                } catch (NumberFormatException | DateTimeParseException e) {
                    System.out.println("Erro ao processar a linha: " + linha + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de pagamentos: " + e.getMessage());
        }

        return pagamentos;
    }
    
    public static String getHoraSaida(){
        return LocalDateTime.now().format(FORMATTER);
    }
    
    public List<HistoricoUso> buscarHistoricoPorCpf(String cpf) throws IOException {
    List<HistoricoUso> historicoFiltrado = new ArrayList<>();
    
    List<Pagamento> ListaPagamentos = listarPagamentos();
    
    for(Pagamento pagamento : ListaPagamentos){
        String placaVeiculo = pagamento.getPlacaVeiculo();
        Veiculo veiculoAtual = veiculos.buscarVeiculoPorPlaca(placaVeiculo);
        String cpfClienteAtual = veiculoAtual.getCliente().getCpf();
        
        if(cpfClienteAtual.equals(cpf)){
            String cpfAtual = cpf;
            Estacionamento estacionamentoAtual = estacionamentos.getEstacionamentoPorId(pagamento.getIdEstacionamento());
            String nomeEstacionamento = estacionamentoAtual.getNome();
            String placaAtual = pagamento.getPlacaVeiculo();
            ITipo vaga = pagamento.getTipoVaga();
            double valorTotal = pagamento.getValorPago();
            int tempoTotal = pagamento.getTempoTotal();
            
            
            HistoricoUso historicoAtual = new HistoricoUso(cpfAtual, nomeEstacionamento, vaga, placaAtual, valorTotal,tempoTotal);
            historicoFiltrado.add(historicoAtual);
        }
    }

        return historicoFiltrado;
    }
}