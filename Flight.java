import java.io.*;
import java.util.*;

public class Flight {
    private int id, startTime;
    private Airport departureAirport, arrivalAirport;
    private String name;
    private AircraftSpecs aircraft;
    private int flightSpeed, altitude, fuel;

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
    }

	public int getStartTime() {
		return startTime;
	}

	public int getID() {
		return id;
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
}