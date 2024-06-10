
/**
 * lista de adjacência dinâmica, usa-se esta na construção do grafo somente.
 */
public class ListaAdjacencia {
    int V;
    int E;
    Vertice[] vertices;

    /**
     * único construtor desta classe, E inicializado em zero, conforme forem
     * adicionadas arestas, E será atualizado.
     * 
     * @param E
     */
    ListaAdjacencia(int V) {
        this.V = V;
        E = 0;
        vertices = new Vertice[V+1];
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
    public void inserirAresta(int v, int w,int peso) throws Exception {
        if (vertices[v].adjacentes.contains(w)) {
            throw new Exception("vértice já existe");
        } else {
            inserir(v, w, peso);
            inserir(w, v, peso);
            E += 2;
        }
    }

    public void inserir(int v, int w, int peso) {
        vertices[v].adjacentes.add(w);
        vertices[v].pesos.add(peso);
    }     
    }
