package sample;

import static java.lang.Thread.sleep;

public class Agent implements Runnable {

    final private char lettre;
    final private Environnement e;
    private int[] coordinates;
    private int[] finalesCoordinates;
    private Strategie strategie;

    public Agent(char lettre, Environnement e) {
        this.lettre = lettre;
        this.e = e;
    }

    public int[] getCoordinatesAgent() {
        return coordinates;
    }

    public int[] getFinalesCoordinatesAgent() {
        return finalesCoordinates;
    }


    public void setFinalesCoordinates(int[] finalesCoordinates) {
        this.finalesCoordinates = finalesCoordinates;
    }

    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }


    public char getLettre() {
        return lettre;
    }

    public boolean isWellPlaced() {
        return (coordinates[0] == finalesCoordinates[0] && finalesCoordinates[1] == coordinates[1]);
    }

    public void setStrategie(Strategie s) {
        strategie = s;
    }

    public Environnement getEnvironnement() {
        return e;
    }

    public void push() {
        if (strategie instanceof Strategie2)
            ((Strategie2) strategie).push();
    }

    @Override
    public String toString() {
        return Character.toString(lettre);
    }


    @Override
    public void run() {
        setStrategie(new Strategie2(this));
        System.out.println(this);
        int i = 0;
        while (i < 1000) {
            strategie.move();
            try {
                sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            i++;
        }
    }
}
