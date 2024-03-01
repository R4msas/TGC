import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class exerc {
    public static int n;
    public static int m;
    public static int[]saida;
    public static int[]destino;
    public static int[]vertice;
public static void main(String[] args) throws Exception{
    Scanner sc=new Scanner(System.in);
    System.out.println("Informe o nome do arquivo:");//presume-se que há um cabeçalho como nos arquivos teste, com os números de m e n
    //String nomeArq=sc.nextLine();
    String nomeArq="g1.txt";
    lerArquivo(nomeArq);
    //ordena();
    geraForwardStar();
    consultas();
    sc.close();

}
public static void lerArquivo(String nomeArq) throws FileNotFoundException
{
    Scanner sc=new Scanner(new File(nomeArq));
    n=sc.nextInt();
    m=sc.nextInt();
    saida=new int[m];
    destino=new int[m];
    vertice=new int[n+1];
    for(int c=0;c<m;c++)
    {
        saida[c]=sc.nextInt();
        destino[c]=sc.nextInt();
    }
    sc.close();
}
public static void geraForwardStar()
{
    int repeticao,posAnt,pos;
    posAnt=pos=1;
    repeticao=0;
    vertice[0]=1;
    for(int c=0;c<m;c++)
    {
        if(saida[c]!=pos)
        {
            vertice[pos]=repeticao+posAnt;
            posAnt=vertice[pos];
            repeticao=0;
            pos++;
            c--;
            
        }
        else{
            repeticao++;
        }
    } 
    vertice[pos]=m+1;
}

public static void ordena ()
{
    ordena(0,m-1);
}
public static void ordena(int baixo, int alto) {
    if (baixo < alto) {
        int[] indicesPivo = particionar( baixo, alto);
        ordena(baixo, indicesPivo[0] - 1);
        ordena( indicesPivo[1] + 1, alto);
    }
}

public static int[] particionar(int baixo, int alto) {
    int pivo = saida[alto];
    int i = baixo - 1;
    int j = baixo;

    while (j < alto) {
        if (saida[j] < pivo) {
            i++;
            trocar(i, j);
        } else if (saida[j] == pivo) {
            // Não faz nada se saida[j] == pivo
        }
        j++;
    }

    trocar(i + 1, alto);

    // Encontrar a faixa de índices com valores iguais ao pivo
    int inicioIguais = i + 1;
    int fimIguais = i + 1;

    for (int k = i; k >= baixo; k--) {
        if (saida[k] == pivo) {
            inicioIguais = k;
        } else {
            break;
        }
    }

    for (int k = i + 2; k <= alto; k++) {
        if (saida[k] == pivo) {
            fimIguais = k;
        } else {
            break;
        }
    }

    return new int[]{inicioIguais, fimIguais};
}

public static void trocar(int i, int j) {
    int temp = saida[i];
    saida[i] = saida[j];
    saida[j] = temp;
    temp = destino[i];
    destino[i] = destino[j];
    destino[j] = temp;
}


public static void consultas()
{
    int v;
    Scanner sc=new Scanner(System.in);
    do
    {
        System.out.println("Informe o vertice que deseja pesquisar ou 0 caso queira sair:");
        v=Integer.parseInt(sc.nextLine());
        if(v!=0)
        {
            busca(v);
        }
    }while(v!=0);

    sc.close();
}
public static void busca(int v)
{
    LinkedList<Integer> predecessores=buscaPredecessores(v);
    int sucessores=vertice[v]-vertice[v-1];
    System.out.println("Grau de saída: "+sucessores);
    System.out.print("Lista de Sucessores: ");
    imprimeArray(vertice[v-1]-1,sucessores);
    System.out.println("Grau de entrada "+predecessores.size());
    System.out.print("Lista de predecessores: ");
    imprimeLinkedList(predecessores);
}
public static void imprimeArray(int inicio, int repeticoes)
{
    while(repeticoes>0)
    {
        System.out.print(destino[inicio]+", ");
        repeticoes--;
        inicio++;
    }
    System.out.println("\n");
}
public static void imprimeLinkedList(LinkedList<Integer>list)
{
    while(list.size()>1)
    {
        System.out.print(list.poll()+", ");
    }
    System.out.println(list.poll());
}
public static LinkedList<Integer> buscaPredecessores(int v)
{
    LinkedList<Integer> list=new LinkedList<Integer>();
    for(int c=0;c<m;c++)
    {
        if(destino[c]==v)
        {
            list.add(saida[c]);
        }
    }
    return list;
}
}