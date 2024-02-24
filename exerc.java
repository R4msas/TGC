import java.util.Scanner;

public class exerc {
    public static int n;
    public static int m;
    private static int[] saida;
    
public static void main(String[] args) {
    encontraNM();
    int []saida=new int[m];
    int []destino=new int[m];
    int []vertice=new int[n+1];
    lerArquivo(saida,destino);
    ordena(saida, destino);
    geraForwardStar(vertice, saida);
    consultas(vertice, destino);
}
public static void encontraNM()
{
    Scanner sc=new Scanner("arq.txt");
    n=sc.nextInt();
    m=sc.nextInt();
    sc.close();
}
public static void lerArquivo(int[]saida, int[] destino) 
{
    Scanner sc=new Scanner("arq.txt");
    for(int c=0;c<m;c++)
    {
        saida[c]=sc.nextInt();
        destino[c]=sc.nextInt();
    }
    sc.close();
}
public static void geraForwardStar(int[]vertice, int[] saida)
{
    int repeticao,posAnt,pos;
    posAnt=pos=1;
    repeticao=0;
    int m=saida.length;
    vertice[0]=1;
    for(int c=1;c<m;c++)
    {
        if(vertice[c]!=pos)
        {
            vertice[pos]=repeticao+posAnt;
            posAnt=vertice[pos];
            repeticao=0;
            
        }
        else{
            repeticao++;
        }
    } 
    vertice[m]=m+1;
}
public static void ordena(int[]saida, int[]destino)
{

}
public static void consultas(int[]vertice, int[]destino, Scanner sc)
{


}
}
