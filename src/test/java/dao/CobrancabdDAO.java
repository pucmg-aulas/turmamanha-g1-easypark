package dao;

import Models.Cliente;
import Models.Cobranca;
import Models.Veiculo;
import static dao.PagamentoDAO.FORMATTER;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CobrancabdDAO {

    private static CobrancabdDAO instance;
    private VeiculoDAO veiculos;
    private BancoDados bd;
    
    private CobrancabdDAO() throws SQLException {
        this.veiculos = VeiculoDAO.getInstance();
        this.bd = BancoDados.getInstancia();
    }

    public static CobrancabdDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new CobrancabdDAO();
        }
        return instance;
    }

    public Cobranca getCobranca(int idVaga) throws SQLException, FileNotFoundException {
        String sql = """
            SELECT id, idvaga, placaveiculo, idestacionamento, horaentrada, horasaida, tempototal, valortotal 
            FROM cobranca WHERE idVaga = ?
        """;

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVaga);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String placaVeiculo = rs.getString("placaVeiculo");
                    int idEstacionamento = rs.getInt("idEstacionamento");
                    LocalDateTime horaEntrada = rs.getTimestamp("horaEntrada").toLocalDateTime();
                    LocalDateTime horaSaida = rs.getTimestamp("horaSaida") != null ? rs.getTimestamp("horaSaida").toLocalDateTime() : null;
                    double tempoTotal = rs.getDouble("tempoTotal");
                    double valorTotal = rs.getDouble("valorTotal");

                    Veiculo automovel = veiculos.buscarVeiculoPorPlaca(placaVeiculo);
                    Cobranca cobranca = new Cobranca(id, idVaga, idEstacionamento, automovel, horaEntrada, horaSaida, tempoTotal, valorTotal);

                    return cobranca;
                }
            }
        }
        return null;
    }

    public boolean gerarCobranca(Cobranca cobranca) throws SQLException {
        String sql = """
            INSERT INTO cobranca (idVaga, placaVeiculo, idEstacionamento, horaEntrada, tempoTotal, valorTotal)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cobranca.getIdVaga());
            ps.setString(2, cobranca.getVeiculo().getPlaca());
            ps.setInt(3, cobranca.getIdEstacionamento());
            ps.setString(4, cobranca.getHoraEntrada().format(FORMATTER));
            ps.setDouble(5, cobranca.getTempoTotal());
            ps.setDouble(6, cobranca.getValorTotal());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean removerCobranca(Cobranca cobranca) throws SQLException {
        String sql = "DELETE FROM cobranca WHERE id = ?";

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cobranca.getIdCobranca());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean atualizarCobranca(Cobranca cobrancaAtualizada) throws SQLException {
        String sql = """
            UPDATE cobranca SET idVaga = ?, placaVeiculo = ?, idEstacionamento = ?, 
            horaEntrada = ?, horaSaida = ?, tempoTotal = ?, valorTotal = ? WHERE id = ?
        """;

        try (Connection conn = BancoDados.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cobrancaAtualizada.getIdVaga());
            ps.setString(2, cobrancaAtualizada.getVeiculo().getPlaca());
            ps.setInt(3, cobrancaAtualizada.getIdEstacionamento());
            ps.setString(4, cobrancaAtualizada.getHoraEntrada().format(FORMATTER));
            ps.setString(5, cobrancaAtualizada.getHoraSaida() != null ? cobrancaAtualizada.getHoraSaida().format(FORMATTER) : null);
            ps.setDouble(6, cobrancaAtualizada.getTempoTotal());
            ps.setDouble(7, cobrancaAtualizada.getValorTotal());
            ps.setInt(8, cobrancaAtualizada.getIdCobranca());

            return ps.executeUpdate() > 0;
        }
    }


    public List<Cobranca> lerCobrancas() throws SQLException, FileNotFoundException {
    List<Cobranca> cobrancas = new ArrayList<>();

    String sql = "SELECT id, idVaga, placaVeiculo, idEstacionamento, horaEntrada, horaSaida, tempoTotal, valorTotal FROM cobranca";

    try (Connection conn = BancoDados.getConexao();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("id");
            int idVaga = rs.getInt("idVaga");
            String placaVeiculo = rs.getString("placaVeiculo");
            int idEstacionamento = rs.getInt("idEstacionamento");
            LocalDateTime horaEntrada = rs.getTimestamp("horaEntrada").toLocalDateTime();
            LocalDateTime horaSaida = rs.getTimestamp("horaSaida") != null ? rs.getTimestamp("horaSaida").toLocalDateTime() : null;
            double tempoTotal = rs.getDouble("tempoTotal");
            double valorTotal = rs.getDouble("valorTotal");

            Veiculo automovel = veiculos.buscarVeiculoPorPlaca(placaVeiculo);
            Cobranca cobrancaAtual = new Cobranca(id, idVaga, idEstacionamento, automovel, horaEntrada, horaSaida, tempoTotal, valorTotal);

            cobrancas.add(cobrancaAtual);
        }
    }

    return cobrancas;
}

}