import java.util.ArrayList;
import java.util.HashSet;

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

    public static void procuraCaminhosPeloGrafo(ForwardStar fs){
        ArrayList<ArrayList<Integer>>array=new ArrayList<>();
        for (int c = 1; c <= fs.m; c++) {
            for (int j = c+1; j <= fs.m; j++) {
                CaminhoDisjunto cd = new CaminhoDisjunto(fs, c, j);
                cd.procuraTodosOsCaminhosAciclicos();
                if (cd.solucao.size() != 0) {//&&!array.stream().anyMatch(solucao->solucao.containsAll(cd.solucao))
                    //System.out.println("Há caminho disjunto entre " + c + " e " + j);

                    if(cd.verificaSeExisteSolucao(array)==false)
                    {
                        array.add(cd.solucao);
                    }
                }
               /*  else{
                    System.out.println("não existe caminho disjunto "+ c+" e "+j);
                } */
            }
            imprimeComponentes(array);
        }
    }
     private boolean verificaSeExisteSolucao(ArrayList<ArrayList<Integer>>array)
    {  

        for(var a:array)
        {
            if(a.size()==solucao.size())
            {
                for(int c=0;c<a.size();c++)
                {
                    if(a.get(c)==solucao.get(c))
                    {
                        return true;
                        
                    }
                }
            }
            
        }
        return false; 
    }
    private static void imprimeComponentes(ArrayList<ArrayList<Integer>>array)
    {
        for(var h:array)
        {
            imprimeArray(h);
        }
    }
    private static void imprimeArray(ArrayList<Integer> array)
    {
        System.out.print("Componentes Biconexos:");
        for(var a:array)
        {
        System.out.print(" "+a+",");
        }
        System.out.println("");
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
                
                procuraTodosOsCaminhosAciclicos(sucessores[c], array);
                
          
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
        boolean resp =false;
        int []caminhoDisj=new int[fs.m+1];
        for (int c = 0; c < caminhos.size() - 1; c++) {
            for (int j = c + 1; j < caminhos.size(); j++) {
                if(verificaSeSaoCaminhosDisjuntos(caminhos.get(c), caminhos.get(j),caminhoDisj))
                {
                    resp=true;
                }
            }
        }
        if(resp==true)
        {
            criaSolucao(caminhoDisj);

        }
        return resp;
    }

    private void criaSolucao(int[] caminhoDisj){
        for(int c=1;c<caminhoDisj.length;c++)
        {
            
                if(caminhoDisj[c]!=0)
                {
                    solucao.add(c);
                }
            
            
        }
    }
    private boolean verificaSeSaoCaminhosDisjuntos(ArrayList<Integer> a, ArrayList<Integer> b, int []caminhoDisj) {
        for (int c = 1; c < a.size()-1; c++) {
            
            if (b.contains(a.get(c))) {
                return false;
            }
        }
       /*  solucao = new ArrayList<>(a);
        for (int c = 1; c < b.size()-1; c++) {
            solucao.add(b.get(c));
        } */
        for (int c = 0; c < b.size(); c++) {
            caminhoDisj[b.get(c)]+=1;
        }
        for (int c = 0; c < a.size(); c++) {
            caminhoDisj[a.get(c)]+=1;
        }
        return true;


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
