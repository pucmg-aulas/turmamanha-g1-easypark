
package Controllers;

import dao.PagamentoDAO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import view.ValorArrecadadoView;

public class ValorArrecadadoController {

    private JDesktopPane desktopPane;
    private int idEstacionamento;
    private ValorArrecadadoView view;
    private PagamentoDAO pagamentoDAO;

    public ValorArrecadadoController(JDesktopPane desktopPane, int idEstacionamento) {
        this.view = new ValorArrecadadoView(desktopPane);
        this.desktopPane = desktopPane;
        this.idEstacionamento = idEstacionamento;

        exibirArrecadacaoTotal();
        
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
    
    public void exibirArrecadacaoTotal() {
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
        }
    }

    private void showMessage(String message) {
        view.showMessage(message);
    }
    
    private void sair() {
        this.view.dispose();
    }
}