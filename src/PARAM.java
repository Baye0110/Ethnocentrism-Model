import java.util.Random;

public class PARAM {
    private final static double MUTATION_RATE = 0.005;
    private final static double DEATH_RATE = 0.1;
    private final static int IMMIGRANTS_PER_DAY = 1;
    private final static double INITIAL_PTR = 0.12;
    private final static double COST_OF_GIVING = 0.01;
    private final static double GAIN_OF_RECEIVING = 0.03;
    private final static double IMMIGRANT_CHANCE_COOPERATE_WITH_SAME = 0.5;
    private final static double IMMIGRANT_CHANCE_COOPERATE_WITH_DIFFERENT = 0.45;
    private final static String[] COLORS = {"red", "blue", "yellow", "green"};
    private final static String[] SHAPES = {"circle", "circle 2", "square", "square 2"};
    private static final int GRID_SIZE = 20;
    private static final int CELL_SIZE = 30;
    private static final int NUM_AGENTS = 100;
    private static final int TIMER_DELAY = 10; // milliseconds
    //public int NUM_TICK = 0;
    //public int NUM_ROW;
    //public int NUM_COL;

    public static double getMutationRate(){
        return MUTATION_RATE;
    }
    public static double getDeathRate(){
        return DEATH_RATE;
    }
    public static int getImmgrantsPerDay(){
        return IMMIGRANTS_PER_DAY;
    }
    public static double getInitialPTR(){
        return INITIAL_PTR;
    }
    public static double getCostOfGiving(){
        return COST_OF_GIVING;
    }
    public static double getGainOfReciving(){
        return GAIN_OF_RECEIVING;
    }
    public static boolean getImmigrantChanceCooprateWithSame(){
        Random random = new Random();
        return random.nextDouble(1) < IMMIGRANT_CHANCE_COOPERATE_WITH_SAME;
    }
    public static boolean getImmigrantChanceCooprateWithDiff(){
        Random random = new Random();
        return random.nextDouble(1) < IMMIGRANT_CHANCE_COOPERATE_WITH_DIFFERENT;
    }
    public static int getGridSize(){
        return GRID_SIZE;
    }
    public static int getCellSize(){
        return CELL_SIZE;
    }
    public static int getNumAgents(){
        return NUM_AGENTS;
    }
    public static int getTimerDelay(){
        return TIMER_DELAY;
    }

    public static String getRandomColor() {
        Random random = new Random();
        return COLORS[random.nextInt(COLORS.length)];
    }
    public static String getRandomShape() {
        Random random = new Random();
        return SHAPES[random.nextInt(SHAPES.length)];
    }
    public static boolean die(){
        Random random = new Random();
        return random.nextDouble(1.0) < PARAM.getDeathRate();
    }
}
