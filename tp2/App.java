import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

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
        Arrays.fill(pertenceAoCentro, 1);
        Random rd = new Random();
        int c = rd.nextInt(V);
        c++;
        centros[1] = c;
        int maiorRaio=encontraMaiorDistancia(pertenceAoCentro, centros, 1);
        System.out.println(maiorRaio);
        return maiorRaio;

    }

    // private int encontraMaiorDistancia(int[] pertenceAoCentro, int[] centros, int fim) {
    //     int maiorRaio=0;
    //     for(int i=1;i<=la.V;i++)
    //     {
                        
    //         for(int j=1;j<=fim;j++)
    //         {
    //             if(fw.distancia[i][centros[j]]>maiorRaio)
    //             {
    //                 maiorRaio=fw.distancia[i][centros[j]];
    //             }
    //         }
            
    //     }
    //     if(fim<=k)
    //     {

    //     }
    // }

    private int encontraMaiorDistancia(int[] pertenceAoCentro, int[] centros, int fim) {
        int maiorRaio=0;
        int vertice=0;
        
        try {
            for(int i=1;i<=la.V;i++)
            {
               int menorValorLinha=la.infinito;
               int indice=0;

                for(int k=1;k<=fim;k++)
                {
                    if(fw.distancia[i][centros[k]]<menorValorLinha)
                    {
                        menorValorLinha=fw.distancia[i][centros[k]];
                        indice=fw.caminho[i][k];
                    }
                }
                if(menorValorLinha>maiorRaio&&menorValorLinha<la.infinito)
                {
                    maiorRaio=menorValorLinha;
                    vertice=indice;
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fim++;
        centros[fim]=vertice;
        if(fim<this.k)
        {
            vertice=encontraMaiorDistancia(pertenceAoCentro, centros, fim);
        }
        return vertice;
        
    }

   
}
