package Models;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


public class Cobranca {
    private Vaga vaga;
    private Veiculo veiculo;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;
    private int tempoTotal;
    private static final double VALORTEMPO = 4;
    private static final double LIMITEPRECO = 50;
    private static final double FRACAOTEMPO = 15;
    private double valorTotal;
    private static final String FILE_PATH = "./codigo/src/Archives/Cobrancas.txt";

    public Cobranca(int idVaga, Estacionamento estacionamento, Veiculo veiculo){
        this.vaga = estacionamento.getVagaPorId(idVaga);
        this.veiculo = veiculo;
        this.horaEntrada = LocalDateTime.now();
        if (this.vaga != null){
            vaga.setStatus(false);
            this.vaga.atualizarStatusNoArquivo("Ocupada");
        }else{
            vaga.setStatus(true);
        }
        this.tempoTotal = 0;
        this.valorTotal = 0;
        this.horaSaida = null;
    }

    public Vaga getVaga() {
        return this.vaga;
    }

    public double getValorTotal(){
        return valorTotal;
    }

    public void setHoraSaida(LocalTime horaSaida) {
        if (horaSaida != null) {
            this.horaSaida = LocalDateTime.of(this.horaEntrada.toLocalDate(), horaSaida);
        } else {
            System.out.println("Hora de saída não pode ser nula.");
        }
    }

    public void calcularTempoFinal() {
        if (horaEntrada != null && horaSaida != null) {
            // Calcula a duração entre a hora de entrada e a hora de saída
            Duration duracao = Duration.between(horaEntrada, horaSaida);

            // Obtém a duração total em minutos e armazena no atributo tempoTotal
            this.tempoTotal = (int) duracao.toMinutes();

            System.out.println("Tempo total calculado: " + tempoTotal + " minutos.");
        } else {
            System.out.println("Erro: Hora de entrada ou saída não definida.");
        }
    }

    public double calcularValor() {
        // Calcular a duração total em minutos
        long minutos = Duration.between(horaEntrada, horaSaida).toMinutes();

        // Calcular o valor base (R$4 a cada 15 minutos)
        double valorBase = (minutos / 15) * 4;

        // Aplica o limite de R$50
        valorBase = Math.min(valorBase, 50);

        // Se a vaga for VIP, aumenta o valor em 20%
        if (vaga instanceof VagaVIP) {
            valorTotal = valorBase * 1.2; // 20% a mais
        } else if (vaga instanceof VagaIdoso) {
            valorTotal = valorBase * 0.85; // 15% de desconto
        } else if (vaga instanceof VagaPCD) {
            valorTotal = valorBase * 0.87; // 13% de desconto
        } else {
            valorTotal = valorBase; // Vaga padrão
        }

        // Arredondar para 2 casas decimais, se necessário
        valorTotal = Math.round(valorTotal * 100.0) / 100.0;

        return valorTotal;
    }

