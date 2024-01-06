package dacd.suarez.control;

import dacd.suarez.model.Booking;
import dacd.suarez.model.Hotel;

public interface HotelProvider {
    Booking getHotel(Hotel hotel, Booking booking);
}
