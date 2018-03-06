import java.util.*;

public class Universe {
	private int simulatorClock, aircrafts, crashes, landings;
    private static final Universe instance = new Universe();
    private ArrayList<String> messages;

    private Universe() {
        simulatorClock = 0;
        aircrafts = 0;
        crashes = 0;
        landings = 0;
        messages = new ArrayList<String>();
    };

    public static Universe getInstance() {
        return instance;
    }

	public int getSimulatorClock() {
		return simulatorClock;
	}

	public int getAircrafts() {
		return aircrafts;
	}

	public int getCrashes() {
		return crashes;
	}

	public int getLandings() {
		return landings;
	}

	public void increaseAircrcafts() {
		aircrafts++;
	}

	public void increaseCrashes() {
		crashes++;
	}

	public void increaseLandings() {
		landings++;
	}

    public void increaseSimulatorClock(int intervalInSeconds) {
        simulatorClock += intervalInSeconds;
    }

    public void integrate(double dt) {
        aircrafts = FlightsDatabase.getInstance().countActiveFlights();
    }

    public void addMessage(String m) {
        messages.add(m);
    }

    public ArrayList<String> getMessages() {
        return messages;
    }
}
