import java.io.*;
import java.util.*;
import java.awt.*;

public class Map {
    private int[][] map;

    private static final Map instance = new Map();

    private Map() {
        map = new int[CONSTANTS.ROWS][CONSTANTS.COLS];
    };

    public static Map getInstance() {
        return instance;
    }

    public void parseFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));
            String line;

            int i = 0, j = 0;
            while ((line = reader.readLine()) != null) {
                j = 0;
			    String[] parts = line.split(",");
                for (String part : parts) {
                    if (j >= CONSTANTS.COLS) {
                        throw new WrongInputFormatException("Wrong input file format: " + filePath + " LINE:" + i);
                    }
                    map[i][j] = Integer.parseInt(part);
                    j++;
                }
                i++;
                // Check if out of bound
                if (i >= CONSTANTS.ROWS) {
                    break;
                }
            }

            if (i != CONSTANTS.ROWS || j != CONSTANTS.COLS) {
                throw new WrongInputFormatException("Wrong input file format: " + filePath + " LESS DATA THAN EXPECTED");
            }

        } catch (IOException e) {
        	e.printStackTrace();
        } catch (WrongInputFormatException e) {
            System.out.println(e);
        }
    }

    public void print() {
        for (int i = 0; i < CONSTANTS.ROWS; i++) {
            for (int j = 0; j < CONSTANTS.COLS; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public Color getColor(int row, int column) {
        int altitude = map[row][column];
        int red, green, blue;
        if (altitude <= 0) {
            red = 0;
            green = 0;
            blue = 255;
        }
        else if (altitude <= 200) {
            red = 60;
            green = 179;
            blue = 113;
        }
        else if (altitude <=400) {
            red = 46;
            green = 139;
            blue = 87;
        }
        else if (altitude <= 700) {
            red = 34;
            green = 139;
            blue = 34;
        }
        else if (altitude <= 1500) {
            red = 222;
            green = 184;
            blue = 135;
        }
        else if (altitude <= 3500) {
            red = 205;
            green = 133;
            blue = 63;
        }
        else {
            red = 145;
            green = 80;
            blue = 20;
        }
        return new Color(red, green, blue);
    }
}
