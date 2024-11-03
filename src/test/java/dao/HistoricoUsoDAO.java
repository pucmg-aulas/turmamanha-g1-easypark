<<<<<<< HEAD
//package dao;
//
//import Models.HistoricoUso;
//import Models.Cobranca;
//import Models.Cliente;
//import Models.Estacionamento;
//import Models.ITipo;
//import Models.Pagamento;
//import Models.Veiculo;
//
//import java.io.*;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//public class HistoricoUsoDAO {
//    private List<HistoricoUso> historicos;
//    private static HistoricoUsoDAO instance;
//    private static final String ARQUIVO = "./src/test/java/Archives/HistoricoUso.txt";
//    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
//    private EstacionamentoDAO estacionamentos;
//    private PagamentoDAO pagamentos;
//    private VeiculoDAO veiculos;
//    
//    private HistoricoUsoDAO() throws IOException {
//        pagamentos = PagamentoDAO.getInstance();
//        estacionamentos = EstacionamentoDAO.getInstance();
//        veiculos = VeiculoDAO.getInstance();
//        historicos = lerHistoricos();
//        if(historicos == null){
//            historicos = new ArrayList<>();
//        }
//    }
//
//    public static HistoricoUsoDAO getInstance() throws IOException {
//        if(instance == null) {
//            instance = new HistoricoUsoDAO();
//        }
//        return instance;
//    }
//   
// public List<HistoricoUso> buscarHistoricoPorCpf(String cpf) throws IOException {
//    List<HistoricoUso> historicoFiltrado = new ArrayList<>();
//    
//    List<Pagamento> ListaPagamentos = pagamentos.listarPagamentos();
//    
//    for(Pagamento pagamento : ListaPagamentos){
//        String placaVeiculo = pagamento.getPlacaVeiculo();
//        Veiculo veiculoAtual = veiculos.buscarVeiculoPorPlaca(placaVeiculo);
//        String cpfClienteAtual = veiculoAtual.getCliente().getCpf();
//        
//        if(cpfClienteAtual.equals(cpf)){
//            String cpfAtual = cpf;
//            Estacionamento estacionamentoAtual = estacionamentos.getEstacionamentoPorId(pagamento.getIdEstacionamento());
//            String nomeEstacionamento = estacionamentoAtual.getNome();
//            String placaAtual = pagamento.getPlacaVeiculo();
//            ITipo vaga = pagamento.getTipoVaga();
//            double valorTotal = pagamento.getValorPago();
//            int tempoTotal = pagamento.getTempoTotal();
//            
//            
//            HistoricoUso historicoAtual = new HistoricoUso(cpfAtual, nomeEstacionamento, vaga, placaAtual, valorTotal,tempoTotal);
//            historicoFiltrado.add(historicoAtual);
//        }
//    }
//
//        return historicoFiltrado;
//    }
//
////    public HistoricoUso buscarHistoricoPorCpf(String cpfCliente) {
////        try(BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
////            String linha;
////            while((linha = br.readLine()) != null) {
////                String[] dados = linha.split(";");
////              
////                String cpf = dados[0];
////                int idCobranca = Integer.parseInt(dados[1]);
////                int idEstacionamento = Integer.parseInt(dados[2]);
////                int idVaga = Integer.parseInt(dados[3]);
////                String placaVeiculo = dados[4];
////                LocalDateTime dataEntrada = LocalDateTime.parse(dados[5], formatter);
////                LocalDateTime dataSaida =  LocalDateTime.parse(dados[6], formatter);
////
////                if(cpfCliente.equals(cpf)) {
////                    return new HistoricoUso(cpf, idCobranca, idEstacionamento, idVaga, placaVeiculo, dataEntrada, dataSaida);
////                }
////            }
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
////        return null;
////    }
//
//    public boolean cadastrarHistorico(HistoricoUso historico) throws IOException {
//        historicos.add(historico);
//        return salvarHistoricoArquivo(historicos);
//    }
//
//    private boolean salvarHistoricoArquivo(List<HistoricoUso> listaHistoricos) throws IOException {
//        File arquivo = new File(ARQUIVO);
//
//        try {
//            File diretorio = arquivo.getParentFile();
//            if (diretorio != null && !diretorio.exists()) {
//                diretorio.mkdir();
//            }
//
//            if (!arquivo.exists()) {
//                arquivo.createNewFile();
//            }
//
//            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
//            for (HistoricoUso historico : historicos) {
//                bw.write(
//                    historico.getCpfCliente() + ";" +
//                    historico.getNomeEstacionamento() + ";" +
//                    historico.getTipoVaga().getTipo() + ";" +
//                    historico.getPlacaVeiculo() + ";" +
//                    historico.getDataEntrada().format(formatter) + ";" +
//                    (historico.getDataSaida() != null ? historico.getDataSaida().format(formatter) : "")
//                );
//                bw.newLine();
//                bw.flush();
//            }
//            return true;
//            }
//        } catch (IOException e) {
//            throw new IOException(e.getMessage());
//        }
//    }
//
//    public List<HistoricoUso> lerHistoricos() throws FileNotFoundException, IOException {
//        List<HistoricoUso> historicosLista = new ArrayList<>();
//         File arquivo = new File(ARQUIVO);
//
//    // Verifica se o arquivo existe e o cria, se necessário
//    if (!arquivo.exists()) {
//        arquivo.getParentFile().mkdirs(); // Cria diretórios, se necessário
//        arquivo.createNewFile(); // Cria o arquivo
//        return historicosLista; // Retorna lista vazia já que não há dados ainda
//    }
//
//        try(BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
//            String linha;
//
//            while((linha = br.readLine()) != null) {
//                if (linha.trim().isEmpty()) continue;
//
//                String[] dados = linha.split(";");
//                
//                String cpfCliente = dados[0];
//                int idCobranca = Integer.parseInt(dados[1]);
//                int idEstacionamento = Integer.parseInt(dados[2]);
//                int idVaga = Integer.parseInt(dados[3]);
//                String placaVeiculo = dados[4];
//                LocalDateTime dataEntrada = LocalDateTime.parse(dados[5], formatter);
//                LocalDateTime dataSaida = LocalDateTime.parse(dados[6], formatter);
//
//                HistoricoUso historico = new HistoricoUso(cpfCliente, idCobranca, idEstacionamento, idVaga, placaVeiculo, dataEntrada, dataSaida);
//                historicosLista.add(historico);
//            }
//            return historicosLista;
//        } catch(IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
=======
package dao;

