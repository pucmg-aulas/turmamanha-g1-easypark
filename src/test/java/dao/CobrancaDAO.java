package dao;
/*
import Models.Cobranca;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CobrancaDAO {

    private static final String FILE_PATH = "./codigo/src/Archives/Cobrancas.txt";

    // Método para gravar uma cobrança no arquivo
    public boolean salvarCobranca(Cobranca cobranca) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            escritor.write(cobranca.getVaga().getId() + ";" + cobranca.getVeiculo().getPlaca() + ";" + cobranca.getHoraEntrada() + ";" + cobranca.getHoraSaida() + ";" + cobranca.getTempoTotal() + ";" + cobranca.getValorTotal());
            escritor.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao gravar a cobrança: " + e.getMessage());
            return false;
        }
    }

    // Método para ler todas as cobranças do arquivo
    public List<Cobranca> listarCobrancas() {
        List<Cobranca> cobrancas = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(";");
                // Aqui você pode adicionar a lógica para converter esses dados de volta em um objeto Cobranca.
                // Como o código original de Cobranca não possui um construtor direto com esses parâmetros,
                // seria necessário adaptar a construção do objeto aqui.

                // Por exemplo:
                // Cobranca cobranca = new Cobranca(...);
                // cobrancas.add(cobranca);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de cobranças: " + e.getMessage());
        }

        return cobrancas;
    }

    // Método para remover uma cobrança do arquivo
    public boolean removerCobranca(int idVaga) {
        File arquivoOriginal = new File(FILE_PATH);
        File arquivoTemp = new File("./codigo/src/Archives/Cobrancas_temp.txt");
        boolean found = false;

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivoOriginal));
             BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivoTemp))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(";");
                if (Integer.parseInt(dados[0]) != idVaga) {
                    escritor.write(linha);
                    escritor.newLine();
                } else {
                    found = true; // Encontrou a cobrança e não a escreveu no novo arquivo
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao remover a cobrança: " + e.getMessage());
            return false;
        }

        // Substituir o arquivo original pelo arquivo temporário
        if (!arquivoOriginal.delete()) {
            System.out.println("Erro ao deletar o arquivo original.");
            return false;
        }
        if (!arquivoTemp.renameTo(arquivoOriginal)) {
            System.out.println("Erro ao renomear o arquivo temporário.");
            return false;
        }

        return found;
    }
}
*/