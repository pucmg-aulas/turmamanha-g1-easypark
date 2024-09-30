TESTE VAGA
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VagaTest {

    @Test
    public void testOcuparVaga() {
        Vaga vaga = new Vaga();
        assertTrue(vaga.ocuparVaga());
        assertFalse(vaga.ocuparVaga()); // já ocupada
    }

    @Test
    public void testLiberarVaga() {
        Vaga vaga = new Vaga();
        vaga.ocuparVaga();
        assertTrue(vaga.liberarVaga());
    }
}
