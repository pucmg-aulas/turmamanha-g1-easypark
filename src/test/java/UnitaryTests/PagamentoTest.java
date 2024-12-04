package UnitaryTests;

import Models.ITipo;
import Models.Pagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PagamentoTest {

    private Pagamento pagamento;

    @BeforeEach
    void setUp() throws IOException {
        // Limpa ou cria o arquivo antes de cada teste
        File arquivo = new File("./src/test/java/Archives/Pagamentos.txt");
        if (arquivo.exists()) {
            try (FileWriter writer = new FileWriter(arquivo, false)) {
                writer.write("");
            }
        } else {
            arquivo.getParentFile().mkdirs();
            arquivo.createNewFile();
        }

        pagamento = new Pagamento();
    }

    @Test
    void testIdPagamentoIncremental() throws IOException {
        // Cria uma nova instância para verificar incremento de ID
        Pagamento novoPagamento = new Pagamento();
        assertEquals(pagamento.getIdPagamento() + 1, novoPagamento.getIdPagamento());
    }

    @Test
    void testGetAndSetDataPagamento() {
        LocalDateTime novaData = LocalDateTime.now().minusDays(1);
        pagamento.setDataPagamento(novaData);
        assertEquals(novaData, pagamento.getDataPagamento());
    }

    @Test
    void testGetAndSetDataEntrada() {
        LocalDateTime novaDataEntrada = LocalDateTime.now().minusHours(2);
        pagamento.setDataEntrada(novaDataEntrada);
        assertEquals(novaDataEntrada, pagamento.getDataEntrada());
    }

    @Test
    void testGetAndSetIdEstacionamento() {
        pagamento.setIdEstacionamento(123);
        assertEquals(123, pagamento.getIdEstacionamento());
    }

    @Test
    void testGetAndSetValorPago() {
        pagamento.setValorPago(50.75);
        assertEquals(50.75, pagamento.getValorPago());
    }

    @Test
    void testGetAndSetPlacaVeiculo() {
        pagamento.setPlacaVeiculo("ABC-1234");
        assertEquals("ABC-1234", pagamento.getPlacaVeiculo());
    }

    @Test
    void testGetAndSetTipoVaga() {
        ITipo tipoMock = new ITipo() {
            public String getDescricao() {
                return "Teste Tipo";
            }

            @Override
            public double calculoValor(double valor) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public String getTipo() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        };

        pagamento.setTipoVaga(tipoMock);
        assertEquals(tipoMock, pagamento.getTipoVaga());
    }

    @Test
    void testGetAndSetTempoTotal() {
        pagamento.setTempoTotal(120);
        assertEquals(120, pagamento.getTempoTotal());
    }
}
