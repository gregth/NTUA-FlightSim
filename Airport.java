import java.io.*;
import java.util.*;

public class Airport {
	private int id, direction, category;
    private Position position;
	private boolean open;
	private String name;

	public Airport(int id, int x, int y, String name, int direction, int category, boolean open) {
        this.id = id;
        this.position = new Position(x, y);
        this.name = name;
		this.direction = direction;
		this.category = category;
        this.open = open;
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

	public int getDirection() {
		return direction;
	}

	public int getCategory(){
		return category;
	}

	public String getName() {
		return name;
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
}
