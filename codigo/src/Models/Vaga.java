package Models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Vaga implements EncontrarMaior{

    private int id;
    protected double tarifaBase;

    // Status True: Desocupado
    // Status False: Ocupado
    private boolean status;
    private static int nextId = 1;

    public Vaga() {
        this.id = EncontrarMaiorId() + 1;
        this.status = true;
        this.tarifaBase = 10.0;
    }

    public boolean isDesocupada(){
        return status;
    }

    public boolean liberarVaga() {
        if (!status) { // Se a vaga estiver ocupada
            this.status = true; // Liberar a vaga
            // gravarEmArquivo();
            return true;
        } else {
            return false; // A vaga já está desocupada
        }
    }


    public int getId() {
        return id;
    }

    public boolean getStatus() {
        return status;
    }

    public double calcularValor(double tarifaBase) {
        return tarifaBase;
    }

    File vaga = new File("./src/Models/Archives/Vaga.txt");

    // Método para gravar os dados da vaga em um arquivo de texto
    public boolean gravarEmArquivo() {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(vaga))) {
            escritor.write("Vaga ID: " + this.id + "\n");
            escritor.write("Status: " + (this.status ? "Desocupada" : "Ocupada") + "\n");
            escritor.write("-----------------------------------------------");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

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


    @Override
    public int EncontrarMaiorId() {
        return 0;
    }
}
