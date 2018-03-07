import java.util.*;

public class AircraftSpecsDatabase {
    private HashMap<Integer, AircraftSpecs> aircrafts;

    // Create Singleton Instance of the AirportDatabase Class
    private static final AircraftSpecsDatabase instance = new AircraftSpecsDatabase();

    private AircraftSpecsDatabase() {
        aircrafts = new HashMap<Integer, AircraftSpecs>();
        this.add(new AircraftSpecs(CONSTANTS.SINGLE_ENGINE, "Single-Engine", 60, 110, 280, 8000, 700, 3));
        this.add(new AircraftSpecs(CONSTANTS.TURBO, "Turbodrom", 100, 220, 4200, 16000, 1200, 9));
        this.add(new AircraftSpecs(CONSTANTS.JET, "Jet", 140, 280, 16000, 28000, 2300, 15));
    }

    public static AircraftSpecsDatabase getInstance() {
        return instance;
    }

    // Adds aircraft in database
    public void add(AircraftSpecs aircraft) {
        this.aircrafts.put(aircraft.getType(), aircraft);
    }

    // Returns the specs for specific type of aircraft
    public AircraftSpecs getAircraftSpecsByType(int type) {
        return aircrafts.get(type);
    }
}
