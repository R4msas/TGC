import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class App {
    int k;
    static String prefixo = "arquivoBase/pmed";
    static String sufixo = ".txt";

    public static void main(String[] args) throws Exception {
        ListaAdjacencia listaAdjacencia = ListaAdjacencia.returnAdjacencyListFromFile(prefixo + "5" + sufixo);
        FloydWarshall floydWarshall = new FloydWarshall(listaAdjacencia);
        ClusteringKCenters clusteringKCenters = new ClusteringKCenters(floydWarshall, listaAdjacencia);

        // clusteringKCenters.gonMethod(listaAdjacencia.V);
        clusteringKCenters.findExactRadius(listaAdjacencia.k);

    }
}
