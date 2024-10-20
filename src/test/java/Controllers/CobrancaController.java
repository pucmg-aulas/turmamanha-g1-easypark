package Controllers;
/*
import dao.CobrancaDAO;
import Models.Cobranca;
import Models.Estacionamento;
import Models.Veiculo;

public class CobrancaController {
    private CobrancaDAO cobrancaDAO;

    public CobrancaController() {
        cobrancaDAO = new CobrancaDAO();
    }

    public boolean iniciarCobranca(int idVaga, Estacionamento estacionamento, Veiculo veiculo) {
        Cobranca cobranca = new Cobranca(idVaga, estacionamento, veiculo);
        return cobrancaDAO.salvarCobranca(cobranca);
    }

    public boolean finalizarCobranca(Cobranca cobranca) {
        //cobranca.finalizarCobrança();
        return cobrancaDAO.salvarCobranca(cobranca); // Atualiza no arquivo
    }

    public boolean removerCobranca(int idVaga) {
        return cobrancaDAO.removerCobranca(idVaga);
    }

    public void listarCobrancas() {
        cobrancaDAO.listarCobrancas().forEach(cobranca -> {
            System.out.println(cobranca); // Aqui você pode personalizar como deseja exibir as cobranças
        });
    }
}
*/