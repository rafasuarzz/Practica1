package dacd.suarez.model;

import java.time.Instant;

public class Weather {
    private final double temperature;
    private final int humidity;
    private final int clouds;
    private final double windSpeed;
    private final double rainProbability;
    private final Instant instant;
    private static Instant predictiontime = Instant.now() ;
    private static String ss = "event-store-builder";
    private final Location location;



    public Weather(double temp, int humidity, int clouds, double windSpeed, double rainProbability, Instant instant, Location location) {
        this.temperature = temp;
        this.humidity = humidity;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.rainProbability = rainProbability;
        this.instant = instant;
        this.location = location;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getClouds() {
        return clouds;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getRainProbability() {
        return rainProbability;
    }

    public Instant getInstant() {
        return instant;
    }

    public Location getLocation() {
        return location;
    }

}
