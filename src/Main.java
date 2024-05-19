import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Main extends JPanel implements ActionListener{
    public Map map;
    public Sim sim;
    public Timer timer;
    public boolean running;

	public Main() {
        sim = new Sim();
        timer = new Timer(PARAM.getTimerDelay(), this);
        running = false;
        map = sim.getMap();
        createCSV();
    }

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

    public boolean getRunning(){
        return running;
    }

    public void setRunning(boolean b){
        this.running = b;
    }

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

	@Override
    public void actionPerformed(ActionEvent e) {
        sim.go();
        repaint();
        writeCSV();
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
                    gui.setRunning(false);
                    gui.sim.setupEmpty();
                    gui.repaint();
                }
            });

            setFullButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("setFull button clicked");
                    gui.setRunning(false);
                    gui.sim.setupFull();
                    gui.repaint();
                }
            });

            goButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("go button clicked");
                    if(gui.getRunning()){
                        gui.timer.stop();
                        gui.setRunning(false);

                    }else{
                        gui.timer.start();
                        gui.setRunning(true);
                    }
                }
            });

            frame.setVisible(true);
        });

	}
}
