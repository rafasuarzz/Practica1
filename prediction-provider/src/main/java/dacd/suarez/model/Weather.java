package dacd.suarez.model;

import java.time.Instant;

public class Weather {
    private final double temperature;
    private final int humidity;
    private final int clouds;
    private final double windSpeed;
    private final double rainProbability;
    private final Instant instant;
    private static Instant predictionTime = Instant.now() ;
    private static String ss = "prediction-provider";
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

    public Instant getInstant() {
        return instant;
    }

    public static Instant getPredictiontime() {
        return predictionTime;
    }

    public static String getSs() {
        return ss;
    }
    public String toString() {
        return "City=" + location.getName() +
                ", Lat=" + location.getLat() +
                ", Lon=" + location.getLon() +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", clouds=" + clouds +
                ", windSpeed=" + windSpeed +
                ", rainProbability=" + rainProbability +
                ", instant=" + instant ;
    }
}
