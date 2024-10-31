//package dao;
//
//import Models.HistoricoUso;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class HistoricoUsoDAO {
//    private List<Historico> historicos;
//    private static HistoricoDAO instance;
//    private static final String ARQUIVO = "./src/test/java/Archives/HistoricoUso.txt";
//
//    private HistoricoDAO() throws IOException {
//        historicos = lerHistoricos();
//        if(historicos == null){
//            historicos = new ArrayList<>();
//        }
//    }
//    
//    public static HistoricoDAO getInstance() throws IOException {
//        if(instance == null) {
//            instance = new HistoricoDAO();
//        }
//        return instance;
//    }
//    
//    public Historico buscarHistoricoPorId(String id) {
//        try(BufferedReader br = new BufferedReader(new FileReader(Arquivo))) {
//            String linha;
//            while((linha = br.readLine()) != null) {
//                String[] dados = linha.split(";");
//                String idHistorico = dados[0];
//                String descricao = dados[1];
//                String data = dados[2];
//
//                if(id.equals(idHistorico)) {
//                    return new Historico(idHistorico, descricao, data);
//                }
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    }
//    
//    public boolean cadastrarHistorico(Historico historico) throws IOException {
//        historicos.add(historico);
//        return salvarHistoricoArquivo(historicos);
//    } 
//    
//    private boolean salvarHistoricoArquivo(List<Historico> listaHistoricos) throws IOException {
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
//            try(BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
//                for(Historico historico : listaHistoricos) {
//                    // Escrever o CPF e nome do cliente
//                    bw.write(historico.getCpfCliente() + ";" + historico.getNomeCliente());
//                    bw.newLine();
//                    
//                    // Escrever detalhes da cobrança
//                    bw.write(historico.getDescricao() + ";" + historico.getIdCobranca() + ";" + historico.getIdVaga() + ";"
//                            + historico.getPlacaVeiculo() + ";" + historico.getStatus() + ";" + historico.getDataEntrada());
//                    bw.newLine();
//                    
//                    // Se houver data de saída (pagamento), adicionar linha correspondente
//                    if (historico.getDataSaida() != null && !historico.getDataSaida().isEmpty()) {
//                        bw.write(historico.getDescricao() + ";" + historico.getIdCobranca() + ";" + historico.getIdVaga() + ";"
//                                + historico.getPlacaVeiculo() + ";" + historico.getStatus() + ";" + historico.getDataSaida());
//                        bw.newLine();
//                    }
//                    
//                    // Linha em branco para separar registros
//                    bw.newLine();
//                }
//                bw.flush();
//                return true;
//            }
//        } catch (IOException e) {
//            throw new IOException(e.getMessage());
//        }
//    }
//    
//    public List<Historico> lerHistoricos() throws FileNotFoundException, IOException {
//        List<Historico> historicosLista = new ArrayList<>();
//        
//        try(BufferedReader br = new BufferedReader(new FileReader(Arquivo))) {
//            String linha;
//            
//            while((linha = br.readLine()) != null) {
//                if (linha.trim().isEmpty()) continue;
//
//                // Primeira linha: CPF e nome do cliente
//                String[] dadosCliente = linha.split(";");
//                String cpfCliente = dadosCliente[0];
//                String nomeCliente = dadosCliente[1];
//                
//                // Próxima linha: dados da cobrança
//                linha = br.readLine();
//                String[] dadosCobranca = linha.split(";");
//                String descricao = dadosCobranca[0];
//                String idCobranca = dadosCobranca[1];
//                String idVaga = dadosCobranca[2];
//                String placaVeiculo = dadosCobranca[3];
//                String status = dadosCobranca[4];
//                String dataEntrada = dadosCobranca[5];
//
//                // Cria objeto Historico com data de entrada
//                Historico historico = new Historico(cpfCliente, nomeCliente, descricao, idCobranca, idVaga, placaVeiculo, status, dataEntrada);
//
//                // Verifica se há data de saída (pagamento) na linha seguinte
//                linha = br.readLine();
//                if (linha != null && !linha.trim().isEmpty()) {
//                    String[] dadosPagamento = linha.split(";");
//                    String dataSaida = dadosPagamento[5];
//                    historico.setDataSaida(dataSaida); // Define data de saída no objeto
//                }
//
//                historicosLista.add(historico);
//
//                // Avança para a próxima linha em branco entre registros
//                br.readLine();
//            }
//            return historicosLista;
//        } catch(IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    
//    
//    
//    
//    }
//}
