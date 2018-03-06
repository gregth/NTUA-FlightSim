import java.io.*;
import java.util.*;

public class Flight {
    private int id, startTime;
    private Airport departureAirport, arrivalAirport;
    private String name;
    private AircraftSpecs aircraft;
    private int flightSpeed, altitude, fuel;
    private int currentDuration;
    private PrecisePosition aircraftPosition;
    private int degrees;
    private boolean exitedDepartureAirport, enteredArrivalAirport, reachedDestination;
    public static final int LEFT = 270, RIGHT = 90, UP = 0, DOWN = 180;
    private Universe myUniverse;

    public Flight(int id, int startTime, int departureAirportID, int arrivalAirportID,
        String name, int aircraftType, int flightSpeed, int altitude, int fuel) {

        AirportDatabase airportsDB = AirportDatabase.getInstance();
        AircraftSpecsDatabase aircraftsDB = AircraftSpecsDatabase.getInstance();

        this.id = id;
        this.startTime = startTime;
        this.name = name;
        this.departureAirport = airportsDB.getAirportByID(departureAirportID);
        this.arrivalAirport = airportsDB.getAirportByID(arrivalAirportID);
        this.aircraft = aircraftsDB.getAircraftSpecsByType(aircraftType);
        this.flightSpeed = flightSpeed;
        this.altitude = altitude;
        this.fuel = fuel;
        Position dp = departureAirport.getPosition();
        this.aircraftPosition = new PrecisePosition(dp.getX(), dp.getY());
        this.exitedDepartureAirport = false;
        this.enteredArrivalAirport = false;
        this.reachedDestination = false;
        this.myUniverse = Universe.getInstance();
    }

    public boolean hasReachedDestination() {
        return reachedDestination;
    }

	public int getStartTime() {
		return startTime;
	}

	public int getID() {
		return id;
	}

	public PrecisePosition getAircraftPosition() {
		return aircraftPosition;
	}

    public int getDegrees() {
        return degrees;
    }

    public boolean isValidFlight() {
        if (departureAirport == null || arrivalAirport == null || aircraft == null ) {
            return false;
        }
        if (!arrivalAirport.isOpen() || !departureAirport.isOpen()) {
            return false;
        }
        if (!departureAirport.acceptsAircraft(aircraft) ||
            !departureAirport.acceptsAircraft(aircraft)) {
            return false;
        }
        if (flightSpeed > aircraft.getMaxFlightSpeed()) {
            return false;
        }
        if (altitude > aircraft.getMaxAlt()) {
            return false;
        }
        if (altitude > aircraft.getMaxAlt()) {
            return false;
        }
        if (fuel > aircraft.getMaxFuelWeight()) {
            return false;
        }
        return true;
    }

    public void print() {
        System.out.println("Flight: " + id + " time:" + startTime + " name:" + name + " alt:" + altitude + "fuel: " + fuel);
        System.out.println("Printing Departure Airport:");
        departureAirport.print();
        System.out.println("Printing Arrival Airport");
        arrivalAirport.print();
        System.out.println("Printing aircraft");
        aircraft.print();
    }

    public void init() {
       currentDuration = 0;
       navigate();
    }

    public void integrate(double dt) {
        if (!reachedDestination) {
            currentDuration += dt * CONSTANTS.TIME_RATIO;
            System.out.println(currentDuration + " " +  id);
            navigate();
            updateAircraftPosition(dt);
        } else {
            System.out.println("HASSSSSSSSSSSSSSS REACHED DESTINATIONNNNNNNNNNNN :" + degrees);

        }
    }

