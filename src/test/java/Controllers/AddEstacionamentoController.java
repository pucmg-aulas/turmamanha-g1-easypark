package Controllers;

import dao.EstacionamentoDAO;
import view.CadastroEstacionamentoView;
import Models.Estacionamento;
import java.io.IOException;
import dao.VagaDAO;

import javax.swing.*;

public class AddEstacionamentoController  {

    private CadastroEstacionamentoView view;
    private EstacionamentoDAO estacionamentos;


    public AddEstacionamentoController(JDesktopPane desktopPane) throws IOException {
        
        this.view = new CadastroEstacionamentoView();
        this.estacionamentos = EstacionamentoDAO.getInstance();
        
        desktopPane.add(view);
        this.view.setVisible(true);

        this.view.addConfirmButtonActionListener(e -> {
            addEstacionamento();
            limparCampos();
        });

        this.view.addCancelButtonActionListener(e -> {
             sair();
        });
        
    }

    public void addEstacionamento(){
        String nome = view.getTxtNomeEstacionamento().getText();
        String bairro = view.getTxtBairroEstacionamento().getText();
        String rua = view.getTxtRuaEstacionamento().getText();
        String numero = view.getTxtNumeroEstacionamento().getText();
        JSpinner qtdVagas = view.getSpnQntdVagasEstacionamento();
        int numeroVagas = (int)qtdVagas.getValue();

        try {
            Estacionamento e = new Estacionamento(nome, rua, bairro, Integer.parseInt(numero), numeroVagas);
            boolean salvar = estacionamentos.cadastrarEstacionamento(e);
            
            if(salvar){
                JOptionPane.showMessageDialog(view, "Estacionamento cadastrado com sucesso!");
                int idEstacionamento = e.getId();

                VagaDAO vagaDAO = VagaDAO.getInstance(idEstacionamento);
                vagaDAO.instanciarVagas(numeroVagas, idEstacionamento);
            }else{
                 JOptionPane.showMessageDialog(view, "Erro ao cadastar estacionamento!");
            }
            
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erro ao cadastrar estacionamento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Para depuração, você pode remover isso em produção.
        }
    }

    private void limparCampos(){
        view.getTxtNomeEstacionamento().setText("");
        view.getTxtBairroEstacionamento().setText("");
        view.getTxtRuaEstacionamento().setText("");
        view.getTxtNumeroEstacionamento().setText("");
        view.getSpnQntdVagasEstacionamento().setValue(0);
        
    }

    private void sair(){
        view.dispose();
    }
}
