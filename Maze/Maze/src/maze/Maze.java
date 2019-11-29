package maze;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Maze {

    private int[][] maze;
    private int lines;
    private int blank;
    Point sol;
    Point currentPosition;
    int score;
    boolean fim = false;
    int moedas = 0;

    public void findPath() {
        readFile("lab.txt");
        MLP mlp = new MLP(maze, lines, sol, currentPosition);
        mlp.treinamento();

        int move = 0;
        int cont = 0;
        while(!fim && cont <20){
            move = mlp.generalizacao(getPositions());
            moveAgent(move);
            System.out.println("Score: " + score + " Moedas: " + moedas);
            printMaze();
            cont++;
        }

    }
    public void printMaze() {
        String msg = "";

        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < lines; j++) {
                if(j == currentPosition.x && i == currentPosition.y){
                    msg+= "A ";
                }
                else{                
                    msg += (maze[i][j] == 0 ? "1" : maze[i][j] == 1 ? "0" : maze[i][j] == 2 ? "M" :maze[i][j] == 3 ? "S" : "A")+ " ";
                }
            }
            msg += "\n";
        }

        System.out.println(msg);
    }

    public void moveAgent(int move) {
        int aux = 0;
        try {                 
            switch (move) {
                case (0):
                    aux = maze[currentPosition.y -1][currentPosition.x];
                    if(aux!=0){
                        currentPosition.y--;
                    }
                    else fim = true;
                    break;
                case (1):
                    aux = maze[currentPosition.y +1][currentPosition.x ];
                    if(aux!=0) currentPosition.y++;
                    else fim = true;
                    break;
                case (2):
                    aux = maze[currentPosition.y][currentPosition.x +1];
                    if(aux!=0) currentPosition.x++;
                    else fim = true;
                    break;
                case (3):
                    aux = maze[currentPosition.y][currentPosition.x -1 ];
                    if(aux!=0) currentPosition.x--;
                    else fim = true;
                    break;
                default:
                    break;
            }
            if(aux==0) {score -=10; fim = true;};
            if (aux == 2) {
                score += 10;
                moedas += 50;
                maze[currentPosition.y][currentPosition.x] = 1;
            }
            if (aux == 3) {
                fim = true;
            }
        } catch (Exception e) {
            score -= 10;
        }
    }

    public int[] getPositions() {
        int[] p = new int[4];
        p[0] = currentPosition.y - 1 > 0 ? maze[currentPosition.y - 1][currentPosition.x] : 0;
        p[1] = currentPosition.y +1  < lines ? maze[currentPosition.y + 1][currentPosition.x] : 0;
        p[2] = currentPosition.x + 1 < lines ? maze[currentPosition.y] [currentPosition.x + 1]: 0;
        p[3] = currentPosition.x - 1 > 0 ? maze[currentPosition.y][currentPosition.x - 1] : 0;
        return p;
    }

    public void readFile(String file) {
        try (FileReader fr = new FileReader(new File(file)); BufferedReader buff = new BufferedReader(fr)) {
            int col = 0;
            int n = Integer.parseInt(buff.readLine());
            maze = new int[n][n];
            lines = n;
            while (col < n) {
                String linha = buff.readLine();
                for (int i = 0; i < n; i++) {
                    linha = linha.replace(" ", "");
                    if (linha.charAt(i) == '0') {
                        blank++;
                    }
                    if (linha.charAt(i) == 'S') {
                        sol = new Point(col, i);
                    }
                    if (linha.charAt(i) == 'E') {
                        currentPosition = new Point(col, i);
                    }
                    maze[col][i] = linha.charAt(i) == '0' ? 1 : linha.charAt(i) == '1' ? 0 : linha.charAt(i) == 'M' ? 2 : linha.charAt(i) == 'S' ? 3 : 1;
                }
                col++;
            }
        } catch (IOException ex) {
            System.out.println("Não foi possível ler o arquivo");
        }
    }

    public static void main(String[] args) {
        Maze m = new Maze();
        m.findPath();

    }

}
