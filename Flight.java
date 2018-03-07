import java.io.*;
import java.util.*;

public class Flight {
    private int id, startTime;
    private Airport departureAirport, arrivalAirport;
    private String name;
    private AircraftSpecs aircraft;
    private double flightSpeed;
    private int altitude, fuel, currentDuration, degrees, currentAltitude;
    private PrecisePosition aircraftPosition;
    private boolean exitedDepartureAirport, enteredArrivalAirport;
    private boolean crashed, landed, reachedDestination, active, fixedAltitude;

    public static final int LEFT = 270, RIGHT = 90, UP = 0, DOWN = 180;
    private Universe myUniverse;

    public Flight(int id, int startTime, int departureAirportID, int arrivalAirportID,
        String name, int aircraftType, int flightSpeed, int altitude, int fuel) {

        myUniverse = Universe.getInstance();
        AirportDatabase airportsDB = myUniverse.getAirportsDatabase();
        AircraftSpecsDatabase aircraftsDB = AircraftSpecsDatabase.getInstance();

        this.id = id;
        this.startTime = startTime;
        this.name = name;
        this.departureAirport = airportsDB.getAirportByID(departureAirportID);
        this.arrivalAirport = airportsDB.getAirportByID(arrivalAirportID);
        this.aircraft = aircraftsDB.getAircraftSpecsByType(aircraftType);
        this.flightSpeed = ((double) flightSpeed) / 3600;
        this.altitude = altitude;
        this.fuel = fuel;
        Position dp = departureAirport.getPosition();
        this.aircraftPosition = new PrecisePosition(dp.getX(), dp.getY());
        this.exitedDepartureAirport = false;
        this.enteredArrivalAirport = false;
        this.reachedDestination = false;
        this.fixedAltitude = false;
        this.myUniverse = Universe.getInstance();
        this.currentAltitude = 0;
        this.crashed = false;
        this.active = false;
        this.landed = false;
    }

