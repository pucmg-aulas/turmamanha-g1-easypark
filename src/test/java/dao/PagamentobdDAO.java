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
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagamentobdDAO {
    
    private BancoDados bd;
    private VagabdDAO vagas;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static PagamentobdDAO instance;
    private ClienteDAO clientes;
    private VeiculoDAO veiculos;
    private EstacionamentobdDAO estacionamentos;
    private List<Pagamento> pagamentos;

    private PagamentobdDAO() throws SQLException, IOException {
        this.clientes = ClienteDAO.getInstance();
        this.veiculos = VeiculoDAO.getInstance();
        this.estacionamentos = EstacionamentobdDAO.getInstance();
        this.bd = BancoDados.getInstancia();
        
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
    
   public void salvarPagamento(Cobranca cobranca, Timestamp dataPagamento) throws SQLException, IOException {
        String sql = """
            INSERT INTO Pagamento (dataPagamento, dataEntrada, idEstacionamento, valorPago, tipoVaga, placaVeiculo, idVaga, tempoTotal)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        int idEstacionamento = cobranca.getIdEstacionamento();
        this.vagas = VagabdDAO.getInstance(idEstacionamento);
        double valorPago = cobranca.getValorTotal();
        String placaVeiculo = cobranca.getVeiculo().getPlaca();
        int idVaga = cobranca.getIdVaga();
        Vaga vagaAtual = vagas.getVagaPorId(idVaga);
        String tipoVaga = vagaAtual.getTipo();
        int tempoTotal = (int) cobranca.getTempoTotal();

        Timestamp dataEntrada = Timestamp.valueOf(cobranca.getHoraEntrada());

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setTimestamp(1, dataPagamento);  // Usar o Timestamp passado como parâmetro
            ps.setTimestamp(2, dataEntrada);    // Usar Timestamp para dataEntrada
            ps.setInt(3, idEstacionamento);
            ps.setDouble(4, valorPago);
            ps.setString(5, tipoVaga);
            ps.setString(6, placaVeiculo);
            ps.setInt(7, idVaga);
            ps.setInt(8, tempoTotal);
            
            ps.executeUpdate(); 
        } catch (SQLException e) { 
            throw new SQLException("Erro ao salvar pagamento: " + e.getMessage());
        }
    }  

    public List<Pagamento> getPagamentosPorCpf(String cpf) throws SQLException, IOException {
        List<Pagamento> pagamentos = new ArrayList<>();

        String sql = "SELECT p.id, p.idEstacionamento, p.valorPago, p.tipoVaga, "
                   + "p.placaVeiculo, p.tempoTotal, p.dataEntrada, p.dataPagamento "
                   + "FROM Pagamento p "
                   + "JOIN Veiculo v ON p.placaVeiculo = v.placa "
                   + "JOIN Cliente c ON v.cpfCliente = c.cpf "
                   + "WHERE c.cpf = ?";

        Connection conn = BancoDados.getConexao();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idPagamento = rs.getInt("id");
                    int idEstacionamento = rs.getInt("idEstacionamento");
                    double valorPago = rs.getDouble("valorPago");
                    String tipoVaga = rs.getString("tipoVaga");
                    String placaVeiculo = rs.getString("placaVeiculo");
                    int tempoTotal = rs.getInt("tempoTotal");
                    LocalDateTime dataEntrada = rs.getTimestamp("dataEntrada").toLocalDateTime();
                    LocalDateTime dataPagamento = rs.getTimestamp("dataPagamento").toLocalDateTime();

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
                    pagamento.setDataPagamento(dataPagamento);

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

        String sql = "SELECT id, idestacionamento, valorpago, tipovaga, placaveiculo, "
                   + "tempototal, dataentrada, datapagamento FROM Pagamento";

        Connection conn = BancoDados.getConexao();

        try (PreparedStatement ps = conn.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int idEstacionamento = rs.getInt("idEstacionamento");
                double valorPago = rs.getDouble("valorPago");
                String tipoVaga = rs.getString("tipoVaga");
                String placaVeiculo = rs.getString("placaVeiculo");
                int tempoTotal = rs.getInt("tempoTotal");
                LocalDateTime dataEntrada = rs.getTimestamp("dataEntrada").toLocalDateTime();
                LocalDateTime dataPagamento = rs.getTimestamp("dataPagamento").toLocalDateTime();

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
                pagamento.setIdPagamento(id);
                pagamento.setIdEstacionamento(idEstacionamento);
                pagamento.setValorPago(valorPago);
                pagamento.setTipoVaga(vagaAtual.getTipoVaga());
                pagamento.setPlacaVeiculo(placaVeiculo);
                pagamento.setTempoTotal(tempoTotal);
                pagamento.setDataEntrada(dataEntrada);
                pagamento.setDataPagamento(dataPagamento);

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
            SELECT p.placaVeiculo, p.valorPago, p.tempoTotal, p.tipoVaga, e.nome AS nomeEstacionamento
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
                    double valorPago = rs.getDouble("valorPago");
                    int tempoTotal = rs.getInt("tempoTotal");
                    String tipoVaga = rs.getString("tipoVaga");
                    String nomeEstacionamento = rs.getString("nomeEstacionamento");

                    ITipo vaga;
                    switch (tipoVaga) {
                        case "Idoso" -> vaga = new VagaIdoso();
                        case "PCD" -> vaga = new VagaPCD();
                        case "VIP" -> vaga = new VagaVIP();
                        default -> vaga = new VagaRegular();
                    }

                    HistoricoUso historicoAtual = new HistoricoUso(cpf, nomeEstacionamento, vaga, placaAtual, valorPago, tempoTotal);
                    historicoFiltrado.add(historicoAtual);
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        
        return historicoFiltrado;
    }
    
    public List<Pagamento> getPagamentosPorEstacionamento(int idEstacionamento) throws SQLException, IOException{
        List<Pagamento> pagamentosFiltrados = new ArrayList<>();
        String sql = "SELECT id, idestacionamento, valorpago, tipovaga, placaveiculo, "
                   + "tempototal, dataentrada, datapagamento FROM Pagamento WHERE idestacionamento = ?";
        
        try{
            Connection conn = BancoDados.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idEstacionamento);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                double valorPago = rs.getDouble("valorPago");
                String tipoVaga = rs.getString("tipoVaga");
                String placaVeiculo = rs.getString("placaVeiculo");
                int tempoTotal = rs.getInt("tempoTotal");
                LocalDateTime dataEntrada = rs.getTimestamp("dataEntrada").toLocalDateTime();
                LocalDateTime dataPagamento = rs.getTimestamp("dataPagamento").toLocalDateTime();

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
                pagamento.setIdPagamento(id);
                pagamento.setIdEstacionamento(idEstacionamento);
                pagamento.setValorPago(valorPago);
                pagamento.setTipoVaga(vagaAtual.getTipoVaga());
                pagamento.setPlacaVeiculo(placaVeiculo);
                pagamento.setTempoTotal(tempoTotal);
                pagamento.setDataEntrada(dataEntrada);
                pagamento.setDataPagamento(dataPagamento);

                pagamentosFiltrados.add(pagamento);
            }
            return pagamentosFiltrados;
        }catch(SQLException e){
             throw new SQLException("Erro ao listar pagamentos: " + e.getMessage());
        }
    }

    public Map<String,Double> getArrecadacaoPorTipoVaga(int idEstacionamento) throws SQLException{
        String sql = "SELECT tipovaga, COUNT(*) AS totalVagas, SUM(valorPago) AS totalArrecadado FROM pagamento WHERE idestacionamento = ? GROUP BY tipoVaga";
        Map<String, Double> arrecadacaoPorTipo = new HashMap<>();
        
        
        try{
            Connection conn = BancoDados.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, idEstacionamento);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                String tipoVaga = rs.getString("tipovaga");
                double totalArrecadado = rs.getDouble("totalArrecadado");
                
                arrecadacaoPorTipo.put(tipoVaga, totalArrecadado);
            }
        }catch(SQLException e){
            throw new SQLException("Erro ao buscar arrecadação por tipo de vaga: " + e.getMessage());
        }
        return arrecadacaoPorTipo;
    }
    
}
