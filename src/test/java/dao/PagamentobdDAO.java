
package dao;

import Models.Cliente;
import Models.Cobranca;
import Models.Estacionamento;
import Models.HistoricoUso;
import Models.ITipo;
import Models.Pagamento;
import Models.Vaga;
import Models.VagaIdoso;
import Models.VagaPCD;
import Models.VagaRegular;
import Models.VagaVIP;
import Models.Veiculo;
import static dao.PagamentoDAO.FORMATTER;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PagamentobdDAO {
    
    private VagaDAO vagas;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static PagamentobdDAO instance;
    private ClienteDAO clientes;
    private VeiculoDAO veiculos;
    private EstacionamentoDAO estacionamentos;
    private List<Pagamento> pagamentos;

    private PagamentobdDAO() throws SQLException, IOException {
        this.clientes = ClienteDAO.getInstance();
        this.veiculos = VeiculoDAO.getInstance();
        this.estacionamentos = EstacionamentoDAO.getInstance();

        pagamentos = listarPagamentos();
        if (pagamentos == null) {
            pagamentos = new ArrayList<>();
        }
    }

    public static PagamentobdDAO getInstance() throws SQLException, IOException {
        if (instance == null) {
            instance = new PagamentobdDAO();
        }
        return instance;
    }
    
    
    public void salvarPagamento(Cobranca cobranca) throws SQLException, IOException {
    String sql = """
        INSERT INTO Pagamento (idPagamento, idEstacionamento, valorTotal, tipoVaga, placaVeiculo, tempoTotal, horaEntrada, horaSaida)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
    """;

    Pagamento pagamento = new Pagamento();
    int idEstacionamento = cobranca.getIdEstacionamento();
    this.vagas = VagaDAO.getInstance(idEstacionamento);
    double valorTotal = cobranca.getValorTotal();
    String placaVeiculo = cobranca.getVeiculo().getPlaca();
    int idVaga = cobranca.getIdVaga();
    Vaga vagaAtual = vagas.getVagaPorId(idVaga);
    String tipoVaga = vagaAtual.getTipo();
    int tempoTotal = cobranca.getTempoTotal();
    int idPagamento = pagamento.getIdPagamento();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    try (Connection conn = BancoDados.getConexao();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, idPagamento);
        ps.setInt(2, idEstacionamento);
        ps.setDouble(3, valorTotal);
        ps.setString(4, tipoVaga);
        ps.setString(5, placaVeiculo);
        ps.setInt(6, tempoTotal);
        ps.setString(7, cobranca.getHoraEntrada().format(formatter));
        ps.setString(8, pagamento.getDataPagamento().format(formatter));
        
        ps.executeUpdate(); 
    } catch (SQLException e) { 
        throw e;
    }
        }   

    public List<Pagamento> getPagamentosPorCpf(String cpf) throws SQLException, IOException {
        List<Pagamento> pagamentos = new ArrayList<>();

        String sql = "SELECT p.idPagamento, p.idEstacionamento, p.valorTotal, p.tipoVaga, "
                   + "p.placaVeiculo, p.tempoTotal, p.dataEntrada, p.dataSaida "
                   + "FROM Pagamento p "
                   + "JOIN Veiculo v ON p.placaVeiculo = v.placa "
                   + "JOIN Cliente c ON v.cpfCliente = c.cpf "
                   + "WHERE c.cpf = ?";

        Connection conn = BancoDados.getConexao();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idPagamento = rs.getInt("idPagamento");
                    int idEstacionamento = rs.getInt("idEstacionamento");
                    double valorTotal = rs.getDouble("valorTotal");
                    String tipoVaga = rs.getString("tipoVaga");
                    String placaVeiculo = rs.getString("placaVeiculo");
                    int tempoTotal = rs.getInt("tempoTotal");
                    LocalDateTime dataEntrada = rs.getTimestamp("dataEntrada").toLocalDateTime();
                    LocalDateTime dataSaida = rs.getTimestamp("dataSaida").toLocalDateTime();

                    Vaga vagaAtual = new Vaga();
                    vagaAtual.setIdEstacionamento(idEstacionamento);

                    switch (tipoVaga) {
                        case "Idoso":
                            vagaAtual.setTipo(new VagaIdoso());
                            break;
                        case "PCD":
                            vagaAtual.setTipo(new VagaPCD());
                            break;
                        case "VIP":
                            vagaAtual.setTipo(new VagaVIP());
                            break;
                        case "Regular":
                            vagaAtual.setTipo(new VagaRegular());
                            break;
                    }

                    Pagamento pagamento = new Pagamento();
                    pagamento.setIdPagamento(idPagamento);
                    pagamento.setIdEstacionamento(idEstacionamento);
                    pagamento.setValorPago(valorTotal);
                    pagamento.setTipoVaga(vagaAtual.getTipoVaga());
                    pagamento.setPlacaVeiculo(placaVeiculo);
                    pagamento.setTempoTotal(tempoTotal);
                    pagamento.setDataEntrada(dataEntrada);
                    pagamento.setDataPagamento(dataSaida);

                    pagamentos.add(pagamento);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar pagamentos por CPF: " + e.getMessage());
        }

        return pagamentos;
    }    
    
    public List<Pagamento> listarPagamentos() throws SQLException, IOException {
        List<Pagamento> pagamentos = new ArrayList<>();

        String sql = "SELECT idPagamento, idEstacionamento, valorTotal, tipoVaga, placaVeiculo, "
                   + "tempoTotal, dataEntrada, dataSaida FROM Pagamento";

        Connection conn = BancoDados.getConexao();

        try (PreparedStatement ps = conn.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idPagamento = rs.getInt("idPagamento");
                int idEstacionamento = rs.getInt("idEstacionamento");
                double valorPago = rs.getDouble("valorTotal");
                String tipoVaga = rs.getString("tipoVaga");
                String placaVeiculo = rs.getString("placaVeiculo");
                int tempoTotal = rs.getInt("tempoTotal");
                LocalDateTime dataEntrada = rs.getTimestamp("dataEntrada").toLocalDateTime();
                LocalDateTime dataSaida = rs.getTimestamp("dataSaida").toLocalDateTime();

                Vaga vagaAtual = new Vaga();
                vagaAtual.setIdEstacionamento(idEstacionamento);

                switch (tipoVaga) {
                    case "Idoso":
                        vagaAtual.setTipo(new VagaIdoso());
                        break;
                    case "PCD":
                        vagaAtual.setTipo(new VagaPCD());
                        break;
                    case "VIP":
                        vagaAtual.setTipo(new VagaVIP());
                        break;
                    case "Regular":
                        vagaAtual.setTipo(new VagaRegular());
                        break;
                }

                Pagamento pagamento = new Pagamento();
                pagamento.setIdPagamento(idPagamento);
                pagamento.setIdEstacionamento(idEstacionamento);
                pagamento.setValorPago(valorPago);
                pagamento.setTipoVaga(vagaAtual.getTipoVaga());
                pagamento.setPlacaVeiculo(placaVeiculo);
                pagamento.setTempoTotal(tempoTotal);
                pagamento.setDataEntrada(dataEntrada);
                pagamento.setDataPagamento(dataSaida);

                pagamentos.add(pagamento);
            }
        } catch (SQLException e) {

            throw new SQLException("Erro ao listar pagamentos: " + e.getMessage());
        }

        return pagamentos;
    }
    
    public static String getHoraSaida(){
        return LocalDateTime.now().format(FORMATTER);
    }
    
    public List<HistoricoUso> buscarHistoricoPorCpf(String cpf) throws SQLException, IOException {
    List<HistoricoUso> historicoFiltrado = new ArrayList<>();
    
    String sql = """
        SELECT p.placaVeiculo, p.valorTotal, p.tempoTotal, p.tipoVaga, e.nome AS nomeEstacionamento
        FROM Pagamento p
        INNER JOIN Veiculo v ON p.placaVeiculo = v.placa
        INNER JOIN Cliente c ON v.cpfCliente = c.cpf
        INNER JOIN Estacionamento e ON p.idEstacionamento = e.idEstacionamento
        WHERE c.cpf = ?
    """;
    
    try (Connection conn = BancoDados.getConexao();
         PreparedStatement ps = conn.prepareStatement(sql)) {
         
        ps.setString(1, cpf);
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String placaAtual = rs.getString("placaVeiculo");
                double valorTotal = rs.getDouble("valorTotal");
                int tempoTotal = rs.getInt("tempoTotal");
                String tipoVaga = rs.getString("tipoVaga");
                String nomeEstacionamento = rs.getString("nomeEstacionamento");

                // Mapeia o tipo da vaga para o objeto apropriado
                ITipo vaga;
                switch (tipoVaga) {
                    case "Idoso" -> vaga = new VagaIdoso();
                    case "PCD" -> vaga = new VagaPCD();
                    case "VIP" -> vaga = new VagaVIP();
                    default -> vaga = new VagaRegular();
                }

                HistoricoUso historicoAtual = new HistoricoUso(cpf, nomeEstacionamento, vaga, placaAtual, valorTotal, tempoTotal);
                historicoFiltrado.add(historicoAtual);
            }
        }
    } catch (SQLException e) {
        throw e;
    }
    
    return historicoFiltrado;
}

}
