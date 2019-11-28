/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

/**
 *
 * @author PC
 */
public class Neuronio {
     //Neuronio para 2 entradas
    
    private double w0;  //pesos
    private double w1;
    private double w2;
    private double w3;
    private double w4;

    public Neuronio(double w1, double w2, double w3, double w4) {
        this.w0 = 1;
        this.w1 = w1;
        this.w2 = w2;
        this.w3 = w3;
        this.w4 = w4;
    }
    
    public double getW0(){ return w0; }
    public double getW1(){ return w1; }
    public double getW2(){ return w2; }
    public double getW3(){ return w3; }
    public double getW4(){ return w4; }
    
    
    public double calculaV(double x1, double x2, double x3, double x4){ return w0 + w1*x1 + w2*x2 + w3*x3 + w4*x4;  } //calcula o campo local induzido
    
    public double calculaY(double x1, double x2, double x3, double x4){ //aplica a função
        double v = calculaV(x1,x2,x3,x4); 
        double x = (1 + Math.exp(-v));
        double aux = 1/x;
        return aux;    
    }
    
    public void setW0(double w0){ this.w0 = w0;  }
    public void setW1(double w1){ this.w1 = w1;  }
    public void setW2(double w2){ this.w2 = w2;  }

    public void setW3(double w3) {
        this.w3 = w3;
    }

    public void setW4(double w4) {
        this.w4 = w4;
    }
    
    
    public String toString(){ return "w0 = " + w0 + " w1= " + w1 + " w2= " + w2 + " w3= " +w3 + " w4= "+ w4;  }
}
