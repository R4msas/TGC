import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * lista de adjacência dinâmica, usa-se esta na construção do grafo somente.
 */
public class ListaAdjacencia {
    int V;
    int E;
    int k;
    Vertice[] vertices;
    public final static int INFINITO = 100000;//estratégia de usar como infinito um número inteiro grande o suficiente.

    /**
     * único construtor desta classe, E inicializado em zero, conforme forem
     * adicionadas arestas, E será atualizado.
     * 
     * @param V
     */
    ListaAdjacencia(int V) {
        this.V = V;
        E = 0;
        vertices = new Vertice[V + 1];
        for (int c = 1; c <= V; c++) {
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
     * @throws Exception
     */
    public void inserirAresta(int v, int w, int peso) throws Exception {
        if (!vertices[v].adjacentes.contains(w)) {
            inserir(v, w, peso);
            inserir(w, v, peso);
            E += 2;
        }
        else {
            int indexWeightV = vertices[v].adjacentes.indexOf(w),
                currentWeightV = vertices[v].pesos.get(indexWeightV);

            if (peso < currentWeightV) {
                System.out.printf("[+] Updated {%d, %d}\n", v, w);
                vertices[v].pesos.set(indexWeightV, peso);

                int indexWeightW = vertices[w].adjacentes.indexOf(v);
                vertices[w].pesos.set(indexWeightW, peso);
            }
        }
    }

    public void inserir(int v, int w, int peso) {
        vertices[v].adjacentes.add(w);
        vertices[v].pesos.add(peso);
    }

    public int retornaPeso(int v, int w) {
        int resp = INFINITO;
        
        try {
            int indice = vertices[v].adjacentes.indexOf(w);
            if (indice != -1) {
                
                resp = vertices[v].pesos.get(indice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resp;
    }

    public static ListaAdjacencia returnAdjacencyListFromFile(String nomeArquivo) throws Exception {
        Scanner sc = new Scanner(new File(nomeArquivo));

        int V = sc.nextInt(),
            E = sc.nextInt(),
            k = sc.nextInt();

        ListaAdjacencia la = new ListaAdjacencia(V);
        la.k = k;

        int v, w, peso;
        while (sc.hasNext()) {
            v = sc.nextInt();
            w = sc.nextInt();
            peso = sc.nextInt();
            la.inserirAresta(v, w, peso);
        }
        sc.close();

        return la;
    }
}