    private double orthoDistance(PrecisePosition a, PrecisePosition b) {
        double x1 = a.getX();
        double y1 = a.getY();

        double x2 = b.getX();
        double y2 = b.getY();

        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
    // Calculated time for aitcraft to arrive when not in
    // departure or arrival area
    private double timeRemaining() {
        double time = 0;
        if (!exitedDepartureAirport) {
            time += orthoDistance(departureAirport.getGatePoint(), aircraftPosition) / aircraft.getLandingSpeed();
            time += orthoDistance(departureAirport.getGatePoint(), arrivalAirport.getGatePoint()) / flightSpeed;
            time += CONSTANTS.TILE_SIZE_IN_MILES / 2 / aircraft.getLandingSpeed();
            return time;
        }
        if (enteredArrivalAirport) {
            System.out.println("here");
            time += orthoDistance(arrivalAirport.getPrecisePosition(), aircraftPosition) / aircraft.getLandingSpeed();
            return time;
        }

        time += orthoDistance(aircraftPosition, arrivalAirport.getGatePoint()) / flightSpeed;
        time += CONSTANTS.TILE_SIZE_IN_MILES / 2 / aircraft.getLandingSpeed();

        return time;
    }

    public boolean hasReachedDestination() {
        return reachedDestination;
    }

	public int getStartTime() {
		return startTime;
	}

    public int getAltitude() {
        return altitude;
    }

	public int getID() {
		return id;
	}

	public void setCrashed() {
        if (active) {
            myUniverse.increaseCrashes();
            myUniverse.addMessage("[Flight ID: " + id + "] CRASHED");
            crashed = true;
            active = false;
        }
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
        active = true;
        currentDuration = 0;
        navigate(0);
    }

    public void integrate(double dt) {
        reduceFuel(dt);
        if (fuel <= 0 && active) {
            myUniverse.addMessage("[Flight ID: " + id + "] RUN OUT OFF FUEL");
            crashed = true;
            myUniverse.increaseCrashes();
            active = false;
        } else if (!reachedDestination) {
            currentDuration += dt * CONSTANTS.TIME_RATIO;
            int tolerance = (int) (getCurrentSpeed() * dt + 1);
            navigate(tolerance);
            updateAircraftPosition(dt);
            System.out.println("[Flight ID: " + id + "] Estimated time: " + timeRemaining());
        } else {
            myUniverse.addMessage("[Flight ID: " + id + "] LANDED");
            myUniverse.increaseLandings();
            landed = true;
            active = false;
        }
    }

    private void reduceFuel(double dt) {
        fuel -= aircraft.getFuelConsumptionRate() * dt *  getCurrentSpeed();
        System.out.println("FUEL: " + fuel);
    }

    public boolean isActive() {
        return active;
    }

    // Το degrees αναπαριστά τη δεξιόστροφη γωνία σε μοίρες
    public void updateAircraftPosition(double dt) {
        double speed = getCurrentSpeed();
        double speedY = - speed * Math.cos(Math.toRadians(degrees));
        double speedX = speed * Math.sin(Math.toRadians(degrees));
        double newX = aircraftPosition.getX() + dt * speedX;
        double newY = aircraftPosition.getY() + dt * speedY;

        // Use tolerance value beacuse position takes discrete values
        int tolerance = (int) (getCurrentSpeed() * dt + 1);
        if (comparePositions((int) newX, arrivalAirport.getPosition().getX(), tolerance) == 0 &&
            comparePositions((int) newY, arrivalAirport.getPosition().getY(), tolerance) == 0 &&
            enteredArrivalAirport) {
            reachedDestination = true;
        }

        // Makes exitedDepartureAirport true asap it takes off from airport tile
        if (!exitedDepartureAirport && !isInAirportRange(departureAirport)) {
            myUniverse.addMessage("[Flight ID: " + id + "] OUT OF Departure Airport Area");
            exitedDepartureAirport = true;
        }

        if (enteredArrivalAirport) {
        }
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
        if (enteredArrivalAirport || exitedDepartureAirport) {
            return aircraft.getLandingSpeed();
        } else {
            return flightSpeed;
        }
    }

    // Determines aircaft rotation
    private void navigate(int tolerance) {
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
            double gatePointX = arrivalAirport.getGatePoint().getX();
            double gatePointY = arrivalAirport.getGatePoint().getY();
            double aircraftX = aircraftPosition.getX();
            double aircraftY = aircraftPosition.getY();

            // Move horizontally first, then vertically, towards gate point
            if (comparePositions(gatePointX, aircraftX, tolerance) == -1) {
                degrees = LEFT;
            } else if (comparePositions(gatePointX, aircraftX, tolerance) == 1) {
                degrees = RIGHT;
            } else {
                // Start moving vertically
                if (comparePositions(gatePointY, aircraftY, tolerance) == -1) {
                    degrees = UP;
                } else if (comparePositions(gatePointY, aircraftY, tolerance) == 1) {
                    degrees = DOWN;
                } else if (degrees == arrivalAirport.getDegrees())  {
                    enteredArrivalAirport = true;
                    myUniverse.addMessage("[Flight ID: " + id + "] ENTERED Destination Airport Area");
                }
            }
        }

    }

    // Return > 1 if a "greater than b", 0 if "equal", else -1
    private int comparePositions(double a, double b, int tolerance) {
        double diff = a - b;
        if (diff > tolerance)
            return 1;
        else if (diff < -tolerance)
            return -1;
        else return 0;
    }

    // String format for active flights
    public String stringifyActiveFlight() {
        String s = "TakeOff Airport: " + departureAirport.getName() + " | Destination Airport: " + arrivalAirport.getName() +
            " | Speed: " + getCurrentSpeed() * 3600 + " knots | Altitude: " + altitude + " feets | Remaining Fuel: " + fuel + " kg\n";
        return s;
    }

    // String format for all flights
    public String stringify() {
        String status;
        if (crashed) {
            status = "crashed";
        } else if (landed) {
            status = "landed";
        } else if (active) {
            status = "active";
        } else {
            status = "inactive";
        }
        String s = "TakeOff Airport: " + departureAirport.getName() + " | Destination Airport: " + arrivalAirport.getName() +
            " | Status: " + status + " | Aircraft Type : " + aircraft.getName() + "\n";
        return s;
    }
}
