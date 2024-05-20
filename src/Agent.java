import java.util.Random;

public class Agent {
    private String color;
    private boolean coopSame;
    private boolean coopDiff;
    private int xcor;
    private int ycor;
    private String shape;
    private double ptr;
    private boolean death;

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
    }

    public int getX() {
        return xcor;
    }

    public int getY() {
        return ycor;
    }

    public String getShape() {
        return shape;
    }

    public String getColor() {
        return color;
    }

    public boolean getCoopSame() {
        return coopSame;
    }

    public boolean getCoopDiff() {
        return coopDiff;
    }

    public double getPTR() {
        return ptr;
    }

    public boolean isDeath(){
        return death;
    }
    
    public void interact(Agent agent){
        if (color.equals(agent.getColor())) {
            if (coopSame) {
                losePTR();
                agent.gainPTR();
            }
        } else {
            if (coopDiff) {
                losePTR();
                agent.gainPTR();
            }
        }
    }

    public void gainPTR(){
        this.ptr = ptr + PARAM.getGainOfReciving();
    }

    public void losePTR(){
        this.ptr = ptr - PARAM.getCostOfGiving();
    }

    public void resetPTR() {
        ptr = PARAM.getInitialPTR();
    }

    public void mutate(){
        Random random = new Random();
        if (random.nextFloat() < PARAM.getMutationRate()) {
            String newColor = PARAM.getRandomColor();
            while (newColor.equals(color)) {
                newColor = PARAM.getRandomColor();
            }
            color = newColor;
        }
        if (random.nextFloat() < PARAM.getMutationRate()) {
            coopSame = !coopSame;
        }
        if (random.nextFloat() < PARAM.getMutationRate()) {
            coopDiff = !coopDiff;
        }
    }
}
