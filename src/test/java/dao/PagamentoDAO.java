package dao;

import Models.Cliente;
import Models.Cobranca;
import Models.Estacionamento;
import Models.Pagamento;
import Models.Veiculo;
import dao.VeiculoDAO;
import java.io.*;
import java.time.LocalDate;
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
    
    private PagamentoDAO() throws IOException{
        this.clientes = ClienteDAO.getInstance();
        this.veiculos = VeiculoDAO.getInstance();
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
        double valorTotal = cobranca.getValorTotal();
        String placaVeiculo = cobranca.getPlacaVeiculo();
        Veiculo veiculoAtual = veiculos.buscarVeiculoPorPlaca(placaVeiculo);
        Cliente clienteAtual = veiculoAtual.getCliente();
        String cpfCliente = clienteAtual.getCpf();
        
        // Salvar no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            writer.write(pagamento.getIdPagamento() + ";" + idEstacionamento + ";" + valorTotal + ";" + pagamento.getDataPagamento().format(FORMATTER) + ";" + cpfCliente + "\n");
        }
    }

    public List<Pagamento> getPagamentosPorCpf(String cpf) throws IOException {
    List<Pagamento> pagamentos = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
        String linha;
        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(";");
            
            try {
                int idPagamento = Integer.parseInt(dados[0]);
                int idEstacionamento = Integer.parseInt(dados[1]);
                double valorTotal = Double.parseDouble(dados[2]);
                LocalDateTime dataPagamento = LocalDateTime.parse(dados[3], formatter);
                String cpfCliente = dados[4];
                Cliente clienteAtual = clientes.buscarClientePorCpf(cpfCliente);

                // Verifica se o CPF do pagamento corresponde ao CPF desejado
                if (cpfCliente.equals(cpf)) {
                    Pagamento pagamento = new Pagamento();
                    pagamento.setIdEstacionamento(idEstacionamento);
                    pagamento.setValorPago(valorTotal);
                    pagamento.setIdPagamento(idPagamento);
                    pagamento.setDataPagamento(dataPagamento);
                    pagamento.setCliente(clienteAtual);
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
                if (dados.length < 5 || dados[0].isEmpty() || dados[1].isEmpty() || dados[2].isEmpty() || dados[3].isEmpty() || dados[4].isEmpty()) {
                    System.out.println("Linha inválida ou incompleta: " + linha);
                    continue;
                }

                try {
                    int idPagamento = Integer.parseInt(dados[0]);
                    int idEstacionamento = Integer.parseInt(dados[1]);
                    double valorPago = Double.parseDouble(dados[2]);
                    LocalDateTime dataPagamento = LocalDateTime.parse(dados[3], FORMATTER);
                    String cpfCliente = dados[4];
                    Cliente clienteAtual = clientes.buscarClientePorCpf(cpfCliente);

                    Pagamento pagamento = new Pagamento();
                    pagamento.setIdEstacionamento(idEstacionamento);
                    pagamento.setValorPago(valorPago);
                    pagamento.setIdPagamento(idPagamento);
                    pagamento.setDataPagamento(dataPagamento);
                    pagamento.setCliente(clienteAtual);

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
}