/**
 * Autor: Vinicius Azevedo dos Santos
 * Trabalho de Inteligencia Artificial
 * PUCRS 2019-2 - Profa: Silvia
 */
import java.util.*;

public class LabirintoGenetico {
    private List<Populacao> geracao;
    private Labirinto labirinto;
    private Random random;
    private int tamanhoCromossomo;
    private Cromossomo cromossomo;
    private Caminho caminho;
    private List<Double> avaliacao;
    private double criterioEliminacao;

    public LabirintoGenetico(Labirinto lab, int popInicial){
        labirinto = lab;
        geracao = new ArrayList<Populacao>();
        random = new Random();
        caminho = new Caminho(lab);
        criterioEliminacao = distanciaEuclidiana(new Coordenada(0,0), labirinto.getSaida()) * 15 / 100;
        criterioEliminacao = distanciaEuclidiana(new Coordenada(0,0), labirinto.getSaida()) - criterioEliminacao;


        inicializa(popInicial);
        // cicloDaVida(popInicial);
    }

    public void inicializa(int popInicial) {
        geracao.add(new Populacao());
        tamanhoCromossomo = (labirinto.getTamanho() * labirinto.getTamanho()) / 2;

        for (int i = 0; i < popInicial; i++) {
            cromossomo = new Cromossomo(tamanhoCromossomo);
            while(!cromossomo.completo()) {
                cromossomo.add(random.nextInt(8));
            }
            geracao.get(0).add(cromossomo);
        }

        double avaliacao[] = aptidao(geracao.get(0));
        for (double v : avaliacao) {
            if(v == 0) {
                System.out.println("Achou saida");
                break;
            }
        }

        int melhor = 0;
        double melhorV = 9999;
        for (int i = 0 ; i < avaliacao.length; i++) {
            if(melhorV > avaliacao[i]) {
                melhorV = avaliacao[i];
                melhor = i;
            }
        }

        System.out.println("O melhor encontrado foi o cromossomo " + melhor + " (gerado de 0 a " + popInicial + ")");
        System.out.println("Aptidão   : " + avaliacao[melhor] + " (distancia euclidiana: posicao x saida)");
        System.out.println("Caminho   : " + caminho.exibe(geracao.get(0).get(melhor)) + " (percorrido até bater na parede)");
        System.out.println("Cromossomo: " + geracao.get(0).get(melhor));

        System.out.println("LISTA DE TODOS CROMOSSOMOS (POPULACAO INICIAL) : ");

        for (int i = 0; i < popInicial; i++) {
            System.out.println("["+i+"] Aptidão: " + avaliacao[i] + ", caminho: " + caminho.exibe(geracao.get(0).get(i)));
            System.out.println("["+i+"] Cromossomo: " + geracao.get(0).get(i));
        }
    }

    public void cicloDaVida(int popInicial) {
        int numGeracao = 0;
        double avaliacao[];
        boolean resolveu = false;
        //1 inicializa a populacao inicial (primeira geracao) com numeros aleatorios
        //quantidade de cromossomos baseada no tamanho do labirinto (tamLabirinto^2 / 2)
        geracao.add(new Populacao());
        tamanhoCromossomo = (labirinto.getTamanho() * labirinto.getTamanho()) / 2;

        for (int i = 0; i < popInicial; i++) {
            cromossomo = new Cromossomo(tamanhoCromossomo);
            while(!cromossomo.completo()) {
                cromossomo.add(random.nextInt(8));
            }
            geracao.get(numGeracao).add(cromossomo);
        }

        //2 AVALIACAO DA GERACAO
        avaliacao = aptidao(geracao.get(numGeracao));
        for (double v : avaliacao) {
            if(v == 0) {
                resolveu = true;
                break;
            }
        }

        //3 PARA (CRITERIO)? SIM > STOP , NAO > REPETE 3-8
        while(!resolveu) {
            //4 SELECAO e CRUZA
            geracao.add(cruzamento(geracao.get(numGeracao), avaliacao));
            numGeracao++;

            //6 MUTACAO
            mutacao(geracao.get(numGeracao).get(random.nextInt(geracao.get(numGeracao).size()-1)));

            //7 AVALIACAO
            avaliacao = aptidao(geracao.get(numGeracao));
            for (double v : avaliacao) {
                if(v == 0) {
                    resolveu = true;
                    break;
                }
            }

            // 8 ELIMINACAO MENOS APTOS
            // for (int i = avaliacao.length-1; i > 0; i--) {
            //     if(avaliacao[i] > criterioEliminacao) {
            //         // System.out.println("DEBUG");
            //         geracao.get(numGeracao).remove(i);
            //     }
            // }
            // avaliacao = aptidao(geracao.get(numGeracao));
        }

    }

