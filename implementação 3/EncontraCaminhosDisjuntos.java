import java.io.File;
// import java.util.Scanner;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;

public class EncontraCaminhosDisjuntos {
    public static void main(String[] args) throws Exception {
        // geraOsGrafosAleatorios();
        // Scanner sc=new Scanner(System.in);
        // String nomeArq=sc.nextLine();
        // Scanner arq=new Scanner(nomeArq);
        // int s=arq.nextInt();
        // int t=arq.nextInt();
        // Scanner sc=new Scanner(System.in);
        // String nomeArq=sc.nextLine();
        // Scanner arq=new Scanner(nomeArq);
        // int s=arq.nextInt();
        // int t=arq.nextInt();
        int s = 1;
        int t = 100;
        calculaFluxoParaCadaArquivo(s, t);
        criaRedeDeFluxoAPartirDoArquivo();

    }

    public static void calculaFluxoParaCadaArquivo(int s, int t) throws Exception {
        for (int i = 1; i <= 2; i++) {
            System.out.println("Arquivo a" + i);
            calculaFluxo(new ForwardStar(new File("a" + i)), s, t, "a" + i);
            System.out.println("Arquivo b" + i);
            calculaFluxo(new ForwardStar(new File("b" + i)), s, t, "b" + i);
            System.out.println("Arquivo c" + i);
            calculaFluxo(new ForwardStar(new File("c" + i)), s, t, "c" + i);
            System.out.println("Arquivo d" + i);
            calculaFluxo(new ForwardStar(new File("d" + i)), s, t, "d" + i);

        }
    }

    public static void calculaFluxo(ForwardStar fs, int s, int t, String nomeArq) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(nomeArq + ".csv", true));
        writer.println("iteracao de s "+s+" para t "+t+" ,tempo(ms)");
        writer.close();
        for (int c = 1; c <= 10; c++) {
            Instant inicio = Instant.now();
            FlowNetwork G = new FlowNetwork(fs);
            @SuppressWarnings("unused") // ao executar o construtor, calcula-se o fluxo máximo e altera o G
            FordFulkerson ff = new FordFulkerson(G, s, t);
            List<List<Integer>> caminhos = G.listaCaminhosDisjuntos(s, t);
            Instant fim = Instant.now();
            registraTempo(nomeArq, Duration.between(inicio, fim).toMillis(),c);
            imprimeCaminhos(caminhos);
        }
       

    }

    public static void registraTempo(String nomeArq, long tempo, int iteracao) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(nomeArq + ".csv", true));
        writer.println(iteracao+","+tempo);
        writer.close();
    }

    private static void imprimeCaminhos(List<List<Integer>> caminhos) {
        for (int i = 0; i < caminhos.size(); i++) {
            System.out.println("Caminho número " + (i + 1) + ":");
            for (int j = caminhos.get(i).size() - 1; j >= 0; j--) {
                System.out.print(" " + caminhos.get(i).get(j));
            }
            System.out.println("");
        }
    }

    public static void criaRedeDeFluxoAPartirDoArquivo() {

    }

    public static void geraOsGrafosAleatorios() throws Exception {
        CriarGrafoDirecionado.criar();
    }

}
