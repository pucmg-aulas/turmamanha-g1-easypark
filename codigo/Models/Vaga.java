package Models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Vaga {

    private int id;

    // Status True: Desocupado
    // Status False: Ocupado
    private boolean status;

    private static int nextId = 1;

    public Vaga() {
        this.id = nextId;
        nextId++;
        this.status = true;
    }

    public boolean ocuparVaga() {
        if (status) {
            this.status = false;
            // O true retorna a ocupação da vaga.
            return true;
        } else {
            // O false retorna a não ocupação da vaga.
            return false;
        }
    }

    public boolean liberarVaga() {
        if (!status) { // Se a vaga estiver ocupada
            this.status = true; // Liberar a vaga
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

    // Método para gravar os dados da vaga em um arquivo de texto
    public void gravarEmArquivo() {
        File vaga = new File("vaga.txt");

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(vaga))) {
            escritor.write("Vaga ID: " + this.id + "\n");
            escritor.write("Status: " + (this.status ? "Desocupada" : "Ocupada") + "\n");
            System.out.println("Dados da vaga gravados em " + vaga.getName());
        } catch (IOException e) {
            System.out.println("Erro ao gravar no arquivo: " + e.getMessage());
        }
    }
}
