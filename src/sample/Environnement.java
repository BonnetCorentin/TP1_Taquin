package sample;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Environnement {
    private final Object[][] map;
    private final Object[][] mapFinale;
    private final ArrayList<Agent> agents = new ArrayList<>();
    private List<Character> alphabet = "abcdefghijklmnopqrstuvwxyz".chars()
            .mapToObj(e -> (char) e).collect(Collectors.toList());
    private Boolean isOver = false;


    public Environnement(int x, int y, int nbAgent) {
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

        }

        System.out.println(agents);
        startThread();

    }

//    public void start() {
//        new Thread(this).start();
//        startThread();
//    }

    private void startThread() {
        for (Agent agent : agents) {
            Thread thread = new Thread(agent);
            thread.start();
        }
    }
//
//    @Override
//    public void run() {
//        while (keepGoing()) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ex) {
//
//            }
//
//        }
//    }

    public Boolean keepGoing() {
        return this.isOver;
    }

    public Object[][] getMap() {
        return map;
    }

    public Object[][] getMapFinale() {
        return mapFinale;
    }

    private int[] getCoordinatesAgent(Agent a, Object[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == a) {
                    return new int[]{i, j};
                }
            }

        }
        return new int[]{-1, -1};
    }

    private synchronized Boolean moveAgent(Agent a, int[] newCoordinates) throws InterruptedException {
        int[] coordinates = getCoordinatesAgent(a, map);

        if (!(map[newCoordinates[0]][newCoordinates[1]] instanceof Agent)) {
            map[coordinates[0]][coordinates[1]] = '.';
            map[newCoordinates[0]][newCoordinates[1]] = a;
            System.out.println(this);
            return true;
        } else {
            return false;
        }


    }


    public void bestMove(Agent a) throws InterruptedException {
        int[] finalesCoordinates = getCoordinatesAgent(a, mapFinale);
        int[] coordinates = getCoordinatesAgent(a, map);

        int dX = coordinates[0] - finalesCoordinates[0];
        int dY = coordinates[1] - finalesCoordinates[1];

        if (dX < 0) {
            moveAgent(a, new int[]{coordinates[0] + 1, coordinates[1]});
        } else if (dX > 0) {
            moveAgent(a, new int[]{coordinates[0] - 1, coordinates[1]});
        } else if (dY < 0) {
            moveAgent(a, new int[]{coordinates[0], coordinates[1] + 1});
        } else if (dY > 0) {
            moveAgent(a, new int[]{coordinates[0], coordinates[1] - 1});
        }
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

        toString.append("Map Finale :\n");

        for (Object[] objects : mapFinale) {
            for (Object object : objects) {
                toString.append(object);
            }
            toString.append('\n');
        }

        return toString.toString();
    }


}
