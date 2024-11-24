package Controllers;

import Exceptions.VagaIndisponivelException;
import Models.Cliente;
import Models.Cobranca;
import Models.Vaga;
import Models.Veiculo;
import dao.ClientebdDAO;
import dao.CobrancabdDAO;
import dao.VagaDAO;
import dao.VagabdDAO;
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
    private AddVeiculoController cadastroVeiculo;
    private AtualizaVeiculoController atualizaVeiculo;
    private int idEstacionamento;
    private JDesktopPane desktopPane;
    private VagabdDAO vagas;
    private CobrancabdDAO cobrancas;
    private VeiculoDAO veiculos;
    private ClientebdDAO clientes;

    public GerarCobrancaController(JDesktopPane desktopPane, int idEstacionamento) throws IOException, SQLException {
        this.view = new GerarCobrancaView(desktopPane);
        this.idEstacionamento = idEstacionamento;
        this.desktopPane = desktopPane;
        this.vagas = VagabdDAO.getInstance(idEstacionamento);
        this.cobrancas = CobrancabdDAO.getInstance();
        this.veiculos = VeiculoDAO.getInstance();
        this.clientes = ClientebdDAO.getInstance();

        desktopPane.add(view);
        this.view.setVisible(true);

        carregarVagasDisponiveis();

        view.getConfirmarBtn().addActionListener(e -> {
            try {
                try {
                    createCobranca();
                } catch (VagaIndisponivelException ex) {
                    Logger.getLogger(GerarCobrancaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                limparCampos();
                carregarVagasDisponiveis();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GerarCobrancaController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | SQLException ex) {
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

    private void carregarVagasDisponiveis() throws SQLException {
        Object colunas[] = {"ID", "Tipo", "Status"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);
        tm.setNumRows(0);
        Iterator<Vaga> it = vagas.getVagasDisponiveis().iterator();
        while (it.hasNext()) {
            Vaga v = it.next();
            String vaga = v.toString();
            String linha[] = vaga.split("-");
            linha[2] = "Desocupado";
            tm.addRow(new Object[]{linha[0], linha[1], linha[2]});
        }
        view.getVagasTable().setModel(tm);
    }

    private void sair() {
        this.view.dispose();
    }

    private Cobranca getAtributos() throws FileNotFoundException, IOException, SQLException {
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

    private void createCobranca() throws FileNotFoundException, IOException, SQLException, VagaIndisponivelException {
        Cobranca novaCobranca = getAtributos();
        boolean testeValido = true;
        if (novaCobranca == null) {
            JOptionPane.showMessageDialog(view, "Preencha todos os campos!");
            return;
        }

        Veiculo veiculoAtual = isVeiculoCadastrado(novaCobranca.getVeiculo().getPlaca());
        if (veiculoAtual == null) {
            JOptionPane.showMessageDialog(view, "Esse veículo não está cadastrado!");
            Object[] opcoes = {"Sim", "Não"};
            int resposta = JOptionPane.showOptionDialog(
                    view,
                    "Esse veículo não está cadastrado. Deseja cadastrar o veículo?",
                    "Confirmação",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]
            );

            if (resposta == 0) {
                int respostaprop = JOptionPane.showOptionDialog(
                        view,
                        "O cliente já é cadastrado?",
                        "Confirmação",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opcoes,
                        opcoes[0]
                );

                if (respostaprop == 0) {
                    cadastroVeiculo = new AddVeiculoController(desktopPane, JOptionPane.showInputDialog("Insira o cpf: "));
                } else if (respostaprop == 1) {
                    cadastroCliente = new AddClienteController(desktopPane);
                    //mesmo problema de cadastrar o cliente primeiro
                    cadastroVeiculo = new AddVeiculoController(desktopPane, JOptionPane.showInputDialog("Insira o cpf: "));
                }

            } else if (resposta == 1) {
                Cliente clienteAnonimo = clientes.buscarClientePorCpf("anonimo");
                veiculoAtual = new Veiculo(novaCobranca.getVeiculo().getPlaca(), clienteAnonimo, "Aleatório");
                veiculos.cadastrarVeiculoPorCliente(veiculoAtual);

            }

        } else {
            String nomeCliente = veiculoAtual.getCliente().getNome();
            testeValido = testarAnonimo(novaCobranca);
            JOptionPane.showMessageDialog(view, "Veículo Encontrado - (Proprietário) " + nomeCliente);
        }

        Vaga vaga = vagas.getVagaPorId(novaCobranca.getIdVaga());
        if (vaga == null) {
            JOptionPane.showMessageDialog(view, "A vaga especificada não existe.");
            return;
        }

        if (!vaga.getStatus()) {
            JOptionPane.showMessageDialog(view, "Vaga Ocupada!");
            throw new VagaIndisponivelException();
        }
        if (testeValido) {
            if (cobrancas.gerarCobranca(novaCobranca)) {
                vagas.ocuparVaga(novaCobranca.getIdVaga());
                JOptionPane.showMessageDialog(view, "Cobrança gerada com sucesso!");
            }
        }
    }

    private boolean testarAnonimo(Cobranca cobranca) {
        try {
            String nomeCliente = cobranca.getVeiculo().getCliente().getNome();
            String placa = cobranca.getVeiculo().getPlaca();
            if ("anonimo".equalsIgnoreCase(nomeCliente)) {
                Object[] opcoes = {"Sim", "Não"};
                int resposta = JOptionPane.showOptionDialog(
                        view,
                        "Esse veículo esta cadastrado como anônimo, deseja alterar o cliente?",
                        "Confirmação",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opcoes,
                        opcoes[0]
                );

                if (resposta == 0) {
                    int respostaprop1 = JOptionPane.showOptionDialog(
                            view,
                            "O cliente já é cadastrado?",
                            "Confirmação",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            opcoes,
                            opcoes[0]
                    );
                    if (respostaprop1 == 0) {
                        atualizaVeiculo = new AtualizaVeiculoController(desktopPane, JOptionPane.showInputDialog("Insira o cpf: "), placa);
                    } else if (respostaprop1 == 1) {
                        cadastroCliente = new AddClienteController(desktopPane);
                        
                        //nao consegui puxar a tela de cadastro de cliente primeiro, para conseguir puxar o cpf do cliente
                        atualizaVeiculo = new AtualizaVeiculoController(desktopPane, JOptionPane.showInputDialog("Insira o cpf: ") ,placa);

                    }
                    
                    return false;
                } else if (resposta == 1) {
                    return true;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PagarCobrancaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void limparCampos() {
        view.getPlaca().setText("");
    }
}
