import java.io.*;
import java.util.*;
import java.lang.StringBuilder;

public class AirportDatabase {
    private HashMap<Integer, Airport> airports;
    private static final int ROWS = CONSTANTS.ROWS;
    private static final int COLS = CONSTANTS.COLS;

    private Airport[][] airportMap;

    public AirportDatabase(String filePath) {
        airports = new HashMap<Integer, Airport>();
        airportMap = new Airport[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                airportMap[i][j] = null;
            }
        }
        parseFile(filePath);
    };

    // Adds an airport to database
    public void add(Airport airport) {
        airports.put(airport.getID(), airport);
        Position p = airport.getGridPosition();
        airportMap[p.getX()][p.getY()] = airport;
    }

    public Airport getAirportByID(int id) {
        return airports.get(id);
    }

    // Parse airport database from file
    public void parseFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));
            String line;

            while ((line = reader.readLine()) != null) {
			    String[] parts = line.split(",");

                int id = Integer.parseInt(parts[0]);
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);
                String name = parts[3];
                int direction = Integer.parseInt(parts[4]);
                int type = Integer.parseInt(parts[5]);
                boolean open = parts[6].equals("1");

                Airport currentAirport = new Airport(id, x, y, name, direction, type, open);
                this.add(currentAirport);
            }
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }

    // Prints debugging info
    public void print() {
        System.out.println("Printing Airports Database");
        airports.forEach((k, airport) -> {
            airport.print();
        });
    }

    public Airport findAirport(int row, int col) {
        return airportMap[row][col];
    }

    public String stringify() {
        String airportString;
        StringBuilder result = new StringBuilder();
        airports.forEach((k, airport) -> {
            result.append(airport.stringify());
        });
        return result.toString();
    }
}
