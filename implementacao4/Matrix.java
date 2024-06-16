/******************************************************************************
 *  Compilation:  javac Matrix.java
 *  Execution:    java Matrix
 *
 *  A bare-bones collection of static methods for manipulating
 *  matrices.
 * @author Robert Sedgewick
 * @author Kevin Wayne 
 * do método só troquei de double para int
 ******************************************************************************/
public class Matrix {
   // matrix-vector multiplication (y = A * x)
   public static int[] multiply(int[][] a, int[] x) {
    int m = a.length;
    int n = a[0].length;
    if (x.length != n) throw new RuntimeException("Illegal matrix dimensions.");
    int[] y = new int[m];
    for (int i = 1; i < m; i++)
        for (int j = 1; j < n; j++)
            y[i] += a[i][j] * x[j];
    return y;
}

    // vector-matrix multiplication (y = x^T A)
    public static int[] multiply(int[] x, int[][] a) {
        int m = a.length;
        int n = a[0].length;
        if (x.length != m) throw new RuntimeException("Illegal matrix dimensions.");
        int[] y = new int[n];
        for (int j = 1; j < n; j++)
            for (int i = 1; i < m; i++)
                y[j] += a[i][j] * x[i];
        return y;
    }
    public static int[][] copy(int [][]a)
    {
        int [][]b=new int[a.length][a[0].length];
        for (int i = 1; i < b.length; i++) {
            for(int j = 1; j < b.length; j++){
                b[i][j]=a[i][j];
            }
            
        }
        return b;

    }


    // vector-matrix multiplication (y = x^T A)
    // public static int[] multiply(int[] x, int[][] a) {
    //     int m = a.length;
    //     int n = a[0].length;
    //     if (x.length != m) throw new RuntimeException("Illegal matrix dimensions.");
    //     int[] y = new int[n];
    //     for (int j = 1; j < n; j++)
    //         for (int i = 1; i < m; i++)
    //             y[j] += a[i][j] * x[j];
    //     return y;
    // }
    public static void printMatrix(int[][] matrix) {
        for (int row = 1; row < matrix.length; row++) {
            for (int col = 1; col < matrix[row].length; col++) {
                System.out.printf("%4d", matrix[row][col]);
            }
            System.out.println();
        }
    }
    public static void printVector(int[] vector) {
        for (int i = 1; i < vector.length; i++) {
                System.out.printf("%4d", vector[i]);
            }
            System.out.println();
        }
    

}
