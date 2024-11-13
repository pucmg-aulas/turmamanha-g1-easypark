
package Controllers;

import Models.Pagamento;
import dao.PagamentoDAO;
import dao.PagamentobdDAO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import view.ValorArrecadadoView;

public class ValorArrecadadoController {

    private JDesktopPane desktopPane;
    private int idEstacionamento;
    private ValorArrecadadoView view;
    private PagamentobdDAO pagamentos;

    public ValorArrecadadoController(JDesktopPane desktopPane, int idEstacionamento) throws IOException, SQLException {
        this.view = new ValorArrecadadoView(desktopPane);
        this.desktopPane = desktopPane;
        this.idEstacionamento = idEstacionamento;
        this.pagamentos = PagamentobdDAO.getInstance();

        exibirArrecadacaoTotal();
        exibirValorMedioUtilizacao();
        listarMeses();
        
        this.view.mesesAno().addActionListener(e -> {
            try {
                exibirArrecadacaoMensal();
            } catch (IOException ex) {
                Logger.getLogger(ValorArrecadadoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ValorArrecadadoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        desktopPane.add(view);
        this.view.setVisible(true);
                          
        this.view.getVoltarBtn().addActionListener(e -> {
            sair();
        });
    }
    

    
    public List<String> lerPagamentosPorEstacionamento(int idEstacionamento) {
        List<String> pagamentosFiltrados = new ArrayList<>();
        String linha;

        try (BufferedReader br = new BufferedReader(new FileReader("./src/test/java/Archives/Pagamentos.txt"))) {
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";"); 
                int idEstacionamentoArquivo = Integer.parseInt(dados[1]); // ID do estacionamento

                if (idEstacionamentoArquivo == idEstacionamento) {
                    pagamentosFiltrados.add(linha);
                }
            }
        } catch (IOException e) {
            showMessage("Erro ao ler o arquivo de pagamentos: " + e.getMessage());
        }

        return pagamentosFiltrados; 
    }
    
    public double exibirArrecadacaoTotal() {
        List<String> pagamentosFiltrados = lerPagamentosPorEstacionamento(idEstacionamento);

        if (pagamentosFiltrados.isEmpty()) {
            view.getValorTotal().setText("Nenhum pagamento encontrado.");
        } else {
            double totalArrecadado = pagamentosFiltrados.stream()
                    .mapToDouble(pagamento -> {
                        String[] dados = pagamento.split(";");
                        return Double.parseDouble(dados[2]); // Valor pago
                    })
                    .sum();

            view.getValorTotal().setText(String.format("R$ %.2f", totalArrecadado));
            return totalArrecadado;
        }
        return 0;
    }

    private void showMessage(String message) {
        view.showMessage(message);
    }
    
    private void sair() {
        this.view.dispose();
    }
    
    private void listarMeses() {
       view.mesesAno().removeAllItems();
    
        // Array com os nomes dos meses em português
        String[] mesesEmPortugues = {
            "", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
            "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        };
    
        // Loop para adicionar os meses no formato desejado
        for (int i = 1; i < mesesEmPortugues.length; i++) {
            String item = (i) + " - " + mesesEmPortugues[i];
            view.mesesAno().addItem(item);
    }
}   

    private int extrairMes(LocalDateTime dataHora) {
        return dataHora.getMonthValue();
    }

    private List<Pagamento> filtrarPagamentosPorMes(List<Pagamento> pagamentos, int mesSelecionado, int idEstacionamento) {
        List<Pagamento> pagamentosFiltrados = new ArrayList<>();

        for (Pagamento pagamento : pagamentos) {
            int mesPagamento = extrairMes(pagamento.getDataPagamento());

            if (mesPagamento == mesSelecionado && pagamento.getIdEstacionamento() == idEstacionamento) {
                pagamentosFiltrados.add(pagamento);
            }
        }
        return pagamentosFiltrados;
    }

    private void exibirArrecadacaoMensal() throws IOException, SQLException {
        String mesSelecionado = (String) view.mesesAno().getSelectedItem();
        String[] dadosMes = mesSelecionado.split("-");
        int numeroMes = Integer.parseInt(dadosMes[0].trim());

        List<Pagamento> listaPagamentos = pagamentos.listarPagamentos();
        if (listaPagamentos == null || listaPagamentos.isEmpty()) {
            JOptionPane.showMessageDialog(view, "A lista de pagamentos está vazia ou é nula.");
            return;
        }

        int idEstacionamento = this.idEstacionamento;
        List<Pagamento> pagamentosFiltrados = filtrarPagamentosPorMes(listaPagamentos, numeroMes, idEstacionamento);

        double valorTotalMensal = 0;

        for (Pagamento pagamento : pagamentosFiltrados) {
            valorTotalMensal += pagamento.getValorPago();
        }

        String valorFormatado = String.format("R$%.2f", valorTotalMensal);
        this.view.getValorMensal().setText(valorFormatado);
    }

    
     
    public void exibirValorMedioUtilizacao(){
        List<String> pagamentosFiltrados = lerPagamentosPorEstacionamento(idEstacionamento);
        int qntdPagamentos = pagamentosFiltrados.size();
        
         if (pagamentosFiltrados.isEmpty()) {
            view.getValorMedio().setText("Nenhum pagamento encontrado.");
        } else {
            double totalArrecadado = exibirArrecadacaoTotal();

            double valorMedio = totalArrecadado / qntdPagamentos;
                   
            view.getValorMedio().setText(String.format("R$ %.2f", valorMedio));
        }
    }
    
}