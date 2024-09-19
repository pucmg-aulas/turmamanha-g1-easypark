package codigo;

public class Vaga {

    private String id;
    private boolean status;

    public Vaga(String id) {
        this.id = id;
        this.status = false;
    }

    public void ocuparVaga() {
        if (!status) { 
            this.status = true;
        } else {
            System.out.println("A vaga já está ocupada!");
        }
    }

    public String getId() {
        return id;
    }

    public boolean getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }
}
