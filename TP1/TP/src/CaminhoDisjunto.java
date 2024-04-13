import java.util.ArrayList;

public class CaminhoDisjunto {
    ForwardStar fs;
    int verticeOrigem;
    int verticeDestino;
    ArrayList<ArrayList<Integer>> caminhos;
    ArrayList<Integer> solucao;

    public CaminhoDisjunto(ForwardStar fs, int verticeOrigem, int verticeDestino) {
        this.fs = fs;
        this.verticeOrigem = verticeOrigem;
        this.verticeDestino = verticeDestino;
        caminhos = new ArrayList<ArrayList<Integer>>();
        solucao = new ArrayList<>();
    }

    public void procuraTodosOsCaminhosAciclicos() {

      /*   int[] sucessores = fs.listaSucessores(verticeOrigem);
        int grauSaida = sucessores.length;
        for (int c = 0; c < grauSaida; c++) {
            {
                
            }
        } */
        procuraTodosOsCaminhosAciclicos(verticeOrigem, new ArrayList<Integer>());
        if (caminhos.size() > 1) {

            iteraPelosCaminhosEncontrados();
        }
    }

    private void iteraPelosCaminhosEncontrados() {
        for (int c = 0; c < caminhos.size() - 1; c++) {
            for (int j = c + 1; j < caminhos.size(); j++) {
                verificaSeSaoCaminhosDisjuntos(caminhos.get(c), caminhos.get(j));
            }
        }

    }

    private void verificaSeSaoCaminhosDisjuntos(ArrayList<Integer> a, ArrayList<Integer> b) {
        for (int c = 0; c < a.size(); c++) {
            if (b.contains(a.get(c))) {
                return;
            }
        }
        solucao = new ArrayList<>(a);
        for (int c = 0; c < b.size(); c++) {
            solucao.add(b.removeLast());
        }

    }

    private void procuraTodosOsCaminhosAciclicos(int vertice, ArrayList<Integer> caminhoAnterior) {
        ArrayList<Integer> caminhoAtual = new ArrayList<Integer>(caminhoAnterior);
        caminhoAtual.add(vertice);
        if (vertice == verticeDestino) {
            caminhos.add(caminhoAnterior);

        } else if (caminhoAtual.contains(vertice) == false) {
            
            int[] sucessores = fs.listaSucessores(vertice);
            int grauSaida = sucessores.length;
            for (int c = 0; c < grauSaida; c++) {
                {
                    if (caminhoAtual.contains(vertice) == false && sucessores[c] != verticeOrigem) {
                        procuraTodosOsCaminhosAciclicos(sucessores[c], caminhoAtual);
                    }
                }
            }
        }

    }
}
