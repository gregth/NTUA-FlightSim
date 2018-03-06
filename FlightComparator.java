import java.util.Comparator;

public class FlightComparator implements Comparator<Flight> {

    @Override
    public int compare(Flight x, Flight y) {
        if (x.getStartTime() < y.getStartTime()) {
            return -1;
        }
        if (x.getStartTime() > y.getStartTime()) {
            return 1;
        }
        return 0;
    }
}
