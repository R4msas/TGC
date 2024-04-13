import java.util.ArrayList;

public class CaminhoDisjunto {
    ForwardStar fs;
    int verticeSaida;
    int verticeChegada;
    ArrayList<ArrayList<Integer>> caminhos;
    ArrayList<ArrayList<Integer>> solucao;

    public CaminhoDisjunto(ForwardStar fs, int verticeSaida, int verticeChegada) {
        this.fs = fs;
        this.verticeSaida = verticeSaida;
        this.verticeChegada = verticeChegada;
        caminhos = new ArrayList<ArrayList<Integer>>();
    }

    public ArrayList<Integer> procuraCaminhosDisjuntos() {
        int ponteiro = fs.saida[verticeSaida - 1];

        procuraCaminhosDisjuntos(ponteiro, new ArrayList<Integer>());
        if (caminhos.size() > 1) {
         /*    ArrayList <Integer> conjunto=new ArrayList<>(caminhos.get(1));
            caminhos.get(0).removeFirst();
            conjunto.removeLast();
            for(int c=0;c<caminhos.get(0).size();c++)
            {  
                conjunto.add(caminhos.get(0).get(c));
                
            }
            return conjunto; */

        }
        private ArrayList<Integer>
        return null;
    }

    private void procuraCaminhosDisjuntos(int vertice, ArrayList<Integer> caminhoAnterior) {
        ArrayList<Integer> caminhoAtual = new ArrayList<Integer>(caminhoAnterior);
        caminhoAtual.add(vertice);
        if (vertice == verticeChegada) {
            caminhos.add(caminhoAtual);

        } else if (caminhoAnterior.contains(vertice)) {
            caminhoAtual = null;
        } else {

            int grauSaida = fs.saida[verticeSaida] - fs.saida[verticeSaida - 1];
            int ponteiro = fs.saida[verticeSaida - 1];
            for (int c = 0; c < grauSaida; c++) {
                if (caminhoAnterior.contains(vertice) == false) {
                    procuraCaminhosDisjuntos(fs.destino[c + ponteiro], caminhoAtual);
                }
            }
        }

    }
}
