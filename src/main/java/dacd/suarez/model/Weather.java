package dacd.suarez.model;

import java.time.Instant;

public class Weather {
    private final double temp;
    private final int humidity;
    private final int all;
    private final double speed;
    private final double pop;
    private final Instant dt;
    private final Location location;


    public Weather(double temp, int humidity, int all, double speed, double pop, Instant dt, Location location) {
        this.temp = temp;
        this.humidity = humidity;
        this.all = all;
        this.speed = speed;
        this.pop = pop;
        this.dt = dt;
        this.location = location;
    }


    public double getTemp() {
        return temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getAll() {
        return all;
    }

    public double getSpeed() {
        return speed;
    }

    public double getPop() {
        return pop;
    }

    public Instant getDt() {
        return dt;
    }

    public Location getLocation() {
        return location;
    }
}
