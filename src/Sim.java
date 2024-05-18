import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sim {
    private List<Agent> numCC;
    private List<Agent> numCD;
    private List<Agent> numDD;
    private List<Agent> numDC;
    private List<Agent> Agents;
    private int numTick;
    private int numEmptyPatch;
    private Map map;

    public Sim(){
        numCC = new ArrayList<>();
        numCD = new ArrayList<>();
        numDD = new ArrayList<>();
        numDC = new ArrayList<>();
        Agents = new ArrayList<>();
        numTick = 0;
        map = new Map(PARAM.getGridSize(), PARAM.getGridSize());
    }

    public void setupEmpty(){
        numCC = new ArrayList<>();
        numCD = new ArrayList<>();
        numDD = new ArrayList<>();
        numDC = new ArrayList<>();
        numTick = 0;
    }

    public void setupFull(){
        //need to clear previous agents first and then initialize
        initalizeAgents();
        System.out.println("numCC: " + numCC.size());
        System.out.println("numCD: " + numCD.size());
        System.out.println("numDD: " + numDD.size());
        System.out.println("numDC: " + numDC.size());
        for(Agent agent: numCC){
            Agents.add(agent);
        }

        for(Agent agent: numCD){
            Agents.add(agent);
        }

        for(Agent agent: numDD){
            Agents.add(agent);
        }

        for(Agent agent: numDC){
            Agents.add(agent);
        }
        System.out.println("Agents: " + Agents.size());
        numTick = 0;
    }

    public void go(){
        numTick += 1;
        immigration();
        interaction();
        reproduction();
        death();
    }
    public List<Agent> getAgents(){
        return Agents;
    }
    public int getNumTick(){
        return numTick;
    }

    public void initalizeAgents(){
        Random random = new Random();
        for (int i = 0; i < PARAM.getNumAgents(); i++) {
            int x = random.nextInt(PARAM.getGridSize());
            int y = random.nextInt(PARAM.getGridSize());
            String color = PARAM.getRandomColor();
            //String shape = PARAM.getRandomShape();
            boolean coopSame = PARAM.getImmigrantChanceCooprateWithSame();
            boolean coopDiff = PARAM.getImmigrantChanceCooprateWithDiff();
			boolean death = PARAM.die();
			double ptr = PARAM.getInitialPTR();
            // if the agent cooperates with same they are a circle
            if(coopSame && coopDiff){
                //filled in circle (altruist)
                numCC.add(new Agent(x, y, color, "circle", coopSame, 
			coopDiff, ptr, death));
            }else if(coopSame && !coopDiff){
                //empty circle (ethnocentric)
                numCD.add(new Agent(x, y, color, "circle 2", coopSame, 
			coopDiff, ptr, death));
            }else if(!coopSame && coopDiff){
                //filled in square (cosmopolitan)
                numDC.add(new Agent(x, y, color, "square", coopSame, 
			coopDiff, ptr, death));
            }else{
                //empty square (egoist)
                numDD.add(new Agent(x, y, color, "square 2", coopSame, 
			coopDiff, ptr, death));
            }
        }
    }

    public void updateShape(Agent agent, Graphics g){

            int x = agent.getX() * PARAM.getCellSize();
            int y = agent.getY() * PARAM.getCellSize();

            g.setColor(getColor(agent.getColor()));
            if(agent.getShape().equals("circle")){
                g.fillOval(x, y, PARAM.getCellSize(), PARAM.getCellSize());
            }else if(agent.getShape().equals("square")){
                g.fillRect(x, y, PARAM.getCellSize(), PARAM.getCellSize());
            }else if(agent.getShape().equals("square 2")){
                g.drawRect(x, y, PARAM.getCellSize(), PARAM.getCellSize());
            }else{
                g.drawOval(x, y, PARAM.getCellSize(), PARAM.getCellSize());
            }
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
            String color = PARAM.getRandomColor();
            //String shape = PARAM.getRandomShape();
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
            }else if(coopSame && !coopDiff){
                //empty circle (ethnocentric)
                Agent agent = new Agent(x, y, color, "circle 2", coopSame, 
			    coopDiff, ptr, death);
                map.setElement(x, y, agent);
            }else if(!coopSame && coopDiff){
                //filled in square (cosmopolitan)
                Agent agent = new Agent(x, y, color, "square", coopSame, 
			    coopDiff, ptr, death);
                map.setElement(x, y, agent);
            }else{
                //empty square (egoist)
                Agent agent = new Agent(x, y, color, "square 2", coopSame, 
			    coopDiff, ptr, death);
                map.setElement(x, y, agent);
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
