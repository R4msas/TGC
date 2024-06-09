import java.util.Random;

public class CriarGrafoDirecionado {
    
    public static void criar() throws Exception {
        
          int a = 100, b = 1000, c = 10000, d = 50000;
          criar("a" + 1, a, 3);
          criar("b" + 1, b, 4);
          criar("c" + 1, c, 4);
          criar("d" + 1, d, 6);
         
          criar("a" + 2, a, 1);
          criar("b" + 2, b, 2);
          criar("c" + 2, c, 3);
          criar("d" + 2, d, 4);

    }

    /**
     * Gera um grafo aleatório direcionado de acordo com os parâmetros abaixo. Gera um número
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
    private static void criar(String nomeArquivo, int V, double enesimaRaiz) throws Exception {
        Random rd = new Random();
        ListaAdjacencia la = new ListaAdjacencia(V);
        double nRoot = (double) 1 / enesimaRaiz;
        try{
        for (int c = 1; c <= V; c++) {
            int numArestas = rd.nextInt(V);//pelo limite superior ser exclusivo, adiciona-se um
            numArestas += 1;
            double bound = Math.pow(numArestas, nRoot);
            numArestas = (int) bound;
            int aresta;
            while (numArestas > 0) {
                aresta = rd.nextInt(V);
                aresta += 1;
                boolean arestaFoiInserida=la.inserirAresta(c, aresta);
                while (arestaFoiInserida) {
                    aresta = rd.nextInt(V);
                    aresta++;
                    arestaFoiInserida=la.inserirAresta(c, aresta);
                    
                }
                numArestas--;

            }
            // System.out.println("iterando por "+c);
        }
    }
    catch(Exception e){
        e.printStackTrace();
    }
        // certificaConexao(la);

        la.gerarArquivo(nomeArquivo);
    }
}
