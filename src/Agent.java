import java.awt.Color;
import java.util.Random;

public class Agent {
    private String color;
    private boolean coopSame;
    private boolean coopDiff;
    private String type;
    private int id;
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

    public void move(int dx, int dy) {
        this.xcor += dx;
        this.ycor += dy;
    }
    
    public void interact(Agent agent){

    }

    public void cooperate(Agent agent){

    }

    public void gainPTR(){
        this.ptr = ptr + PARAM.getGainOfReciving();
    }

    public void losePTR(){
        this.ptr = ptr -PARAM.getCostOfGiving();
    }

    public void mutate(){

    }

}
