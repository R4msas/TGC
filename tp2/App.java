import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class App {
    int k;
    static String prefixo = "arquivoBase/pmed";
    static String sufixo = ".txt";
    FloydWarshall fw;
    ListaAdjacencia la;

    public static void main(String[] args) throws Exception {
        App app = new App(prefixo + "1" + sufixo);
        app.fw = new FloydWarshall(app.la);
        app.gon(app.la.V);

    }

    public App(String nomeArquivo) throws Exception {
        Scanner sc = new Scanner(new File(nomeArquivo));
        int V = sc.nextInt();
        int E = sc.nextInt();
        k = sc.nextInt();
        la = new ListaAdjacencia(V);
        int v, w, peso;
        while (E > 0) {
            v = sc.nextInt();
            w = sc.nextInt();
            peso = sc.nextInt();
            la.inserirAresta(v, w, peso);
            E--;
        }
        sc.close();
    }

    public int gon(int V) {
        int[] centros = new int[k + 1];
        int[] pertenceAoCentro = new int[V + 1];
        Random rd = new Random();
        int c = rd.nextInt(V);
        c++;
        // int inf=contaNumeroDeInfinitos();
        // System.out.println("número de infinitos "+inf);
        centros[1] = c;
        int maiorRaio = encontraMaiorDistancia(pertenceAoCentro, centros, 1);
        System.out.println(maiorRaio);
        // int centroAjustado=ajustaCentro(pertenceAoCentro, centros);
        return maiorRaio;

    }

    // private int encontraMaiorDistancia(int[] pertenceAoCentro, int[] centros, int
    // iteradorCentro) {
    // int maiorRaio=0;
    // for(int i=1;i<=la.V;i++)
    // {

    // for(int j=1;j<=iteradorCentro;j++)
    // {
    // if(fw.distancia[i][centros[j]]>maiorRaio)
    // {
    // maiorRaio=fw.distancia[i][centros[j]];
    // }
    // }

    // }
    // if(iteradorCentro<=k)
    // {

    // }
    // }
/**
 * método para debug, somente
 * @return
 */
    public int contaNumeroDeInfinitos() {
        int resp = 0;
        for (int i = 1; i <= la.V; i++) {
            for (int j = 1; j <= la.V; j++) {
                if (fw.distancia[i][j] >= la.infinito) {
                    resp++;
                }
            }
        }
        return resp;
    }
    /**
     * A partir da matriz de Floyd-Warshall gera um novo centro de modo que diminua a maior distância
     * @param pertenceAoCentro vetor de inteiros que indica qual centro pertence o vértice.
     * @param centros vetor com os centros
     * @param iteradorCentro posicao do último centro preenchido
     * @return maior raio
     */
    private int encontraMaiorDistancia(int[] pertenceAoCentro, int[] centros, int iteradorCentro) {
        int maiorRaio = 0;
        int verticeComMaiorRaio = 0;
        int col;
        int vertice = 0;
        try {
            for (int linha = 1; linha <= la.V; linha++) {
                int menorValorLinha = la.infinito;
                for (int i = 1; i <= iteradorCentro; i++) {
                    col = centros[i];
                    if (fw.distancia[linha][col] < menorValorLinha) {
                        menorValorLinha = fw.distancia[linha][col];
                        vertice = linha;
                        pertenceAoCentro[linha] = col;
                    }

                }
                if (menorValorLinha > maiorRaio) {
                    maiorRaio = menorValorLinha;
                    verticeComMaiorRaio = vertice;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int verticeComdistanciaDoisTercos;
        try {
            verticeComdistanciaDoisTercos = encontraVerticeComDistanciaX(centros[iteradorCentro], verticeComMaiorRaio,
                    centros);
        } catch (Exception e) {
            verticeComdistanciaDoisTercos = verticeComMaiorRaio;
            System.out.println("HELP");

        }
        iteradorCentro++;
        centros[iteradorCentro] = verticeComdistanciaDoisTercos;
        if (iteradorCentro < this.k) {
            maiorRaio = encontraMaiorDistancia(pertenceAoCentro, centros, iteradorCentro);
        }
        else{
            maiorRaio=recalculaCentro(centros,pertenceAoCentro,iteradorCentro);
        }
        return maiorRaio;
    }
    //este método está bugado, preciso implementá-lo
    // private int ajustaCentro(int[] pertenceAoCentro, int[] centros) {
    //     int maiorRaio = 0;
    //     int verticeComMaiorRaio = 0;
    //     int col;
    //     int vertice = 0;
    //     try {
    //         for (int linha = 1; linha <= la.V; linha++) {
    //             int menorValorLinha = la.infinito;
    //             for (int i = 1; i <= k; i++) {
    //                 col = centros[i];
    //                 if (fw.distancia[linha][col] < menorValorLinha) {
    //                     menorValorLinha = fw.distancia[linha][col];
    //                     vertice = linha;
    //                     pertenceAoCentro[linha] = col;
    //                 }

    //             }
    //             if (menorValorLinha > maiorRaio) {
    //                 maiorRaio = menorValorLinha;
    //                 verticeComMaiorRaio = vertice;
    //             }

    //         }

    //     }
    //     catch(Exception e){

    //     }
        
    //     return maiorRaio;
    // }
    /**
     * verifica qual o maior raio nesta solução.
    *  @param pertenceAoCentro vetor de inteiros que indica qual centro pertence o vértice.
     * @param centros vetor com os centros
     * @param iteradorCentro posicao do último centro preenchido
     * @return
     */
    private int recalculaCentro(int[] centros,int[]pertenceAoCentro, int iteradorCentro) {
        int maiorRaio = 0;
        int col;
        try {
            for (int linha = 1; linha <= la.V; linha++) {
                int menorValorLinha = la.infinito;
                for (int i = 1; i <= iteradorCentro; i++) {
                    col = centros[i];
                    if (fw.distancia[linha][col] < menorValorLinha) {
                        menorValorLinha = fw.distancia[linha][col];
                        pertenceAoCentro[linha] = col;
                    }

                }
                if (menorValorLinha > maiorRaio) {
                    maiorRaio = menorValorLinha;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maiorRaio;

    }
    /**
     * Encontra um vértice que fique no meio da distância em vértices do centro ao vértice mais distante.
     * @param verticeCentro centro ao qual o vértice com maior distância está associado
     * @param verticeChegada vértice com maior distância
     * @param centros //lista de centros até o momento
     * @return
     * @throws Exception
     */
    private int encontraVerticeComDistanciaX(int verticeCentro, int verticeChegada, int[] centros)
            throws Exception {
        int resp = 0;
        ArrayList<Integer> array = encontraCaminho(verticeCentro, verticeChegada);
        resp = verificaSeECentro(centros, array);
        return resp;

    }
    /**
     * 
     * @param verticeCentro vértice do centro
     * @param verticeChegada vértice com maior distância ao centro
     * @return
     */
    private ArrayList<Integer> encontraCaminho(int verticeCentro, int verticeChegada) {
        int w = verticeChegada;
        ArrayList<Integer> array = new ArrayList<>();
        array.add(w);
        while (w != verticeCentro) {
            w = fw.caminho[verticeCentro][w];
            array.add(w);
        }
        return array;
    }

    // private int verificaSeECentro(int[] centros, int verticeCentro, int w) {
    //     final int vertice = w;
    //     if (Arrays.stream(centros).anyMatch(value -> (value == vertice))) {
    //         w = fw.caminho[verticeCentro][w];
    //         w = verificaSeECentro(centros, verticeCentro, w);
    //     }
    //     return w;
    // }
    /**
     * Encontra todo o caminho entre o centro e o vértice e retorna a metade da distância em vértices
     * @param centros lista de centros
     * @param array array com o caminho do centro até o vértice mais distante
     * @return
     */
    private int verificaSeECentro(int[] centros, ArrayList<Integer> array) {
        int pos=array.size()/2;
        int w = array.get(pos);
        final int vertice = w;
        if (Arrays.stream(centros).anyMatch(value -> (value == vertice))) {
            array.remove(pos);
            w = verificaSeECentro(centros, array);
        }
        return w;
    }

}
