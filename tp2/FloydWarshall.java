public class FloydWarshall {
    int[][] distancia;
    int[][] caminho;

    /**
     * Este construtor recebe a lista de adjacência e calcula as distâncias e
     * caminhos. Para evitar o uso de um [v -1] toda vez que fosse manusear a
     * matriz, foi inicializada a matriz com uma coluna e linha não utilizadas, não acesse a posição zero da linha ou coluna.
     * 
     * @param la
     */
    public FloydWarshall(ListaAdjacencia la) {
        distancia = new int[la.V + 1][la.V + 1];
        caminho = new int[la.V + 1][la.V + 1];
        inicializacao(la);
        procuraCaminhos(la);

    }
/**
 * Inicialização de Floyd-Warwall a partir da Lista de Adjacência
 * @param la
 */
    private void inicializacao(ListaAdjacencia la) {
        for (int i = 1; i <= la.V; i++) {
            for (int j = 1; j <= la.V; j++) {
                distancia[i][j] = la.retornaPeso(i, j);
                if (distancia[i][j] < la.infinito) {
                    caminho[i][j] = i;
                } else {
                    caminho[i][j] = -1;
                }
            }
        }
        for(int i=1;i<=la.V;i++)
        {
            distancia[i][i]=0;
        }
    }

    private void procuraCaminhos(ListaAdjacencia la) {
        for (int i = 1; i <= la.V; i++) {
            for (int j = 1; j <= la.V; j++) {
                for (int k = 1; k <= la.V; k++) {
                    if (distancia[i][j] > distancia[i][k] + distancia[k][j]) {
                        distancia[i][j] = distancia[i][k] + distancia[k][j];
                        caminho[i][j] = k;
                    }
                }
            }
        }
    }
    public static void printMatrix(int[][] matrix) {
        System.out.print("....");
        for (int j = 1; j < matrix.length; j++) {
            System.out.printf("%4d", j);
            
        }
        System.out.println();

        for (int row = 1; row < matrix.length; row++) {
            System.out.printf("%4d", row);
            for (int col = 1; col < matrix[row].length; col++) {
                System.out.printf("%4d", matrix[row][col]);
            }
            System.out.println();
        }
    }
}
