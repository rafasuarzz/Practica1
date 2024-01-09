package dacd.suarez.model;


import java.time.Instant;
import java.util.List;

public class Booking {
    private Hotel hotel;
    private List<Rate> rates;
    private String check_in;
    private String check_out;
    private final String ss;
    private final Instant ts;
    public Booking(String check_in, String check_out, List<Rate> rates, Hotel hotel) {
        this.check_in = check_in;
        this.check_out = check_out;
        this.rates = rates;
        this.ss = "hotel-price-provider";
        this.ts = Instant.now();
        this.hotel = hotel;
    }


    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }



    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }


    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }
}
