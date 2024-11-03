package Controllers;


import Models.Cobranca;
import Models.Vaga;
import Models.Veiculo;
import dao.CobrancaDAO;
import dao.VagaDAO;
import dao.VeiculoDAO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import view.GerarCobrancaView;

public class GerarCobrancaController {
   private GerarCobrancaView view;
   private int idEstacionamento;
   private JDesktopPane desktopPane;
   private VagaDAO vagas;
   private CobrancaDAO cobrancas;
   private VeiculoDAO veiculos;

    public GerarCobrancaController(JDesktopPane desktopPane, int idEstacionamento) throws IOException {
        this.view = new GerarCobrancaView(desktopPane);
        this.idEstacionamento = idEstacionamento;
        this.desktopPane = desktopPane;
        this.vagas = VagaDAO.getInstance(idEstacionamento);
        this.cobrancas = CobrancaDAO.getInstance();
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
            }
        });
        
        view.getVoltarBtn().addActionListener(e -> {
            sair();
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

    private Cobranca getAtributos() throws FileNotFoundException {
    int selectedRow = view.getVagasTable().getSelectedRow();
    String idVaga = (String) view.getVagasTable().getValueAt(selectedRow, 0);
    String placaVeiculo = view.getPlaca().getText().trim();

    int idVagaNumber = Integer.parseInt(idVaga);
    
    if (validarCampos(idVaga, placaVeiculo)) {
        Cobranca c = new Cobranca(idVagaNumber, idEstacionamento, placaVeiculo);
        return c;  
    } else {
        JOptionPane.showMessageDialog(view, "Preencha todos os campos!");
        return null;  // Retorna null apenas se os campos não forem válidos
    }
}
     
     private boolean validarCampos(String idVaga, String placaVeiculo){
        return !(idVaga.isEmpty()  || placaVeiculo.isEmpty());
    }
     
    private Veiculo isVeiculoCadastrado(String placa) throws FileNotFoundException{
        Veiculo veiculoEncontrado = veiculos.buscarVeiculoPorPlaca(placa);
        
        return veiculoEncontrado != null ? veiculoEncontrado : null;
    }
     
     private void createCobranca() throws FileNotFoundException{
        Cobranca novaCobranca = getAtributos();
        if(novaCobranca == null){
            JOptionPane.showMessageDialog(view, "Preencha todos os campos!");
            return;
        }
        Veiculo veiculoAtual = isVeiculoCadastrado(novaCobranca.getPlacaVeiculo());
        if(veiculoAtual == null){
             JOptionPane.showMessageDialog(view, "Esse veículo não está cadastrado!");
             return;
        }else{
            String nomeCliente = veiculoAtual.getCliente().getNome();
            JOptionPane.showMessageDialog(view, "Veículo Encontrado - (Proprietário) " + nomeCliente);
        }
        
        Vaga vaga = vagas.getVagaPorId(novaCobranca.getIdVaga());
        if(vaga != null){
            if(vaga.getStatus() == false){
                JOptionPane.showMessageDialog(view, "Vaga Ocupada!");
                return;
            }
            vaga.setStatus(false);
        }else{
            JOptionPane.showMessageDialog(view, "Vaga inválida");
            return;
        }
        try {
            if(cobrancas.gerarCobranca(novaCobranca)){
                vagas.salvarVagasArquivo(vagas.getVagas(), idEstacionamento);
                JOptionPane.showMessageDialog(view, "Cobrança gerada com sucesso!");
            };
          
        } catch (IOException ex) {
            Logger.getLogger(GerarCobrancaController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     private void limparCampos(){
         view.getPlaca().setText("");
     }
}
