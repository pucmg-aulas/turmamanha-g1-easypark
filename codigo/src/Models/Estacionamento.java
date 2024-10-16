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
    private int qntdVagas;
    private List<Vaga> vagas;
    private static String arquivoEstacionamento = "./codigo/src/Archives/Estacionamentos.txt";

    public Estacionamento(String nome, String rua, String bairro, int numero, int qntdVagas) {
        this.nome = nome;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.qntdVagas = qntdVagas;
        this.vagas = new ArrayList<>();
        this.id = EncontrarMaiorId() + 1;
        this.instanciarVagas();
    }

  public void instanciarVagas() {
        int vagasRegulares = (int) (qntdVagas * 0.5);
        int vagasIdoso = (int) (qntdVagas * 0.2);
        int vagasPCD = (int) (qntdVagas * 0.2);
        int vagasVIP = (int) (qntdVagas * 0.1);
        int idVaga = 1;

        int totalInstanciadas = vagasRegulares + vagasIdoso + vagasPCD + vagasVIP;

        // Ajusta para compensar qualquer arredondamento que cause diferença no total
        while (totalInstanciadas < qntdVagas) {
            vagasRegulares++;
            totalInstanciadas++;
        }

        for (int i = 0; i < vagasRegulares; i++) {
            vagas.add(new VagaRegular(idVaga++));
        }
        for (int i = 0; i < vagasIdoso; i++) {
            vagas.add(new VagaIdoso(idVaga++));
        }
        for (int i = 0; i < vagasPCD; i++) {
            vagas.add(new VagaPCD(idVaga++));
        }
        for (int i = 0; i < vagasVIP; i++) {
            vagas.add(new VagaVIP(idVaga++));
        }
    }

    public void adicionarVaga(Vaga vaga) {
        this.vagas.add(vaga);
        salvarVagaEmArquivo(vaga);
    }

    public Vaga getVagaPorId(int id) {
        String filePath = "./codigo/src/Archives/Vagas" + this.id + ".txt"; // Ajuste o caminho se necessário
        File arquivoVagas = new File(filePath);

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivoVagas))) {
            String linha;
            Vaga vagaAtual = null;
            int idAtual = -1;

            while ((linha = leitor.readLine()) != null) {
                if (linha.startsWith("ID: ")) {
                    idAtual = Integer.parseInt(linha.split(": ")[1]);
                }

                if (linha.startsWith("Status: ")) {
                    String status = linha.split(": ")[1].trim();
                    if (vagaAtual != null) {
                        vagaAtual.setStatus("Desocupada".equalsIgnoreCase(status));
                    }
                }

                if (linha.startsWith("Tipo: ")) {
                    String tipoVaga = linha.split(": ")[1].trim();
                    // Cria a vaga com base no tipo
                    if (tipoVaga.equalsIgnoreCase("VagaPCD")) {
                        vagaAtual = new VagaPCD(idAtual);
                    } else if (tipoVaga.equalsIgnoreCase("VagaVIP")) {
                        vagaAtual = new VagaVIP(idAtual);
                    } else if (tipoVaga.equalsIgnoreCase("VagaIdoso")) {
                        vagaAtual = new VagaIdoso(idAtual);
                    } else {
                        vagaAtual = new VagaRegular(idAtual); // Vaga regular como padrão
                    }
                }

                // Aqui você pode adicionar lógica para armazenar as vagas em uma lista ou retornar diretamente a vaga encontrada
                if (vagaAtual != null && vagaAtual.getId() == id) {
                    return vagaAtual; // Retorna a vaga encontrada
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de vagas: " + e.getMessage());
        }

        return null; // Retorna null se não encontrou a vaga
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

    public List<Vaga> getVagasDisponiveis() {
        List<Vaga> vagasDisponiveis = new ArrayList<>();
        Vaga vagaAtual = null;
        int idVaga = 0;

        try (BufferedReader leitor = new BufferedReader(new FileReader("./codigo/src/Archives/Vagas" + this.id + ".txt"))) {
            String linha;

            while ((linha = leitor.readLine()) != null) {
                if (linha.startsWith("ID: ")) {
                    idVaga = Integer.parseInt(linha.substring(4).trim());
                    vagaAtual = null; // Reseta a vagaAtual ao encontrar um novo ID
                }

                if (linha.startsWith("Tipo: ")) {
                    String tipoVaga = linha.substring(6).trim();

                    // Cria a vaga com base no tipo específico encontrado no arquivo
                    if (tipoVaga.equalsIgnoreCase("VagaIdoso")) {
                        vagaAtual = new VagaIdoso(id); // Instancia VagaIdoso
                    } else if (tipoVaga.equalsIgnoreCase("VagaPCD")) {
                        vagaAtual = new VagaPCD(id); // Instancia VagaPCD
                    } else if (tipoVaga.equalsIgnoreCase("VagaVIP")) {
                        vagaAtual = new VagaVIP(id); // Instancia VagaVIP
                    } else if (tipoVaga.equalsIgnoreCase("VagaRegular")) {
                        vagaAtual = new VagaRegular(id); // Instancia uma vaga comum
                    }

                    // Define o ID da vaga se a instância for criada com sucesso
                    if (vagaAtual != null) {
                        vagaAtual.setId(idVaga);
                    }
                }

                if (linha.startsWith("Status: ") && vagaAtual != null) {
                    String statusVaga = linha.substring(8).trim();

                    // Define o status da vaga com base no valor lido no arquivo
                    vagaAtual.setStatus(statusVaga.equalsIgnoreCase("Desocupada"));
                }

                // Ao chegar no final de uma vaga ("--------------------------------"), verifica o status e adiciona se for desocupada
                if (linha.equals("--------------------------------")) {
                    if (vagaAtual != null && vagaAtual.isDesocupada()) {
                        vagasDisponiveis.add(vagaAtual);
                    }
                    vagaAtual = null; // Reseta para preparar a leitura da próxima vaga
                }
            }

            // Adiciona a última vaga lida, se não tiver sido adicionada antes
            if (vagaAtual != null && vagaAtual.isDesocupada()) {
                vagasDisponiveis.add(vagaAtual);
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de vagas: " + e.getMessage());
        }

        return vagasDisponiveis;
    }

    public List<Vaga> getVagasOcupadas() {
        List<Vaga> vagasDisponiveis = new ArrayList<>();
        Vaga vagaAtual = null;
        int idVaga = 0;

        try (BufferedReader leitor = new BufferedReader(new FileReader("./codigo/src/Archives/Vagas" + this.id + ".txt"))) {
            String linha;

            while ((linha = leitor.readLine()) != null) {
                if (linha.startsWith("ID: ")) {
                    idVaga = Integer.parseInt(linha.substring(4).trim());
                    vagaAtual = null; // Reseta a vagaAtual ao encontrar um novo ID
                }

                if (linha.startsWith("Tipo: ")) {
                    String tipoVaga = linha.substring(6).trim();

                    // Cria a vaga com base no tipo específico encontrado no arquivo
                    if (tipoVaga.equalsIgnoreCase("VagaIdoso")) {
                        vagaAtual = new VagaIdoso(id); // Instancia VagaIdoso
                    } else if (tipoVaga.equalsIgnoreCase("VagaPCD")) {
                        vagaAtual = new VagaPCD(id); // Instancia VagaPCD
                    } else if (tipoVaga.equalsIgnoreCase("VagaVIP")) {
                        vagaAtual = new VagaVIP(id); // Instancia VagaVIP
                    } else if (tipoVaga.equalsIgnoreCase("Vaga")) {
                        vagaAtual = new VagaRegular(id); // Instancia uma vaga comum
                    }

                    // Define o ID da vaga se a instância for criada com sucesso
                    if (vagaAtual != null) {
                        vagaAtual.setId(idVaga);
                    }
                }

                if (linha.startsWith("Status: ") && vagaAtual != null) {
                    String statusVaga = linha.substring(8).trim();

                    // Define o status da vaga com base no valor lido no arquivo
                    vagaAtual.setStatus(statusVaga.equalsIgnoreCase("Ocupada"));
                }

                // Ao chegar no final de uma vaga ("--------------------------------"), verifica o status e adiciona se for desocupada
                if (linha.equals("--------------------------------")) {
                    if (vagaAtual != null && vagaAtual.isDesocupada()) {
                        vagasDisponiveis.add(vagaAtual);
                    }
                    vagaAtual = null; // Reseta para preparar a leitura da próxima vaga
                }
            }

            // Adiciona a última vaga lida, se não tiver sido adicionada antes
            if (vagaAtual != null && vagaAtual.isDesocupada()) {
                vagasDisponiveis.add(vagaAtual);
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de vagas: " + e.getMessage());
        }

        return vagasDisponiveis;
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
                escritor.write("Vagas: " + this.qntdVagas + "\n");
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
            escritor.write("Estacionamento: " + this.id + "\n");
            escritor.write("--------------------------------\n");
            System.out.println("Vaga registrada com sucesso no arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar a vaga no arquivo: " + e.getMessage());
        }
    }


}