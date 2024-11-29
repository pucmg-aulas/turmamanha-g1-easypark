package Models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Veiculo{
    private String placa;
    private Cliente cliente;
    private String modelo;

    public Veiculo(String placa, Cliente cliente, String modelo) {
        this.placa = placa;
        this.cliente = cliente;
        this.modelo = modelo;
    }

    public Veiculo(String abC1234, String fiat, String uno, String preto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
