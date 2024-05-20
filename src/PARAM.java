import java.util.Random;

public class PARAM {
    // Mutation rate for Agent
    private final static double MUTATION_RATE = 0.005;
    // Death rate for Agent
    private final static double DEATH_RATE = 0.1;
    // Number of immigration per day
    private final static int IMMIGRANTS_PER_DAY = 1;
    // Agent's initial potential to reproduce(PTR) number
    private final static double INITIAL_PTR = 0.12;
    // The cost when choose to help neighbor
    private final static double COST_OF_GIVING = 0.01;
    // The gain when the agent get helped
    private final static double GAIN_OF_RECEIVING = 0.03;
    // The chance of Agent cooperate with same color Agent
    private final static double IMMIGRANT_CHANCE_COOPERATE_WITH_SAME = 0.5;
    // The chance of Agent cooperate with different color Agent
    private final static double IMMIGRANT_CHANCE_COOPERATE_WITH_DIFFERENT = 0.5;
    // Agent's possible color 
    private final static String[] COLORS = {"red", "blue", "yellow", "green"};
    // Agent's possible shape
    private final static String[] SHAPES = {"circle", "circle 2", "square", "square 2"};
    // Frame's grid size, number of agent per row and column
    private static final int GRID_SIZE = 50;
    // Frame's cell size, agent size
    private static final int CELL_SIZE = 10;
    // Timer delay => speed of moving for Agent on Frame
    private static final int TIMER_DELAY = 10; // milliseconds

    // Get mutation rate
    public static double getMutationRate(){
        return MUTATION_RATE;
    }

    // Get death rate
    public static double getDeathRate(){
        return DEATH_RATE;
    }

    //get immigrant per day
    public static int getImmgrantsPerDay(){
        return IMMIGRANTS_PER_DAY;
    }

    // Get initial PTR
    public static double getInitialPTR(){
        return INITIAL_PTR;
    }

    // Get cost of giving
    public static double getCostOfGiving(){
        return COST_OF_GIVING;
    }

    // Get gain of recieving
    public static double getGainOfReciving(){
        return GAIN_OF_RECEIVING;
    }

    // Get whether Agent cooperate with same color
    public static boolean getImmigrantChanceCooprateWithSame(){
        Random random = new Random();
        return random.nextDouble() < IMMIGRANT_CHANCE_COOPERATE_WITH_SAME;
    }

    // Get whether Agent cooperate with different color
    public static boolean getImmigrantChanceCooprateWithDiff(){
        Random random = new Random();
        return random.nextDouble() < IMMIGRANT_CHANCE_COOPERATE_WITH_DIFFERENT;
    }

    // Get the grid size of Frame
    public static int getGridSize(){
        return GRID_SIZE;
    }

    // Get the cell size of Frame
    public static int getCellSize(){
        return CELL_SIZE;
    }

    // Get the timer delay
    public static int getTimerDelay(){
        return TIMER_DELAY;
    }

    // Get the random color from color array
    public static String getRandomColor() {
        Random random = new Random();
        return COLORS[random.nextInt(COLORS.length)];
    }

    // Get the random shape from shape array
    public static String getRandomShape() {
        Random random = new Random();
        return SHAPES[random.nextInt(SHAPES.length)];
    }

    // Get die
    // Has possibility of die
    public static boolean die(){
        Random random = new Random();
        return random.nextDouble() < PARAM.getDeathRate();
    }
}
