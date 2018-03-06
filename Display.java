import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.*;

public class Display {
    private JFrame frame;
    private StatusPane statusPane;
    private MenuPane menuPane;
    private SimulationPane simulationPane;

    private static final int FRAME_WIDTH = CONSTANTS.FRAME_WIDTH;
    private static final int FRAME_HEIGHT= CONSTANTS.FRAME_HEIGHT;

    public Display() {
		JFrame frame = new JFrame("Medialab Simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        frame.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        frame.setMaximumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        frame.setResizable(false);
		frame.setLocationRelativeTo(null);
        frame.setVisible(true);


		JPanel wrapPane= new JPanel();
		wrapPane.setLayout(new BorderLayout());
		wrapPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        frame.add(wrapPane);

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
