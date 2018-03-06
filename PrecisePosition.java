public class PrecisePosition {
    private double x;
    private double y;

    public PrecisePosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void print() {
        System.out.println("x: " + x + " y: " + y);
    }

    public String stringify() {
        return this.x + " " + this.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void update(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
