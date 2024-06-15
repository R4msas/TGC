package implementacao

4;

public class DualMatrix {
    int[] A;
    int ofertas[];
    int demandas[];
    int m;// número de ofertas
    int n;// número de demandas
    int u[];
    int v[];

    public void inicializa(){
       A=new int[m+n+1];
       ofertas=new int[m+1];
       demandas=new int[n+1];
       //passo 0.1
       for(int i=1;i<=n;i++){
        A[i]=demandas[i];

       }
       for(int i=1;i<=n;i++){
        A[i+m]=-ofertas[i];

       }
       //passo 0.2
       u=new int[m+1];// u já inicia com zero
       v=new int[m+1];
       for(int i=1;i<=m;i++)
       {
        int min=2000000000;
        for(int j=1;j<=n;j++){
            if(matriz[i][j]<min)
            {
                min=matriz[i][j];
            }
            v[i]=min;
       }
    }
       //passo 0.3
       for(int i=1;i<=n;i++){
        

       }
       for(int i=1;i<=n;i++){

       }

    //    passo 0.4
        int D[][]=new int[n+m+1][n+m+1];
        for(int i=1;i<=n+m;i++)
        {
            for(int j=1;j<=n+m;j++)
                {
                    if(i==j)
                    {
                        D[i][j]=1;
                    }
                    else if(j==)
                }
        }
    
    
}
}