import java.io.*;
import java.util.*;

public class Airport {
    public static final int NORTH = 1, EAST = 2, SOUTH = 3, WEST = 4;
	private int id, direction, category;
    private Position position; // In miles
    private Position gridPosition;
	private boolean open;
	private String name;
    private Position gatePoint;

	public Airport(int id, int positionRow, int positionColumn, String name, int direction, int category, boolean open) {
        this.id = id;
        this.gridPosition = new Position(positionRow, positionColumn);
        this.name = name;
		this.direction = direction;
		this.category = category;
        this.open = open;

        int x = CONSTANTS.TILE_SIZE_IN_MILES * positionColumn + CONSTANTS.TILE_SIZE_IN_MILES / 2;
        int y = CONSTANTS.TILE_SIZE_IN_MILES * positionRow + CONSTANTS.TILE_SIZE_IN_MILES / 2;
        this.position = new Position(x, y);
        this.gatePoint = findGatePoint();
        System.out.println("AIRPOR LOCATION with direction " + direction);
        position.print();
        System.out.println("GATE POINT");
        gatePoint.print();
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

	public Position getGatePoint() {
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

    private Position findGatePoint() {
        int x = position.getX();
        int y = position.getY();

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
        return new Position(x, y);

    }
}
