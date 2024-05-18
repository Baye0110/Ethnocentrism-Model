import java.util.Random;

public class Sim {
    private int numCC;
    private int numCD;
    private int numDD;
    private int numDC;
    private int numTick;
    private int numEmptyPatch;
    private Map map = new Map(PARAM.getGridSize(), PARAM.getGridSize());

    public void setupEmpty(){
    }

    public void setupFull(){
    }

    public void go(){
        immigration();
        interaction();
        reproduction();
        death();
    }

    public void immigration(){
        int numImmi = Math.min(numEmptyPatch, PARAM.getImmgrantsPerDay());
        Random random = new Random();
        for (int i = 0; i < numImmi; i++) {
            int x = random.nextInt(PARAM.getGridSize());
            int y = random.nextInt(PARAM.getGridSize());
            while (!map.isEmpty(x, y)) {
                x = random.nextInt(PARAM.getGridSize());
                y = random.nextInt(PARAM.getGridSize());
            }
            String color = PARAM.getRandomColor();
            String shape = PARAM.getRandomShape();
            boolean coopSame = PARAM.getImmigrantChanceCooprateWithSame();
            boolean coopDiff = PARAM.getImmigrantChanceCooprateWithDiff();
            boolean death = false;
            double ptr = PARAM.getInitialPTR();
            Agent agent = new Agent(x, y, color, shape, coopSame,
                    coopDiff, ptr, death);
            map.setElement(x, y, agent);
        }
    }

    public void interaction(){
        for (int i = 0; i < PARAM.getGridSize(); i++) {
            for (int j = 0; j < PARAM.getGridSize(); j++) {
                // has element at row i and col j
                if (!map.isEmpty(i, j)) {
                    Agent agent = map.getElement(i, j);
                    // agent interact with neighbors
                    if (i - 1 >= 0 && !map.isEmpty(i - 1, j)) {
                        Agent neighbor = map.getElement(i - 1, j);
                        agent.interact(neighbor);
                    }
                    if (i + 1 < PARAM.getGridSize() && !map.isEmpty(i + 1, j)) {
                        Agent neighbor = map.getElement(i + 1, j);
                        agent.interact(neighbor);
                    }
                    if (j - 1 >= 0 && !map.isEmpty(i, j - 1)) {
                        Agent neighbor = map.getElement(i, j - 1);
                        agent.interact(neighbor);
                    }
                    if (j + 1 < PARAM.getGridSize() && !map.isEmpty(i, j + 1)) {
                        Agent neighbor = map.getElement(i, j + 1);
                        agent.interact(neighbor);
                    }
                }
            }
        }
    }

    public void reproduction(){
        Random random = new Random();
        for (int i = 0; i < PARAM.getGridSize(); i++) {
            for (int j = 0; j < PARAM.getGridSize(); j++) {
                // has element at row i and col j
                if (!map.isEmpty(i, j)) {
                    Agent agent = map.getElement(i, j);
                    if (random.nextFloat() < agent.getPTR()) {
                        // get a random empty neighbor
                        int xNeighbor = random.nextInt(2) * 2 - 1 + i;
                        int yNeighbor = random.nextInt(2) * 2 - 1 + j;
                        while (xNeighbor < 0 || xNeighbor >= PARAM.getGridSize() ||
                                yNeighbor < 0 || yNeighbor >= PARAM.getGridSize() ||
                                !map.isEmpty(xNeighbor, yNeighbor)) {
                            xNeighbor = random.nextInt(2) * 2 - 1 + i;
                            yNeighbor = random.nextInt(2) * 2 - 1 + j;
                        }
                        Agent neighbor = new Agent(xNeighbor, yNeighbor, agent.getColor(), agent.getShape(),
                                agent.getCoopSame(), agent.getCoopDiff(), agent.getPTR(), false);
                        // mutate neighbor
                        neighbor.mutate();
                        map.setElement(xNeighbor, yNeighbor, neighbor);
                    }
                }
            }
        }
    }

    public void death(){
        Random random = new Random();
        for (int i = 0; i < PARAM.getGridSize(); i++) {
            for (int j = 0; j < PARAM.getGridSize(); j++) {
                if (random.nextDouble(1.0) < PARAM.getDeathRate()) {
                    map.setElement(i, j, null);
                }
            }
        }
    }
}
