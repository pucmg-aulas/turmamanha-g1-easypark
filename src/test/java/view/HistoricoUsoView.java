package View;

import Controllers.HistoricoUsoController;
import Models.HistoricoUso;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HistoricoUsoView extends JFrame {
    private final HistoricoUsoController controller = new HistoricoUsoController();

    public HistoricoUsoView() {
        setTitle("Histórico de Uso");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Components
        JTextField placaField = new JTextField(10);
        JButton entradaButton = new JButton("Registrar Entrada");
        JButton saidaButton = new JButton("Registrar Saída");
        JTextArea historicoArea = new JTextArea(10, 30);
        historicoArea.setEditable(false);

        // Action Listeners
        entradaButton.addActionListener(e -> {
            String placa = placaField.getText();
            controller.registrarEntrada(placa);
            atualizarHistorico(historicoArea);
        });

        saidaButton.addActionListener(e -> {
            String placa = placaField.getText();
            controller.registrarSaida(placa);
            atualizarHistorico(historicoArea);
        });

        // Layout
        JPanel panel = new JPanel();
        panel.add(new JLabel("Placa:"));
        panel.add(placaField);
        panel.add(entradaButton);
        panel.add(saidaButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(historicoArea), BorderLayout.CENTER);

        atualizarHistorico(historicoArea);
    }

    private void atualizarHistorico(JTextArea historicoArea) {
        List<HistoricoUso> historicos = controller.carregarHistorico();
        StringBuilder sb = new StringBuilder();
        for (HistoricoUso historico : historicos) {
            sb.append("Placa: ").append(historico.getPlacaCarro())
              .append(" | Entrada: ").append(historico.getDataEntrada())
              .append(" | Saída: ").append(historico.getDataSaida())
              .append(" | Tempo: ").append(historico.calcularTempoPermanencia()).append(" minutos\n");
        }
        historicoArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HistoricoUsoView().setVisible(true));
    }
}
