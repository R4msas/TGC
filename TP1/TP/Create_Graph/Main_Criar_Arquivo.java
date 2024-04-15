import java.util.LinkedList;
import java.util.Random;

//CLASSE PARA CRIAR GRAFO CONEXO
public class Main_Criar_Arquivo {
    public static void main(String[] args) throws Exception {
         criar(1,7,"Arquivo");

    }
    /*
     * @quantidadeDeArquivos -> numero de arquivos que deve ser criado (recomendado deixar 1)
     * @enesimaRaiz -> eleva o numero a essa Raiz
     * @nome -> nome do arquivo
     */
    public static void criar(int quantidadeDeArquivos,int enesimaRaiz,String nome) throws Exception {
        
          int a = 100, b = 1000, c = 10000, d = 100000;
          for (int i = 1; i <= quantidadeDeArquivos; i++) {
            criar(nome+'_'+i+"_100_g", a, enesimaRaiz);
            criar(nome+'_'+i+"_1000_g", b, enesimaRaiz);
            criar(nome+'_'+i+"_10000_g", c, enesimaRaiz);
            criar(nome+'_'+i+"_100000_g", d, enesimaRaiz);
          }
         
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
