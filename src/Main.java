import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Main extends JPanel implements ActionListener{
    // 2D map used to save Agents
    public Map map;
    // Sim class to use its method
    public Sim sim;
    // Timer used for action performed
    public Timer timer;
    // whether timer is running
    public boolean running;

    // COnstructor
	public Main() {
        sim = new Sim();
        timer = new Timer(PARAM.getTimerDelay(), this);
        running = false;
        map = sim.getMap();
        createCSV();
    }

    // Create a CSV file
    public void createCSV() {
        try {
            FileWriter fw = new FileWriter("Ethnocentrism.csv", false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("tick, CC, CD, DC, DD\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Write CSV file
    private void writeCSV() {
        try {
            FileWriter fw = new FileWriter("Ethnocentrism.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sim.getNumTick() + ", " + sim.getNumCC() + ", " + sim.getNumCD() + ", " + sim.getNumDC() +
                    ", " + sim.getNumDD() + "\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Paint the shape on Frame
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < PARAM.getGridSize(); i++) {
            for (int j = 0; j < PARAM.getGridSize(); j++) {
                Agent agent = map.getElement(i, j);
                if (agent != null) {
                    sim.updateShape(agent, g);
                }
            }
        }
    }

    // Action performed for agent, use go method in Sim
	@Override
    public void actionPerformed(ActionEvent e) {
        sim.go();
        repaint();
        writeCSV();
    }

    // Get whether timer is running
    public boolean getRunning(){
        return running;
    }
    
    // Set timer to run or not
    public void setRunning(boolean b){
        this.running = b;
    }

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            // Create a new Frame called "Agent Simulation"
            JFrame frame = new JFrame("Agent Simulation");
            // Set the size of Frame
            frame.setSize(PARAM.getGridSize() * PARAM.getCellSize(), PARAM.getGridSize() * PARAM.getCellSize());
            // Set the Frame exit to close
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            
            // create a button background color
            Color buttonColor = new Color(153,204,255);
            // Create a JPanel with buttons
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(Color.white);
            // Create a setEmpty button
            JButton setEmptyButton = new JButton("setEmpty");
            // set the background color of setEmptyButton
            setEmptyButton.setBackground(buttonColor);
            // Create a setFull button
            JButton setFullButton = new JButton("setFull");
            // set the background color of setFullButton
            setFullButton.setBackground(buttonColor);
            // Create a go button
            JButton goButton = new JButton("go");
            // set the background color of goButton
            goButton.setBackground(buttonColor);

            // Add three buttons on Jpanel
            buttonPanel.add(setEmptyButton);
            buttonPanel.add(setFullButton);
            buttonPanel.add(goButton);
            // Add button panel to Frame, and set its layer to north
            frame.add(buttonPanel, BorderLayout.NORTH);

            // add main to frame and ser its layer to center
            Main gui = new Main();
            //set the gui background as black
            gui.setBackground(Color.black);
            // add gui to the frame
            frame.add(gui, BorderLayout.CENTER);

            // SetEmpty button action listener
            setEmptyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("setEmpty!");
                    // use setEmpty method form Sim
                    gui.sim.setupEmpty();
                    // update
                    gui.repaint();
                }
            });

            // SetFull button action listener
            setFullButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("setFull!");
                    // use setFull method form Sim
                    gui.sim.setupFull();
                    // update
                    gui.repaint();
                }
            });

            // go button action listener
            goButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("go button clicked");
                    // if first click 
                    // start timer
                    // do action 
                    // stop timer, otherwise
                    if(gui.getRunning()){
                        gui.timer.stop();
                        gui.setRunning(false);
                        //change the goButton background back when timer is stopped
                        goButton.setBackground(buttonColor);
                        System.out.println("running stopped!");

                    }else{
                        //change the goButton background when timer is runing
                        goButton.setBackground(new Color(0,128,255));
                        gui.timer.start();
                        gui.setRunning(true);
                        System.out.println("running!");
                    }
                }
            });

            // Set frame visible to true
            frame.setVisible(true);
        });

	}
}
