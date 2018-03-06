import java.awt.*;
import java.awt.event.*;  
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.util.*;

public class MenuPane extends JPanel {
    private static final int STATUS_HEIGHT = CONSTANTS.STATUS_HEIGHT;
    private static final int STATUS_WIDTH = CONSTANTS.STATUS_WIDTH;
    private JLabel simulatedTimeLabel, aircraftsLabel, crashesLabel, landingsLabel, realTimeLabel;

	public MenuPane () {
        super();
		setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(STATUS_WIDTH, STATUS_HEIGHT));

        JButton startButton = new JButton("Start");  
        startButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                System.out.println("click start");
            }
        });
        this.add(startButton);

        JButton stopButton = new JButton("Stop");  
        stopButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                System.out.println("click stop");
            }
        });
        this.add(stopButton);

        JButton loadButton = new JButton("Load");  
        loadButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                String mapId = JOptionPane.showInputDialog("Enter Map ID: ");
                System.out.println("Entered map id " + mapId);
            }
        });
        this.add(loadButton);

        MenuPane that = this;
        JButton airportsButton = new JButton("Airports");  
        airportsButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                JOptionPane.showMessageDialog(that, "Airports\nNew line");
            }
        });
        this.add(airportsButton);

        JButton aircraftsButton = new JButton("Aircrafts");  
        aircraftsButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                JOptionPane.showMessageDialog(that, "Aircrafts\nNew line");

            }
        });
        this.add(aircraftsButton);

        JButton flightsButton = new JButton("Flights");  
        flightsButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                JOptionPane.showMessageDialog(that, "Flights\nNew line");
            }
        });
        this.add(flightsButton);

        JButton exitButton = new JButton("Exit");  
        exitButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                System.exit(0);
            }
        });
        this.add(exitButton);

		setBackground(Color.GRAY);
        this.repaint();
	}

	public void refresh() {
        Universe myUniverse = Universe.getInstance();
	}
}
