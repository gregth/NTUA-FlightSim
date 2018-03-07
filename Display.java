import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.*;

public class Display extends JFrame {
    private StatusPane statusPane;
    private MenuPane menuPane;
    private SimulationPane simulationPane;

    private static final int FRAME_WIDTH = CONSTANTS.FRAME_WIDTH;
    private static final int FRAME_HEIGHT= CONSTANTS.FRAME_HEIGHT;

    public Display() {
        super("Medialab Simulator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setMaximumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setResizable(false);
		this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void putContent() {
		JPanel wrapPane= new JPanel();
		wrapPane.setLayout(new BorderLayout());
		wrapPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.add(wrapPane);

        statusPane = new StatusPane();
        simulationPane = new SimulationPane();
        wrapPane.add(statusPane, BorderLayout.NORTH);
        wrapPane.add(simulationPane, BorderLayout.CENTER);

        menuPane = new MenuPane();
        wrapPane.add(menuPane, BorderLayout.SOUTH);
    }

    public void refresh() {
        statusPane.refresh();
        simulationPane.refresh();
    }
}
