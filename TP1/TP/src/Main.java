import java.io.*;

public class Main {


    public static void main(String[] args) throws Exception {
        ForwardStar fs = new ForwardStar(new File("c2"));
        // System.out.println("Articulacao");
        // usingArticulations(fs);
        System.out.println("Tarjan");
        usingArticulations(fs);
    }

    public static void usingTarjan(ForwardStar fs){
        new Tarjan(fs);
    }

    public static void usingArticulations(ForwardStar fs){
        Biconnected biConnected=new Biconnected(new Graph(fs));
        int array[] =biConnected.getArrayArticulation();
        Articulacao dfs = new Articulacao(fs.saida.length-1, fs);
        dfs.getComponentes(array);
    }
}
