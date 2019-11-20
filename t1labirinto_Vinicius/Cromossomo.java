/**
 * Autor: Vinicius Azevedo dos Santos
 * Trabalho de Inteligencia Artificial
 * PUCRS 2019-2 - Profa: Silvia
 */

public class Cromossomo {
    // Cromossomo é uma colecao de genes [G|G|G|G|G|G|G|G|G|...]
    private Gene cromossomo[];
    private int numGenes;
    private int lotacao;

    public Cromossomo(Gene[] c) {
        cromossomo = c;
        numGenes = c.length;
    }
    public Cromossomo(int tamanho) {
        numGenes = tamanho;
        cromossomo = new Gene[numGenes];
        lotacao = 0;
    }

    public void add(Gene g){
        if(lotacao < numGenes) {
            cromossomo[lotacao] = g;
            lotacao++;
        }
    }

    public void add(int g){
        if(lotacao < numGenes) {
            switch (g){
                case 0: cromossomo[lotacao] = Gene.N; break;
                case 1: cromossomo[lotacao] = Gene.NE; break;
                case 2: cromossomo[lotacao] = Gene.E; break;
                case 3: cromossomo[lotacao] = Gene.SE; break;
                case 4: cromossomo[lotacao] = Gene.S; break;
                case 5: cromossomo[lotacao] = Gene.SW; break;
                case 6: cromossomo[lotacao] = Gene.W; break;
                case 7: cromossomo[lotacao] = Gene.NW; break;
                default: System.err.println("Erro ao inserir Gene"); System.exit(1);
            }
            lotacao++;
        }
    }

    public int tamanho() {
        return numGenes;
    }

    public boolean completo() {
        return lotacao == numGenes;
    }

    public Gene getGene(int p) {
        if(p >= 0 && p < numGenes) {
            return cromossomo[p];
        } else {
            return null;
        }
    }

    public void troca(int g1, int g2) {
        Gene aux = cromossomo[g1];
        cromossomo[g1] = cromossomo[g2];
        cromossomo[g2] = aux;
    }

    public String toString() {
        String res = "";
        for (int g = 0; g < numGenes; g++) {
            res += cromossomo[g] + " ";
        }
        return res;
    }
}
