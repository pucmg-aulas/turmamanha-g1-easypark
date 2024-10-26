package Controllers;

import Models.Estacionamento;
import dao.EstacionamentoDAO;
import java.io.IOException;
import javax.swing.JDesktopPane;
import view.ExibirDetalhesEstacionamentoView;

public class ExibirDetalhesEstacionamentoController {

    private ExibirDetalhesEstacionamentoView view;
    private int idEstacionamento;
    private EstacionamentoDAO estacionamentoDAO;
    private JDesktopPane desktopPane;

public ExibirDetalhesEstacionamentoController(JDesktopPane desktopPane, int idEstacionamento) throws IOException{
        this.view = new ExibirDetalhesEstacionamentoView(desktopPane);
        this.idEstacionamento = idEstacionamento;
        this.estacionamentoDAO = EstacionamentoDAO.getInstance();
        this.desktopPane = desktopPane;

        desktopPane.add(view);
        this.view.setVisible(true);

       
        listarDetalhesEstacionamento();
        
}

public void listarDetalhesEstacionamento(){
       Estacionamento estacionamento = estacionamentoDAO.getEstacionamentoPorId(idEstacionamento);
       view.getBairro().setText(estacionamento.getBairro());
       view.getRua().setText(estacionamento.getRua());
       view.getVagas().setText(String.valueOf(estacionamento.getQntdVagas()));
       view.getNumero().setText(String.valueOf(estacionamento.getNumero()));
       view.getNome().setText(estacionamento.getNome());
}
    

}
