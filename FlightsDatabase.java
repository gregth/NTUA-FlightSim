import java.io.*;
import java.util.*;

public class FlightsDatabase {
    private HashMap<Integer, Flight> flights;

    // Create Singleton Instance of the FlightsDatabase Class
    private static final FlightsDatabase instance = new FlightsDatabase();

    private FlightsDatabase() {
        flights = new HashMap<Integer, Flight>();
    };

    public static FlightsDatabase getInstance() {
        return instance;
    }

    // Adds a flight to database
    public void add(Flight flight) {
        flights.put(flight.getID(), flight);
    }

    public Flight getFlightByID(int id) {
        return flights.get(id);
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
                int startTime = Integer.parseInt(parts[1]);
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
}
