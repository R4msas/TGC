import java.util.Scanner;
import java.io.File;

public class DualMatrix {
    int[] A;
    int ofertas[];
    int demandas[];
    int m;// número de ofertas
    int n;// número de demandas
    int u[];
    int v[];
    int c[][];// matriz de custos
    Cell[] gamma;
    int D[][];
    int Q[];
    int P[];
    int[] Y;

    public static void main(String[] args) throws Exception {
        // Scanner sc=new Scanner(System.in);
        // String nomeArq=sc.nextLine();
        // sc.close();
        
        String[] vetor = { "balanceado3x3.txt", "balanceado4x4.txt", "desbalanceado3x2.txt", "desbalanceado4x3.txt" };
        for (String string : vetor) {
            DualMatrix dm = new DualMatrix();
            dm.leArquivo(string);
            dm.inicializa();
            boolean repete = true;
            while (repete == true) {
                repete = dm.determinaACelulaQueSai();
            }
            dm.imprimeResultado(string);
        }


    }


    private void imprimeResultado(String nomeArq) {
        System.out.println(nomeArq);
        for (int i = 1; i <= m + n; i++) {
            if (gamma[i].oferta > 0 && gamma[i].demanda > 0) {
                System.out.println(
                        "oferta: " + gamma[i].oferta + "\n demanda: " + gamma[i].demanda + "\nQuantidade Enviada: "
                                + Y[i]);
            }
        }
        System.out.println("____________________________");
    }

    public void leArquivo(String nomeArq) throws Exception {
        Scanner sc = new Scanner(new File(nomeArq));
        m = sc.nextInt();
        n = sc.nextInt();
        c = new int[m + 1][n + 1];
        ofertas = new int[m + 1];
        demandas = new int[n + 1];
        for (int i = 1; i <= m; i++) {
            ofertas[i] = sc.nextInt();
        }
        for (int j = 1; j <= n; j++) {
            demandas[j] = sc.nextInt();
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                c[i][j] = sc.nextInt();
            }
        }
        sc.close();
        // Matrix.printMatrix(c);
    }

    public void inicializa() throws Exception {
        A = new int[m + n + 1];
        // passo 0.1
        for (int i = 1; i <= n; i++) {
            A[i] = demandas[i];

        }
        for (int i = 1; i <= m; i++) {
            A[i + n] = -ofertas[i];

        }
        // passo 0.2
        u = new int[m + 1];// u já inicia com zero
        v = new int[n + 1];
        for (int j = 1; j <= n; j++) {
            int min = 2000000000;
            for (int i = 1; i <= m; i++) {
                if (c[i][j] < min) {
                    min = c[i][j];
                }
                v[j] = min;
            }
        }
        // passo 0.3
        gamma = new Cell[n + m + 1];
        for (int i = 1; i <= n; i++) {
            gamma[i] = new Cell(getPosicao(i, v[i]), i);

        }
        for (int i = 1; i <= m; i++) {
            gamma[n + i] = new Cell(i, 0);
        }

        // passo 0.4
        // determina onde vai ser um na subc n * n
        D = new int[n + m + 1][n + m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j) {
                    D[i][j] = 1;
                }
            }
        }
        //
        for (int i = 1; i <= n; i++) {
            D[i][n + gamma[i].oferta] = -1;
        }
        //
        for (int i = 1; i <= m; i++) {
            D[n + i][n + i] = -1;
        }
        // o valor igual a zero já está certo pois o java inicializa a c com 0

    }

    private int getPosicao(int coluna, int valorColuna) throws Exception {
        for (int i = 1; i <= m; i++) {
            if (c[i][coluna] == valorColuna) {
                return i;
            }
        }
        throw new Exception("não encontrou este valor na coluna!");
    }

    public boolean determinaACelulaQueSai() throws Exception {
        // passo 1.1
        // Matrix.printMatrix(D);
        // Matrix.printVector(A);
        Y = Matrix.multiply(A, D);
        // passo 1.2
        // Matrix.printVector(Y);
        int min = 100000000;
        int indice = 0;
        for (int k = 1; k <= m + n; k++) {
            if (Y[k] < min) {
                min = Y[k];
                indice = k;
            }
        }
        // passo 1.3
        if (min >= 0) {
            return false;
        } else {
            Cell st = determinaACelulaQueEntra(indice);
            update(st, indice);
            return true;
        }
    }

    private void update(Cell st, int k) {
        int s = st.oferta;
        int t = st.demanda;
        // passo 3.1
        // Matrix.printMatrix(D);
        // System.out.println("\n");
        int[][] Dchapeu = Matrix.copy(D);
        for (int l = 1; l <= n + m; l++) {
            Dchapeu[l][k] = -(D[l][k]);
        }
        // Matrix.printMatrix(D);
        // System.out.println("__________________________________");

        for (int r = 1; r <= n + m; r++) {
            for (int l = 1; l <= m + n; l++) {
                // Matrix.printMatrix(D);
                // System.out.println("__________________________________");

                if (r != k) {
                    // System.out.println("Dl,r=D["+l+"]"+"["+r+"]="+D[l][r]);
                    // System.out.println("Ds+n,r["+(s+n)+"]"+"["+r+"]="+D[s + n][r]);
                    // System.out.println("Dt,r["+t+"]"+"["+r+"]="+D[t][r]);
                    // System.out.println("Dchapeu l,k["+l+"]"+"["+k+"]="+Dchapeu[l][k]);
                    // System.out.println("__________________________________");
                    Dchapeu[l][r] = D[l][r] + (D[s + n][r] - D[t][r]) * Dchapeu[l][k];
                    // Matrix.printMatrix(Dchapeu);
                    // System.out.println("__________________________________");
                }

            }

        }
        // Matrix.printMatrix(Dchapeu);
        // passo 3.2
        gamma[k] = st;
        D = Dchapeu;
        // passo 3.3
        for (

                int i = 1; i <= m; i++) {
            u[i] = u[i] - st.theta * P[i];
        }
        for (int j = 1; j <= n; j++) {
            v[j] = v[j] - st.theta * P[j];
        }
        // vai para o passo 1

    }

    public Cell determinaACelulaQueEntra(int indice) throws Exception {
        // passo 2.1
        Q = new int[n + 1];
        P = new int[m + 1];

        for (int i = 1; i <= n; i++) {
            Q[i] = D[i][indice];
        }
        for (int i = 1; i <= m; i++) {
            P[i] = D[n + i][indice];
        }
        // passo 2.2
        Cell theta = new Cell(0, 0);
        boolean trocou = false;
        int min = 2000000000;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                if (P[i] - Q[j] > 0) {
                    // passo 2.3
                    if (c[i][j] + u[i] - v[j] < min) {
                        min = c[i][j];
                        theta.theta = min;
                        theta.oferta = i;
                        theta.demanda = j;
                        trocou = true;
                    }
                }

            }

        }
        if (trocou == false) {
            throw new Exception("Problema não solucionável");
        }

        return theta;
    }
}