package Models;

import dao.EstacionamentobdDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
    private int id;
    private String nome;
    private String rua;
    private int numero;
    private String bairro;
    private List<Vaga> vagas;
    private int qntdVagas;

    public Estacionamento(String nome, String rua, String bairro, int numero, int qntdVagas) throws SQLException {
        EstacionamentobdDAO dao = EstacionamentobdDAO.getInstance();
        this.id = dao.obterMaiorId() + 1;  // Pega o maior ID do banco de dados e incrementa
        this.nome = nome;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.vagas = new ArrayList<>();
        this.qntdVagas = qntdVagas;
    }

    public Estacionamento(int id, String nome, String rua, String bairro, int numero, int qntdVagas) {
        this.id = id;
        this.nome = nome;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.vagas = new ArrayList<>();
        this.qntdVagas = qntdVagas;
    }

    
    public int getQntdVagas(){
        return qntdVagas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public List<Vaga> getVagas() {
        return vagas;
    }

    public void setVagas(List<Vaga> vagas) {
        this.vagas = vagas;
    }
}
