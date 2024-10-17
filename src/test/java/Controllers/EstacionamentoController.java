//package Controllers;
//
//import Models.Estacionamento;
//import Models.Vaga;
//import java.util.List;
//
//public class EstacionamentoController {
//
//    private Estacionamento estacionamento;
//
//    public EstacionamentoController() {
//     //   this.estacionamento = new Estacionamento();
//    }
//
//    public void cadastrarEstacionamento(String nome, String rua, int numero, String bairro, int qntdVagas) {
//        estacionamento = new Estacionamento(nome, rua, bairro, numero, qntdVagas);
//        estacionamento.instanciarVagas();
//        salvarEstacionamento();
//    }
//
//   public void setEstacionamento(Estacionamento estacionamento) {
//       // estacionamento.add(estacionamento);
//    }
//
//    //public List<Estacionamento> getEstacionamentos() {
//      //  return estacionamentos;
//    //}
//
//    public void adicionarVaga(Vaga vaga) {
//        estacionamento.adicionarVaga(vaga);
//    }
//
//    public Vaga buscarVagaPorId(int id) {
//        return estacionamento.getVagaPorId(id);
//    }
//
//    public List<Vaga> listarVagasDisponiveis() {
//        return estacionamento.getVagasDisponiveis();
//    }
//
//    public List<Vaga> listarVagasOcupadas() {
//        return estacionamento.getVagasOcupadas();
//    }
//
//    public boolean reservarVaga(int idVaga) {
//        return estacionamento.reservarVagaPorId(idVaga);
//    }
//
//    public boolean salvarEstacionamento() {
//        return estacionamento.gravarEstacionamentosEmArquivo();
//    }
//
//    public List<String> listarEstacionamentos() {
//        return Estacionamento.lerEstacionamentosDoArquivo();
//    }
//
//    public void exibirDetalhesEstacionamento() {
//        System.out.println("Estacionamento: " + estacionamento.getNome());
//        System.out.println("Rua: " + estacionamento.getRua() + ", " + estacionamento.getNumero());
//        System.out.println("Bairro: " + estacionamento.getBairro());
//        System.out.println("Quantidade de Vagas: " + estacionamento.getVagas().size());
//    }
//}