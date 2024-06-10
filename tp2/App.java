import java.io.File;
import java.util.Scanner;

public class App {
    static int k;
    static String prefixo="arquivoBase/pmed";
    static String sufixo=".txt";

    public static void main(String[] args) {
        
    }



    public ForwardStar lerDoArquivo(String nomeArquivo) throws Exception
    {
        Scanner sc=new Scanner(new File(nomeArquivo));
        int V=sc.nextInt();
        int E=sc.nextInt();
        k=sc.nextInt();
        ListaAdjacencia la=new ListaAdjacencia(V);
        int v,w,peso;
        while(E>0)
        {
            v=sc.nextInt();
            w=sc.nextInt();
            peso=sc.nextInt();
            la.inserirAresta(v, w,peso);
            E--;
        }
        sc.close();
        return new ForwardStar(la);
    }
    
}
