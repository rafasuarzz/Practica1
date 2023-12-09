package dacd.suarez.model;

import java.time.Instant;

public class Weather {
    private final double temperature;
    private final int humidity;
    private final int clouds;
    private final double windSpeed;
    private final double rainProbability;
    private final Instant instant;
    private final Instant predictionTime;
    private final String ss;
    private final Location location;



    public Weather(double temp, int humidity, int clouds, double windSpeed, double rainProbability, Instant instant, Location location) {
        this.temperature = temp;
        this.humidity = humidity;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.rainProbability = rainProbability;
        this.instant = instant;
        this.location = location;
        this.predictionTime = Instant.now();
        this.ss =  "prediction-provider";
    }



}
