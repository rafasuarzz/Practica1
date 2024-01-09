package dacd.suarez.model;

public class Hotel {
    private String location;
    private String hotel_name;
    private String hotel_key;

    public Hotel(String location, String hotel_name, String hotel_key) {
        this.location = location;
        this.hotel_name = hotel_name;
        this.hotel_key = hotel_key;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_key() {
        return hotel_key;
    }
}
