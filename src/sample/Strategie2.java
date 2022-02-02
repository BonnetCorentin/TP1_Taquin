package sample;

import java.util.Random;

public class Strategie2 extends Strategie {
    private boolean needToMove = false;

    Strategie2(Agent a) {
        super(a);
    }

    public void moveAgent() {

        boolean isPushed = false;

        do {

            Random rand = new Random();
            int index = rand.nextInt(2);
            int deplacementRand = rand.nextInt(2);
            int deplacement;

            if (deplacementRand == 0)
                deplacement = 1;
            else deplacement = -1;

            int[] dest = a.getCoordinatesAgent().clone();


            dest[index] = dest[index] + deplacement;

            if (a.getEnvironnement().moveAgent(a, dest))
                isPushed = true;


        } while (!isPushed);
        this.needToMove = false;

    }

    public void push() {
        needToMove = true;
    }


    @Override
    public void move() {
        if (needToMove)
            moveAgent();
        else bestMove();
    }
}
