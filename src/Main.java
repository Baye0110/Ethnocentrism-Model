import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import java.awt.*;

public class Main extends JPanel implements ActionListener{
	public List<Agent> Agents;
    public Map map;
    public Sim sim;
    public Timer timer;
    public boolean runing;

	public Main() {
        Agents = new ArrayList<>();
        sim = new Sim();
        timer = new Timer(PARAM.getTimerDelay(), this);
        runing = false;
        map = sim.getMap();
    }

	public void outputResults(){

	}

    public void setAgents(List<Agent> Agents){
        for(Agent agent : Agents){
            this.Agents.add(agent);
        }
    }

    public boolean getRuning(){
        return runing;
    }

    public void setRuning(boolean b){
        this.runing = b;
    }

	public int getAL(){
        return Agents.size();
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
        sim.go();
        //updateStats();
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

            setEmptyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("setEmpty button clicked");
                    gui.sim.setupEmpty();
                }
            });

            setFullButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("setFull button clicked");
                    gui.sim.setupFull();
                    gui.setAgents(gui.sim.getAgents());
                    System.out.println("length: " + gui.getAL());
                    gui.repaint();
                }
            });

            goButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("go button clicked");
                    if(gui.getRuning()){
                        gui.timer.stop();
                        gui.setRuning(false);

                    }else{
                        gui.timer.start();
                        gui.setRuning(true);
                    }
                }
            });



            frame.setVisible(true);

        });

	}
}
