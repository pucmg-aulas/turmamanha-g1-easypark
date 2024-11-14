package Controllers;

import Models.Estacionamento;
import dao.EstacionamentoDAO;
import dao.EstacionamentobdDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JDesktopPane;
import view.ExibirDetalhesEstacionamentoView;

public class ExibirDetalhesEstacionamentoController {

    private ExibirDetalhesEstacionamentoView view;
    private int idEstacionamento;
    private EstacionamentobdDAO EstacionamentobdDAO;
    private JDesktopPane desktopPane;
    private final EstacionamentobdDAO estacionamentobdDAO;

public ExibirDetalhesEstacionamentoController(JDesktopPane desktopPane, int idEstacionamento) throws IOException, SQLException{
        this.view = new ExibirDetalhesEstacionamentoView(desktopPane);
        this.idEstacionamento = idEstacionamento;
        this.estacionamentobdDAO = EstacionamentobdDAO.getInstance();
        this.desktopPane = desktopPane;

        desktopPane.add(view);
        this.view.setVisible(true);

       
        listarDetalhesEstacionamento();
        
}

public void listarDetalhesEstacionamento() throws SQLException{
       Estacionamento estacionamento = estacionamentobdDAO.getEstacionamentoPorId(idEstacionamento);
       view.getBairro().setText(estacionamento.getBairro());
       view.getRua().setText(estacionamento.getRua());
       view.getVagas().setText(String.valueOf(estacionamento.getQntdVagas()));
       view.getNumero().setText(String.valueOf(estacionamento.getNumero()));
       view.getNome().setText(estacionamento.getNome());
}
    

}
