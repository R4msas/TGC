
public class ForwardStar {
    int V;
    int E;
    int[] saida;
    int[] destino;
    int[] pesos;

    public ForwardStar(ListaAdjacencia la) {
        V = la.V;
        E = la.E;
        int[] saida = new int[V + 1];
        int[] destino = new int[E];
        int[] pesos = new int[E];
        saida[0] = 1;
        int posVertice=1;
        int posAresta=0;
        for (int i = 1; i <= V; i++) {
            posVertice=posVertice+la.vertices[i].adjacentes.size();
            saida[i]=posVertice;
            for (int j = 0; j < la.vertices[i].adjacentes.size(); j++) {
                destino[posAresta]=la.vertices[i].adjacentes.get(j);
                pesos[posAresta]=la.vertices[i].pesos.get(j);
                posAresta++;
            }
        }

    }

    public int[] listaSucessores(int v) {
        int sucessores = saida[v] - saida[v - 1];
        int[] resp = new int[sucessores];
        int inicio = saida[v - 1] - 1;
        int c = 0;
        while (sucessores > 0) {
            resp[c] = destino[inicio];
            inicio++;
            c++;
            sucessores--;

        }
        return resp;
    }

    public int[] listaPesos(int v) {
        int sucessores = saida[v] - saida[v - 1];
        int[] resp = new int[sucessores];
        int inicio = saida[v - 1] - 1;
        int c = 0;
        while (sucessores > 0) {
            resp[c] = pesos[inicio];
            inicio++;
            c++;
            sucessores--;

        }
        return resp;
    }
}