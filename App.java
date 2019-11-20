public class App {
    public static void main(String[] args) {
        Labirinto lab = new Labirinto("teste.txt");
        System.out.println(lab);

        System.err.println("\n\n\n");

        System.out.println(lab.printBonito());

    }
}