import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Display {
    private JFrame frame;
    Canvas canvas;

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

        wrapPane.add(new StatusPane(), BorderLayout.NORTH);
        wrapPane.add(new SimulationPane(), BorderLayout.CENTER);
    }

}
