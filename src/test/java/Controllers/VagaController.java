package Controllers;

import dao.VagaDAO;
import Models.Estacionamento;

public class VagaController {

    private VagaDAO vagaDAO;
    private Estacionamento estacionamento;

    public VagaController(){

    }

    private void instanciarVagas() {
        // Obtem a quantidade de vagas do estacionamento
        int qntdVagas = estacionamento.getQntdVagas();
        vagaDAO.instanciarVagas(qntdVagas, estacionamento.getId()); // Instancia as vagas
        JOptionPane.showMessageDialog(view, "Vagas instanciadas com sucesso!");
    }

    private void carregarVagas() {

        List<Vaga> vagasCarregadas = vagaDAO.carregarVagasArquivo(estacionamento.getId());
        if (vagasCarregadas.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nenhuma vaga encontrada para este estacionamento.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Aqui você pode atualizar a view com as vagas carregadas
            // Por exemplo, preencher uma tabela ou lista com as vagas
            view.atualizarTabelaVagas(vagasCarregadas); // Supondo que você tenha esse método na view
            JOptionPane.showMessageDialog(view, "Vagas carregadas com sucesso!");
        }
    }



//    public int EncontrarQntdVagas(){
//        private List<Estacionamento> lerEstacionamentos() throws FileNotFoundException, IOException {
//            List<Estacionamento> estacionamentos = new ArrayList();
//            try(BufferedReader br = new BufferedReader(new FileReader(Arquivo))){
//                String linha;
//
//                while((linha = br.readLine()) != null){
//                    String[] dados = linha.split(";");
//                    String id = dados[0];
//                    String nome = dados[1];
//                    String rua = dados[2];
//                    String bairro = dados[3];
//                    String numero = dados[4];
//                    String qntVagas = dados[5];
//
//                    Estacionamento e = new Estacionamento(Integer.parseInt(id), nome, rua, bairro, Integer.parseInt(numero),Integer.parseInt(qntVagas));
//                    estacionamentos.add(e);
//                }
//
//                return estacionamentos;
//            }catch(IOException e){
//                throw new RuntimeException(e);
//            }
//
//        }
//    }

}
