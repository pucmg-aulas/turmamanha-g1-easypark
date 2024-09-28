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

    public String getId(){
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
    public static boolean gravarVariosEstacionamentosEmArquivo(List<Estacionamento> estacionamentos) {
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

	//metodo para ler arquivos
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

    public boolean lerEstacionamentos() {
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
        
            while ((linha = leitor.readLine()) != null) {
                    System.out.println(linha); 
                    System.out.println(leitor.readLine()); 
                    System.out.println(leitor.readLine()); 
                    System.out.println(leitor.readLine()); 
                    System.out.println(leitor.readLine()); 
                
            }
			return true;
        } catch (IOException e) {
			return false;
        }
    }


    // Método para ler e registrar os estacionamentos e suas informações do arquivo
    public static List<Estacionamento> lerEstacionamentosDeArquivo() {
        File arquivoVarios = new File(".codigo/src/Models/Archives/Estacionamentos.txt");
        List<Estacionamento> estacionamentos = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivoVarios))) {
            String linha;
            Estacionamento estacionamentoAtual = null;

            while ((linha = leitor.readLine()) != null) {
                if (linha.startsWith("Estacionamento: ")) {
                    if (estacionamentoAtual != null) {
                        estacionamentos.add(estacionamentoAtual); // Adiciona o estacionamento anterior à lista
                    }
                    String nome = linha.substring(15); // Extrai o nome do estacionamento
                    String enderecoLinha = leitor.readLine(); // Lê a linha com o endereço
                    String rua = enderecoLinha.split(",")[0].split(":")[1].trim();
                    int numero = Integer.parseInt(enderecoLinha.split(",")[1].split("-")[0].trim());
                    String bairro = enderecoLinha.split("-")[1].trim();

                    estacionamentoAtual = new Estacionamento(nome, rua, bairro, numero); // Cria um novo estacionamento
                } else if (linha.startsWith("  - Vaga ID: ")) {
                    int idVaga = Integer.parseInt(linha.split(":")[1].trim());
                    Vaga vaga = new Vaga(idVaga); // Supondo que você tenha uma classe Vaga com o construtor por ID
                    estacionamentoAtual.adicionarVaga(vaga);
                }
            }

            if (estacionamentoAtual != null) {
                estacionamentos.add(estacionamentoAtual); // Adiciona o último estacionamento lido à lista
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

}