import Models.HistoricoUso;
import Models.Cobranca;
import Models.Cliente;
import Models.Veiculo;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistoricoUsoDAO {
    private List<HistoricoUso> historicos;
    private static HistoricoUsoDAO instance;
    private static final String ARQUIVO = "./src/test/java/Archives/HistoricoUso.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private HistoricoUsoDAO() throws IOException {
        historicos = lerHistoricos();
        if(historicos == null){
            historicos = new ArrayList<>();
        }
    }

    public static HistoricoUsoDAO getInstance() throws IOException {
        if(instance == null) {
            instance = new HistoricoUsoDAO();
        }
        return instance;
    }
   
// public List<HistoricoUso> buscarHistoricoPorPlaca(String placaVeiculo) {
//    List<HistoricoUso> historicoFiltrado = new ArrayList<>();
//    
//    List<Cobranca> cobrancas = CobrancaDAO.getInstance().lerCobrancas();
//    List<Veiculo> veiculos = VeiculoDAO.getInstance().getVeiculos();
//    for (Cobranca cobranca : cobrancas) {
//        // Verifica se a placa da cobrança corresponde à placa desejada
//        if (cobranca.getPlacaVeiculo().equals(placaVeiculo)) {
//            Cliente clienteEncontrado = null;
//            
//            // Busca o veículo correspondente à placa e obtém o cliente
//            for (Veiculo veiculo : veiculos) {
//                if (veiculo.getPlaca().equals(placaVeiculo)) {
//                    clienteEncontrado = veiculo.getCliente();
//                    break;
//                }
//            }
//            
//            if (clienteEncontrado != null) {
//                // Cria um registro de histórico para cada cobrança correspondente
//                HistoricoUso historico = new HistoricoUso(
//                        clienteEncontrado.getCpf(),
////                        pagamento.getIdCobranca(),
////                        pagamento.getIdEstacionamento(),
////                        pagamento.getIdVaga(),
////                        pagamento.getPlacaVeiculo(),
////                        pagamento.getHoraEntrada(),
////                        pagamento.getHoraSaida()
//                );
//                historicoFiltrado.add(historico);
//            }
//        }
//    }
//
//    return historicoFiltrado;
//}

    public HistoricoUso buscarHistoricoPorCpf(String cpfCliente) {
        try(BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
              
                String cpf = dados[0];
                int idCobranca = Integer.parseInt(dados[1]);
                int idEstacionamento = Integer.parseInt(dados[2]);
                int idVaga = Integer.parseInt(dados[3]);
                String placaVeiculo = dados[4];
                LocalDateTime dataEntrada = LocalDateTime.parse(dados[5], formatter);
                LocalDateTime dataSaida =  LocalDateTime.parse(dados[6], formatter);

                if(cpfCliente.equals(cpf)) {
                    return new HistoricoUso(cpf, idCobranca, idEstacionamento, idVaga, placaVeiculo, dataEntrada, dataSaida);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean cadastrarHistorico(HistoricoUso historico) throws IOException {
        historicos.add(historico);
        return salvarHistoricoArquivo(historicos);
    }

    private boolean salvarHistoricoArquivo(List<HistoricoUso> listaHistoricos) throws IOException {
        File arquivo = new File(ARQUIVO);

        try {
            File diretorio = arquivo.getParentFile();
            if (diretorio != null && !diretorio.exists()) {
                diretorio.mkdir();
            }

            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (HistoricoUso historico : historicos) {
                bw.write(
                    historico.getCpfCliente() + ";" +
                    historico.getIdCobranca() + ";" +
                    historico.getIdEstacionamento() + ";" +
                    historico.getIdVaga() + ";" +
                    historico.getPlacaVeiculo() + ";" +
                    historico.getDataEntrada().format(formatter) + ";" +
                    (historico.getDataSaida() != null ? historico.getDataSaida().format(formatter) : "")
                );
                bw.newLine();
                bw.flush();
            }
            return true;
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public List<HistoricoUso> lerHistoricos() throws FileNotFoundException, IOException {
        List<HistoricoUso> historicosLista = new ArrayList<>();
         File arquivo = new File(ARQUIVO);

    // Verifica se o arquivo existe e o cria, se necessário
    if (!arquivo.exists()) {
        arquivo.getParentFile().mkdirs(); // Cria diretórios, se necessário
        arquivo.createNewFile(); // Cria o arquivo
        return historicosLista; // Retorna lista vazia já que não há dados ainda
    }

        try(BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;

            while((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split(";");
                
                String cpfCliente = dados[0];
                int idCobranca = Integer.parseInt(dados[1]);
                int idEstacionamento = Integer.parseInt(dados[2]);
                int idVaga = Integer.parseInt(dados[3]);
                String placaVeiculo = dados[4];
                LocalDateTime dataEntrada = LocalDateTime.parse(dados[5], formatter);
                LocalDateTime dataSaida = LocalDateTime.parse(dados[6], formatter);

                HistoricoUso historico = new HistoricoUso(cpfCliente, idCobranca, idEstacionamento, idVaga, placaVeiculo, dataEntrada, dataSaida);
                historicosLista.add(historico);
            }
            return historicosLista;
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
>>>>>>> 17fd84970fd53f1152add27d26674b251d9cb7fb
