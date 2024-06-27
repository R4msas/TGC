
/**
 * lista de adjacência dinâmica, usa-se esta na construção do grafo somente.
 */
public class ListaAdjacencia {
    int V;
    int E;
    Vertice[] vertices;
    final int infinito = 100000;//estratégia de usar como infinito um número inteiro grande o suficiente.

    /**
     * único construtor desta classe, E inicializado em zero, conforme forem
     * adicionadas arestas, E será atualizado.
     * 
     * @param E
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
     * Ao inserir a aresta, verifica se esta já existe, caso exista, insere a menor. Ao
     * inserir aumenta em um o número de n. Por ser um grafo não direcionado, ao
     * adicionar o adjacente w na lista de v, também adiciona v na lista de w.
     * 
     * @param v
     * @param w
     * @return
     * @throws Exception
     */
    public void inserirAresta(int v, int w, int peso) throws Exception {
        if (vertices[v].adjacentes.contains(w)) {
            //System.out.println("tem um erro neste dataset, a aresta ("+v+", "+w+") já existe com custo "+retornaPeso(v,w)+". E foi solicitada a inserção da aresta aresta ("+v+", "+w+") com custo "+peso+"ignoramos a segunda inserção");
            
            if(peso<vertices[v].pesos.get(vertices[v].adjacentes.indexOf(w)))
            {
                vertices[v].pesos.set(vertices[v].adjacentes.indexOf(w),(peso));
                vertices[w].pesos.set(vertices[w].adjacentes.indexOf(v),(peso));


            }
        } else {
            inserir(v, w, peso);
            inserir(w,v,peso);
            E+=2;
        }
    }

    public void inserir(int v, int w, int peso) {
        vertices[v].adjacentes.add(w);
        vertices[v].pesos.add(peso);
    }

    public int retornaPeso(int v, int w) {
        int resp = infinito;
        
        try {
            int indice=vertices[v].adjacentes.indexOf(w);
            if (indice!=-1) {
                
                resp = vertices[v].pesos.get(indice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resp;
    }
}
