/**
 * Autor: Vinicius Azevedo dos Santos
 * Trabalho de Inteligencia Artificial
 * PUCRS 2019-2 - Profa: Silvia
 */
import java.io.*;

public class Labirinto {
    private FileReader fr;
    // FileWriter fw;
    private int tamanho;
    private char[][] labirinto;
    private Coordenada saida;

    public Labirinto(String file) {
        try {
            fr = new FileReader(new File(file));
            String aux = "";
            int i = fr.read();
            boolean flag = false;
            int l = 0, c = 0;

            //Le cada caracter do arquivo para montar o labirinto
            while(i != -1) {
                //A flag é para determinar se a linha 1 foi lida (tamanho do lab)
                if(!flag) {
                    if((char) i == '\n') {
                        tamanho = Integer.parseInt(aux);
                        labirinto = new char[tamanho][tamanho];
                        flag = true;
                    } else aux += (char) i;
                } else {
                    if(i != '\n' && i != ' ') {
                        labirinto[l][c] = (char) i;
                        if((char) i == 'S') saida = (new Coordenada(l, c));
                        c++;
                    } else if(i == '\n') {
                        l++;
                        c = 0;
                    }
                }
                i = fr.read();
            }
            fr.close();
            System.out.println("Arquivo de Entrada : " + file);
            // return texto;
        } catch (IOException e) {
            System.err.println("Arquivo de entrada não encontrado, para executar utilize o comando:");
            System.err.println(">> $ java LabirintoGenetico nome_do_arquivo.txt");
            System.exit(1);

        }
    }

    public Coordenada getSaida() {
        return saida;
    }

    public String linha(int l) {
        String x = "";
        for (int i = 0; i < tamanho; i++) {
            x += labirinto[l][i] + " ";
        }
        return x;
    }

    public String coluna(int c) {
        String x = "";
        for (int i = 0; i < tamanho; i++) {
            x += labirinto[i][c] + " ";
        }
        return x;
    }

    public char get(int l, int c) {
        return labirinto[l][c];
    }

    public boolean ehSaida(int l, int c) {
        return (labirinto[l][c] == 'S');
    }

    public int getTamanho() {
        return tamanho;
    }

    public String printChar(char c) {
        if(c == '0') return "  ";
        else if(c == '1') return "██";
        else return c+""+c;
    }

    public String printBonito() {
        String x = "";
        for (int i = 0; i < tamanho+2; i++)
            x += "▒▒";
        x += "\n";
        for (int l = 0; l < tamanho; l++) {
            x += "▒▒";
            for (int c = 0; c < tamanho; c++) {
                x += printChar(labirinto[l][c]);
            }
            x += "▒▒\n";
        }
        for (int i = 0; i < tamanho+2; i++)
            x += "▒▒";
        return x;
    }

    public String toString() {
        String x = "";
        for (int l = 0; l < tamanho; l++) {
            for (int c = 0; c < tamanho; c++) {
                x += labirinto[l][c] + " ";
            }
            x += "\n";
        }
        return x;
    }

}
