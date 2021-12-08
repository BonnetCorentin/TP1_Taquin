package sample;

import static java.lang.Thread.sleep;

public class Agent implements Runnable {

    final private char lettre;
    final private Environnement e;

    public Agent(char lettre, Environnement e) {
        this.lettre = lettre;
        this.e = e;
    }

    private void bestMove() throws InterruptedException {
        e.bestMove(this);
    }

    public char getLettre() {
        return lettre;
    }

    @Override
    public String toString() {
        return Character.toString(lettre);
    }


    @Override
    public void run() {
        System.out.println(this);
        int i = 0;
        while (i < 10) {
            try {
                bestMove();
                sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            i++;
        }
    }
}
