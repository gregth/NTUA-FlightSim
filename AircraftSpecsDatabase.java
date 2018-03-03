import java.util.*;

public class AircraftSpecsDatabase {
    private HashMap<Integer, AircraftSpecs> aircrafts;

    // Create Singleton Instance of the AirportDatabase Class
    private static final AircraftSpecsDatabase instance = new AircraftSpecsDatabase();

    private AircraftSpecsDatabase() {
        aircrafts = new HashMap<Integer, AircraftSpecs>();
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
