import java.io.*;
import java.util.*;

public class FlightsDatabase {
    private HashMap<Integer, Flight> flights;
    private PriorityQueue<Flight> flightsQueue;
    private Comparator<Flight> comparator;
    private ArrayList<Flight> activeFlights;
    private Position position;

    private int time;

    // Create Singleton Instance of the FlightsDatabase Class
    private static final FlightsDatabase instance = new FlightsDatabase();

    private FlightsDatabase() {
        flights = new HashMap<Integer, Flight>();
        comparator = new FlightComparator();
        flightsQueue = new PriorityQueue<Flight>(1, comparator);
        activeFlights = new ArrayList<Flight>();
    };

    public static FlightsDatabase getInstance() {
        return instance;
    }

    // Adds a flight to database
    public void add(Flight flight) {
        flights.put(flight.getID(), flight);
        System.out.println("ADDED");
        flightsQueue.add(flight);
    }

    public Flight getFlightByID(int id) {
        return flights.get(id);
    }

    public int countActiveFlights() {
        return activeFlights.size();
    }

    public ArrayList<Flight> getActiveFlights() {
        return activeFlights;
    }
    // Parse fligths database from file
    public void parseFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));
            String line;

            while ((line = reader.readLine()) != null) {
			    String[] parts = line.split(",");
                if (parts.length != 9) {
                    throw new WrongInputFormatException("Wrong input file format: " + filePath);
                }

                int id = Integer.parseInt(parts[0]);
                int startTime = Integer.parseInt(parts[1]) * 60;
                int departureAirportID = Integer.parseInt(parts[2]);
                int arrivalAirportID = Integer.parseInt(parts[3]);
                String name = parts[4];
                int aircraftType = Integer.parseInt(parts[5]);
                int flightSpeed = Integer.parseInt(parts[6]);
                int altitude = Integer.parseInt(parts[7]);
                int fuel = Integer.parseInt(parts[8]);

                Flight currentFlight = new Flight(id, startTime, departureAirportID,
                    arrivalAirportID, name, aircraftType, flightSpeed, altitude, fuel);

                if (currentFlight.isValidFlight()) {
                    this.add(currentFlight);
                    System.out.println("\nValid flight:");
                    currentFlight.print();
                } else {
                    System.out.println("\nInvalid flight:");
                    currentFlight.print();
                }
            }
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (WrongInputFormatException e) {
            System.out.println(e);
        }
    }

    // Prints debugging info
    public void print() {
        System.out.println("Printing Flights Database");
        flights.forEach((k, flight) -> {
            flight.print();
        });
    }

    public void init() {
        time = 0;
        integrate(0);
    }

    public void integrate(double dt) {
        Flight currentFlight = flightsQueue.peek();
        time += dt * CONSTANTS.TIME_RATIO;
        System.out.println("Current controller TIME: " + time);

        while (true) {
            if (currentFlight != null && currentFlight.getStartTime() <= time) {
                System.out.println("VALID CONDITION -  START TIME: " + currentFlight.getStartTime());
                currentFlight.init();
                flightsQueue.remove(currentFlight);
                activeFlights.add(currentFlight);

                currentFlight = flightsQueue.peek();
            } else {
                if (currentFlight != null) {
                    System.out.println("IBVALID CONDITION -  START TIME: " + currentFlight.getStartTime());
                }
                break;
            }
        }
        for (Flight flight : activeFlights) {
            flight.integrate(dt);
        }
    }
}