    // Το degrees αναπαριστά τη δεξιόστροφη γωνία σε μοίρες
    private void updateAircraftPosition(double dt) {
        double speed = getCurrentSpeed();
        System.out.println("Speed :" + speed);
        System.out.println("Degrees :" + degrees);
        double speedY = - speed * Math.cos(Math.toRadians(degrees));
        double speedX = speed * Math.sin(Math.toRadians(degrees));
        double newX = aircraftPosition.getX() + dt * speedX;
        double newY = aircraftPosition.getY() + dt * speedY;
        int error = (int) (getCurrentSpeed() * dt + 1);
        System.out.println("Error: " + error);
            System.out.println("TRYING TO REACH AIRPORT DESTINATION: " + (int) newX + " " + (int) newY);
            arrivalAirport.getPosition().print();
        if (comparePositions((int) newX, arrivalAirport.getPosition().getX(), error) == 0 &&
            comparePositions((int) newY, arrivalAirport.getPosition().getY(), error) == 0 ) {
            System.out.println("REACHED DESTINATION: ");
            arrivalAirport.getPosition().print();
            reachedDestination = true;
        }

        // Makes exitedDepartureAirport true asap it takes off from airport tile
        if (!exitedDepartureAirport && !isInAirportRange(departureAirport)) {
            myUniverse.addMessage("Airplane ID: " + id + " EXITED Departure Airport");
        System.out.println("EXITED DEPART: " + exitedDepartureAirport);
            exitedDepartureAirport = true;
        }

        System.out.println("New Location :" + newX + " " + newY);
        aircraftPosition.update(newX, newY);
    }

    // Returns true if current flight position is in departure airport tile
    private boolean isInAirportRange(Airport airport) {
       Position airportPosition = airport.getPosition();
       int bottomY, topY, leftX, rightX;
       bottomY = airportPosition.getY() + CONSTANTS.TILE_SIZE_IN_MILES / 2;
       topY = airportPosition.getY() - CONSTANTS.TILE_SIZE_IN_MILES / 2;
       leftX = airportPosition.getX() - CONSTANTS.TILE_SIZE_IN_MILES / 2;
       rightX = airportPosition.getX() + CONSTANTS.TILE_SIZE_IN_MILES / 2;
       return aircraftPosition.getX() >= leftX &&
           aircraftPosition.getX() <= rightX &&
           aircraftPosition.getY() >= topY && // Things displayed lower in screen have higher Y value
           aircraftPosition.getY() <= bottomY;
    }

    // Return aircraft current speed in miles per second
    private double getCurrentSpeed() {
        if (isInAirportRange(departureAirport) ||
            isInAirportRange(arrivalAirport)) {
            return ((double) aircraft.getLandingSpeed()) / 3600;
        } else {
            return ((double) aircraft.getMaxFlightSpeed()) / 3600;
        }
    }

    private void navigate() {
        if (!exitedDepartureAirport) {
            switch (departureAirport.getDirection()) {
                case Airport.NORTH :
                    degrees = UP;
                    break;
                case Airport.EAST :
                    degrees = RIGHT;
                    break;
                case Airport.SOUTH :
                    degrees = DOWN;
                    break;
                case Airport.WEST :
                    degrees = LEFT;
                    break;
            }
        } else if (enteredArrivalAirport) {
            switch (arrivalAirport.getDirection()) {
                case Airport.NORTH :
                    degrees = DOWN;
                    break;
                case Airport.EAST :
                    degrees = LEFT;
                    break;
                case Airport.SOUTH :
                    degrees = UP;
                    break;
                case Airport.WEST :
                    degrees = RIGHT;
                    break;
            }
        } else {
            int gatePointX = arrivalAirport.getGatePoint().getX();
            int gatePointY = arrivalAirport.getGatePoint().getY();
            int aircraftX = (int) aircraftPosition.getX();
            int aircraftY = (int) aircraftPosition.getY();

            // Move horizontally first, then vertically, towards gate point
                System.out.println("Gate Point X " + gatePointX + " gpY : " + gatePointX + " AirX: " + aircraftX + " AIRY: " + aircraftY);
            int error = 3;
            if (comparePositions(gatePointX, aircraftX, error) == -1) {
                System.out.println("\t GOING LEFT");
                degrees = LEFT;
            } else if (comparePositions(gatePointX, aircraftX, error) == 1) {
                System.out.println("\t GOING RIGHT");
                degrees = RIGHT;
            } else {
                // Start moving vertically
                if (comparePositions(gatePointY, aircraftY, error) == -1) {
                    System.out.println("\t GOING UP");
                    degrees = UP;
                } else if (comparePositions(gatePointY, aircraftY, error) == 1) {
                    System.out.println("\t GOING DOWN");
                    degrees = DOWN;
                } else {
                    enteredArrivalAirport = true;
                }
            }

        }
    }

    // Return > 1 if a "greater than b", 0 if "equal", else -1
    private int comparePositions(int a, int b, int error) {
        int diff = a - b;
        if (diff > error)
            return 1;
        else if (diff < -error)
            return -1;
        else return 0;
    }

}
