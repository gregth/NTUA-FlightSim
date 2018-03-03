import java.io.*;
import java.util.*;

public class Airport {
	private int id, direction, type;
    private Position position;
	private boolean open;
	private String name;

	public Airport(int id, int x, int y, String name, int direction, int type, boolean open) {
        this.id = id;
        this.position = new Position(x, y);
        this.name = name;
		this.direction = direction;
		this.type = type;
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

	public int getType(){
		return type;
	}

	public String getName() {
		return name;
	}
}
