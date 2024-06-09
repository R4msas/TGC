public class Dfs {
    public int[] termino;
    public int[] pai;
    public int[] descobrimento;
    public int tempo;
    public

    public Dfs(int m) {
        termino = new int[m];
        pai = new int[m];;
        descobrimento = new int[m];;
        tempo=0;
    }
    public void busca()
    {   
        busca(1);
    }
    public void busca(int raiz)
    {
        
    }
}
