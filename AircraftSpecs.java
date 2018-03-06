public class AircraftSpecs {
    private int type;
    private String name;
    private double landingSpeed, takeOffSpeed; // Landing and Takeoff speed in knots
    private double maxFlightSpeed; // Normal flight speed in knots
    private int maxAlt; // Maximum Altitude in feets
    private int ascDescRate; // Ascent and Descent Rate in ft/min
    private int fuelConsumptionRate; //
    private int maxFuelWeight;

    public AircraftSpecs(int type, String name, int landingSpeed, int maxFlightSpeed,
        int maxFuelWeight, int maxAlt, int ascDescRate, int fuelConsumptionRate) {

        this.type = type;
        this.name = name;
        this.landingSpeed = ((double) landingSpeed) / 3600;
        this.takeOffSpeed = ((double) landingSpeed) / 3600;
        this.maxFlightSpeed = ((double) maxFlightSpeed) / 3600;
        this.maxAlt = maxAlt;
        this.ascDescRate = ascDescRate ;
        this.fuelConsumptionRate = fuelConsumptionRate;
        this.maxFuelWeight = maxFuelWeight;
    }

    public int getType() {
        return type;
    }

    public double  getLandingSpeed() {
        return landingSpeed;
    }

    public double  getTakeOffSpeed() {
        return takeOffSpeed;
    }

    public int getMaxAlt() {
        return maxAlt;
    }

    public double getMaxFlightSpeed() {
        return maxFlightSpeed;
    }

    public int getAscDescRate() {
        return ascDescRate;
    }

    public int getFuelConsumptionRate() {
        return fuelConsumptionRate;
    }

    public int getMaxFuelWeight() {
        return maxFuelWeight;
    }

    public void print() {
        System.out.println("Type:" + type + " landingSpeed: " + landingSpeed
            + " maxFlightSpeed:" + maxFlightSpeed + " maxFuel:" + maxFuelWeight + " maxAlt:" + maxAlt);
    }
}
