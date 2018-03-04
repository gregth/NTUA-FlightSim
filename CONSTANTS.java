public class CONSTANTS {
    private CONSTANTS() {
    }

    public final static int SINGLE_ENGINE = 1;
    public final static int TURBO = 2;
    public final static int JET = 3;

    public static final int ROWS = 30;
    public static final int COLS = 60;

    public static final int DELAY = 1000;

    public static final int TILE_SIZE = 16;

    public static final int MAP_HEIGHT = CONSTANTS.ROWS * CONSTANTS.TILE_SIZE;
    public static final int MAP_WIDTH = CONSTANTS.COLS * CONSTANTS.TILE_SIZE;
    public static final int STATUS_HEIGHT = 4 * CONSTANTS.TILE_SIZE;
    public static final int STATUS_WIDTH = MAP_WIDTH;
    public static final int MESSAGES_WIDTH = 20 * CONSTANTS.TILE_SIZE;
    public static final int MESSAGES_HEIGHT  = MAP_HEIGHT;

    public static final int FRAME_WIDTH = MAP_WIDTH + MESSAGES_WIDTH;
    public static final int FRAME_HEIGHT = STATUS_HEIGHT + MAP_HEIGHT;

    public static final int SIMULATION_PANE_WIDTH = MAP_WIDTH + MESSAGES_WIDTH;
    public static final int SIMULATION_PANE_HEIGHT = MAP_HEIGHT;
}
