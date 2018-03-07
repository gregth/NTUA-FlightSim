import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Simulator {
    public static void main(String[] args) {
        Universe myUniverse = Universe.getInstance();
		Timer timer = new Timer(1000 * CONSTANTS.DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
                myUniverse.integrate(CONSTANTS.DELAY * CONSTANTS.TIME_RATIO);
			}
		});
        myUniverse.setTimer(timer);
        myUniverse.loadNew("default");
        myUniverse.init();
        myUniverse.startTimer();
    }
}
