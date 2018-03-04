import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class StatusPane extends JPanel {
    private static final int STATUS_HEIGHT = CONSTANTS.STATUS_HEIGHT;
    private static final int STATUS_WIDTH = CONSTANTS.STATUS_WIDTH;

    private JLabel simulatedTimeLabel, aircraftsLabel, crashesLabel, landingsLabel;
	private int timeElapsedInSec, aircrafts, crashes, landings;

	public StatusPane (){
		setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(STATUS_WIDTH, STATUS_HEIGHT));

		simulatedTimeLabel = new JLabel("Simulated Time: 00:00", JLabel.LEFT);
		this.add(simulatedTimeLabel);

		aircraftsLabel = new JLabel("Aircrafts: 0", JLabel.LEFT);
        this.add(aircraftsLabel);

		crashesLabel = new JLabel("Crashes: 0", JLabel.LEFT);
        this.add(crashesLabel);

		landingsLabel = new JLabel("Landings: 0", JLabel.LEFT);
        this.add(landingsLabel);

        timeElapsedInSec = 0;
        aircrafts = 0;
        crashes = 0;
        landings = 0;

		setBackground(Color.CYAN);
	}

	public int getTimeElapsed() {
		return timeElapsedInSec;
	}

	public void increaseAircrcafts() {
		aircrafts++;
		aircraftsLabel.setText("Aircrafts: " + aircrafts);
	}

	public void increaseCrashes() {
		crashes++;
		crashesLabel.setText("Crashes: " + crashes);
	}

	public void increaseLandings() {
		landings++;
		landingsLabel.setText("Landings: " + landings);
	}

    public void increaseTime(int intervalInSeconds) {
        timeElapsedInSec += intervalInSeconds;
        displayTime(timeElapsedInSec);
    }

	public void displayTime(int timeInSeconds) {
        int minutes = timeElapsedInSec / 60;
        int seconds = timeElapsedInSec % 60;

        String minutesString = String.format("%02d", minutes);
        String secondsString = String.format("%02d", seconds);

		simulatedTimeLabel.setText("Simulated Time: " + minutesString + ":" + secondsString);
	}
}
