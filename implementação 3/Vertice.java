import java.util.ArrayList;

class Vertice {
    ArrayList<Integer> adjacentes;

    Vertice() {
        adjacentes = new ArrayList<Integer>();
    }

    public boolean verificaSeExiste(int v) {
        return adjacentes.contains(v);
    }

   

}