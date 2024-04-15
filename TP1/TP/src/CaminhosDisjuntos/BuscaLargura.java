import java.util.LinkedList;
import java.util.Queue;

public class BuscaLargura {
    int[] l;
    int[] nivel;
    int[] pai;
    int t;
    Queue<Integer> fila;

    BuscaLargura(int m){
    l=new int[m];
    nivel=new int[m];
    pai=new int [m];
    t=0;
    fila=new LinkedList<>(); 
    }
    public LinkedList<Integer> raizesBusca(ListaAdjacencia la)
    {
        LinkedList<Integer> resp=new LinkedList<>();
        int pos=0;
        while(pos<l.length)
            {
                
                if(l[pos]==0)
                {
                t++;
                fila.add(pos+1);
                resp.add(pos+1);
                this.busca(la);
            }
            pos++;

        }


        return resp;
    }
    private void busca(ListaAdjacencia la)
    {
        while(fila.isEmpty()==false)
        {
            int v=fila.poll();
            for(int c=0;c<la.vertices[v-1].adjacentes.size();c++)
            {
                int w=la.vertices[v-1].adjacentes.get(c);
                if(l[w-1]==0)
                {
                    t++;
                    l[w-1]=t;
                    pai[w-1]=v;
                    nivel[w-1]=nivel[v-1]+1;
                    fila.add(w);
                }
            }
        }
    }
}
