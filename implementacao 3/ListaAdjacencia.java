import java.io.File;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 * lista de adjacência dinâmica, usa-se esta na construção do grafo somente.
 */
public class ListaAdjacencia {
    int V;
    int E;
    Vertice[] vertices;

    /**
     * único construtor desta classe, n inicializado em zero, conforme for
     * adicionadas arestas, n será atualizado.
     * 
     * @param m
     */
    ListaAdjacencia(int V) {
        this.V = V;
        E = 0;
        vertices = new Vertice[V+1];
        for (int c = 1; c <= V; c++) {
            vertices[c] = new Vertice();
        }
    }
    ListaAdjacencia(String nomeArq,int V, int numArestas) throws Exception {
        this.V = V;
        Scanner sc=new Scanner(new File(nomeArq));
        E = 0;
        vertices = new Vertice[V+1];
        for (int c = 1; c <= V; c++) {
            vertices[c] = new Vertice();
        }
        int v;
        int w;
        while(numArestas>0)
        {
            v=sc.nextInt();
            w=sc.nextInt();
            inserirAresta(v, w);
            numArestas--;
        }
        sc.close();
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
        try{    
        if(vertices[v].verificaSeExiste(w)||vertices[w].verificaSeExiste(v)||v==w)//verifica se a aresta já existe/se ao inserir criaria uma aresta antiparalela/se a aresta criaria um ciclo unitário
            {
                return false;
            }
            inserir(v, w);
            return true;
           }catch(Exception e)
           {
                System.err.println("erro no acesso aos v e w" +v+" "+w);
                return false;
           }

    }
    

    public void inserir(int v, int w) {
        vertices[v].adjacentes.add(w);
        E ++;
    }


    /**
     * Escreve o arquivo primeiro com o valor de m, depois com o valor de n;
     * Escreverá V+1 valores, do primeiro vetor do Forward Star
     * Depois escreverá E valores para o vetor de chegada do Forward Star
     * 
     * @param nomeArquivo nome do arquivo que será salvo
     * @throws Exception
     */
    public void gerarArquivo(String nomeArquivo) throws Exception {
        
        File arq = new File(nomeArquivo);
        RandomAccessFile raf = new RandomAccessFile(arq, "rw");
        raf.writeInt(V);
        raf.writeInt(E);
        raf.writeInt(1);
        int anterior = 1;
        for (int c = 1; c <= V; c++) {
            anterior += vertices[c].adjacentes.size();
            raf.writeInt(anterior);
        }
        for (int c =1; c <= V; c++) {
            int i = vertices[c].adjacentes.size();

            for (int j = 0; j < i; j++) {
                raf.writeInt(vertices[c].adjacentes.get(j));
            }
        }
        raf.close();

        
      
    }

    // public boolean verificaSeVizinho(int v, int w) {
    //     return this.vertices[v].adjacentes.contains(w)||this.vertices[w].adjacentes.contains(v);
    // }
}
