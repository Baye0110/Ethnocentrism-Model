import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import java.awt.*;

public class Main extends JPanel implements ActionListener{
	private List<Agent> Agents;

	public Main() {
        Agents = new ArrayList<>();
        initalizeAgents();

        Timer timer = new Timer(PARAM.getTimerDelay(), this);
        timer.start();
    }

	public void outputResults(){

	}

	 public void initalizeAgents(){
        Random random = new Random();
        for (int i = 0; i < PARAM.getNumAgents(); i++) {
            int x = random.nextInt(PARAM.getGridSize());
            int y = random.nextInt(PARAM.getGridSize());
            String color = PARAM.getRandomColor();
            String shape = PARAM.getRandomShape();
            boolean coopSame = PARAM.getImmigrantChanceCooprateWithSame();
            boolean coopDiff = PARAM.getImmigrantChanceCooprateWithDiff();
			boolean death = PARAM.die();
			double ptr = PARAM.getInitialPTR();
            Agents.add(new Agent(x, y, color, shape, coopSame, 
			coopDiff, ptr, death));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Agent agent : Agents) {
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
            default:
                return Color.BLACK;
        }
    }

	@Override
    public void actionPerformed(ActionEvent e) {
        moveAgents();
        updateStats();
        repaint();
    }

	private void updateStats() {
        // Update statistics here if needed
    }

	private void moveAgents() {
        Random random = new Random();
        for (Agent agent : Agents) {
            int dx = random.nextInt(3) - 1; // Random movement in range [-1, 1]
            int dy = random.nextInt(3) - 1; // Random movement in range [-1, 1]
            agent.move(dx, dy);
        }
    }


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Agent Simulation");
            Main gui = new Main();
            frame.add(gui);
            frame.setSize(PARAM.getGridSize() * PARAM.getCellSize(), PARAM.getGridSize() * PARAM.getCellSize());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });

	}
}
