import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Simulator {
    public static void main(String[] args) {
        AirportDatabase airportDB = AirportDatabase.getInstance();
        airportDB.parseFile("data/airports_default.txt");

        AircraftSpecsDatabase airSpecsDB = AircraftSpecsDatabase.getInstance();
        airSpecsDB.add(new AircraftSpecs(CONSTANTS.SINGLE_ENGINE, "Single-Engine", 60, 110, 280, 8000, 700, 3));
        airSpecsDB.add(new AircraftSpecs(CONSTANTS.TURBO, "Turbodrom", 100, 220, 4200, 16000, 1200, 9));
        airSpecsDB.add(new AircraftSpecs(CONSTANTS.JET, "Jet", 140, 280, 16000, 28000, 2300, 15));

        Map myMap = Map.getInstance();
        myMap.parseFile("data/world_default.txt");

        Universe myUniverse = Universe.getInstance();
        FlightsDatabase flightDB = FlightsDatabase.getInstance();
        flightDB.parseFile("data/flights_default.txt");

        Display display = new Display();

        flightDB.init();
		Timer timer = new Timer(1000 * CONSTANTS.DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
                flightDB.integrate(CONSTANTS.TIME_RATIO * CONSTANTS.DELAY);
                myUniverse.increaseSimulatorClock(CONSTANTS.DELAY);
                myUniverse.integrate(CONSTANTS.TIME_RATIO * CONSTANTS.DELAY);
                display.refresh();
			}
		});
        timer.start();
    }
}
