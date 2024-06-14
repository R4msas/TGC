import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ClusteringKCenters {
    private FloydWarshall floydWarshallMatrix;
    private ListaAdjacencia la;

    public ClusteringKCenters(FloydWarshall floydWarshall, ListaAdjacencia listaAdjacencia) {
        this.floydWarshallMatrix = floydWarshall;
        this.la = listaAdjacencia;
    }

    public void setFloydWarshallMatrix(FloydWarshall floydWarshallMatrix) {
        this.floydWarshallMatrix = floydWarshallMatrix;
    }

    public int findExactRadius(int k) {
        int[] radius = new int[1];
        radius[0] = ListaAdjacencia.INFINITO;

        backTrackingExactRadius(k, 0, radius);

        return radius[0];
    }

    private void backTrackingExactRadius(int k, int i, int[] radius) {
        if (i > floydWarshallMatrix.caminho.length - k) return;

        for (int x = i; x < floydWarshallMatrix.caminho.length - k; x += k) {
           backTrackingExactRadius(k, i + 1, radius);
           int[] kCenters = new int[k];

           for (int y = 0; y < k; y++) {
               kCenters[y] = x + y;
           }

           int majorDistance = majorDistanceBetweenKCenters(kCenters);
           if (majorDistance < radius[0]) {
               radius[0] = majorDistance;
           }
        }

    }

    private int majorDistanceBetweenKCenters(int[] kCenters) {
        int[][] distanceMatrix = floydWarshallMatrix.distancia;
        int majorDistance = 0;

        for (int[] row : distanceMatrix) {
            int minorBetweenCols = ListaAdjacencia.INFINITO;

            for (int center : kCenters) {
                if (row[center] < minorBetweenCols) minorBetweenCols = row[center];
            }

            if (minorBetweenCols > majorDistance) majorDistance = minorBetweenCols;
        }

        return majorDistance;
    }

    // Gon method stuff below
    public int gonMethod(int V) {
        int[] centros = new int[la.k + 1];
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

    public int contaNumeroDeInfinitos() {
        int resp = 0;
        for (int i = 1; i <= la.V; i++) {
            for (int j = 1; j <= la.V; j++) {
                if (floydWarshallMatrix.distancia[i][j] >= ListaAdjacencia.INFINITO) {
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
                int menorValorLinha = ListaAdjacencia.INFINITO;
                for (int i = 1; i <= fim; i++) {
                    col = centros[i];
                    if (floydWarshallMatrix.distancia[linha][col] < menorValorLinha) {
                        menorValorLinha = floydWarshallMatrix.distancia[linha][col];
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
        if (fim < la.k) {
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
            w = floydWarshallMatrix.caminho[verticeCentro][w];
            array.add(w);
        }
        return array;
    }

    private int verificaSeECentro(int[] centros, int verticeCentro, int w) {
        final int vertice = w;
        if (Arrays.stream(centros).anyMatch(value -> (value == vertice))) {
            w = floydWarshallMatrix.caminho[verticeCentro][w];
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
