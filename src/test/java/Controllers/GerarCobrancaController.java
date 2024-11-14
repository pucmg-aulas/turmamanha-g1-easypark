package Controllers;

import Models.Cliente;
import Models.Cobranca;
import Models.Vaga;
import Models.Veiculo;
import dao.CobrancabdDAO;
import dao.VagaDAO;
import dao.VeiculoDAO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import view.GerarCobrancaView;

public class GerarCobrancaController {
   private GerarCobrancaView view;
   private AddClienteController cadastroCliente;
   private int idEstacionamento;
   private JDesktopPane desktopPane;
   private VagaDAO vagas;
   private CobrancabdDAO cobrancas;
   private VeiculoDAO veiculos;

    public GerarCobrancaController(JDesktopPane desktopPane, int idEstacionamento) throws IOException, SQLException {
        this.view = new GerarCobrancaView(desktopPane);
        this.idEstacionamento = idEstacionamento;
        this.desktopPane = desktopPane;
        this.vagas = VagaDAO.getInstance(idEstacionamento);
        this.cobrancas = CobrancabdDAO.getInstance();
        this.veiculos = VeiculoDAO.getInstance();
        
        desktopPane.add(view);
        this.view.setVisible(true);
        
        carregarVagasDisponiveis();
        
        view.getConfirmarBtn().addActionListener(e ->{
            try {
                createCobranca();
                limparCampos();
                carregarVagasDisponiveis();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GerarCobrancaController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GerarCobrancaController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(GerarCobrancaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        view.getVoltarBtn().addActionListener(e -> sair());
        
        view.getCadastrarBtn().addActionListener(e -> {
            try {
                cadastroCliente = new AddClienteController(desktopPane);
            } catch (IOException ex) {
                Logger.getLogger(MenuClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void carregarVagasDisponiveis() {
        Object colunas[] = {"ID", "Tipo", "Status"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);
        tm.setNumRows(0);
        Iterator<Vaga> it = vagas.getVagasDisponiveis().iterator();
        while(it.hasNext()){
            Vaga v = it.next();
            String vaga = v.toString();
            String linha[] = vaga.split("-");
            linha[2] = "Desocupado";
            tm.addRow(new Object[]{linha[0], linha[1], linha[2]});
        }
        view.getVagasTable().setModel(tm);
    }
     
    private void sair(){
         this.view.dispose();
    }

    private Cobranca getAtributos() throws FileNotFoundException, IOException {
        int selectedRow = view.getVagasTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Selecione uma vaga.");
            return null;
        }
        
        String idVaga = (String) view.getVagasTable().getValueAt(selectedRow, 0);
        String placaVeiculo = view.getPlaca().getText().trim();

        Veiculo automovel = veiculos.buscarVeiculoPorPlaca(placaVeiculo);
        if (automovel == null) {
            automovel = new Veiculo(placaVeiculo, new Cliente("Anônimo", "Anônimo"), "Aleatório");
            veiculos.cadastrarVeiculoPorCliente(automovel);
        }
        
        int idVagaNumber = Integer.parseInt(idVaga);

        if (validarCampos(idVaga, placaVeiculo)) {
            return new Cobranca(idVagaNumber, idEstacionamento, automovel);
        } else {
            JOptionPane.showMessageDialog(view, "Preencha todos os campos!");
            return null;
        }
    }

    private boolean validarCampos(String idVaga, String placaVeiculo) {
        return !(idVaga.isEmpty() || placaVeiculo.isEmpty());
    }

    private Veiculo isVeiculoCadastrado(String placa) throws FileNotFoundException {
        Veiculo veiculoEncontrado = veiculos.buscarVeiculoPorPlaca(placa);
        return veiculoEncontrado != null ? veiculoEncontrado : null;
    }

    private void createCobranca() throws FileNotFoundException, IOException, SQLException {
        Cobranca novaCobranca = getAtributos();
        if(novaCobranca == null) {
            JOptionPane.showMessageDialog(view, "Preencha todos os campos!");
            return;
        }
        
        Veiculo veiculoAtual = isVeiculoCadastrado(novaCobranca.getVeiculo().getPlaca());
        if (veiculoAtual == null) {
            JOptionPane.showMessageDialog(view, "Esse veículo não está cadastrado!");
            return;
        } else {
            String nomeCliente = veiculoAtual.getCliente().getNome();
            JOptionPane.showMessageDialog(view, "Veículo Encontrado - (Proprietário) " + nomeCliente);
        }
        
        Vaga vaga = vagas.getVagaPorId(novaCobranca.getIdVaga());
        if (vaga == null) {
            JOptionPane.showMessageDialog(view, "A vaga especificada não existe.");
            return;
        }

        if (!vaga.getStatus()) {
            JOptionPane.showMessageDialog(view, "Vaga Ocupada!");
            return;
        }
        
        vaga.setStatus(false);
        
        try {
            if(cobrancas.gerarCobranca(novaCobranca)) {
                vagas.salvarVagasArquivo(vagas.getVagas(), idEstacionamento);
                JOptionPane.showMessageDialog(view, "Cobrança gerada com sucesso!");
            }
        } catch (IOException ex) {
            Logger.getLogger(GerarCobrancaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void limparCampos() {
        view.getPlaca().setText("");
    }
}
