package maze;

import java.awt.Point;

public class MLP {

    private int x1[][];
    private int d[];
    public Neuronio input[];
    public Neuronio output[];
    private int TAM_TREINO = 21;
    private Genetico gen;

    public MLP(int[][] maze, int lines, Point sol, Point init) {
        x1 = new int[TAM_TREINO][];
        d = new int[TAM_TREINO];
        input = new Neuronio[4];
        output = new Neuronio[4];
        gen = new Genetico(maze,lines, sol, init);
        inicializacao();
    }

    private void inicializacao() {
        //Conjunto de Treino : OR
        x1[0] = new int[]{2,1,0,1}; d[0] = 0;
        x1[1] = new int[]{0,0,2,2}; d[1] = 2;
        x1[2] = new int[]{1,0,1,2}; d[2] = 3;
        x1[3] = new int[]{0,0,1,2}; d[3] = 3;
        x1[4] = new int[]{0,0,3,1}; d[4] = 2;
        x1[5] = new int[]{2,0,1,1}; d[5] = 0;
        x1[6] = new int[]{0,1,2,1}; d[6] = 2;
        x1[7] = new int[]{0,2,1,2}; d[7] = 3;
        x1[8] = new int[]{0,2,0,0}; d[8] = 1;
        x1[9] = new int[]{0,1,0,0}; d[9] = 1;
        x1[10] = new int[]{1,0,1,1}; d[10] = 3;
        x1[11] = new int[]{0,0,0,2}; d[11] = 3;
        x1[12] = new int[]{2,1,0,2}; d[12] = 0;
        x1[13] = new int[]{0,1,1,2}; d[13] = 3;
        x1[14] = new int[]{1,2,1,0}; d[14] = 1;
        x1[15] = new int[]{0,1,0,1}; d[15] = 1;
        x1[16] = new int[]{1,1,0,0}; d[16] = 1;
        x1[17] = new int[]{0,1,0,1}; d[17] = 1;
        x1[18] = new int[]{1,1,0,1}; d[18] = 2;
        x1[19] = new int[]{0,1,1,0}; d[19] = 2;
        x1[20] = new int[]{1,0,1,0}; d[20] = 2;
       
        
        gen.firstGen();        
        gen.genss();
        for (int i = 0; i < input.length; i++) {
            setWeights(i);
        }

    }

  
    public void setWeights(int indice){
        int cont = 0;
        while(cont<32){
            double a1 = 1/gen.pop[cont][7];
            double a2 = 1/gen.pop[cont+1][7];
            double a3 = 1/gen.pop[cont+2][7];
            double a4 = 1/gen.pop[cont+4][7];
                    
//            input[indice] = new Neuronio((1/gen.pop[cont][7])*Math.random(), 1/gen.pop[cont+1][7]*Math.random(), 1/gen.pop[cont+2][7]*Math.random(), 1/gen.pop[cont+2][7]*Math.random());
//            cont+=4;
//            output[indice] = new Neuronio(1/gen.pop[cont][7]*Math.random(), 1/gen.pop[cont+1][7]*Math.random(), 1/gen.pop[cont+2][7]*Math.random(), 1/gen.pop[cont+2][7]*Math.random());
//            cont+=4;
            input[indice] = new Neuronio((1/gen.pop[cont][7]), 1/gen.pop[cont+1][7], 1/gen.pop[cont+2][7], 1/gen.pop[cont+2][7]);
            cont+=4;
            output[indice] = new Neuronio(1/gen.pop[cont][7], 1/gen.pop[cont+1][7], 1/gen.pop[cont+2][7], 1/gen.pop[cont+2][7]);
            cont+=4;
        }
    
    }
    public void treinamento() {

        
        int predictions =0;
        int erroGeral = Integer.MAX_VALUE;
        int epocas = 0;
        double[] y = new double[4];
        double[] r = new double[4];
        int direction = 0;
        while (erroGeral > 0 && epocas < 250) {
            erroGeral = 0;
            epocas++;
            gen.nextGen();
            gen.genss();
            predictions =0;
            for (int j = 0; j < TAM_TREINO; j++) {
                
                int erro = 1;
                for (int i = 0; i < input.length; i++) {
                    y[i] = input[i].calculaY(x1[j][0], x1[j][1], x1[j][2], x1[j][3]);

                }
                for (int i = 0; i < output.length; i++) {
                    r[i] = output[i].calculaY(y[0], y[1], y[2], y[3]);
                }
                double aux = 0;
                for (int i = 0; i < r.length; i++) {
                    if (aux < Math.abs(r[i])) {
                        aux = Math.abs(r[i]);
                        direction = i;
                        gen.move(i);
                    }
                }               
                
                if (direction == d[j]) {
                    predictions++;
                    erro = 0;
                }
                if (erro != 0) {
                    double learningRate = 0.35;
                    
                    double momentum = 0.05;
                    double d1p = 0;
                    double d2p = 0;
                    
                    for (int i = 0; i < input.length; i++) {
                        double signal =( d[j] - r[i]) * r[i] * (1-r[i]);
                        double delta1 = learningRate*signal*y[i] + d1p*momentum;
                        double delta2 = learningRate*signal*r[i] + d2p*momentum;
                        input[i].setW1(input[i].getW1()+delta1);
                        input[i].setW2(input[i].getW2()+delta1);
                        input[i].setW3(input[i].getW3()+delta1);
                        input[i].setW4(input[i].getW4()+delta1);
                        output[i].setW1(output[i].getW1()+delta2);
                        output[i].setW2(output[i].getW2()+delta2);
                        output[i].setW3(output[i].getW3()+delta2);
                        output[i].setW4(output[i].getW4()+delta2);
                        d1p = delta1;
                        d2p = delta2;
                        //setWeights(i);
                    }
                }
                erroGeral += erro;
            }
            
        }
       // System.out.println("Predições corretas: " + predictions);
    }

    public int generalizacao(int[] positions) {  //uso da rede
        double[] y = new double[4];
        double[] r = new double[4];
        int direction = 0;
        for (int i = 0; i < input.length; i++) {
            y[i] = input[i].calculaY(positions[0], positions[1], positions[2], positions[3]);

        }
        for (int i = 0; i < output.length; i++) {
            r[i] = output[i].calculaY(y[0], y[1], y[2], y[3]);
        }
        double aux = 0;
        for (int i = 0; i < r.length; i++) {
            if (aux < Math.abs(r[i])) {
                aux = Math.abs(r[i]);
                direction = i;
            }
        }

        return direction;
    }

}
