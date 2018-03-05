import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class SimulationPane extends JPanel {

    private static final int MAP_WIDTH = CONSTANTS.MAP_WIDTH;
    private static final int STATUS_HEIGHT = CONSTANTS.STATUS_HEIGHT;
    private static final int MESSAGES_WIDTH = CONSTANTS.MESSAGES_WIDTH;
    private static final int MAP_HEIGHT = CONSTANTS.MAP_HEIGHT;

    private static final int SIMULATION_PANE_WIDTH = CONSTANTS.SIMULATION_PANE_WIDTH;
    private static final int SIMULATION_PANE_HEIGHT = CONSTANTS.SIMULATION_PANE_HEIGHT;

	public SimulationPane (){
        setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        this.setPreferredSize(new Dimension(SIMULATION_PANE_WIDTH, SIMULATION_PANE_HEIGHT));
        this.setMinimumSize(new Dimension(SIMULATION_PANE_WIDTH, SIMULATION_PANE_HEIGHT));
        this.setMaximumSize(new Dimension(SIMULATION_PANE_WIDTH, SIMULATION_PANE_HEIGHT));
		setBackground(Color.RED);

        JPanel map = new MapPane();
        this.add(map);

        JPanel messages = new JPanel();
        messages.setBackground(Color.GREEN);
        messages.setPreferredSize(new Dimension(MESSAGES_WIDTH, MAP_HEIGHT));
        messages.setMinimumSize(new Dimension(MESSAGES_WIDTH, MAP_HEIGHT));
        messages.setMaximumSize(new Dimension(MESSAGES_WIDTH, MAP_HEIGHT));
        this.add(messages);
	}
}
