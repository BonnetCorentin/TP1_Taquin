public class Agent implements Runnable {

    final private char lettre;

    public Agent(char lettre) {
        this.lettre = lettre;
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
        System.out.println("Start of agent " + lettre);
    }
}
