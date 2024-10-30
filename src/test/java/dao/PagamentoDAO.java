package dao;

import Models.Cobranca;
import Models.Estacionamento;
import Models.Pagamento;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {
    private static final String ARQUIVO = "./src/test/java/Archives/Pagamentos.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public void salvarPagamento(Cobranca cobranca) throws IOException {
        Pagamento pagamento = new Pagamento();
        int idEstacionamento = cobranca.getIdEstacionamento();
        double valorTotal = cobranca.getValorTotal();

        // Salvar no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            writer.write(pagamento.getIdPagamento() + ";" + idEstacionamento + ";" + valorTotal + ";" + pagamento.getDataPagamento().format(FORMATTER) + "\n");
        }
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
                if (dados.length < 4 || dados[0].isEmpty() || dados[1].isEmpty() || dados[2].isEmpty() || dados[3].isEmpty()) {
                    System.out.println("Linha inválida ou incompleta: " + linha);
                    continue;
                }

                try {
                    int idPagamento = Integer.parseInt(dados[0]);
                    int idEstacionamento = Integer.parseInt(dados[1]);
                    double valorPago = Double.parseDouble(dados[2]);
                    LocalDateTime dataPagamento = LocalDateTime.parse(dados[3], FORMATTER);

                    Pagamento pagamento = new Pagamento();
                    pagamento.setIdPagamento(idPagamento);
                    pagamento.setDataPagamento(dataPagamento);

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
}
