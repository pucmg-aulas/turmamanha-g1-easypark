package Models;

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

    public void setPlaca(String placa)
    {
        this.placa = placa;
    }

    public Cliente getCliente()
    {
        return cliente;
    }

    public void operation()
    {
        System.out.println("Operação realizada para o veículo de placa: " + placa);
        if (cliente != null)
        {
            System.out.println("Cliente associado: " + cliente.getNome());
        }
        else
        {
            System.out.println("Nenhum cliente associado.");
        }
    }
}