    public double[] aptidao(Populacao pop){
        double avaliacao[] = new double[pop.size()];
        Coordenada coord;
        //percorre os cromossomos para verificar o quãp longe eles foram
        //e calcula distancia em relacao a saida
        for (int c = 0; c < pop.size(); c++) {
            coord = caminho.calcular(pop.get(c));
            avaliacao[c] = distanciaEuclidiana(coord, labirinto.getSaida());
        }
        return avaliacao;
    }

    public double distanciaEuclidiana(Coordenada atual, Coordenada saida) {
        double difL, difC;
        difC = Math.abs(atual.c - saida.c);
        difL = Math.abs(atual.l - saida.l);
        return Math.sqrt( (difL*difL) + (difC*difC) );
    }


    public int[] selecaoMelhores(double[] avaliacao){
        int res[] = {-1,-1};
        double pai = 999999999, mae = 999999999;
        for (int i = 0; i < avaliacao.length; i++) {
            if(avaliacao[i] <= pai){
                mae = pai;
                pai = avaliacao[i];
                res[1] = res[0];
                res[0] = i;
            } else if(avaliacao[i] <= mae) {
                mae = avaliacao[i];
                res[1] = i;
            }
        }
        return res;
    }

    public int[] selecaoTorneio(double[] avaliacao){
        int res[] = {-1,-1};
        int r1, r2;
        r1 = random.nextInt(avaliacao.length);
        r2 = random.nextInt(avaliacao.length);

        if(avaliacao[r1] < avaliacao[r2]) {
            res[0] = r1;
            res[1] = r2;
        } else {
            res[1] = r1;
            res[0] = r2;
        }
        return res;
    }

    //cruzamento primeiro faz a selecao dos 2 melhores, depois torneio
    public Populacao cruzamento(Populacao pais, double[] avaliacao) {
        Populacao filhos = new Populacao();
        Cromossomo c1;
        Cromossomo c2;
        int corte, tamanhoCromossomo = pais.get(0).tamanho();
        int selecionados[] = new int[2];
        int taxaCruza = 0;
        selecionados = selecaoMelhores(avaliacao);
        taxaCruza = random.nextInt(pais.size())+3;
        do{
            c1 = new Cromossomo(tamanhoCromossomo);
            c2 = new Cromossomo(tamanhoCromossomo);
            corte = random.nextInt(tamanhoCromossomo);
            for (int i = 0; i < tamanhoCromossomo; i++) {
                if(i < corte) {
                    c1.add(pais.get(selecionados[0]).getGene(i));
                    c2.add(pais.get(selecionados[1]).getGene(i));
                } else {
                    c1.add(pais.get(selecionados[1]).getGene(i));
                    c2.add(pais.get(selecionados[0]).getGene(i));
                }
            }
            filhos.add(c1);
            filhos.add(c2);
            selecionados = selecaoTorneio(avaliacao);
            taxaCruza--;
        }while(taxaCruza >= 0);

        return filhos;
    }

    //mutacao
    public void mutacao(Cromossomo c) {
        int gene1, gene2;
        gene1 = random.nextInt(c.tamanho());
        gene2 = random.nextInt(c.tamanho());
        c.troca(gene1,gene2);

    }

    public Populacao get(int indice) {
        return geracao.get(indice);
    }

    public String printGeracao(int indice){
        Populacao res = geracao.get(0);
        return res.toString();
    }

}
