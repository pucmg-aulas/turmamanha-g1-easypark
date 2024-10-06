package Models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Estacionamento implements EncontrarMaior{
    private int id;
    private String nome;
    private String rua;
    private String bairro;
    private int numero;
    private List<Vaga> vagas;
    private static String arquivoEstacionamento = "./codigo/src/Archives/Estacionamentos.txt";

    public Estacionamento(String nome, String rua, String bairro, int numero) {
        this.nome = nome;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.vagas = new ArrayList<>();
        this.id = EncontrarMaiorId() + 1;
    }

    public void adicionarVaga(Vaga vaga) {
        this.vagas.add(vaga);
        salvarVagaEmArquivo(vaga);
    }

    public Vaga getVagaPorId(int id) {
        for (Vaga v : vagas) {
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getRua() {
        return rua;
    }

    public String getBairro() {
        return bairro;
    }

    public int getNumero() {
        return numero;
    }

    public void setId(int id) {
        this.id = id;
    }


    public List<Vaga> getVagas() {
        return vagas;
    }

    public boolean reservarVagaPorId(int idVaga) {
        File vagaFile = new File("./codigo/src/Archives/Vagas" + this.id + ".txt");
        List<String> linhas = new ArrayList<>();
        boolean vagaEncontrada = false;

        try (BufferedReader leitor = new BufferedReader(new FileReader(vagaFile))) {
            String linha;

            while ((linha = leitor.readLine()) != null) {
                // Verifica se a linha contém o ID da vaga
                if (linha.contains("ID: " + idVaga)) {
                    vagaEncontrada = true;
                    linhas.add(linha); // Adiciona a linha da vaga
                    String statusLinha = leitor.readLine(); // Lê a linha de status
                    if (statusLinha.contains("Desocupada")) {
                        linhas.add("Status: Ocupada"); // Atualiza o status para "Ocupada"
                    } else {
                        System.out.println("A vaga já está ocupada.");
                        return false;
                    }
                } else {
                    linhas.add(linha); // Adiciona as outras linhas sem modificações
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return false;
        }

        if (vagaEncontrada) {
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(vagaFile))) {
                // Reescreve todas as linhas no arquivo
                for (String l : linhas) {
                    escritor.write(l + "\n");
                }
                return true;
            } catch (IOException e) {
                System.out.println("Erro ao gravar o arquivo: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("Vaga com ID " + idVaga + " não encontrada.");
            return false;
        }
    }


    // Método para gravar os dados de vários estacionamentos
    public boolean gravarEstacionamentosEmArquivo() {
        File arquivo = new File("./codigo/src/Archives/Estacionamentos.txt");

        try {

            File diretorio = arquivo.getParentFile();
            if (diretorio != null && !diretorio.exists()) {
                diretorio.mkdirs();  // Cria o diretório se ele não existir
            }

            // Cria o arquivo se ele não existir
            if (!arquivo.exists()) {
                arquivo.createNewFile(); // Cria o arquivo
            }

            // Escreve no arquivo
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, true))) {
                escritor.write("Estacionamento: " + this.getNome() + "\n");
                escritor.write("ID: " + this.getId() + "\n");
                escritor.write("Endereço: " + this.getRua() + ", " + this.getNumero() + " - " + this.getBairro() + "\n");
                escritor.write("--------------------------------\n");
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

        //metodo para ler vaga por id
    public boolean lerVagaPorId(int idVaga) {
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivoEstacionamento))) {
            String linha;
            boolean vagaEncontrado = false;
            while ((linha = leitor.readLine()) != null) {
                if (linha.contains("ID vaga: " + idVaga)) {
                    vagaEncontrado = true;
                    System.out.println(linha);
                    System.out.println(leitor.readLine());
                    System.out.println(leitor.readLine());
                    break;
                }
            }
            if (!vagaEncontrado) {
                System.out.println("Vaga com ID " + idVaga + " não encontrada.");
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    // Método para ler e registrar os estacionamentos e suas informações do arquivo
    public static List<String> lerEstacionamentosDoArquivo() {
        File arquivo = new File("./codigo/src/Archives/Estacionamentos.txt");
        List<String> estacionamentos = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            StringBuilder estacionamentoAtual = new StringBuilder();

            // Lê cada linha do arquivo
            while ((linha = leitor.readLine()) != null) {
                estacionamentoAtual.append(linha).append("\n");

                // Verifica se o final do estacionamento foi encontrado
                if (linha.equals("--------------------------------")) {
                    estacionamentos.add(estacionamentoAtual.toString());
                    estacionamentoAtual.setLength(0); // Limpa o builder para o próximo estacionamento
                }
            }

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
        }

        return estacionamentos;
    }


    @Override
    public int EncontrarMaiorId() {
        File arquivo = new File(arquivoEstacionamento);
        int maiorId = 0;

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.startsWith("ID: ")) {
                    int idAtual = Integer.parseInt(linha.replace("ID: ", "").trim());
                    if (idAtual > maiorId) {
                        maiorId = idAtual;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo para obter o maior ID: " + e.getMessage());
        }

        return maiorId;
    }

    private void salvarVagaEmArquivo(Vaga vaga) {
        String nomeArquivo = "./codigo/src/Archives/Vagas" + this.id + ".txt";
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
            escritor.write("ID: " + vaga.getId() + "\n");
            escritor.write("Status: Desocupada\n");
            escritor.write("Tipo: " + vaga.getClass().getSimpleName() + "\n");
            escritor.write("--------------------------------\n");
            System.out.println("Vaga registrada com sucesso no arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar a vaga no arquivo: " + e.getMessage());
        }
    }
}