    public boolean atualizarArquivoComInformacoes() {
        File cobrancas = new File(FILE_PATH);
        File tempFile = new File("./src/Models/Archives/Cobrancas_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(cobrancas));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String linha;
            boolean found = false;

            while ((linha = reader.readLine()) != null) {
                if (linha.contains("Vaga: " + this.vaga.getId())) {
                    found = true;
                    // Escreve a cobrança atualizada
                    writer.write("Vaga: " + this.vaga.getId() + "\n");
                    writer.write("Veículo: " + this.veiculo.getPlaca() + "\n");
                    writer.write("Hora de Entrada: " + this.horaEntrada.toString() + "\n");
                    writer.write("Hora de Saída: " + this.horaSaida.toString() + "\n");
                    writer.write("Tempo Total: " + this.tempoTotal + " minutos\n");
                    writer.write("Valor Total: R$" + this.valorTotal + "\n");
                    writer.write("------------------------------------------\n");
                } else {
                    writer.write(linha + "\n"); // Mantém a linha existente
                }
            }

            // Se não encontrou, pode querer lançar um aviso ou erro
            if (!found) {
                System.out.println("Cobrança não encontrada para a vaga: " + this.vaga.getId());
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o arquivo de cobranças: " + e.getMessage());
            return false;
        }

        // Substitui o arquivo original pelo temporário
        if (!cobrancas.delete()) {
            System.out.println("Erro ao deletar o arquivo original.");
            return false;
        }

        if (!tempFile.renameTo(cobrancas)) {
            System.out.println("Erro ao renomear o arquivo temporário.");
            return false;
        }

        return true;
    }

    public boolean pagar() {
        if (this.vaga != null) {
            // Atualiza o status da vaga para "Desocupada" após o pagamento
            if (this.vaga.liberarVaga()) {
                this.vaga.atualizarStatusNoArquivo("Desocupada");
                return true;
            }
        }
        return false;
    }

    public int getTempoTotal() {
        return tempoTotal;
    }

    // Método para ler os dados de um arquivo de texto
    public static LocalDateTime lerHoraEntradaDeArquivo(String nomeArquivo, int idVaga) {
        try (BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                // Verifica se a linha contém a vaga correspondente
                if (linha.startsWith("Vaga: " + idVaga)) {
                    // Lê as próximas linhas para encontrar a Hora de Entrada
                    for (int i = 0; i < 3; i++) {
                        linha = leitor.readLine();
                        if (linha == null) {
                            return null; // Se não houver linha suficiente, retorna null
                        }

                        // Verifica se esta linha é a Hora de Entrada
                        if (linha.startsWith("Hora de Entrada: ")) {
                            String horaEntradaStr = linha.split(": ")[1].trim();
                            return LocalDateTime.parse(horaEntradaStr); // Retorna a hora de entrada como LocalDateTime
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Erro ao analisar a hora de entrada: " + e.getMessage());
        }
        return null;
    }

    // Método para gravar os dados da cobrança em um arquivo de texto
    public boolean gravarEmArquivo() {
        File cobrancas = new File(FILE_PATH);

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(cobrancas, true))) {
            escritor.write("Vaga: " + this.vaga.getId() + "\n");
            escritor.write("Veículo: " + this.veiculo.getPlaca() + "\n");
            escritor.write("Hora de Entrada: " + this.horaEntrada.toString() + "\n");
            escritor.write("Hora de Saída: " + (horaSaida != null ? horaSaida.toString() : "N/A") + "\n");
            escritor.write("Tempo Total: " + (tempoTotal > 0 ? tempoTotal + " minutos" : "N/A") + "\n");
            escritor.write("Valor Total: R$" + (valorTotal > 0 ? valorTotal : "0") + "\n");
            escritor.write("------------------------------------------\n");
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao gravar a cobrança: " + e.getMessage());
            return false;
        }
    }

    public boolean finalizarCobrança() {
        this.horaSaida = LocalDateTime.now(); // Captura a hora de saída
        this.tempoTotal = (int) Duration.between(horaEntrada, horaSaida).toMinutes(); // Calcula o tempo total
        this.valorTotal = calcularValor(); // Calcula o valor total

        // Atualiza o arquivo com as informações finais
        return atualizarArquivoComInformacoes();
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    public static List<String> lerCobrançasDoArquivo() {
        List<String> cobrancas = new ArrayList<>();
        String caminhoArquivo = "cobrancas.txt"; // Substitua pelo caminho do seu arquivo de cobranças

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            StringBuilder sb = new StringBuilder();
            String linha;

            while ((linha = br.readLine()) != null) {
                // Adiciona a linha ao StringBuilder
                sb.append(linha).append(System.lineSeparator());

                // Verifica se a linha é uma linha de separação
                if (linha.trim().equals("------------------------------------------")) {
                    cobrancas.add(sb.toString().trim()); // Adiciona a cobrança completa à lista
                    sb.setLength(0); // Limpa o StringBuilder para a próxima cobrança
                }
            }

            // Adiciona a última cobrança se não terminar com separador
            if (sb.length() > 0) {
                cobrancas.add(sb.toString().trim());
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de cobranças: " + e.getMessage());
        }

        return cobrancas;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public static void removerCobrancaDoArquivo(String nomeArquivo, int idVaga) {
        File arquivo = new File(nomeArquivo);
        List<String> linhas = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean found = false;

            while ((linha = leitor.readLine()) != null) {
                // Adiciona a linha à lista, exceto a que corresponde à vaga paga
                if (linha.startsWith("Vaga: " + idVaga)) {
                    found = true; // Indica que a cobrança foi encontrada
                    // Pula as próximas linhas da cobrança a ser removida
                    for (int i = 0; i < 4; i++) {
                        linha = leitor.readLine();
                        if (linha == null) break; // Evita NullPointerException
                    }
                } else {
                    linhas.add(linha);
                }
            }

            if (found) {
                // Grava as linhas restantes de volta ao arquivo
                try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))) {
                    for (String l : linhas) {
                        escritor.write(l);
                        escritor.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler ou escrever no arquivo: " + e.getMessage());
        }
    }


}
