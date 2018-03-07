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

    public Display display;
    public Timer timer;
    private ArrayList<String> messages;

    private Universe() {
        simulatorClock = 0;
        aircrafts = 0;
        crashes = 0;
        landings = 0;
        messages = new ArrayList<String>();
    };

    // Updated universe after time dt in seconds
    public void integrate(double dt) {
        aircrafts = flightDB.countActiveFlights();
        flightDB.integrate(dt);
        increaseSimulatorClock(CONSTANTS.DELAY);
        display.refresh();
    }

    public void loadNew(String id) {
        myMap = new Map("data/world_" + id + ".txt");
        airportsDB = new AirportDatabase("data/airports_" + id + ".txt");
        flightDB = new FlightsDatabase("data/flights_" + id + ".txt");
        display = new Display();
    }

    // Tells database to init simulation
    public void init() {
        flightDB.init();
    }

    // Sets timer
    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void startTimer() {
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

}
