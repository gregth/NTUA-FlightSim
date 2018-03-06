import java.util.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Universe {
	private int simulatorClock, aircrafts, crashes, landings;
    private static final Universe instance = new Universe();
    private ArrayList<String> messages;

    public AircraftSpecsDatabase airSpecsDB;
    public AirportDatabase airportDB;
    public Map myMap;
    public FlightsDatabase flightDB;
    public Display display;
    public Timer timer;

    private Universe() {
        simulatorClock = 0;
        aircrafts = 0;
        crashes = 0;
        landings = 0;
        messages = new ArrayList<String>();
    };

    public void constructDatabases() {
        airportDB = AirportDatabase.getInstance();
        airportDB.parseFile("data/airports_default.txt");

        airSpecsDB = AircraftSpecsDatabase.getInstance();
        airSpecsDB.add(new AircraftSpecs(CONSTANTS.SINGLE_ENGINE, "Single-Engine", 60, 110, 280, 8000, 700, 3));
        airSpecsDB.add(new AircraftSpecs(CONSTANTS.TURBO, "Turbodrom", 100, 220, 4200, 16000, 1200, 9));
        airSpecsDB.add(new AircraftSpecs(CONSTANTS.JET, "Jet", 140, 280, 16000, 28000, 2300, 15));

        myMap = Map.getInstance();
        myMap.parseFile("data/world_default.txt");

        flightDB = FlightsDatabase.getInstance();
        flightDB.parseFile("data/flights_default.txt");

        display = new Display();

    }

    public void init() {
        flightDB.init();
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void startTimer() {
        timer.start();
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

    public void integrate(double dt) {
        aircrafts = FlightsDatabase.getInstance().countActiveFlights();
        flightDB.integrate(dt);
        increaseSimulatorClock(CONSTANTS.DELAY);
        display.refresh();
    }

    public void addMessage(String m) {
        messages.add(m);
    }

    public ArrayList<String> getMessages() {
        return messages;
    }
}
