import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.util.*;

public class StatusPane extends JPanel {
    private static final int STATUS_HEIGHT = CONSTANTS.STATUS_HEIGHT;
    private static final int STATUS_WIDTH = CONSTANTS.STATUS_WIDTH;
    private JLabel simulatedTimeLabel, aircraftsLabel, crashesLabel, landingsLabel, realTimeLabel;

	public StatusPane () {
        super();
		setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(STATUS_WIDTH, STATUS_HEIGHT));

		simulatedTimeLabel = new JLabel("Simulated Time: 00:00 | ", JLabel.LEFT);
		this.add(simulatedTimeLabel);

		realTimeLabel = new JLabel("Real Time: 00:00 | ", JLabel.LEFT);
		this.add(realTimeLabel);

		aircraftsLabel = new JLabel("Aircrafts: 0 | ", JLabel.LEFT);
        this.add(aircraftsLabel);

		crashesLabel = new JLabel("Crashes: 0 | ", JLabel.LEFT);
        this.add(crashesLabel);

		landingsLabel = new JLabel("Landings: 0", JLabel.LEFT);
        this.add(landingsLabel);

		setBackground(Color.CYAN);
        this.repaint();
	}

	public String displayTime(int timeInSeconds) {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;

        String minutesString = String.format("%02d", minutes);
        String secondsString = String.format("%02d", seconds);
		return new String(minutesString + ":" + secondsString);
	}

	public void refresh() {
        Universe myUniverse = Universe.getInstance();
		simulatedTimeLabel.setText(" Simulated Time: " + displayTime(myUniverse.getSimulatorClock()) + " | ");
		aircraftsLabel.setText(" Aircrafts: " + myUniverse.getAircrafts() + " | ");
		crashesLabel.setText(" Crashes: " + myUniverse.getCrashes() + " | ");
		realTimeLabel.setText(" Real Time: " + displayTime((int)(CONSTANTS.TIME_RATIO * myUniverse.getSimulatorClock())) + " | ");
		landingsLabel.setText("Landings: " + myUniverse.getLandings());
	}
}
