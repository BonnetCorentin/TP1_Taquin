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

            Agent agent = new Agent(alphabet.get(indexLettre));
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

        for (Agent agent : agents) {
            Thread thread = new Thread(agent);
            thread.start();

        }

    }


    @Override
    public String toString() {

        String toString = "Map initiale :\n";
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                toString = toString + map[i][j];
            }
            toString = toString + '\n';
        }

        toString = toString + "Map Finale :\n";

        for (int i = 0; i < mapFinale.length; i++) {
            for (int j = 0; j < mapFinale[i].length; j++) {
                toString = toString + mapFinale[i][j];
            }
            toString = toString + '\n';
        }

        return toString;
    }

}
