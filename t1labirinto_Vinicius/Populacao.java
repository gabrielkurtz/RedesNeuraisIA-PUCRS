/**
 * Autor: Vinicius Azevedo dos Santos
 * Trabalho de Inteligencia Artificial
 * PUCRS 2019-2 - Profa: Silvia
 */
import java.util.List;
import java.util.ArrayList;

public class Populacao {
    // Populacao é uma colecao de cromossomos
    // C1-[G|G|G|G|G|G|G|G|G|...]
    // C2-[G|G|G|G|G|G|G|G|G|...]
    // C3-[G|G|G|G|G|G|G|G|G|...]

    private List<Cromossomo> cromossomos;

    public Populacao() {
        cromossomos = new ArrayList<Cromossomo>();
    }

    public Cromossomo get(int indice) {
        return cromossomos.get(indice);
    }

    public void add(Cromossomo c) {
        cromossomos.add(c);
    }

    public int size(){
        return cromossomos.size();
    }

    public void remove(int i){
        cromossomos.remove(i);
    }

    public int tamanhoCromossomo(int indice){
        return cromossomos.get(indice).tamanho();
    }

    public String toString() {
        String res = "";
        for (int c = 0; c < size(); c++) {
            res += get(c) + "\n";
        }
        return res;
    }
}
