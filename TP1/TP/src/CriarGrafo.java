import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class CriarGrafo {
    public static void main(String[] args) throws Exception {
        criar();
        /*
         * File arq=new File("c1");
         * ForwardStar fs=new ForwardStar(arq);
         * fs.consultas();
         */
    }

    public static void criar() throws Exception {
        int a = 100, b = 1000, c = 10000, d = 100000;
        for (int i = 1; i <= 10; i++) {
            criar("a" + i, a, 1);
            criar("b" + i, b, 1.5);
            criar("c" + i, c, 1.5);
            criar("d" + i, d, 2);
        }
        // criar("a"+1,100);

    }
/**
 * Gera um grafo aleatório de acordo com os parâmetros abaixo. Gera um número pseudoaleatório para gerar o número de adjacentes daquele vértice.
 * @param nomeArquivo nomo do arquivo a ser salvo no final do processo
 * @param m número total de vértices no grafo
 * @param enesimaRaiz quando gerado aleatoriamente, os grafos tendiam a ficar muito densos. Nos primeiros testes, os grafos de tamanho 10.000 tinham em torno de 5000 arestas cada. Assim, cada número aleatório é submetido a uma raiz para diminuir o número de arestas. 
 * @throws Exception
 */
    private static void criar(String nomeArquivo, int m, double enesimaRaiz) throws Exception {
        Random rd = new Random();
        ListaAdjacencia la = new ListaAdjacencia(m);
        double nRoot=(double)1/enesimaRaiz;
        for (int c = 1; c <= m; c++) {
            int numArestas=rd.nextInt(1,m+1);
            double bound=Math.pow(numArestas, nRoot);
            numArestas=(int)bound;
            int aresta;
            while (numArestas >= 0) {
                aresta = rd.nextInt(1, m + 1);
                while (aresta == c) {
                    aresta = rd.nextInt(1, m + 1);
                }

                la.inserirAresta(c, aresta);
                numArestas--;

            }
        }

        la.gerarArquivo(nomeArquivo);
    }

}

/**
 * lista de adjacência dinâmica, usa-se esta na construção do grafo somente.
 */
class ListaAdjacencia {
    int m;
    int n;
    Vertice[] vertices;
    /**
     * único construtor desta classe, n inicializado em zero
     * @param m
     */
    ListaAdjacencia(int m) {
        this.m = m;
        n = 0;
        vertices = new Vertice[m];
        for (int c = 0; c < m; c++) {
            vertices[c] = new Vertice();
        }
    }
/**
 * Ao inserir a aresta, verifica se esta já existe, caso exista, ignora. Ao inserir aumenta em um o número de n. Por ser um grafo não direcionado, ao adicionar o adjacente w na lista de v, também adiciona v na lista de w.
 * @param v 
 * @param w
 * @return
 */
    public boolean inserirAresta(int v, int w) {
        if (vertices[v - 1].adjacentes.contains(w)) {
            return false;
        } else {
            inserir(v, w);
            inserir(w, v);
            n++;
            return true;
        }
    }

    public void inserir(int v, int w) {
        vertices[v - 1].adjacentes.add(w);
    }

    /**Escreve o arquivo primeiro com o valor de m, depois com o valor de n; 
     * Escreverá m+1 valores, do primeiro vetor do Forward Star
     * Depois escreverá n valores para o vetor de chegada do Forward Star
     * @param nomeArquivo nome do arquivo que será salvo
     * @throws Exception
     */
    public void gerarArquivo(String nomeArquivo) throws Exception {
        File arq = new File(nomeArquivo);
        RandomAccessFile raf = new RandomAccessFile(arq, "rw");
        raf.writeInt(m);
        raf.writeInt(n);
        raf.writeInt(1);
        int anterior = 1;
        for (int c = 0; c < m; c++) {
            anterior += vertices[c].adjacentes.size();
            raf.writeInt(anterior);
        }
        for (int c = 0; c < m; c++) {
            int i = vertices[c].adjacentes.size();

            for (int j = 0; j < i; j++) {
                raf.writeInt(vertices[c].adjacentes.get(j));
            }
        }
        raf.close();

    }
}

class Vertice {
    ArrayList<Integer> adjacentes;

    Vertice() {
        adjacentes = new ArrayList<Integer>();
    }
}

class ForwardStar {
    int m;
    int n;
    int[] saida;
    int[] destino;
/**
 * Lê o arquivo e gera o Forward Star. 
 * @param arq
 * @throws Exception
 */
    ForwardStar(File arq) throws Exception {
        RandomAccessFile raf = new RandomAccessFile(arq, "r");
        m = raf.readInt();
        n = raf.readInt();
        saida = new int[m + 1];
        destino = new int[n];
        for (int c = 0; c < m + 1; c++) {
            saida[c] = raf.readInt();
        }
        for (int c = 0; c < n; c++) {
            destino[c] = raf.readInt();
        }
        raf.close();
    }
/**
 * Método para debug inicialmente, imprime os sucessores do vértice seleciano
 * @param v
 */
    public void busca(int v) {
        int sucessores = saida[v] - saida[v - 1];
        System.out.println("Grau de saída: " + sucessores);
        System.out.print("Lista de Sucessores: ");
        imprimeArray(saida[v - 1] - 1, sucessores);
    }

    public void imprimeArray(int inicio, int repeticoes) {
        while (repeticoes > 0) {
            System.out.print(destino[inicio] + ", ");
            repeticoes--;
            inicio++;
        }
        System.out.println("\n");
    }

    public void consultas() {
        int v = 1;
        Scanner sc = new Scanner(System.in);
        while (v != 0) {

            System.out.println("Informe o vertice que deseja pesquisar ou 0 caso queira sair:");
            v = Integer.parseInt(sc.nextLine());
            if (v != 0 && v > 0) {
                if (v < m) {
                    busca(v);
                } else {
                    System.out.println("Vértice não existe");
                }
            }
        }

        sc.close();
    }
}
