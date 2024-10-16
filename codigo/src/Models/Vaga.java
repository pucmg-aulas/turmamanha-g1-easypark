package Models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public abstract class Vaga{

    private int id;
    protected double tarifaBase;
    private int idEstacionamento;

    // Status True: Desocupado
    // Status False: Ocupado
    private boolean status;
    private static int nextId = 1;
    private static final String FILE_PATH = "./codigo/src/Archives/Vagas";


    public Vaga(int idEstacionamento) {
        this.id = EncontrarMaiorId(idEstacionamento) + 1;
        this.status = true;
        this.tarifaBase = 10.0;
        this.idEstacionamento = idEstacionamento;
    }

    public abstract String getTipo();

    public boolean isDesocupada(){
        return status;
    }

    public boolean liberarVaga() {
        if (!status) { // Se a vaga estiver ocupada
            this.status = true; // Liberar a vaga
            liberarVaga();
            return true;
        } else {
            return false; // A vaga já está desocupada
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getIdEstacionamento() {
        return idEstacionamento;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public boolean getStatus() {
        return status;
    }

    public double calcularValor(double tarifaBase) {
        return tarifaBase;
    }

    File vaga = new File("./codigo/src/Archives/Vaga.txt");


    // Método para ler os dados da vaga 
    public boolean lerVagaPorId(int idVaga) {
        try (BufferedReader leitor = new BufferedReader(new FileReader(vaga))) {
            String linha;
            boolean vagaEncontrada = false;
    
            while ((linha = leitor.readLine()) != null) {
                if (linha.contains("Vaga ID: " + idVaga)) {
                    vagaEncontrada = true;
                    System.out.println(linha); // Exibe "Vaga ID"
                    System.out.println(leitor.readLine()); // Exibe "Status"
                    System.out.println(leitor.readLine()); // Exibe "Tipo de Vaga"
                    break;
                }
            }
    
            if (!vagaEncontrada) {
                System.out.println("Vaga com ID " + idVaga + " não encontrada.");
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static int EncontrarMaiorId(int idEstacionamento) {
        String filePath = FILE_PATH + idEstacionamento + ".txt";
        int maiorId = 0;
        try (BufferedReader leitor = new BufferedReader(new FileReader(filePath))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.startsWith("ID: ")) {
                    int idAtual = Integer.parseInt(linha.split(": ")[1]);
                    if (idAtual > maiorId) {
                        maiorId = idAtual;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            //System.out.println("Arquivo não encontrado, iniciando novo arquivo...");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return maiorId;
    }

    public boolean atualizarStatusNoArquivo(String novoStatus) {
        File arquivoVagas = new File("./codigo/src/Archives/Vagas" + idEstacionamento + ".txt");
        StringBuilder conteudoArquivo = new StringBuilder();

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivoVagas))) {
            String linha;

            while ((linha = leitor.readLine()) != null) {
                if (linha.contains("ID: " + this.getId())) {
                    // Altera a linha do status para o novo valor
                    conteudoArquivo.append(linha).append("\n"); // ID
                    leitor.readLine(); // Pular a linha de status anterior
                    conteudoArquivo.append("Status: ").append(novoStatus).append("\n");
                } else {
                    conteudoArquivo.append(linha).append("\n");
                }
            }

            // Escreve o conteúdo atualizado no arquivo
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivoVagas))) {
                escritor.write(conteudoArquivo.toString());
                return true;
            }

        } catch (IOException e) {
            System.out.println("Erro ao atualizar o status no arquivo: " + e.getMessage());
            return false;
        }
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
