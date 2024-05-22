import java.util.Random;

public class Agent {
    // Agent's color
    private String color;
    // Whether Agent cooperate with same colored agents
    private boolean coopSame;
    // Whether Agent cooperate with different colored agents
    private boolean coopDiff;
    // Agent's x coordinate
    private int xcor;
    // Agent's y coordinate
    private int ycor;
    // Agent's shape
    private String shape;
    // Agent's potential to reproduce(PTR) number
    private double ptr;
    // Whether Agent died or not
    private boolean death;
    private int year;

    //Constructor
    public Agent(int xcor, int ycor, String color, String shape, boolean coopSame, 
    boolean coopDiff, double ptr, boolean death){
        this.xcor = xcor;
        this.ycor = ycor;
        this.color = color;
        this.shape = shape;
        this.coopSame = coopSame;
        this.coopDiff = coopDiff;
        this.ptr = ptr;
        this.death = death;
        year = 80;
    }

    public int getYear(){
        return year;
    }

    public void setYear(int year){
        this.year = year;
    }

    // Get Agent's X coordinate
    public int getX() {
        return xcor;
    }

    // Get Agent's Y coordinate
    public int getY() {
        return ycor;
    }

    // Get Agent's Shape
    public String getShape() {
        return shape;
    }

    // Get Agent's color
    public String getColor() {
        return color;
    }

    // Get whether Agent cooperate with same colored agents
    public boolean getCoopSame() {
        return coopSame;
    }

    // Get whether Agent cooperate with different colored agents
    public boolean getCoopDiff() {
        return coopDiff;
    }

    // Get Agent's potential to reproduce(PTR) number
    public double getPTR() {
        return ptr;
    }

    // Get whether Agent died or not
    public boolean isDeath(){
        return death;
    }
    
    // Interaction with neighbor
    public void interact(Agent agent){
        // if the Agent has same color with neighbor, and
        if (color.equals(agent.getColor())) {
            // if this Agent also cooperate with same colored agents, 
            // then this Agent will lose PTR, and neighbor will
            // gain PTR
            if (coopSame) {
                losePTR();
                agent.gainPTR();
            }
        } else {
            // if the Agent has not same color with neighbor, and
            // if this Agent cooperate with different colored agents, 
            // then this Agent will lose PTR, and neighbor will
            // gain PTR
            if (coopDiff) {
                losePTR();
                agent.gainPTR();
            }
        }
    }

    // Increase PTR
    public void gainPTR(){
        this.ptr = ptr + PARAM.getGainOfReciving();
    }

    // Loss PTR
    public void losePTR(){
        this.ptr = ptr - PARAM.getCostOfGiving();
    }

    // Reset PTR
    public void resetPTR() {
        ptr = PARAM.getInitialPTR();
    }

    // Mutate based the mutate rate
    public void mutate(){
        Random random = new Random();
        // Have possibility mutate the color
        if (random.nextFloat() < PARAM.getMutationRate()) {
            String newColor = PARAM.getRandomColor();
            while (newColor.equals(color)) {
                newColor = PARAM.getRandomColor();
            }
            color = newColor;
        }
        // Have possibility mutate whether Agent cooperate with same colored agents
        if (random.nextFloat() < PARAM.getMutationRate()) {
            coopSame = !coopSame;
        }
        // Have possibility mutate whether Agent cooperate with different colored agents
        if (random.nextFloat() < PARAM.getMutationRate()) {
            coopDiff = !coopDiff;
        }
    }
}
