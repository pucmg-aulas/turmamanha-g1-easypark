package Main;

import Models.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Principal {
    public static void main(String[] args) {
        Estacionamento estacionamento = new Estacionamento("Estacionamento Centro", "Rua A", "Centro", 123);
        Vaga vaga1 = new Vaga();
        Vaga vaga2 = new Vaga();
        estacionamento.adicionarVaga(vaga1);
        estacionamento.adicionarVaga(vaga2);

        Cliente Felipe = new Cliente("Felipe");

        Veiculo veiculo1 = new Veiculo("ABC1234", Felipe); // Supondo que exista a classe Veiculo

        // Criando a cobrança passando o ID da vaga e o estacionamento
        Cobranca cobranca = new Cobranca(vaga1.getId(), estacionamento, veiculo1);

        // Simula o tempo de permanência
        cobranca.setHoraSaida(LocalTime.now().plusHours(2));

        // Calcula o tempo total de permanência
        cobranca.calcularTempoFinal();

        // Calcula o valor total com base no tempo de permanência
        cobranca.calcularValorTotal();

        // Agora os prints vão funcionar corretamente
        System.out.println("Valor total: " + cobranca.getValorTotal());
        System.out.println("Tempo total: " + cobranca.getTempoTotal());

        // Paga a cobrança e libera a vaga
        System.out.println(vaga1.getStatus());
        cobranca.pagar();

        System.out.println(vaga1.getStatus());
    }
}
