package dao;

import Models.Estacionamento;
import Models.Vaga;
import Models.VagaIdoso;
import Models.VagaRegular;
import Models.VagaVIP;
import Models.VagaPCD;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VagaDAO {
    private List<Vaga> vagas;
    private static VagaDAO instance;
    private int idEstacionamento;

    private VagaDAO(int idEstacionamento) {
        vagas = carregarVagasArquivo(idEstacionamento);
        this.idEstacionamento = idEstacionamento;
        if (vagas == null) {
            vagas = new ArrayList<>();
        }
    }

    public static VagaDAO getInstance(int idEstacionamento) {
        if (instance == null || instance.idEstacionamento != idEstacionamento) {
            instance = new VagaDAO(idEstacionamento);
        }
        return instance;
    }

    public List<Vaga> carregarVagasArquivo(int idEstacionamento) {
        List<Vaga> vagasCarregadas = new ArrayList<>();
        File arquivo = new File("./src/test/java/Archives/Vagas" + idEstacionamento + ".txt");

        if (arquivo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    String[] dados = linha.split(";");
                    int id = Integer.parseInt(dados[0]);
                    String tipo = dados[1];
                    String statusString = dados[2];

                    boolean status = statusString.contains("Desocupado");

                    Vaga vaga = null;
                    switch (tipo) {
                        case "Regular" -> vaga = new VagaRegular(idEstacionamento, status, id);
                        case "Idoso" -> vaga = new VagaIdoso(idEstacionamento, status, id);
                        case "PCD" -> vaga = new VagaPCD(idEstacionamento, status, id);
                        case "VIP" -> vaga = new VagaVIP(idEstacionamento, status, id);
                    }

                    if (vaga != null) {
                        vagasCarregadas.add(vaga);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return vagasCarregadas;
    }

    public boolean cadastrarVaga(Vaga vaga, int idEstacionamento) throws IOException {
        vagas.add(vaga);
        return salvarVagasArquivo(vagas, idEstacionamento);
    }

    public boolean salvarVagasArquivo(List<Vaga> vagas, int idEstacionamento) throws IOException {
        File arquivo = new File("./src/test/java/Archives/Vagas" + idEstacionamento + ".txt");

        try {
            File diretorio = arquivo.getParentFile();
            if (diretorio != null && !diretorio.exists()) {
                diretorio.mkdir();
            }

            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
                for (Vaga vaga : vagas) {
                    String statusString = vaga.getStatus() ? "Desocupado" : "Ocupado";
                    bw.write(vaga.getId() + ";" + vaga.getTipo() + ";" + statusString);
                    bw.newLine();
                    bw.flush();
                }
                return true;
            }
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    public void instanciarVagas(int qntdVagas, int idEstacionamento) throws IOException {
        int vagasRegulares = (int) (qntdVagas * 0.5);
        int vagasIdoso = (int) (qntdVagas * 0.2);
        int vagasPCD = (int) (qntdVagas * 0.2);
        int vagasVIP = (int) (qntdVagas * 0.1);

        int totalInstanciadas = vagasRegulares + vagasIdoso + vagasPCD + vagasVIP;
        List<Vaga> vagaLista = new ArrayList<>();

        while (totalInstanciadas < qntdVagas) {
            vagasRegulares++;
            totalInstanciadas++;
        }
        int maiorIdExistente = 0;
        int proximoId = maiorIdExistente + 1;

        for (int i = 0; i < vagasRegulares; i++) {
            Vaga vaga = new VagaRegular(idEstacionamento, proximoId++);
            vagas.add(vaga);
            vagaLista.add(vaga);
        }
        for (int i = 0; i < vagasIdoso; i++) {
            Vaga vaga = new VagaIdoso(idEstacionamento, proximoId++);
            vagas.add(vaga);
            vagaLista.add(vaga);
        }
        for (int i = 0; i < vagasPCD; i++) {
            Vaga vaga = new VagaPCD(idEstacionamento, proximoId++);
            vagas.add(vaga);
            vagaLista.add(vaga);
        }
        for (int i = 0; i < vagasVIP; i++) {
            Vaga vaga = new VagaVIP(idEstacionamento, proximoId++);
            vagas.add(vaga);
            vagaLista.add(vaga);
        }

        salvarVagasArquivo(vagaLista, idEstacionamento);
    }

    public Vaga getVagaPorId(int id) {
        for (Vaga vaga : vagas) {
            if (vaga.getId() == id) {
                return vaga;
            }
        }
        return null;
    }

    public List<Vaga> getVagas() {
        return vagas;
    }

    public List<Vaga> getVagasDisponiveis() {
        List<Vaga> vagasDisponiveis = new ArrayList<>();

        for (Vaga vaga : this.vagas) {
            if (vaga.getStatus()) {  // true = Desocupado
                vagasDisponiveis.add(vaga);
            }
        }

        return vagasDisponiveis;
    }

    public List<Vaga> getVagasOcupadas() {
        List<Vaga> vagasOcupadas = new ArrayList<>();

        for (Vaga vaga : this.vagas) {
            if (!vaga.getStatus()) {  // false = Ocupado
                vagasOcupadas.add(vaga);
            }
        }

        return vagasOcupadas;
    }

    public boolean liberarVaga(int idVaga) {
        for (Vaga vaga : vagas) {
            if (vaga.getId() == idVaga) {
                vaga.setStatus(true); // Marca a vaga como desocupada
                try {
                    salvarVagasArquivo(vagas, vaga.getIdEstacionamento()); // Salva o estado atualizado
                } catch (IOException e) {
                    e.printStackTrace();
                    return false; // Retorna false em caso de erro
                }
                return true; // Retorna true se a vaga foi liberada
            }
        }
        return false; // Retorna false se a vaga não foi encontrada
    }


    /*public Estacionamento lerEstacionamentoPorId(int idEstacionamento) throws FileNotFoundException, IOException {
        Estacionamento estacionamentoAtual;
        try (BufferedReader br = new BufferedReader(new FileReader(Estacionamento.getArquivoPath()))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                String id = dados[0];
                int idNumero = Integer.parseInt(id);
                String nome = dados[1];
                String rua = dados[2];
                String bairro = dados[3];
                String numero = dados[4];
                String qntVagas = dados[5];

                if (idEstacionamento == idNumero) {
                    estacionamentoAtual = new Estacionamento(idNumero, nome, rua, bairro, Integer.parseInt(numero), Integer.parseInt(qntVagas));
                    return estacionamentoAtual;
                }
            }
            return estacionamentoAtual = null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}*/
}
