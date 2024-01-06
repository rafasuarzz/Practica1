package dacd.suarez.control;

import dacd.suarez.model.Hotel;

import java.util.List;

public class HotelController {

    private List<Hotel> createHotelList(){
        return List.of(
                new Hotel("Gran Canaria", "Gloria Palace Amadores Thalasso & Hotel", "g635887-d530796"),
                new Hotel("Gran Canaria", "Axel Beach Maspalomas", "g562819-d4107099"),
                new Hotel("Gran Canaria", "Hotel Santa Catalina", "g187472-d228489"),
                new Hotel("Gran Canaria", "Paradisus by Meliá Gran Canaria", "g562818-d238899"),
                new Hotel("Gran Canaria", "Club Maspalomas Suites & Spa", "g230095-d645272"),
                new Hotel("Tenerife", "Iberostar Bouganville Playa", "g662606-d296925"),
                new Hotel("Tenerife", "Hard Rock Hotel Tenerife", "g315919-d4341700"),
                new Hotel("Tenerife", "Gran Melia Palacio De Isora", "g1773834-d945835"),
                new Hotel("Tenerife", "Iberostar Selection Sabila", "g662606-d291408"),
                new Hotel("Tenerife", "Dreams Jardin Tropical", "g662606-d248458"),
                new Hotel("Lanzarote", "Secrets Lanzarote Resort & Spa", "g580321-d282759"),
                new Hotel("Lanzarote", "Dreams Lanzarote Playa Dorada Resort & Spa", "g652121-d262432"),
                new Hotel("Lanzarote", "Hotel Fariones", "g662290-d282771"),
                new Hotel("Lanzarote", "Barceló Teguise Beach - Adults Only", "g659633-d287948"),
                new Hotel("Lanzarote", "ÆQUORA Lanzarote Suites", "g662290-d285669"),
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
                new Hotel("La Gomera", "Parador de La Gomera", "g187470-d190895"),
                new Hotel("La Gomera", "Hotel Jardin Tecina", "g1187912-d324473"),
                new Hotel("La Gomera", "Hotel Gran Rey", "g674782-d616495"),
                new Hotel("La Gomera", "Ibo Alfaro Hotel Rural", "g674060-d670383"),
                new Hotel("La Gomera", "Apartments Los Telares", "g674060-d729666"),
                new Hotel("La Palma", "La Palma Princess", "g1175543-d638034"),
                new Hotel("La Palma", "Hotel Hacienda De Abajo", "g1177806-d3577949"),
                new Hotel("La Palma", "Esencia de La Palma by Princess", "g1175543-d21175914"),
                new Hotel("La Palma", "H10 Taburiente Playa", "g659966-d289252"),
                new Hotel("La Palma", "Hacienda San Jorge", "g642213-d535420"),
                new Hotel("La Graciosa", "La Pardela", "g3360203-d25244761"),
                new Hotel("La Graciosa", "Graciosa Mar", "g1190272-d945848"),
                new Hotel("La Graciosa", "Evita Beach Aptos y Suites Exclusivas", "g1190272-d2645782"),
                new Hotel("La Graciosa", "Apartamentos El Marinero", "g3360203-d17628872"),
                new Hotel("La Graciosa", "Apartments La Graciosa", "g3360203-d3411835")
                );
    }
}
