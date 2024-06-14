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
    // fim) {
    // int maiorRaio=0;
    // for(int i=1;i<=la.V;i++)
    // {

    // for(int j=1;j<=fim;j++)
    // {
    // if(fw.distancia[i][centros[j]]>maiorRaio)
    // {
    // maiorRaio=fw.distancia[i][centros[j]];
    // }
    // }

    // }
    // if(fim<=k)
    // {

    // }
    // }

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

    private int encontraMaiorDistancia(int[] pertenceAoCentro, int[] centros, int fim) {
        int maiorRaio = 0;
        int verticeComMaiorRaio = 0;
        int col;
        int vertice = 0;
        try {
            for (int linha = 1; linha <= la.V; linha++) {
                int menorValorLinha = la.infinito;
                for (int i = 1; i <= fim; i++) {
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
            verticeComdistanciaDoisTercos = encontraVerticeComDistanciaX(centros[fim], verticeComMaiorRaio,
                    maiorRaio / 2,
                    centros);
        } catch (Exception e) {
            verticeComdistanciaDoisTercos = verticeComMaiorRaio;
            System.out.println("HELP");

        }
        fim++;
        centros[fim] = verticeComdistanciaDoisTercos;
        if (fim < this.k) {
            maiorRaio = encontraMaiorDistancia(pertenceAoCentro, centros, fim);
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

    private int encontraVerticeComDistanciaX(int verticeCentro, int verticeChegada, int distanciaX, int[] centros)
            throws Exception {
        int resp = 0;
        ArrayList<Integer> array = encontraCaminho(verticeCentro, verticeChegada);
        resp = verificaSeECentro(centros, array);
        return resp;

    }

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

    private int verificaSeECentro(int[] centros, int verticeCentro, int w) {
        final int vertice = w;
        if (Arrays.stream(centros).anyMatch(value -> (value == vertice))) {
            w = fw.caminho[verticeCentro][w];
            w = verificaSeECentro(centros, verticeCentro, w);
        }
        return w;
    }

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
