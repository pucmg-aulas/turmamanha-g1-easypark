package dao;

public class TesteBancoDados {
    public static void main(String[] args) {
        // Tenta obter uma instância de conexão com o banco de dados
        BancoDados bancoDados = BancoDados.getInstancia();

        // Verifica se a conexão foi estabelecida com sucesso
        if (bancoDados.getConexao() != null) {
            System.out.println("Conexão estabelecida com sucesso!");
        } else {
            System.out.println("Falha na conexão.");
        }

        // Fecha a conexão
        bancoDados.desconectar();
    }
}