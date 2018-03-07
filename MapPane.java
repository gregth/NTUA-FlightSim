import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.*;
import java.awt.geom.AffineTransform;

public class MapPane extends JPanel {
    private static final int MAP_WIDTH = CONSTANTS.MAP_WIDTH;
    private static final int MAP_HEIGHT = CONSTANTS.MAP_HEIGHT;
    private static final int ROWS = CONSTANTS.ROWS;
    private static final int COLS = CONSTANTS.COLS;
    private static final int TILE_SIZE = CONSTANTS.TILE_SIZE;

    private Universe myUniverse;

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
        paintMapTiles(g2d);
        paintAircrafts(g2d);
	}

	public MapPane (){
        super();
        this.setLayout(new GridLayout());
        this.setPreferredSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));
        this.setMinimumSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));
        this.setMaximumSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));
        this.setBackground(Color.BLACK);
        this.repaint();
        myUniverse = Universe.getInstance();
	}

    private void paintMapTiles(Graphics2D g2d) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                paintTile(g2d, i, j);
            }
        }
    }

    private void paintAircrafts(Graphics2D g2d) {
        ArrayList<Flight> flights = FlightsDatabase.getInstance().getActiveFlights();
        for (Flight flight : flights) {
            paintAircraft(g2d, flight);
        }
    }

    // First column is number 0, first row is number 0
    private void paintTile(Graphics2D g2d, int tileRow, int tileColumn) {
        Color color = myUniverse.myMap.getColor(tileRow, tileColumn);
		g2d.setColor(color);
        int startX = tileColumn * TILE_SIZE;
        int startY = tileRow * TILE_SIZE;
		g2d.fillRect(startX, startY, TILE_SIZE , TILE_SIZE);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(startX, startY, TILE_SIZE , TILE_SIZE);

        // Draw airport pin, if it exists
        Airport airport = myUniverse.airportsDB.findAirport(tileRow, tileColumn);
        if (airport != null) {
            Image image = new ImageIcon("icons/airport.png").getImage();
            int iconX = milesToPixels(airport.getPosition()).getX() - image.getWidth(null) / 2 ;
            int iconY = milesToPixels(airport.getPosition()).getY() - image.getHeight(null) / 2;
            g2d.drawImage(image, iconX, iconY, this);
        }
    }

    private void paintAircraft(Graphics2D g2d, Flight flight) {
        String name = "small_n.png";
        switch (flight.getDegrees()) {
            case Flight.UP:
                name = "small_n.png";
                break;
            case Flight.DOWN:
                name = "small_s.png";
                break;
            case Flight.RIGHT:
                name = "small_e.png";
                break;
            case Flight.LEFT:
                name = "small_w.png";
        }
        Image image = new ImageIcon("icons/" + name).getImage();
        PrecisePosition p = flight.getAircraftPosition();
        int iconX = milesToPixels(p).getX()- image.getWidth(null) / 2 ;
        int iconY = milesToPixels(p).getY()- image.getHeight(null) / 2 ;
        g2d.drawImage(image, iconX, iconY, this);
    }

    private Position milesToPixels(Position p) {
        int x = (int) (((double)p.getX()) * CONSTANTS.PIXEL_TO_MILES_RATIO);
        int y = (int) (((double)p.getY()) * CONSTANTS.PIXEL_TO_MILES_RATIO);
        return new Position(x, y);
    }

    private Position milesToPixels(PrecisePosition p) {
        int x = (int) (p.getX() * CONSTANTS.PIXEL_TO_MILES_RATIO);
        int y = (int) (p.getY() * CONSTANTS.PIXEL_TO_MILES_RATIO);
        return new Position(x, y);
    }
}
