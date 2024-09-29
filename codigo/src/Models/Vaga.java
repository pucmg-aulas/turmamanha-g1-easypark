package src.Models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Vaga {

    private int id;

    // Status True: Desocupado
    // Status False: Ocupado
    private boolean status;
    private TipoVaga tipoVaga;
    private static int nextId = 1;

    public Vaga(TipoVaga tipoVaga) {
        this.id = nextId;
        nextId++;
        this.status = true;
        this.tipoVaga = tipoVaga;
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

    // Enum para representar os diferentes tipos de vaga
    public enum TipoVaga {
        REGULAR, 
        IDOSO, 
        PCD, 
        VIP
    }

    public int getId() {
        return id;
    }

    public boolean getStatus() {
        return status;
    }

    public TipoVaga getTipoVaga() {
        return tipoVaga;
    }

    File vaga = new File("codigo/src/Models/Archives/Vaga.txt");
    // Método para gravar os dados da vaga em um arquivo de texto
    public boolean gravarEmArquivo() {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(vaga))) {
            escritor.write("Vaga ID: " + this.id + "\n");
            escritor.write("Status: " + (this.status ? "Desocupada" : "Ocupada") + "\n");
            escritor.write("Tipo de Vaga: " + this.tipoVaga + "\n");
            escritor.write("-----------------------------------------------");
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
