import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class CriarGrafo {
    public static void main(String[] args) throws Exception {
            criar();
            File arq=new File("a1");
        // ForwardStar fs=new ForwardStar(arq);
        // Graph G=new Graph(fs);
        // Biconnected bi=new Biconnected(G);
        // bi.iterarGrafo(G);
        ForwardStar fs = new ForwardStar();
        CaminhoDisjunto.procuraCaminhosPeloGrafo(fs);
       
        

    }

    public static void criar() throws Exception {
        /*
         * int a = 100, b = 1000, c = 10000, d = 100000;
         * for (int i = 1; i <= 10; i++) {
         * criar("a" + i, a, 1);
         * criar("b" + i, b, 1.5);
         * criar("c" + i, c, 1.5);
         * criar("d" + i, d, 2);
         * }
         */
        criar("a" + 1, 100000, 20);

    }

    /**
     * Gera um grafo aleatório de acordo com os parâmetros abaixo. Gera um número
     * pseudoaleatório para gerar o número de adjacentes daquele vértice.
     * 
     * @param nomeArquivo nomo do arquivo a ser salvo no final do processo
     * @param m           número total de vértices no grafo
     * @param enesimaRaiz quando gerado aleatoriamente, os grafos tendiam a ficar
     *                    muito densos. Nos primeiros testes, os grafos de tamanho
     *                    10.000 tinham em torno de 5000 arestas cada. Assim, cada
     *                    número aleatório é submetido a uma raiz para diminuir o
     *                    número de arestas.
     * @throws Exception
     */
    private static void criar(String nomeArquivo, int m, double enesimaRaiz) throws Exception {
        Random rd = new Random();
        ListaAdjacencia la = new ListaAdjacencia(m);
        double nRoot = (double) 1 / enesimaRaiz;
        for (int c = 1; c <= m; c++) {
            int numArestas = rd.nextInt(m);
            numArestas += 1;
            double bound = Math.pow(numArestas, nRoot);
            numArestas = (int) bound;
            int aresta;
            while (numArestas > 0 && la.vertices[c - 1].adjacentes.size() < c) {
                aresta = rd.nextInt(m);
                aresta += 1;
                while (aresta == c) {
                    aresta = rd.nextInt(m);
                    aresta += 1;
                }

                la.inserirAresta(c, aresta);
                numArestas--;

            }
        }
        certificaConexao(la);

        la.gerarArquivo(nomeArquivo);
    }

    private static void certificaConexao(ListaAdjacencia la) {
        BuscaLargura bl = new BuscaLargura(la.m);
        LinkedList<Integer> raizes = bl.raizesBusca(la);
        int raiz = raizes.poll();

        while (raizes.size() >= 1) {
            la.inserir(raiz, raizes.poll());
        }

    }

}
