import java.io.*;
import java.util.*;

public class Map {
    private int[][] map;

    public Map() {
        map = new int[CONSTANTS.ROWS][CONSTANTS.COLS];
    }

    // Parse map from file
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
}
