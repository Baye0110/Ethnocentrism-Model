import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sim {
    // The number of altruist
    private int numCC;
    // The number of ethnocentric
    private int numCD;
    // The number of egoist
    private int numDD;
    // The number of cosmopolitan
    private int numDC;
    // The number of tick
    private int numTick;
    // The number of empty patch/ spot on Map
    private int numEmptyPatch;
    // The map used to store Agents
    private Map map;
    // Random function used for possibility
    private Random random;

    // Constructor
    public Sim(){
        numCC = 0;
        numCD = 0;
        numDD = 0;
        numDC = 0;
        numTick = 0;
        numEmptyPatch = 0;
        map = new Map(PARAM.getGridSize(), PARAM.getGridSize());
        random = new Random();
    }

    // Set all Empty of Agents, Set numbers of agents to 0
    // Set Tick to 0
    // Set number of empty Path to whole frame
    public void setupEmpty(){
        map.clearAllElements();
        numCC = 0;
        numCD = 0;
        numDD = 0;
        numDC = 0;
        numTick = 0;
        numEmptyPatch = PARAM.getGridSize() * PARAM.getGridSize();
    }

    // Set full of Agents and reset tick to 0 and Empty Path to 0
    public void setupFull(){
        //need to clear previous agents first and then initialize
        map.clearAllElements();
        initalizeAgents();
        numTick = 0;
        numEmptyPatch = 0;
    }

    // Action method
    // list all actions that agent can do
    public void go(){
        numTick += 1;
        immigration();
        resetPTR();
        interaction();
        reproduction();
        death();
    }

    // Create agent with each coordinate on Map
    public void initalizeAgents(){
        for (int i = 0; i < PARAM.getGridSize(); i++) {
            for (int j = 0; j < PARAM.getGridSize(); j++) {
                setupAgent(i, j);
            }
        }
    }

    // Create a agent
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

    // Draw Shape of agent on Frame
    public void updateShape(Agent agent, Graphics g){
        int x = agent.getX() * PARAM.getCellSize();
        int y = agent.getY() * PARAM.getCellSize();

        // set color to the shape
        g.setColor(getColor(agent.getColor()));
        // Draw shape based on the name of shape
        // circle => filled oval
        // square => filled square
        // circle 2 => oval
        // square 2 => square
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

    // Searching whether this is a empty coordinate
    // immigration if yes, and number of empty patch - 1
    public void immigration(){
        // Get the minimum number among empty patch and the number of immigration per day
        int numImmi = Math.min(numEmptyPatch, PARAM.getImmgrantsPerDay());
        for (int i = 0; i < numImmi; i++) {
            int x = random.nextInt(PARAM.getGridSize());
            int y = random.nextInt(PARAM.getGridSize());
            //Keep searching if it is not empty
            while (!map.isEmpty(x, y)) {
                x = random.nextInt(PARAM.getGridSize());
                y = random.nextInt(PARAM.getGridSize());
            }
            setupAgent(x, y);
            numEmptyPatch--;
        }
    }

    // Reset the PTR if agent is not null
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

    // Interaction with neighbor(up,down, right, left)
    // Agent will interacte with neighbor if it has neighbor
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

    // Agent has chance to reproduce if it has empty spot nearby
    public void reproduction(){
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

    // Searching possible empty spot nearby and generate a neighbor
    public void generateNeighbor(Agent agent) {
        int x = agent.getX();
        int y = agent.getY();
        List<Integer> xEmptyNeighbor = new ArrayList<>();
        List<Integer> yEmptyNeighbor = new ArrayList<>();

        // Adding possible empty spot nearby
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
        // Generate neighbor if the list not empty (has empty spot nearby)
        int size = xEmptyNeighbor.size();
        if (size != 0) {
            int index = random.nextInt(size);
            int xNeighbor = xEmptyNeighbor.get(index);
            int yNeighbor = yEmptyNeighbor.get(index);

            Agent neighbor = new Agent(xNeighbor, yNeighbor, agent.getColor(), agent.getShape(),
                    agent.getCoopSame(), agent.getCoopDiff(), agent.getPTR(), false);
            // Have a chance to nutate
            neighbor.mutate();
            // add it on Map
            map.setElement(xNeighbor, yNeighbor, neighbor);
            // Empty patch - 1
            numEmptyPatch--;
            // Update Agents number
            updateNum1(neighbor);
        }
    }

    // Update agent number
    public void updateNum1(Agent agent){
        // if it is altruist, NumCC + 1
        // if it is ethnocentric, NumCD + 1
        // if it is egoist, NumDD + 1
        // if it is cosmopolitan, NumDC + 1
        if (agent.getCoopSame() && agent.getCoopDiff()) {
            numCC++;
        } else if (agent.getCoopSame() && !agent.getCoopDiff()) {
            numCD++;
        } else if (!agent.getCoopSame() && agent.getCoopDiff()) {
            numDC++;
        } else {
            numDD++;
        }
    }

    // Agent has chance to death
    public void death(){
        for (int i = 0; i < PARAM.getGridSize(); i++) {
            for (int j = 0; j < PARAM.getGridSize(); j++) {
                Agent agent = map.getElement(i, j);
                // if die
                // update agent number
                // set 2D map correspond spot to empty
                // empty patch + 1
                if (agent != null && PARAM.die()) {
                    updateNum2(agent);
                    map.setElement(i, j, null);
                    numEmptyPatch++;
                }
            }
        }
    }

    // Update number of Agent who is died
    // if it is altruist, NumCC - 1
    // if it is ethnocentric, NumCD - 1
    // if it is egoist, NumDD - 1
    // if it is cosmopolitan, NumDC - 1
    public void updateNum2(Agent agent){
        if (agent.getCoopSame() && agent.getCoopDiff()) {
            numCC--;
        } else if (agent.getCoopSame() && !agent.getCoopDiff()) {
            numCD--;
        } else if (!agent.getCoopSame() && agent.getCoopDiff()) {
            numDC--;
        } else {
            numDD--;
        }
    }

    // Get the number of altruist
    public int getNumCC() {
        return numCC;
    }

    // Get the number of ethnocentric
    public int getNumCD() {
        return numCD;
    }
    
    // Get the number of egoist
    public int getNumDD() {
        return numDD;
    }

    // Get the number of cosmopolitan
    public int getNumDC() {
        return numDC;
    }

    // Get the 2D Map
    public Map getMap(){
        return map;
    }

    // Get the number of tick
    public int getNumTick(){
        return numTick;
    }

    // Get the color
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
}
