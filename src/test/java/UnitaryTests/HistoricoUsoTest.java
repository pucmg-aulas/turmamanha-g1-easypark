package UnitaryTests;

import Models.HistoricoUso;
import Models.ITipo;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HistoricoUsoTest {

    private HistoricoUso historicoUso;
    private ITipo tipoVaga;

    @BeforeEach
    void setUp() {
        tipoVaga = new ITipo() {
            public String getDescricao() {
                return "Carro";
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
        historicoUso = new HistoricoUso("12345678900", "Estacionamento Central", tipoVaga, "ABC-1234", 20.50, 120);
    }

    @Test
    void testGetCpfCliente() {
        assertEquals("12345678900", historicoUso.getCpfCliente(), "O CPF do cliente deve ser '12345678900'");
    }

    @Test
    void testSetCpfCliente() {
        historicoUso.setCpfCliente("98765432100");
        assertEquals("98765432100", historicoUso.getCpfCliente(), "O CPF do cliente deve ser atualizado para '98765432100'");
    }

    @Test
    void testGetNomeEstacionamento() {
        assertEquals("Estacionamento Central", historicoUso.getNomeEstacionamento(), "O nome do estacionamento deve ser 'Estacionamento Central'");
    }

    @Test
    void testSetNomeEstacionamento() {
        historicoUso.setNomeEstacionamento("Estacionamento Norte");
        assertEquals("Estacionamento Norte", historicoUso.getNomeEstacionamento(), "O nome do estacionamento deve ser atualizado para 'Estacionamento Norte'");
    }

    @Test
    void testGetTipoVaga() {
        assertEquals(tipoVaga, historicoUso.getTipoVaga(), "O tipo da vaga deve ser 'Carro'");
    }

    @Test
    void testSetTipoVaga() {
        ITipo novaTipoVaga = new ITipo() {
            public String getDescricao() {
                return "Moto";
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
        historicoUso.setTipoVaga(novaTipoVaga);
        assertEquals(novaTipoVaga, historicoUso.getTipoVaga(), "O tipo da vaga deve ser atualizado para 'Moto'");
    }

    @Test
    void testGetPlacaVeiculo() {
        assertEquals("ABC-1234", historicoUso.getPlacaVeiculo(), "A placa do veículo deve ser 'ABC-1234'");
    }

    @Test
    void testSetPlacaVeiculo() {
        historicoUso.setPlacaVeiculo("XYZ-5678");
        assertEquals("XYZ-5678", historicoUso.getPlacaVeiculo(), "A placa do veículo deve ser atualizada para 'XYZ-5678'");
    }

    @Test
    void testGetValorPago() {
        assertEquals(20.50, historicoUso.getValorPago(), "O valor pago deve ser 20.50");
    }

    @Test
    void testSetValorPago() {
        historicoUso.setValorPago(25.75);
        assertEquals(25.75, historicoUso.getValorPago(), "O valor pago deve ser atualizado para 25.75");
    }

    @Test
    void testGetTempoTotal() {
        assertEquals(120, historicoUso.getTempoTotal(), "O tempo total deve ser 120 minutos");
    }

    @Test
    void testSetTempoTotal() {
        historicoUso.setTempoTotal(150);
        assertEquals(150, historicoUso.getTempoTotal(), "O tempo total deve ser atualizado para 150 minutos");
    }
}
