package Models;

public class Pagamento {
    private int id;
    private String cliente;
    private double valor;

    public Pagamento(int id, String cliente, double valor) {
        this.id = id;
        this.cliente = cliente;
        this.valor = valor;
    }

    public int getId() { return id; }
    public String getCliente() { return cliente; }
    public double getValor() { return valor; }

    public void setId(int id) { this.id = id; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public void setValor(double valor) { this.valor = valor; }
}
