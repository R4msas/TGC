public class Lista {
    No cabeca;
    int tamanho;
    public Lista(){
        cabeca=new No();
        tamanho=0;  
    }
    public Lista(Lista l)
    {
        cabeca=new No();
        No listaRecebida=l.cabeca.prox;
        while(listaRecebida!=null)
        {
            insereElemento(listaRecebida.elemento);
            listaRecebida=listaRecebida.prox;
        }
    }
    public boolean contemEsteNumero(int numero)
    {
        No aux=cabeca.prox;
        while(aux!=null)
        {
            if(aux.elemento==numero)
            {
                return true;
            }
            aux=aux.prox;
        }
        return false;
    }
    public void insereElemento (int e){
        No aux=new No(e);
        aux.prox=cabeca.prox;
        cabeca.prox=aux;
        tamanho++;
    }
    public int[] listaElementos()
    {
        int []resp=new int[tamanho];
        No aux=cabeca.prox;
        int posicao=0;
        while(aux!=null)
        {
            resp[posicao]=aux.elemento;
            posicao++;
            aux=aux.prox;
        }
        return resp;
    }
    public static boolean verificaSeDuasListasSaoDisjuntas(Lista a,Lista b , int[]vetor,int componentes[])
    {
        if(verificaSeDuasListasSaoDisjuntas(a, b, vetor))
        {
            for (int i = 0; i < componentes.length; i++) {
                if(vetor[i]>0)
                {
                    componentes[i]+=1;
                }
            }
            return true;
        }
        return false;

    }
    public static boolean verificaSeDuasListasSaoDisjuntas(Lista a, Lista b, int[]vetor)
    {

        No aux1=a.cabeca.prox;
        No aux2=b.cabeca.prox;
        while(aux1!=null||aux2!=null)
        {
            if(aux1!=null)
            {
                vetor[aux1.elemento]+=1;
                aux1=aux1.prox;
            }
            if(aux2!=null)
            {
                vetor[aux2.elemento]+=1;
                aux2=aux2.prox;
            }
        }
            int contador=0;
            for (int i = 1; i < vetor.length; i++) {
                if(vetor[i]>=2){
                    contador++;
                }
            }
            if(contador>=3)
            {
                return false;
            }
            return true;
            
    }
    public static boolean verificaSeDuasListasOrdenadasSaoIguais(Lista a, Lista b)throws Exception
    {
        if(a.tamanho==0||b.tamanho==0)
        {
            throw new Exception("Comparando com lista vazia");
        }
        if(a.tamanho!=b.tamanho)
        {
            return false;
        }
        
        No aux1=a.cabeca.prox;
        No aux2=b.cabeca.prox;
        while(aux1!=null)
        {
            if(aux1.elemento!=aux2.elemento)
            {
                return false;
            }
            aux1=aux1.prox;
            aux2=aux2.prox;
        }
        return true;
    }
}
