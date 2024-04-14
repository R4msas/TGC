import java.util.ArrayList;
import java.util.Arrays;

public class CaminhoDisjunto {
    ForwardStar fs;
    int verticeOrigem;
    int verticeDestino;
    ArrayList<Lista> caminhos;
    Lista solucao;

 
    public CaminhoDisjunto(ForwardStar fs, int verticeOrigem, int verticeDestino) 
    {
        this.fs = fs;
        this.verticeOrigem = verticeOrigem;
        this.verticeDestino = verticeDestino;
        caminhos = new ArrayList<Lista>();
        solucao = new Lista();
    }
    

    // public static void procuraCaminhosPeloGrafo(ForwardStar fs){
    //     ArrayList<ArrayList<Integer>>array=new ArrayList<>();
    //     for (int c = 1; c <= fs.m; c++) {
    //         for (int j = c+1; j <= fs.m; j++) {
    //             CaminhoDisjunto cd = new CaminhoDisjunto(fs, c, j);
    //             cd.procuraTodosOsCaminhosAciclicos();
    //             if (cd.solucao.size() != 0) {//&&!array.stream().anyMatch(solucao->solucao.containsAll(cd.solucao))
    //                 //System.out.println("Há caminho disjunto entre " + c + " e " + j);

    //                 if(cd.verificaSeExisteSolucao(array)==false)
    //                 {
    //                     array.add(cd.solucao);
    //                 }
    //             }
    //            /*  else{
    //                 System.out.println("não existe caminho disjunto "+ c+" e "+j);
    //             } */
    //         }
    //         imprimeComponentes(array);
    //     }
    // }
    public static void procuraCaminhosPeloGrafo(ForwardStar fs)throws Exception
    {
        ArrayList<Lista> array=new ArrayList<>();
        for (int c = 1; c <= fs.m; c++) {
            for (int j = c+1; j <= fs.m; j++) {
                CaminhoDisjunto cd = new CaminhoDisjunto(fs, c, j);
                cd.procuraTodosOsCaminhosAciclicos();
                if (cd.solucao.tamanho != 0) {//&&!array.stream().anyMatch(solucao->solucao.containsAll(cd.solucao))
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
    //  private boolean verificaSeExisteSolucao(ArrayList<ArrayList<Integer>>array)
    // {  

    //     for(var a:array)
    //     {
    //         if(a.size()==solucao.size())
    //         {
    //             for(int c=0;c<a.size();c++)
    //             {
    //                 if(a.get(c)==solucao.get(c))
    //                 {
    //                     return true;
                        
    //                 }
    //             }
    //         }
            
    //     }
    //     return false; 
    // }
    private boolean verificaSeExisteSolucao(ArrayList<Lista>array)throws Exception
{
        for(var a:array)
        {
            if(Lista.verificaSeDuasListasOrdenadasSaoIguais(a, solucao))
            {
                return true;
            }
            
        }
        return false; 
    }
    private static void imprimeComponentes(ArrayList<Lista>array)
    {
        for(var h:array)
        {
            imprimeArray(h);
        }
    }
    private static void imprimeArray(Lista lista)
    {
        System.out.print("Componentes Biconexos:");
        int[]vetor=lista.listaElementos();
        for (int i = 0; i < vetor.length; i++) {
            System.out.print(" "+vetor[i]+",");
        }
        System.out.println("");
    }
    public void procuraTodosOsCaminhosAciclicos() {

        int[] sucessores = fs.listaSucessores(verticeOrigem);
        int grauSaida = sucessores.length;
        Lista lista=new Lista();
        lista.insereElemento(verticeOrigem);
        System.out.println("procurando "+verticeOrigem+" "+verticeDestino);
        boolean temVizinho=false, possuiDoisOuMaisCaminhosDisjuntos=false;
        for (int c = 0; c < grauSaida; c++) {
            {
                temVizinho=ehVizinho(sucessores, verticeDestino);
                
                }
                procuraTodosOsCaminhosAciclicos(sucessores[c], lista);
                
          
            } 
        
        if (caminhos.size() > 1) {

            possuiDoisOuMaisCaminhosDisjuntos=iteraPelosCaminhosEncontrados();
        }
        if(temVizinho&&possuiDoisOuMaisCaminhosDisjuntos==false)
        {
            solucao.insereElemento(verticeOrigem);
            solucao.insereElemento(verticeDestino);
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
        int []componentes=new int[fs.m+1];
        
        for (int c = 0; c < caminhos.size() - 1; c++) {
            for (int j = c + 1; j < caminhos.size(); j++) {
               Arrays.fill( caminhoDisj,0);
                if(Lista.verificaSeDuasListasSaoDisjuntas(caminhos.get(c), caminhos.get(j),caminhoDisj, componentes))
                {
                    resp=true;
                }
                
            }
        }
        if(resp==true)
        {
            criaSolucao(componentes);

        }
        return resp;
    }

    private void criaSolucao(int[]componentes){
        for(int c=1;c<componentes.length;c++)
        {
            
                if(componentes[c]!=0)
                {
                    solucao.insereElemento(c);
                }
            
            
        }
    }
    // private boolean verificaSeSaoCaminhosDisjuntos(ArrayList<Integer> a, ArrayList<Integer> b, int []caminhoDisj) {
    //     for (int c = 1; c < a.size()-1; c++) {
            
    //         if (b.contains(a.get(c))) {
    //             return false;
    //         }
    //     }
    //    /*  solucao = new ArrayList<>(a);
    //     for (int c = 1; c < b.size()-1; c++) {
    //         solucao.add(b.get(c));
    //     } */
    //     for (int c = 0; c < b.size(); c++) {
    //         caminhoDisj[b.get(c)]+=1;
    //     }
    //     for (int c = 0; c < a.size(); c++) {
    //         caminhoDisj[a.get(c)]+=1;
    //     }
    //     return true;


    // }

    private void procuraTodosOsCaminhosAciclicos(int vertice, Lista caminhoAnterior) {
        Lista caminhoAtual = new Lista(caminhoAnterior);
        caminhoAtual.insereElemento(vertice);
        if (vertice == verticeDestino) {
           caminhos.add(caminhoAtual);

        } else if (caminhoAnterior.contemEsteNumero(vertice) == false) {
            
            int[] sucessores = fs.listaSucessores(vertice);
            int grauSaida = sucessores.length;
            for (int c = 0; c < grauSaida; c++) {
                {
                    if (caminhoAtual.contemEsteNumero(sucessores[c]) == false ) {
                        procuraTodosOsCaminhosAciclicos(sucessores[c], caminhoAtual);
                    }
                }
            }
        }
        
    }
 }
