import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import java.awt.*;

public class Main extends JPanel implements ActionListener{
	private List<Agent> Agents;
    private Sim sim;

	public Main() {
        Agents = new ArrayList<>();
        initalizeAgents();
        sim = new Sim();
        
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
            //String shape = PARAM.getRandomShape();
            boolean coopSame = PARAM.getImmigrantChanceCooprateWithSame();
            boolean coopDiff = PARAM.getImmigrantChanceCooprateWithDiff();
			boolean death = PARAM.die();
			double ptr = PARAM.getInitialPTR();
            // if the agent cooperates with same they are a circle
            if(coopSame && coopDiff){
                //filled in circle (altruist)
                Agents.add(new Agent(x, y, color, "circle", coopSame, 
			coopDiff, ptr, death));
            }else if(coopSame && !coopDiff){
                //empty circle (ethnocentric)
                Agents.add(new Agent(x, y, color, "circle 2", coopSame, 
			coopDiff, ptr, death));
            }else if(!coopSame && coopDiff){
                //filled in square (cosmopolitan)
                Agents.add(new Agent(x, y, color, "square", coopSame, 
			coopDiff, ptr, death));
            }else{
                //empty square (egoist)
                Agents.add(new Agent(x, y, color, "square 2", coopSame, 
			coopDiff, ptr, death));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Agent agent : Agents) {
           sim.updateShape(agent, g);
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
        // Problem!!!!!
        // sim.go();
    }


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Agent Simulation");
           
            frame.setSize(PARAM.getGridSize() * PARAM.getCellSize(), PARAM.getGridSize() * PARAM.getCellSize());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    

            JPanel buttonPanel = new JPanel();
            JButton setEmptyButton = new JButton("setEmpty");
            JButton setFullButton = new JButton("setFull");
            JButton goButton = new JButton("go");

            buttonPanel.add(setEmptyButton);
            buttonPanel.add(setFullButton);
            buttonPanel.add(goButton);

            frame.add(buttonPanel, BorderLayout.NORTH);


            Main gui = new Main();
            frame.add(gui, BorderLayout.CENTER);
            frame.setVisible(true);

        });

	}
}
