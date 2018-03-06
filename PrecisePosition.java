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

    public double euclideanDistance(PrecisePosition A) {
       return Math.sqrt(Math.pow(A.getX() - x, 2) + Math.pow(A.getY() - y, 2));
    }
}
