package sample;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Environnement {
    private final Object[][] map;
    private final Object[][] mapFinale;
    private List<Character> alphabet = "abcdefghijklmnopqrstuvwxyz".chars()
            .mapToObj(e -> (char) e).collect(Collectors.toList());


    public Environnement(int x, int y, int nbAgent) {

        final ArrayList<Agent> agents = new ArrayList<>();

        map = new Object[x][y];
        mapFinale = new Object[x][y];

        for (int i = 0; i < map.length; ++i) {
            Arrays.fill(map[i], '.');
            Arrays.fill(mapFinale[i], '.');
        }


        for (int i = 0; i < nbAgent; i++) {
            Random rand = new Random();
            int xAgent;
            int yAgent;
            do {

                xAgent = rand.nextInt(x);
                yAgent = rand.nextInt(y);

            } while (map[xAgent][yAgent] instanceof Agent);

            int indexLettre = rand.nextInt(alphabet.size());

            Agent agent = new Agent(alphabet.get(indexLettre), this);
            agent.setCoordinates(new int[]{xAgent, yAgent});
            agents.add(agent);

            map[xAgent][yAgent] = agent;

            alphabet.remove(indexLettre);
        }

        for (int i = 0; i < nbAgent; i++) {
            Random rand = new Random();
            int xAgent;
            int yAgent;
            do {

                xAgent = rand.nextInt(x);
                yAgent = rand.nextInt(y);

            } while (mapFinale[xAgent][yAgent] instanceof Agent);

            mapFinale[xAgent][yAgent] = agents.get(i);
            agents.get(i).setFinalesCoordinates(new int[]{xAgent, yAgent});

        }

        System.out.println(agents);
        System.out.println(this);

        for (Agent agent : agents) {
            Thread thread = new Thread(agent);
            thread.start();
        }

    }


    public boolean isFinished() {
        return map == mapFinale;
    }


    public Object[][] getMap() {
        return map;
    }

    public Object[][] getMapFinale() {
        return mapFinale;
    }

    public synchronized boolean moveAgent(Agent a, int[] newCoordinates) {
        if (newCoordinates[0] >= map.length || newCoordinates[1] >= map[0].length || newCoordinates[0] < 0 || newCoordinates[1] < 0) {
            return false;
        }

        if (!(map[newCoordinates[0]][newCoordinates[1]] instanceof Agent)) {
            map[a.getCoordinatesAgent()[0]][a.getCoordinatesAgent()[1]] = '.';
            map[newCoordinates[0]][newCoordinates[1]] = a;

            a.setCoordinates(new int[]{newCoordinates[0], newCoordinates[1]});

        } else {
            System.out.println("here");
            ((Agent) map[newCoordinates[0]][newCoordinates[1]]).push();
        }
        System.out.println(this);
        return true;
    }


    @Override
    public String toString() {

        StringBuilder toString = new StringBuilder("Map initiale :\n");
        for (Object[] objects : map) {
            for (Object object : objects) {
                toString.append(object);
            }
            toString.append('\n');
        }

        /*toString.append("Map Finale :\n");

        for (Object[] objects : mapFinale) {
            for (Object object : objects) {
                toString.append(object);
            }
            toString.append('\n');
        }*/

        return toString.toString();
    }


}
