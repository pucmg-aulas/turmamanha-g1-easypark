package src.Models;

public class Veiculo
{
    private String placa;
    private Cliente cliente;

    public Veiculo(String placa, Cliente cliente)
    {
        this.placa = placa;
        this.cliente = cliente;
    }

    public void adicionarCliente(Cliente cliente)
    {
        this.cliente = cliente;
    }

    public String getPlaca()
    {
        return placa;
    }

    public Cliente getCliente()
    {
        return cliente;
    }

}