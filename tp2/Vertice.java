
import java.util.ArrayList;

class Vertice {
    ArrayList<Integer> adjacentes;
    ArrayList<Integer> pesos;
    
    Vertice() {
        adjacentes = new ArrayList<Integer>();
        pesos = new ArrayList<Integer>();
    }

    public boolean verificaSeExiste(int v) {
        return adjacentes.contains(v);
    }

}
