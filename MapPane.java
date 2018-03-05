import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class MapPane extends JPanel {
    private static final int MAP_WIDTH = CONSTANTS.MAP_WIDTH;
    private static final int MAP_HEIGHT = CONSTANTS.MAP_HEIGHT;
    private static final int ROWS = CONSTANTS.ROWS;
    private static final int COLS = CONSTANTS.COLS;
    private static final int TILE_SIZE = CONSTANTS.TILE_SIZE;

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillOval(0, 0, 30, 30);
		g2d.drawOval(0, 50, 30, 30);
		g2d.fillRect(50, 0, 30, 30);
		g2d.drawRect(50, 50, 30, 30);
        paintMapTiles(g2d);
	}

	public MapPane (){
        this.setLayout(new GridLayout());
        this.setPreferredSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));
        this.setMinimumSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));
        this.setMaximumSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));
        this.setBackground(Color.BLACK);
        this.repaint();
	}

    public void paintMapTiles(Graphics2D g2d) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                paintTile(g2d, i, j);
            }
        }
    }

    // First column is number 0, first row is number 0
    public void paintTile(Graphics2D g2d, int tileRow, int tileColumn) {
        Color color = Map.getInstance().getColor(tileRow, tileColumn);
		g2d.setColor(color);
        int startX = TILE_SIZE * tileColumn;
        int startY = TILE_SIZE * tileRow;
		g2d.fillRect(startX, startY, TILE_SIZE , TILE_SIZE);

        // Draw airport pin, if it exists
        if (AirportDatabase.getInstance().existsAirport(tileRow, tileColumn)) {
            Image image = new ImageIcon("icons/airport.png").getImage();
            int iconX = startX + TILE_SIZE/2 - 1;
            int iconY = startY + TILE_SIZE/2 - 1;
            System.out.println(iconX + " " + iconY);
            g2d.drawImage(image, iconX, iconY, this);
        }
    }
}
