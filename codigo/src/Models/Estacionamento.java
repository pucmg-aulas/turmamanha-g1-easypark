package src.Models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
    private int id;
    private String nome;
    private String rua;
    private String bairro;
    private int numero;
    private List<Vaga> vagas;
    private static String arquivo = ".codigo/src/Models/Archives/Estacionamentos.txt";

    private static int nextId = 1;

    public Estacionamento(String nome, String rua, String bairro, int numero) {
        this.nome = nome;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.vagas = new ArrayList<>();
        this.id = nextId;
        nextId++;
    }

    public void adicionarVaga(Vaga vaga) {
        this.vagas.add(vaga);
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


    public List<Vaga> getVagas() {
        return vagas;
    }


    // Método para gravar os dados de vários estacionamentos
    public static boolean gravarEstacionamentosEmArquivo(List<Estacionamento> estacionamentos) {
        File arquivo = new File(".codigo/src/Models/Archives/Estacionamentos.txt");

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))) {
            for (Estacionamento estacionamento : estacionamentos) {
                escritor.write("Estacionamento: " + estacionamento.getNome() + "\n");
                escritor.write("ID: " + estacionamento.getId() + "\n");
                escritor.write("Endereço: " + estacionamento.getRua() + ", " + estacionamento.getNumero() + " - " + estacionamento.getBairro() + "\n");
                escritor.write("Vagas Disponíveis:\n");

                for (Vaga vaga : estacionamento.getVagas()) {
                    escritor.write("  - Vaga ID: " + vaga.getId() + "\n");
                }

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
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
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
    public static List<Estacionamento> lerEstacionamentosDeArquivo() {
        File arquivoVarios = new File("codigo/src/Models/Archives/Estacionamentos.txt");
        List<Estacionamento> estacionamentos = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivoVarios))) {
            String linha;
            Estacionamento estacionamentoAtual = null;

            while ((linha = leitor.readLine()) != null) {
                // Quando encontrar um novo estacionamento, cria um novo objeto
                if (linha.startsWith("Estacionamento: ")) {
                    // Adiciona o estacionamento anterior à lista, se não for o primeiro
                    if (estacionamentoAtual != null) {
                        estacionamentos.add(estacionamentoAtual);
                    }
                    // Extraindo o nome do estacionamento
                    String nome = linha.substring(15).trim();

                    // Lendo a linha com o endereço e dividindo as informações
                    String enderecoLinha = leitor.readLine();
                    String[] partesEndereco = enderecoLinha.split(",");
                    String rua = partesEndereco[0].split(":")[1].trim();
                    int numero = Integer.parseInt(partesEndereco[1].split("-")[0].trim());
                    String bairro = partesEndereco[1].split("-")[1].trim();

                    // Criando um novo estacionamento
                    estacionamentoAtual = new Estacionamento(nome, rua, bairro, numero);

                    // Quando encontrar uma vaga, adiciona ao estacionamento atual
                } else if (linha.startsWith("  - Vaga ID: ")) {
                    int idVaga = Integer.parseInt(linha.split(":")[1].trim());

                    // Lendo o próximo campo de tipo de vaga
                    String tipoVagaLinha = leitor.readLine();
                    Vaga.TipoVaga tipoVaga = Vaga.TipoVaga.valueOf(tipoVagaLinha.split(":")[1].trim().toUpperCase());

                    // Criando uma nova vaga com base no ID e tipo
                    Vaga vaga = new Vaga(tipoVaga);

                    // Adicionando a vaga ao estacionamento atual
                    estacionamentoAtual.adicionarVaga(vaga);
                }
            }

            // Adiciona o último estacionamento lido à lista
            if (estacionamentoAtual != null) {
                estacionamentos.add(estacionamentoAtual);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return estacionamentos;
    }

}