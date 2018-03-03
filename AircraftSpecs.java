public class AircraftSpecs {
    private int type;
    private String name;
    private int landingSpeed, takeOffSpeed; // Landing and Takeoff speed in knots
    private int maxFlightSpeed; // Normal flight speed in knots
    private int maxAlt; // Maximum Altitude in feets
    private int ascDescRate; // Ascent and Descent Rate in ft/min
    private int fuelConsumptionRate; //
    private int maxFuelWeight;

    public AircraftSpecs(int type, String name, int landingSpeed, int maxFlightSpeed,
        int maxFuelWeight, int maxAlt, int ascDescRate, int fuelConsumptionRate) {

        this.type = type;
        this.name = name;
        this.landingSpeed = landingSpeed;
        this.takeOffSpeed = landingSpeed;
        this.maxFlightSpeed = maxFlightSpeed;
        this.maxAlt = maxAlt;
        this.ascDescRate = ascDescRate ;
        this.fuelConsumptionRate = fuelConsumptionRate;
        this.maxFuelWeight = maxFuelWeight;
    }

    public int getType() {
        return type;
    }

    public int getLandingSpeed() {
        return landingSpeed;
    }

    public int getTakeOffSpeed() {
        return takeOffSpeed;
    }

    public int getMaxAlt() {
        return maxAlt;
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
}
