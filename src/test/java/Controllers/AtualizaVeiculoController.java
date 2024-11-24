package Controllers;

import Models.Cliente;
import Models.Veiculo;
import dao.ClientebdDAO;
import dao.VeiculoDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import view.AtualizaVeiculoView;

public class AtualizaVeiculoController {
    private AtualizaVeiculoView view;
    private VeiculoDAO veiculos;
    private ClientebdDAO clientes;
    private JDesktopPane desktopPane;
    private String clienteCpf;
    private String veiculoPlaca;

    public AtualizaVeiculoController(JDesktopPane desktopPane, String cpf, String placa) throws IOException {
        view = new AtualizaVeiculoView(desktopPane);
        clientes = ClientebdDAO.getInstance();
        veiculos = VeiculoDAO.getInstance();
        this.desktopPane = desktopPane;
        this.clienteCpf = cpf;
        this.veiculoPlaca = placa;

        desktopPane.add(view);
        this.view.setVisible(true);

        carregarDadosVeiculo();

        view.getConfirmarBtn().addActionListener(e -> {
            try {
                if (atualizarVeiculo()) {
                    JOptionPane.showMessageDialog(view, "Veículo atualizado com sucesso!");
                    view.dispose();
                }
            } catch (IOException ex) {
                Logger.getLogger(AtualizaVeiculoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void carregarDadosVeiculo() {
        Veiculo veiculo = veiculos.buscarVeiculoPorPlaca(veiculoPlaca);
        if (veiculo != null) {
            view.getPlaca().setText(veiculo.getPlaca());
            view.getModelo().setText(veiculo.getModelo());
        } else {
            JOptionPane.showMessageDialog(view, "Veículo não encontrado!");
            view.dispose();
        }
    }

    private Cliente getCliente() {
        return clientes.buscarClientePorCpf(clienteCpf);
    }

    public boolean atualizarVeiculo() throws IOException {
        String novaPlaca = view.getPlaca().getText();
        String novoModelo = view.getModelo().getText();

        if (novaPlaca.isEmpty() || novoModelo.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Preencha todos os campos!");
            return false;
        }

        Cliente clienteAtual = getCliente();
        Veiculo veiculoAtualizado = new Veiculo(novaPlaca, clienteAtual, novoModelo);

        if (veiculos.atualizarProprietario(veiculoPlaca, clienteCpf, novoModelo)) {
            JOptionPane.showMessageDialog(view, "Veículo atualizado: (Placa) -" + veiculoAtualizado.getPlaca());
            return true;
        } else {
            JOptionPane.showMessageDialog(view, "Erro ao atualizar Veículo!");
            return false;
        }
    }
}
