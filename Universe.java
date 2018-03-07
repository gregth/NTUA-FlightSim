import java.util.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Universe {
	private int simulatorClock, aircrafts, crashes, landings;
    private static final Universe instance = new Universe();

    private AircraftSpecsDatabase airSpecsDB;
    private AirportDatabase airportsDB;
    private Map myMap;
    private FlightsDatabase flightDB;

    private Display display;
    private Timer timer;
    private ArrayList<String> messages;

    private String fileID = "default";

    private Universe() {
    };

    // Updated universe after time dt in seconds
    public void integrate(double dt) {
        aircrafts = flightDB.countActiveFlights();
        display.refresh();
        flightDB.integrate(dt);
        increaseSimulatorClock(CONSTANTS.DELAY);
    }

    public void loadNew(String id) {
        simulatorClock = 0;
        aircrafts = 0;
        crashes = 0;
        landings = 0;
        messages = new ArrayList<String>();
        myMap = new Map("data/world_" + id + ".txt");
        airportsDB = new AirportDatabase("data/airports_" + id + ".txt");
        flightDB = new FlightsDatabase("data/flights_" + id + ".txt");
        display.putContent();
        display.refresh();
        this.init();
    }

    public void createDisplay() {
        display = new Display();
        display.putContent();
    }

    // Tells database to init simulation
    private void init() {
        flightDB.init();
        display.refresh();
    }

    // Sets timer
    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void startTimer() {
        display.refresh();
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

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

	public Display getDisplay() {
		return display;
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

    public void addMessage(String m) {
        messages.add(m);
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public AirportDatabase getAirportsDatabase() {
        return airportsDB;
    }

    public Map getMap() {
        return myMap;
    }

    public FlightsDatabase getFlightsDatabase() {
        return flightDB;
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String id) {
        fileID = id;
    }
}
