import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sim {
    private int numCC;
    private int numCD;
    private int numDD;
    private int numDC;
    private int numTick;
    private int numEmptyPatch;
    private Map map;

    public Sim(){
        numCC = 0;
        numCD = 0;
        numDD = 0;
        numDC = 0;
        numTick = 0;
        numEmptyPatch = 0;
        map = new Map(PARAM.getGridSize(), PARAM.getGridSize());
    }

    public void setupEmpty(){
        map.clearAllElements();
        numCC = 0;
        numCD = 0;
        numDD = 0;
        numDC = 0;
        numTick = 0;
        numEmptyPatch = PARAM.getGridSize() * PARAM.getGridSize();
    }

    public void setupFull(){
        //need to clear previous agents first and then initialize
        map.clearAllElements();
        initalizeAgents();
        numTick = 0;
        numEmptyPatch = 0;
    }

    public void go(){
        numTick += 1;
        immigration();
        resetPTR();
        interaction();
        reproduction();

        death();
    }

    public int getNumTick(){
        return numTick;
    }

    public int getNumCC() {
        return numCC;
    }

    public int getNumCD() {
        return numCD;
    }

    public int getNumDD() {
        return numDD;
    }

    public int getNumDC() {
        return numDC;
    }

    public void initalizeAgents(){
        for (int i = 0; i < PARAM.getGridSize(); i++) {
            for (int j = 0; j < PARAM.getGridSize(); j++) {
                setupAgent(i, j);
            }
        }
    }

    public void setupAgent(int x, int y) {
        String color = PARAM.getRandomColor();
        boolean coopSame = PARAM.getImmigrantChanceCooprateWithSame();
        boolean coopDiff = PARAM.getImmigrantChanceCooprateWithDiff();
        boolean death = false;
        double ptr = PARAM.getInitialPTR();
        // if the agent cooperates with same they are a circle
        if(coopSame && coopDiff){
            //filled in circle (altruist)
            Agent agent = new Agent(x, y, color, "circle", coopSame,
                    coopDiff, ptr, death);
            map.setElement(x, y, agent);
            numCC++;
        }else if(coopSame && !coopDiff){
            //empty circle (ethnocentric)
            Agent agent = new Agent(x, y, color, "circle 2", coopSame,
                    coopDiff, ptr, death);
            map.setElement(x, y, agent);
            numCD++;
        }else if(!coopSame && coopDiff){
            //filled in square (cosmopolitan)
            Agent agent = new Agent(x, y, color, "square", coopSame,
                    coopDiff, ptr, death);
            map.setElement(x, y, agent);
            numDC++;
        }else{
            //empty square (egoist)
            Agent agent = new Agent(x, y, color, "square 2", coopSame,
                    coopDiff, ptr, death);
            map.setElement(x, y, agent);
            numDD++;
        }
    }

    public void updateShape(Agent agent, Graphics g){
        int x = agent.getX() * PARAM.getCellSize();
        int y = agent.getY() * PARAM.getCellSize();

        g.setColor(getColor(agent.getColor()));
        if (agent.getShape().equals("circle")){
            g.fillOval(x, y, PARAM.getCellSize(), PARAM.getCellSize());
        } else if (agent.getShape().equals("square")){
            g.fillRect(x, y, PARAM.getCellSize(), PARAM.getCellSize());
        } else if (agent.getShape().equals("square 2")){
            g.drawRect(x, y, PARAM.getCellSize(), PARAM.getCellSize());
        } else {
            g.drawOval(x, y, PARAM.getCellSize(), PARAM.getCellSize());
        }
    }

    public Map getMap(){
        return map;
    }
    
    private Color getColor(String color) {
        switch (color) {
            case "red":
                return Color.RED;
            case "blue":
                return Color.BLUE;
            case "yellow":
                return Color.YELLOW;
            case "green":
                return Color.GREEN;
        }
        return null;
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
            setupAgent(x, y);
            numEmptyPatch--;
            System.out.println("immigration: " + i);
        }
    }

    public void resetPTR() {
        for (int i = 0; i < PARAM.getGridSize(); i++) {
            for (int j = 0; j < PARAM.getGridSize(); j++) {
                Agent agent = map.getElement(i, j);
                if (agent != null) {
                    agent.resetPTR();
                }

            }
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
                Agent agent = map.getElement(i, j);
                if (agent != null && random.nextFloat() < agent.getPTR()) {
                    generateNeighbor(agent);
                }
            }
        }
    }

    public void generateNeighbor(Agent agent) {
        int x = agent.getX();
        int y = agent.getY();
        int xNeighbor = x;
        int yNeighbor = y;

        if (x - 1 >= 0 && map.isEmpty(x - 1, y)) {
            xNeighbor = x - 1;
            yNeighbor = y;
        } else if (x + 1 < PARAM.getGridSize() && map.isEmpty(x + 1, y)) {
            xNeighbor = x + 1;
            yNeighbor = y;
        } else if (y - 1 >= 0 && map.isEmpty(x, y - 1)) {
            xNeighbor = x;
            yNeighbor = y - 1;
        } else if (y + 1 < PARAM.getGridSize() && map.isEmpty(x, y + 1)) {
            xNeighbor = x;
            yNeighbor = y + 1;
        }

        if (xNeighbor != x || yNeighbor != y) {
            Agent neighbor = new Agent(xNeighbor, yNeighbor, agent.getColor(), agent.getShape(),
                    agent.getCoopSame(), agent.getCoopDiff(), agent.getPTR(), false);
            // mutate neighbor
            neighbor.mutate();
            map.setElement(xNeighbor, yNeighbor, neighbor);
            numEmptyPatch--;

            if (neighbor.getCoopSame() && neighbor.getCoopDiff()) {
                numCC++;
            } else if (neighbor.getCoopSame() && !neighbor.getCoopDiff()) {
                numCD++;
            } else if (!neighbor.getCoopSame() && neighbor.getCoopDiff()) {
                numDC++;
            } else {
                numDD++;
            }
        }
    }

    public void generateNeighbor1(Agent agent) {
        int x = agent.getX();
        int y = agent.getY();
        List<Integer> xEmptyNeighbor = new ArrayList<>();
        List<Integer> yEmptyNeighbor = new ArrayList<>();

        if (x - 1 >= 0 && map.isEmpty(x - 1, y)) {
            xEmptyNeighbor.add(x - 1);
            yEmptyNeighbor.add(y);
        }
        if (x + 1 < PARAM.getGridSize() && map.isEmpty(x + 1, y)) {
            xEmptyNeighbor.add(x + 1);
            yEmptyNeighbor.add(y);
        }
        if (y - 1 >= 0 && map.isEmpty(x, y - 1)) {
            xEmptyNeighbor.add(x);
            yEmptyNeighbor.add(y - 1);
        }
        if (y + 1 < PARAM.getGridSize() && map.isEmpty(x, y + 1)) {
            xEmptyNeighbor.add(x);
            yEmptyNeighbor.add(y + 1);
        }

        Random random = new Random();

        int size = xEmptyNeighbor.size();
        int xNeighbor = 0;
        int yNeighbor = 0;
        if (size != 0) {
            int index = random.nextInt(size);
            xNeighbor = xEmptyNeighbor.get(index);
            yNeighbor = yEmptyNeighbor.get(index);

            Agent neighbor = new Agent(xNeighbor, yNeighbor, agent.getColor(), agent.getShape(),
                    agent.getCoopSame(), agent.getCoopDiff(), agent.getPTR(), false);
            // mutate neighbor
            neighbor.mutate();
            map.setElement(xNeighbor, yNeighbor, neighbor);
            numEmptyPatch--;

            if (neighbor.getCoopSame() && neighbor.getCoopDiff()) {
                numCC++;
            } else if (neighbor.getCoopSame() && !neighbor.getCoopDiff()) {
                numCD++;
            } else if (!neighbor.getCoopSame() && neighbor.getCoopDiff()) {
                numDC++;
            } else {
                numDD++;
            }
        }
    }

    public void death(){
        for (int i = 0; i < PARAM.getGridSize(); i++) {
            for (int j = 0; j < PARAM.getGridSize(); j++) {
                Agent agent = map.getElement(i, j);
                if (agent != null && PARAM.die()) {
                    if (agent.getCoopSame() && agent.getCoopDiff()) {
                        numCC--;
                    } else if (agent.getCoopSame() && !agent.getCoopDiff()) {
                        numCD--;
                    } else if (!agent.getCoopSame() && agent.getCoopDiff()) {
                        numDC--;
                    } else {
                        numDD--;
                    }

                    map.setElement(i, j, null);
                    numEmptyPatch++;
                    System.out.println("die: " + i + " " + j);
                }
            }
        }
    }
}
