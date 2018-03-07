import java.io.*;
import java.util.*;

public class FlightsDatabase {
    private HashMap<Integer, Flight> flights;
    private PriorityQueue<Flight> flightsQueue;
    private Comparator<Flight> comparator;
    private ArrayList<Flight> activeFlights;
    private Position position;
    private Universe myUniverse;

    private static final double CRASH_DISTANCE = 2;
    private static final double CRASH_LATITUDE_DIFF = 2;

    private int time;

    public FlightsDatabase(String filePath) {
        flights = new HashMap<Integer, Flight>();
        comparator = new FlightComparator();
        flightsQueue = new PriorityQueue<Flight>(1, comparator);
        activeFlights = new ArrayList<Flight>();
        myUniverse = Universe.getInstance();
        parseFile(filePath);
    };

    // Adds a flight to database
    public void add(Flight flight) {
        flights.put(flight.getID(), flight);
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
        checkForColissions();
    }

    // Integrates for time interval dt in seconds
    public void integrate(double dt) {
        time += dt;

        Flight currentFlight = flightsQueue.peek();
        while (true) {
            if (currentFlight != null && currentFlight.getStartTime() <= time) {
                currentFlight.init();
                flightsQueue.remove(currentFlight);
                activeFlights.add(currentFlight);
                myUniverse.addMessage("[Flight ID: " + currentFlight.getID() + "] TAKES OFF NOW");
                currentFlight = flightsQueue.peek();
            } else {
                break;
            }
        }

        checkForColissions();

        ArrayList<Flight> markedFlights = new ArrayList<Flight>();
        for (Flight flight : activeFlights) {
            if (flight.isActive()) {
                flight.integrate(dt);
            } else {
                markedFlights.add(flight);
            }
        }

        for (Flight flight : markedFlights) {
            activeFlights.remove(flight);
        }

        if (activeFlights.isEmpty() && flightsQueue.isEmpty()) {
            myUniverse.addMessage("END OF SIMULATION");
            myUniverse.stopTimer();
        }

    }

    public void checkForColissions() {
        for (Flight f1 : activeFlights) {
            for (Flight f2 : activeFlights) {
                if (f1 != f2 &&
                        f1.getAircraftPosition().euclideanDistance(f2.getAircraftPosition()) <= CRASH_DISTANCE &&
                        Math.abs(f1.getAltitude() - f2.getAltitude()) <= CRASH_LATITUDE_DIFF) {
                    f1.setCrashed();
                    f2.setCrashed();
                }
            }
        }
    }

    public String stringifyAllFlights() {
        String airportString;
        StringBuilder result = new StringBuilder();
        flights.forEach((k, flight) -> {
            result.append(flight.stringify());
        });
        return result.toString();
    }

    public String stringifyActiveFlights() {
        StringBuilder result = new StringBuilder();
        for (Flight flight : activeFlights) {
            result.append(flight.stringifyActiveFlight());
        }
        return result.toString();
    }
}
