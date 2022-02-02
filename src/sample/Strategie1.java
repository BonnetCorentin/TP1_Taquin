package sample;

public class Strategie1 extends Strategie {

    Strategie1(Agent a) {
        super(a);
    }

    @Override
    public void move() {
        bestMove();
    }
}
