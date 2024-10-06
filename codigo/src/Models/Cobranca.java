package Models;

import java.io.*;
import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;


public class Cobranca {
    private Vaga vaga;
    private Veiculo veiculo;
    private LocalTime horaEntrada;
    private LocalTime horaSaida;
    private int tempoTotal;
    private static final double VALORTEMPO = 4;
    private static final double LIMITEPRECO = 50;
    private static final double FRACAOTEMPO = 15;
    private double valorTotal;

    public Cobranca(int idVaga, Estacionamento estacionamento, Veiculo veiculo){
        this.vaga = estacionamento.getVagaPorId(idVaga);
        this.veiculo = veiculo;
        this.horaEntrada = LocalTime.now();
        if (this.vaga != null){
            this.vaga.atualizarStatusNoArquivo("Ocupada");
        }else{
            System.out.println("Vaga não encontrada!");
        }
    }

    public Vaga getVaga() {
        return this.vaga;
    }

    public double getValorTotal(){
        return valorTotal;
    }

    public void setHoraSaida(LocalTime horaSaida){
        this.horaSaida = LocalTime.of(horaSaida.getHour(), horaSaida.getMinute());
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

    public void calcularValorTotal() {
        // Calcula o valor base sem aplicar os descontos ou acréscimos
        double valorBase = (this.tempoTotal / FRACAOTEMPO) * VALORTEMPO;


        // Respeitar o limite de preço
        this.valorTotal = valorBase > LIMITEPRECO ? LIMITEPRECO : valorBase;
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
    public static Cobranca lerDeArquivo(String nomeArquivo, Estacionamento estacionamento) {
        try (BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo))) {
            // Leitura do ID da vaga
            int idVaga = Integer.parseInt(leitor.readLine().trim());

            // Leitura da placa do veículo
            String placa = leitor.readLine().trim();
            Veiculo veiculo = new Veiculo(placa, new Cliente("Guilherme", "111"), "Tracker");

            // Leitura da hora de entrada
            LocalTime horaEntrada = LocalTime.parse(leitor.readLine().trim(), DateTimeFormatter.ofPattern("HH:mm"));

            // Leitura da hora de saída
            LocalTime horaSaida = LocalTime.parse(leitor.readLine().trim(), DateTimeFormatter.ofPattern("HH:mm"));

            // Criação da instância de Cobranca
            Cobranca cobranca = new Cobranca(idVaga, estacionamento, veiculo);
            cobranca.horaEntrada = horaEntrada;
            cobranca.setHoraSaida(horaSaida);
            cobranca.calcularTempoFinal();
            cobranca.calcularValorTotal();

            return cobranca;
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return null;
    }

    // Método para gravar os dados da cobrança em um arquivo de texto
    public boolean gravarEmArquivo() {
        File cobrancas = new File("./src/Models/Archives/Cobrancas.txt");

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(cobrancas))) {
            escritor.write("Vaga: " + this.vaga.getId() + "\n");
            escritor.write("Veículo: " + this.veiculo.getPlaca() + "\n");
            escritor.write("Hora de Entrada: " + this.horaEntrada.toString() + "\n");
            escritor.write("Hora de Saída: " + this.horaSaida.toString() + "\n");
            escritor.write("Tempo Total: " + this.getTempoTotal() + " minutos\n");
            escritor.write("Valor Total: R$" + this.getValorTotal() + "\n");
            escritor.write("------------------------------------------");
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    public Veiculo getVeiculo() {
        return veiculo;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalTime getHoraSaida() {
        return horaSaida;
    }


}
