import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
/**
 * Foi feita a escolha pelo forward star de sucessores, como é necessário a presença dos predecessores também, guarda-se mais um vetor de inteiros de tamanho m para tal fim. 
 */
public class Exercicio {
    public static int n;
    public static int m;
    public static int[] origem;
    public static int[] destino;


    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Informe o nome do arquivo:");// presume-se que há um cabeçalho como nos arquivos teste, com
                                                         // os números de m e n
        String nomeArq = sc.nextLine();
        lerArquivo(nomeArq);
        ordena();
        geraForwardStar();
        ordenaSucessores();
        Dfs dfs=new Dfs()
        sc.close();

    }

    /**
     * Leitura inicial do arquivo, presume-se que os dois primeiros valores serão o
     * número de vértices e de arestas, respectivamente.
     * 
     * @param nomeArq
     * @throws FileNotFoundException
     */
    public static ordenaSucessores()
    {
        for (int i = 0; i < origem.length; i++) {
            
        }
    }
    public static void lerArquivo(String nomeArq) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(nomeArq));
        n = sc.nextInt();
        m = sc.nextInt();
        origem = new int[m];
        vertex = new int[m];
        destino = new int[n + 1];
        for (int c = 0; c < m; c++) {
            origem[c] = sc.nextInt();
            vertex[c] = sc.nextInt();
        }
        sc.close();
    }

    /**
     * Gera Forward Star a partir do arquivo de saída.
     */
    public static void geraForwardStar() {
        int repeticao, posAnt, pos;
        posAnt = pos = 1;
        repeticao = 0;
        destino[0] = 1;
        for (int c = 0; c < m; c++) {
            if (origem[c] != pos) {
                destino[pos] = repeticao + posAnt;
                posAnt = destino[pos];
                repeticao = 0;
                pos++;
                c--;

            } else {
                repeticao++;
            }
        }
        destino[pos] = m + 1;
    }
    public int[] listaSucessores(int v)
    {
        int sucessores = origem[v] - origem[v - 1];
        int[]resp=new int[sucessores];
        int inicio=origem[v-1]-1;
        int c=0;
        while(sucessores>0)
        {
            resp[c]=destino[inicio];
            inicio++;
            c++;
            sucessores--;

        }
        return resp;
    }
    /**
     * Usa como ordenação um insertion sort
     */
    public static void ordena()
    {
        int j,i;
        for(int c=1;c<m-1;c++)
        {
            i=c;
            j=c+1;
            while(origem[i]>origem[j]&&i>=0)
            {
                trocar(i,j);
                i--;
                j--;
            }
        }
    }
    public static void trocar(int i, int j) {
        int temp = origem[i];
        origem[i] = origem[j];
        origem[j] = temp;
        temp = vertex[i];
        vertex[i] = vertex[j];
        vertex[j] = temp;
    }

    /**
     * Efetua as consultas, como escolha do forward star para os sucessores, tem
     * O(1) para consulta de sucessores, O(m) para consulta de predecessores.
     */
    public static void consultas() {
        int v = 1;
        Scanner sc = new Scanner(System.in);
        while (v != 0) {

            System.out.println("Informe o destino que deseja pesquisar ou 0 caso queira sair:");
            v = Integer.parseInt(sc.nextLine());
            if (v != 0 && v > 0) {
                if (v < m) {
                    busca(v);
                } else {
                    System.out.println("Vértice não existe");
                }
            }
        }

        sc.close();
    }

    public static void busca(int v) {
        LinkedList<Integer> predecessores = buscaPredecessores(v);
        int sucessores = destino[v] - destino[v - 1];
        System.out.println("Grau de saída: " + sucessores);
        System.out.print("Lista de Sucessores: ");
        imprimeArray(destino[v - 1] - 1, sucessores);
        System.out.println("Grau de entrada " + predecessores.size());
        System.out.print("Lista de predecessores: ");
        imprimeLinkedList(predecessores);
    }

    public static void imprimeArray(int inicio, int repeticoes) {
        while (repeticoes > 0) {
            System.out.print(vertex[inicio] + ", ");
            repeticoes--;
            inicio++;
        }
        System.out.println("\n");
    }

    public static void imprimeLinkedList(LinkedList<Integer> list) {
        while (list.size() > 1) {
            System.out.print(list.poll() + ", ");
        }
        System.out.println(list.poll());
    }

    public static LinkedList<Integer> buscaPredecessores(int v) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        for (int c = 0; c < m; c++) {
            if (vertex[c] == v) {
                list.add(origem[c]);
            }
        }
        return list;
    }
}