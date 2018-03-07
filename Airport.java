import java.io.*;
import java.util.*;

public class Airport {
    public static final int NORTH = 1, EAST = 2, SOUTH = 3, WEST = 4;
	private int id, direction, category;
    private Position position; // In miles
    private PrecisePosition precisePosition;
    private Position gridPosition;
	private boolean open;
	private String name;
    private PrecisePosition gatePoint;

	public Airport(int id, int positionRow, int positionColumn, String name, int direction, int category, boolean open) {
        this.id = id;
        this.gridPosition = new Position(positionRow, positionColumn);
        this.name = name;
		this.direction = direction;
		this.category = category;
        this.open = open;

        double x = CONSTANTS.TILE_SIZE_IN_MILES * positionColumn + CONSTANTS.TILE_SIZE_IN_MILES / 2;
        double y = CONSTANTS.TILE_SIZE_IN_MILES * positionRow + CONSTANTS.TILE_SIZE_IN_MILES / 2;
        this.precisePosition = new PrecisePosition(x, y);
        this.position = new Position((int) x, (int) y);
        this.gatePoint = findGatePoint();
	}

    public PrecisePosition getPrecisePosition() {
        return precisePosition;
    }

    public int getDegrees() {
        return (direction - 1) * 90;
    }
	public boolean isOpen() {
		return open;
	}

	public int getID() {
		return id;
	}

	public Position getPosition() {
		return position;
	}

	public Position getGridPosition() {
		return gridPosition;
	}

	public int getDirection() {
		return direction;
	}

	public int getCategory(){
		return category;
	}

	public String getName() {
		return name;
	}

	public PrecisePosition getGatePoint() {
		return gatePoint;
	}

    public void print() {
        System.out.println("Airport ID:" + id + " name:" + name + " direction:" + direction + " category:" + category + " open:" + open);
        position.print();
    }

    public boolean acceptsAircraft(AircraftSpecs aircraft) {
        int aircraftType = aircraft.getType();
        if (this.category == 3) {
            return true;
        }
        if (this.category == 2) {
            if (aircraftType == CONSTANTS.JET ||
                aircraftType == CONSTANTS.TURBO) {
                return true;
            }
            return false;
        }
        if (this.category == 1) {
            if (aircraftType == CONSTANTS.SINGLE_ENGINE) {
                return true;
            }
            return false;
        }
        return false;
    }

    // Findts the gate point (place for entrance) for the airport area
    private PrecisePosition findGatePoint() {
        double x = (double) position.getX();
        double y = (double) position.getY();

        switch (this.getDirection()) {
            case Airport.NORTH :
                y -= CONSTANTS.TILE_SIZE_IN_MILES / 2;
                break;
            case Airport.EAST :
                x += CONSTANTS.TILE_SIZE_IN_MILES / 2;
                break;
            case Airport.SOUTH :
                y += CONSTANTS.TILE_SIZE_IN_MILES / 2;
                break;
            case Airport.WEST :
                x -= CONSTANTS.TILE_SIZE_IN_MILES / 2;
                break;
        }
        return new PrecisePosition(x, y);
    }

    public String stringify() {
        String s = "ID:" + id + " | Name:" + name + " | Direction: " + direction + " | category: " + category + " | open: " + open + "\n";
        return s;
    }
}
