package dao;

import Models.HistoricoUso;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistoricoUsoDAO {
    private static final String ARQUIVO_HISTORICO = "Archives/historico_uso.txt";

    public void registrarEntrada(String placaCarro) {
        LocalDateTime agora = LocalDateTime.now();
        HistoricoUso historico = new HistoricoUso(placaCarro, agora);
        List<HistoricoUso> historicos = carregarHistorico();
        historicos.add(historico);
        salvarHistorico(historicos);
    }

    public void registrarSaida(String placaCarro) {
        List<HistoricoUso> historicos = carregarHistorico();
        for (HistoricoUso historico : historicos) {
            if (historico.getPlacaCarro().equals(placaCarro) && historico.getDataSaida() == null) {
                historico.registrarSaida(LocalDateTime.now());
                break;
            }
        }
        salvarHistorico(historicos);
    }

    private void salvarHistorico(List<HistoricoUso> historicos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_HISTORICO))) {
            for (HistoricoUso historico : historicos) {
                writer.write(historico.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<HistoricoUso> carregarHistorico() {
        List<HistoricoUso> historicos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_HISTORICO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                String placa = partes[0];
                LocalDateTime entrada = LocalDateTime.parse(partes[1], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                LocalDateTime saida = partes[2].isEmpty() ? null : LocalDateTime.parse(partes[2], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                HistoricoUso historico = new HistoricoUso(placa, entrada);
                if (saida != null) {
                    historico.registrarSaida(saida);
                }
                historicos.add(historico);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return historicos;
    }
}
