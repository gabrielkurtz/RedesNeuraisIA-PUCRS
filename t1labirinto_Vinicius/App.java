/**
 * Autor: Vinicius Azevedo dos Santos
 * Trabalho de Inteligencia Artificial
 * PUCRS 2019-2 - Profa: Silvia
 */
public class App {

    public static void main(String[] args) throws Exception {
        Labirinto labirinto = new Labirinto(args[0]);

        System.out.println(labirinto);

        System.out.println(labirinto.printBonito());


        LabirintoGenetico genetico = new LabirintoGenetico(labirinto, 20);

        // System.out.println(genetico.printGeracao(0));
        //
        // genetico.aptidao(genetico.get(0));
        // System.out.println(labirinto.getSaida());
        //
        // System.out.println(labirinto.linha(0));
        // System.out.println(labG.coluna(1));
        // System.out.println(labG.get(3,7));
        // System.out.println(labG.get(3,6));
        // System.out.println(labG.get(3,5));
        // System.out.println(labG.get(3,4));



    }
}
