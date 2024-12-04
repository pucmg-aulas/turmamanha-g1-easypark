package UnitaryTests;

import Models.ITipo;
import Models.Vaga;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VagaTest {

    @Test
    void testConstrutorComIdEIdEstacionamento() throws Exception {
        // Arrange
        int idEstacionamento = 1;
        int id = 100;

        // Act
        Vaga vaga = new Vaga(idEstacionamento, id);

        // Assert
        assertEquals(id, vaga.getId(), "O ID da vaga foi configurado incorretamente.");
        assertEquals(idEstacionamento, vaga.getIdEstacionamento(), "O ID do estacionamento foi configurado incorretamente.");
        assertTrue(vaga.getStatus(), "O status inicial deve ser true (desocupado).");
        assertEquals(10.0, vaga.getTarifaBase(), 0.01, "A tarifa base inicial deve ser 10.0.");
    }

    @Test
    void testConstrutorComStatus() throws Exception {
        // Arrange
        int idEstacionamento = 1;
        int id = 101;
        boolean status = false;

        // Act
        Vaga vaga = new Vaga(idEstacionamento, status, id);

        // Assert
        assertEquals(id, vaga.getId(), "O ID da vaga foi configurado incorretamente.");
        assertEquals(idEstacionamento, vaga.getIdEstacionamento(), "O ID do estacionamento foi configurado incorretamente.");
        assertFalse(vaga.getStatus(), "O status inicial deve ser false (ocupado).");
    }

    @Test
    void testSettersAndGetters() throws Exception {
        // Arrange
        Vaga vaga = new Vaga();
        vaga.setId(200);
        vaga.setIdEstacionamento(2);
        vaga.setStatus(false);
        vaga.setTarifaBase(15.0);
        vaga.setNextId(300);

        // Act & Assert
        assertEquals(200, vaga.getId(), "O ID foi configurado incorretamente.");
        assertEquals(2, vaga.getIdEstacionamento(), "O ID do estacionamento foi configurado incorretamente.");
        assertFalse(vaga.getStatus(), "O status foi configurado incorretamente.");
        assertEquals(15.0, vaga.getTarifaBase(), 0.01, "A tarifa base foi configurada incorretamente.");
        assertEquals(300, vaga.getNextId(), "O próximo ID foi configurado incorretamente.");
    }

    @Test
    void testCalculoValor() throws Exception {
        // Arrange
        ITipo tipoMock = new ITipo() {
            @Override
            public String getTipo() {
                return "Teste";
            }

            @Override
            public double calculoValor(double valor) {
                return valor * 0.9; // Aplicar um desconto fictício de 10%.
            }
        };
        Vaga vaga = new Vaga(tipoMock);

        // Act
        double valorCalculado = vaga.calculoValor(100.0);

        // Assert
        assertEquals(50.0, valorCalculado, 0.01, "O valor calculado deve ser limitado a 50.");
    }

    @Test
    void testGetArquivoPath() throws Exception {
        // Arrange
        int idEstacionamento = 5;
        Vaga vaga = new Vaga(idEstacionamento, 1);

        // Act
        String arquivoPath = vaga.getArquivoPath();

        // Assert
        assertTrue(arquivoPath.contains("Vagas" + idEstacionamento), "O caminho do arquivo não está correto.");
    }

    @Test
    void testToString() throws Exception {
        // Arrange
        ITipo tipoMock = new ITipo() {
            @Override
            public String getTipo() {
                return "Teste";
            }

            @Override
            public double calculoValor(double valor) {
                return valor;
            }
        };
        Vaga vaga = new Vaga(tipoMock);
        vaga.setId(123);
        vaga.setStatus(true);

        // Act
        String result = vaga.toString();

        // Assert
        assertEquals("123-Teste-true", result, "A representação em string da vaga está incorreta.");
    }
}
