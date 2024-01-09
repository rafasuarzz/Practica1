package dacd.suarez.control;

import dacd.suarez.model.Booking;
import dacd.suarez.model.Hotel;

import java.util.ArrayList;
import java.util.List;

public class HotelController {
    private final JMSHotelSender jmsHotelSender;

    public HotelController(JMSHotelSender jMSHotelSender) {
        this.jmsHotelSender = jMSHotelSender;
    }

    public void execute(){

        List<Booking> bookingList = new ArrayList<>();

        for (Hotel hotel : createHotelList()) {
            Booking booking = new OpenHotelProvider().getHotelDetails(hotel);
            if (booking != null) {
                bookingList.add(booking);
            }
        }

        for (Booking booking : bookingList) {
            jmsHotelSender.send(booking);
        }
    }

    private List<Hotel> createHotelList(){

        return List.of(
                new Hotel("Gran Canaria", "BlueBay Beach-Club", "g562818-d677815"),
                new Hotel("Gran Canaria", "Lopesan Baobab Resort", "g2089121-d1488268"),
                new Hotel("Gran Canaria", "Abora Buenaventura", "g562819-d289606"),
                new Hotel("Gran Canaria", "Hotel Santa Catalina", "g187472-d228489"),
                new Hotel("Gran Canaria", "Paradisus by Meliá Gran Canaria", "g562818-d238899"),
                new Hotel("Tenerife", "Iberostar Bouganville Playa", "g662606-d296925"),
                new Hotel("Tenerife", "Hard Rock Hotel Tenerife", "g315919-d4341700"),
                new Hotel("Tenerife", "Gran Melia Palacio De Isora", "g1773834-d945835"),
                new Hotel("Tenerife", "Iberostar Selection Sabila", "g662606-d291408"),
                new Hotel("Tenerife", "Dreams Jardin Tropical", "g662606-d248458"),
                new Hotel("Lanzarote", "Secrets Lanzarote Resort & Spa", "g580321-d282759"),
                new Hotel("Lanzarote", "Dreams Lanzarote Playa Dorada Resort & Spa", "g652121-d262432"),
                new Hotel("Lanzarote", "Hotel Lancelot", "g187478-d273097"),
                new Hotel("Lanzarote", "Relaxia Lanzasur", "g652121-d292136"),
                new Hotel("Lanzarote", "Hotel Beatriz Costa Spa", "g659633-d291300"),
                new Hotel("Fuerteventure", "Barceló Fuerteventura Mar", "g658907-d255145"),
                new Hotel("Fuerteventura", "Secrets Bahía Real Resort & Spa", "g580322-d287995"),
                new Hotel("Fuerteventura", "Barcelo Corralejo Bay", "g580322-d678447"),
                new Hotel("Fuerteventura", "Occidental Jandia Mar", "g13513937-d557197"),
                new Hotel("Fuerteventura", "Iberostar Selection Fuerteventura Palace", "g659632-d288584"),
                new Hotel("El Hierro", "Parador de El Hierro", "g187474-d277394"),
                new Hotel("El Hierro", "Balneario Pozo de la Salud", "g1189149-d1193818"),
                new Hotel("El Hierro", "Hotel Ida Ines", "g2139290-d603283"),
                new Hotel("El Hierro", "Puntagrande Hotel", "g2139290-d627753"),
                new Hotel("El Hierro", "Hotel Villa El Mocanal", "g1190055-d630598"),
                new Hotel("La Gomera", "Hotel Torre del Conde", "g187470-d566709"),
                new Hotel("La Gomera", "Hotel Villa Gomera", "g187470-d614341"),
                new Hotel("La Gomera", "Hotel Playa Calera", "g21309597-d1379967"),
                new Hotel("La Gomera", "Ibo Alfaro Hotel Rural", "g674060-d670383"),
                new Hotel("La Gomera", "Apartments Los Telares", "g674060-d729666"),
                new Hotel("La Palma", "La Palma Princess", "g1175543-d638034"),
                new Hotel("La Palma", "Hotel Hacienda De Abajo", "g1177806-d3577949"),
                new Hotel("La Palma",  "H10 Taburiente Playa", "g659966-d289252"),
                new Hotel("La Palma", "Parador de LaPalma", "g642213-d482745"),
                new Hotel("La Palma", "Hotel Las Olas", "g642213-d488944"),
                new Hotel("La Graciosa", "La Pardela", "g3360203-d25244761"),
                new Hotel("La Graciosa", "Graciosa Mar", "g1190272-d945848"),
                new Hotel("La Graciosa", "Evita Beach Aptos y Suites Exclusivas", "g1190272-d2645782"),
                new Hotel("La Graciosa", "Apartamentos El Marinero", "g3360203-d17628872"),
                new Hotel("La Graciosa", "Apartments La Graciosa", "g3360203-d3411835")
        );
    }
}
