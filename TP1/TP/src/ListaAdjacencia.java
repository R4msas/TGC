import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * lista de adjacência dinâmica, usa-se esta na construção do grafo somente.
 */
public class ListaAdjacencia {
    int m;
    int n;
    Vertice[] vertices;

    /**
     * único construtor desta classe, n inicializado em zero, conforme for
     * adicionadas arestas, n será atualizado.
     * 
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
     * Ao inserir a aresta, verifica se esta já existe, caso exista, ignora. Ao
     * inserir aumenta em um o número de n. Por ser um grafo não direcionado, ao
     * adicionar o adjacente w na lista de v, também adiciona v na lista de w.
     * 
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
            n += 2;
            return true;
        }
    }

    public void inserir(int v, int w) {
        vertices[v - 1].adjacentes.add(w);
    }

    public void remover(int v, int w) {
        int index = vertices[v - 1].adjacentes.indexOf(w);
        vertices[v - 1].adjacentes.remove(index);
    }

    public ArrayList<ArrayList<Integer>> getDisjointPaths(int origin, int destiny) {
        ArrayList<ArrayList<Integer>> paths = new ArrayList<>();

        ListaAdjacencia temp = this.copy();
        int parents[] = new int[this.vertices.length];
        for (int i = 0; i < parents.length; i++) {
            parents[i] = -1;
        }

        while (temp.isReachable(origin, destiny, parents)) {
            ArrayList<Integer> path = new ArrayList<>();
            int v = destiny;

            while (v != origin) {
                int u = parents[v - 1];
                path.add(v);
                temp.remover(u, v);
                temp.inserir(v, u);
                v = parents[v - 1];
            }

            path.add(origin);
            paths.add(path);
        }

        return paths;
    }

    public boolean isReachable(int origin, int destiny, int[] parents) {
        boolean visited[] = new boolean[this.vertices.length];
        for (int i = 0; i < visited.length; i++) {
            visited[i] = false;
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(origin);
        visited[origin - 1] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int adjacent : vertices[u - 1].adjacentes) {
                if (!visited[adjacent - 1]) {
                    queue.add(adjacent);
                    visited[adjacent - 1] = true;
                    parents[adjacent - 1] = u;
                }
            }
        }

        return visited[destiny - 1];
    }

    public ListaAdjacencia copy() {
        ListaAdjacencia list = new ListaAdjacencia(this.vertices.length);
        for (int i = 0; i < vertices.length; i++) {
            for (int adjacent : vertices[i].adjacentes) {
                list.inserir(i + 1, adjacent);
            }
        }

        return list;
    }

    /**
     * Escreve o arquivo primeiro com o valor de m, depois com o valor de n;
     * Escreverá m+1 valores, do primeiro vetor do Forward Star
     * Depois escreverá n valores para o vetor de chegada do Forward Star
     * 
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

    public static void main(String[] args) {
        ListaAdjacencia listaAdjacencia = new ListaAdjacencia(12);
        listaAdjacencia.inserirAresta(6, 4);
        listaAdjacencia.inserirAresta(4, 7);
        listaAdjacencia.inserirAresta(7, 8);
        listaAdjacencia.inserirAresta(8, 9);
        listaAdjacencia.inserirAresta(9, 5);
        listaAdjacencia.inserirAresta(4, 3);
        listaAdjacencia.inserirAresta(3, 12);
        listaAdjacencia.inserirAresta(12, 11);
        listaAdjacencia.inserirAresta(11, 10);
        listaAdjacencia.inserirAresta(10, 2);
        listaAdjacencia.inserirAresta(2, 5);
        listaAdjacencia.inserirAresta(5, 1);
        listaAdjacencia.inserirAresta(2, 1);
        // listaAdjacencia.inserirAresta(3, 5);
        listaAdjacencia.inserirAresta(2, 7);

        ArrayList<ArrayList<Integer>> paths = listaAdjacencia.getDisjointPaths(4, 1);
        for (ArrayList<Integer> path : paths) {
            for (int vertice : path) {
                System.out.print(vertice + " ");
            }
            System.out.println();
        }

        System.out.println("inverso");
        paths = listaAdjacencia.getDisjointPaths(1, 4);
        for (ArrayList<Integer> path : paths) {
            for (int vertice : path) {
                System.out.print(vertice + " ");
            }
            System.out.println();
        }

    }
}
