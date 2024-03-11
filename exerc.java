import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
/**
 * Foi feita a escolha pelo forward star de sucessores, como é necessário a presença dos predecessores também, guarda-se mais um vetor de inteiros de tamanho m para tal fim. 
 */
public class exerc {
    public static int n;
    public static int m;
    public static int[] saida;
    public static int[] destino;
    public static int[] vertice;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Informe o nome do arquivo:");// presume-se que há um cabeçalho como nos arquivos teste, com
                                                         // os números de m e n
        String nomeArq = sc.nextLine();
        lerArquivo(nomeArq);
        ordena();
        geraForwardStar();
        consultas();
        sc.close();

    }

    /**
     * Leitura inicial do arquivo, presume-se que os dois primeiros valores serão o
     * número de vértices e de arestas, respectivamente.
     * 
     * @param nomeArq
     * @throws FileNotFoundException
     */
    public static void lerArquivo(String nomeArq) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(nomeArq));
        n = sc.nextInt();
        m = sc.nextInt();
        saida = new int[m];
        destino = new int[m];
        vertice = new int[n + 1];
        for (int c = 0; c < m; c++) {
            saida[c] = sc.nextInt();
            destino[c] = sc.nextInt();
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
        vertice[0] = 1;
        for (int c = 0; c < m; c++) {
            if (saida[c] != pos) {
                vertice[pos] = repeticao + posAnt;
                posAnt = vertice[pos];
                repeticao = 0;
                pos++;
                c--;

            } else {
                repeticao++;
            }
        }
        vertice[pos] = m + 1;
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
            while(saida[i]>saida[j]&&i>=0)
            {
                trocar(i,j);
                i--;
                j--;
            }
        }
    }
    public static void trocar(int i, int j) {
        int temp = saida[i];
        saida[i] = saida[j];
        saida[j] = temp;
        temp = destino[i];
        destino[i] = destino[j];
        destino[j] = temp;
    }

    /**
     * Efetua as consultas, como escolha do forward star para os sucessores, tem
     * O(1) para consulta de sucessores, O(m) para consulta de predecessores.
     */
    public static void consultas() {
        int v = 1;
        Scanner sc = new Scanner(System.in);
        while (v != 0) {

            System.out.println("Informe o vertice que deseja pesquisar ou 0 caso queira sair:");
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
        int sucessores = vertice[v] - vertice[v - 1];
        System.out.println("Grau de saída: " + sucessores);
        System.out.print("Lista de Sucessores: ");
        imprimeArray(vertice[v - 1] - 1, sucessores);
        System.out.println("Grau de entrada " + predecessores.size());
        System.out.print("Lista de predecessores: ");
        imprimeLinkedList(predecessores);
    }

    public static void imprimeArray(int inicio, int repeticoes) {
        while (repeticoes > 0) {
            System.out.print(destino[inicio] + ", ");
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
            if (destino[c] == v) {
                list.add(saida[c]);
            }
        }
        return list;
    }
}