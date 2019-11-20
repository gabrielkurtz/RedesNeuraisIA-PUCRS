/**
 * Autor: Vinicius Azevedo dos Santos
 * Trabalho de Inteligencia Artificial
 * PUCRS 2019-2 - Profa: Silvia
 */
import java.util.ArrayList;
import java.util.List;

public class Caminho {

    private List<Coordenada> caminho;
    private Labirinto labirinto;

    public Caminho(Labirinto labirinto){
        caminho = new ArrayList<Coordenada>();
        this.labirinto = labirinto;
    }

    public List<Coordenada> exibe(Cromossomo cromossomo){
        boolean bateu = false;
        caminho = new ArrayList<Coordenada>();
        caminho.add(new Coordenada(0, 0));

        //Para cada cromossomo da populacao, calcula o caminho percorrido até colidir
        while(!bateu) {
            for (int g = 1; g < cromossomo.tamanho(); g++) {
                caminho.add(move(cromossomo.getGene(g-1), caminho.get(g-1), labirinto.getTamanho()));
                if(caminho.get(g) == null) {
                    caminho.remove(g);
                    bateu = true;
                    break;
                } else /* deu um passo certo verifico se é saida */ {
                    if(caminho.get(g) == labirinto.getSaida()) break;
                }
            }
        }
        //retorna a posicao final
        return caminho;
    }
    public Coordenada calcular(Cromossomo cromossomo){
        boolean bateu = false;
        caminho = new ArrayList<Coordenada>();
        caminho.add(new Coordenada(0, 0));

        //Para cada cromossomo da populacao, calcula o caminho percorrido até colidir
        while(!bateu) {
            for (int g = 1; g < cromossomo.tamanho(); g++) {
                caminho.add(move(cromossomo.getGene(g-1), caminho.get(g-1), labirinto.getTamanho()));
                if(caminho.get(g) == null) {
                    caminho.remove(g);
                    bateu = true;
                    break;
                } else /* deu um passo certo verifico se é saida */ {
                    if(caminho.get(g) == labirinto.getSaida()) break;
                }
            }
        }
        //retorna a posicao final
        return caminho.get(caminho.size()-1);
    }

    private Coordenada move(Gene g, Coordenada atual, int limite) {
        //teste de movimento verificando borda e parede
        // System.out.println("DEBUG " + limite + " " + atual + " " + g);

        switch (g) {
            case N:
                if (atual.l == 0) return null;
                if (labirinto.get(atual.l-1, atual.c) == '1') return null;
                return new Coordenada(atual.l-1, atual.c);
            case NE:
                if (atual.l == 0 || atual.c == limite) return null;
                if (labirinto.get(atual.l-1, atual.c+1) == '1') return null;
                return new Coordenada(atual.l-1, atual.c+1);
            case E:
                if (atual.c == limite) return null;
                if (labirinto.get(atual.l, atual.c+1) == '1') return null;
                return new Coordenada(atual.l, atual.c+1);
            case SE:
                if (atual.l == limite || atual.c == limite) return null;
                if (labirinto.get(atual.l+1, atual.c+1) == '1') return null;
                return new Coordenada(atual.l+1, atual.c+1);
            case S:
                if (atual.l == limite) return null;
                if (labirinto.get(atual.l+1, atual.c) == '1') return null;
                return new Coordenada(atual.l+1, atual.c);
            case SW:
                if (atual.l == limite || atual.c == 0) return null;
                if (labirinto.get(atual.l+1, atual.c-1) == '1') return null;
                return new Coordenada(atual.l+1, atual.c-1);
            case W:
                if (atual.c == 0) return null;
                if (labirinto.get(atual.l, atual.c-1) == '1') return null;
                return new Coordenada(atual.l, atual.c-1);
            case NW:
                if (atual.l == 0 || atual.c == 0) return null;
                if (labirinto.get(atual.l-1, atual.c-1) == '1') return null;
                return new Coordenada(atual.l-1, atual.c-1);
            default: return null;
        }
    }


}
