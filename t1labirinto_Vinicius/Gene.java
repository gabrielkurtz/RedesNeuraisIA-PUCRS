/**
 * Autor: Vinicius Azevedo dos Santos
 * Trabalho de Inteligencia Artificial
 * PUCRS 2019-2 - Profa: Silvia
 */

public enum Gene {
    // Cada gene represente a direção de um passo do agente caminhando no labirinto

    N(0), NE(1), E(2), SE(3), S(4), SW(5), W(6), NW(7);

    private int direcao;

    private Gene(int direcao){
        this.direcao = direcao;
    }

    public int getDirecao() {
        return direcao;
    }

}