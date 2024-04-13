import java.util.ArrayList;

public class CaminhoDisjunto {
    ForwardStar fs;
    int verticeOrigem;
    int verticeDestino;
    ArrayList<ArrayList<Integer>> caminhos;
    ArrayList<Integer> solucao;

    public CaminhoDisjunto(ForwardStar fs, int verticeOrigem, int verticeDestino) 
    {
        this.fs = fs;
        this.verticeOrigem = verticeOrigem;
        this.verticeDestino = verticeDestino;
        caminhos = new ArrayList<ArrayList<Integer>>();
        solucao = new ArrayList<>();
    }

    public void procuraTodosOsCaminhosAciclicos() {

        int[] sucessores = fs.listaSucessores(verticeOrigem);
        int grauSaida = sucessores.length;
        ArrayList<Integer>array=new ArrayList<Integer>();
        array.add(verticeOrigem);
        boolean temVizinho=false, possuiDoisOuMaisCaminhosDisjuntos=false;
        for (int c = 0; c < grauSaida; c++) {
            {
                temVizinho=ehVizinho(sucessores, verticeDestino);
                
                }
                if(sucessores[c]>verticeOrigem){
                procuraTodosOsCaminhosAciclicos(sucessores[c], array);
                }
          
            } 
        
        if (caminhos.size() > 1) {

            possuiDoisOuMaisCaminhosDisjuntos=iteraPelosCaminhosEncontrados();
        }
        if(temVizinho&&possuiDoisOuMaisCaminhosDisjuntos==false)
        {
            solucao.add(verticeOrigem);
            solucao.add(verticeDestino);
        }
    }
    public boolean ehVizinho(int [] sucessores, int destino){
        
        for(int c=0;c<sucessores.length;c++){
            if(sucessores[c]==destino)
            {
                return true;
            }    
        }
        return false;
    }

    private boolean iteraPelosCaminhosEncontrados() {
        boolean resp;
        int []caminhoDisj=new int[caminhos.size()];
        for (int c = 0; c < caminhos.size() - 1; c++) {
            for (int j = c + 1; j < caminhos.size(); j++) {
                verificaSeSaoCaminhosDisjuntos(caminhos.get(c), caminhos.get(j));
            }
        }
        return resp;
    }

    private void verificaSeSaoCaminhosDisjuntos(ArrayList<Integer> a, ArrayList<Integer> b) {
        for (int c = 1; c < a.size()-1; c++) {
            
            if (b.contains(a.get(c))) {
                return;
            }
        }
        solucao = new ArrayList<>(a);
        for (int c = 1; c < b.size()-1; c++) {
            solucao.add(b.get(c));
        }

    }

    private void procuraTodosOsCaminhosAciclicos(int vertice, ArrayList<Integer> caminhoAnterior) {
        ArrayList<Integer> caminhoAtual = new ArrayList<Integer>(caminhoAnterior);
        caminhoAtual.add(vertice);
        if (vertice == verticeDestino) {
           caminhos.add(caminhoAtual);

        } else if (caminhoAnterior.contains(vertice) == false) {
            
            int[] sucessores = fs.listaSucessores(vertice);
            int grauSaida = sucessores.length;
            for (int c = 0; c < grauSaida; c++) {
                {
                    if (caminhoAtual.contains(sucessores[c]) == false ) {
                        procuraTodosOsCaminhosAciclicos(sucessores[c], caminhoAtual);
                    }
                }
            }
        }

    }
}
