package sample;

abstract public class Strategie {

    protected Agent a;

    Strategie(Agent a) {
        this.a = a;
    }

    abstract public void move();

    public void bestMove() {

        int dX = a.getCoordinatesAgent()[0] - a.getFinalesCoordinatesAgent()[0];
        int dY = a.getCoordinatesAgent()[1] - a.getFinalesCoordinatesAgent()[1];

        if (dX < 0) {
            a.getEnvironnement().moveAgent(a, new int[]{a.getCoordinatesAgent()[0] + 1, a.getCoordinatesAgent()[1]});
        } else if (dX > 0) {
            a.getEnvironnement().moveAgent(a, new int[]{a.getCoordinatesAgent()[0] - 1, a.getCoordinatesAgent()[1]});
        } else if (dY < 0) {
            a.getEnvironnement().moveAgent(a, new int[]{a.getCoordinatesAgent()[0], a.getCoordinatesAgent()[1] + 1});
        } else if (dY > 0) {
            a.getEnvironnement().moveAgent(a, new int[]{a.getCoordinatesAgent()[0], a.getCoordinatesAgent()[1] - 1});
        }

    }
}
