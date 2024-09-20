package Models;

public class Vaga {

    private int id;

    //Status True: Desocupado
    //Status False: Ocupado
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

}
