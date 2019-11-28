package maze;

import java.awt.Point;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author PC
 */
public class Genetico {

    private int[][] maze;
    private int lines;
    private int tam = 3;
    public double[][] pop;
    private int tamPop = 32;
    private double[] mostFitted;
    double currentMF = Double.MAX_VALUE;
    int posMostFitted = 0;
    Point sol;
    Point init;

    public Genetico(int[][] maze, int lines, Point sol, Point init) {
        this.maze = maze;
        this.lines = lines;
        this.sol = sol;
        this.init = init;
    }

    public void move(int d) {
        switch (d) {
            case (1):
                if (init.y - 1 > 0 && maze[init.x][init.y - 1] != 0) {
                    init = new Point(init.x, init.y - 1);
                }
                break;
            case (2):
                if (init.y + 1 < lines && maze[init.x][init.y + 1] != 0) {
                   init = new Point(init.x, init.y + 1);
                }
                break;
            case (3):
                if (init.x - 1 >= 0 && maze[init.x - 1][init.y] != 0) {
                    
                   init = new Point(init.x - 1, init.y);
                }
                break;
            case (4):
                if (init.x + 1 < lines && maze[init.x + 1][init.y] != 0) {
                    
                    init = new Point(init.x + 1, init.y);
                }
                break;
            default:
                break;
        }

    }

    public void genss() {

        currentMF = Double.MAX_VALUE;
        posMostFitted = 0;
        for (int i = 0; i < tamPop; i++) {
            int penalty = 0;
            boolean hit = false;
            Point currentMove = init;
            for (int j = 0; j < tam - 1; j++) {
                if (!hit) {
                    int move = (int) pop[i][j];
                    switch (move) {
                        case (1):
                            if (currentMove.y - 1 < 0 || maze[currentMove.x][currentMove.y - 1] == 0) {
                                hit = true;
                                penalty = tam - j;
                            } else if (maze[currentMove.x][currentMove.y - 1] == 3) {
                                hit = true;
                                currentMove = new Point(currentMove.x, currentMove.y - 1);
                            } else {
                                currentMove = new Point(currentMove.x, currentMove.y - 1);
                            }
                            break;
                        case (2):
                            if (currentMove.y + 1 >= lines || maze[currentMove.x][currentMove.y + 1] == 0) {
                                hit = true;
                                penalty = tam - j;
                            } else if (maze[currentMove.x][currentMove.y + 1] == 3) {
                                hit = true;
                                currentMove = new Point(currentMove.x, currentMove.y + 1);
                            } else {
                                currentMove = new Point(currentMove.x, currentMove.y + 1);
                            }
                            break;
                        case (3):
                            if (currentMove.x - 1 < 0 || maze[currentMove.x - 1][currentMove.y] == 0) {
                                hit = true;
                                penalty = tam - j;
                            } else if (maze[currentMove.x - 1][currentMove.y] == 3) {
                                hit = true;
                                currentMove = new Point(currentMove.x - 1, currentMove.y);
                            } else {
                                currentMove = new Point(currentMove.x - 1, currentMove.y);
                            }
                            break;
                        case (4):
                            if (currentMove.x + 1 >= lines || maze[currentMove.x + 1][currentMove.y] == 0) {
                                hit = true;
                                penalty = tam - j;
                            } else if (maze[currentMove.x + 1][currentMove.y] == 3) {
                                hit = true;
                                currentMove = new Point(currentMove.x + 1, currentMove.y);
                            } else {
                                currentMove = new Point(currentMove.x + 1, currentMove.y);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }

            pop[i][tam - 1] = currentMove.distance(sol) + penalty;
            if (currentMove.distance(sol) + penalty < currentMF) {
                currentMF = currentMove.distance(sol) + penalty;
                posMostFitted = i;
            }
        }
        mostFitted = new double[tam];
        for (int i = 0; i < tam; i++) {
            mostFitted[i] = pop[posMostFitted][i];

        }

    }

    public void nextGen() {
        double oldpop[][] = new double[tamPop][tam + 1];
        for (int i = 0; i < tamPop; i++) {
            for (int j = 0; j < tam; j++) {
                oldpop[i][j] = pop[i][j];
            }
        }
        pop = new double[tamPop][tam + 1];
        pop[0] = mostFitted;
        Random r = new Random();
        for (int i = 1; i < tamPop - 1; i += 2) {
            Integer[] s = new Integer[4];
            s = randomCrom().toArray(s);
            int r1 = s[0];
            int r2 = s[1];
            int r3 = s[2];
            int r4 = s[3];

            int father = oldpop[r1][tam - 1] < oldpop[r2][tam - 1] ? r1 : r2;
            int mother = oldpop[r3][tam - 1] < oldpop[r4][tam - 1] ? r3 : r4;
            for (int j = 0; j < tam - 1; j++) {

                if (j < 1) {
                    pop[i][j] = oldpop[father][j];
                    pop[i + 1][j] = oldpop[mother][j];
                } else {
                    pop[i][j] = oldpop[mother][j];
                    pop[i + 1][j] = oldpop[father][j];
                }
            }
        }
        // int mut = 0;
        //while (mut < tamPop) {
        int cromo = r.nextInt(tamPop);
        int gene = r.nextInt(tam - 1);
        int move = randomMove();
        pop[cromo][gene] = move;
        //mut++;
        //}

    }

    public void firstGen() {
        pop = new double[tamPop][tam + 1];
        for (int i = 0; i < tamPop; i++) {
            for (int j = 0; j < tam - 1; j++) {
                pop[i][j] = randomMove();
            }
            pop[i][tam] = 0;
        }
    }

    private Set<Integer> randomCrom() {
        Set<Integer> s = new HashSet<>(4);
        int cont = 0;
        Random r = new Random();
        while (cont < 4) {
            int i = r.nextInt(tamPop);
            if (!s.contains(i)) {
                s.add(i);
                cont++;
            }
        }
        return s;
    }

    private int randomMove() {
        Random r = new Random();
        return (r.nextInt(4) + 1);
    }

}
