import java.util.Scanner;

public class EstacionamentoApp 
{

    public static void main(String[] args) 
  {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do 
        {
            System.out.println("=== | Sistema de Estacionamento | ===");
            System.out.println("Seja bem-vindo ao nosso sistema de Auto-Atendimento!");
            System.out.println("Menu Principal:");
            System.out.println("(1) Informacoes do Estacionamento");
            System.out.println("(2) Cadastrar Cliente");
            System.out.println("(3) Cadastrar Veiculo");
            System.out.println("(4) Cadastrar Vaga");
            System.out.println("(5) Reservar Vaga");
            System.out.println("(6) Liberar Vaga");
            System.out.println("(7) Realizar Cobranca");
            System.out.println("(8) Sair");
            System.out.print("Escolha uma opcao: ");
            
            opcao = scanner.nextInt();

            switch (opcao) 
            {
                case 1:
                    System.out.println("Informacoes sobre o Estacionamento:");
                    break;
                case 2:
                    System.out.println("Cadastrar novo cliente:");
                    break;
                case 3:
                    System.out.println("Cadastrar novo veiculo:");
                    break;
                case 4:
                    System.out.println("Cadastrar nova vaga:");
                    break;
                case 5:
                    System.out.println("Reservar uma vaga:");
                    break;
                case 6:
                    System.out.println("Liberar uma vaga:");
                    break;
                case 7:
                    System.out.println("Realizar cobranca:");
                    break;
                case 8:
                    System.out.println("Saindo do sistema:");
                    break;
                default:
                    System.out.println("Opcao invalida! Tente novamente.");
            }

            System.out.println();

        }
          while (opcao != 8);

        scanner.close();
    }
}
