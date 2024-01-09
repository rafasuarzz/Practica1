package dacd.suarez.view;

import dacd.suarez.model.Booking;
import dacd.suarez.model.BusinessUnit;
import dacd.suarez.model.WeatherData;


import java.util.*;

import static dacd.suarez.model.BusinessUnit.convertEpochToDateTime;


public class UserInterface {


    private final Scanner scanner;
    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }



    public void chooseConditions() {
        List<String> weatherConditions = getAvailableWeatherConditions();



        System.out.println("Condiciones Climáticas Disponibles:");
        for (int i = 0; i < weatherConditions.size(); i++) {
            System.out.println((i + 1) + ". " + weatherConditions.get(i));
        }


        System.out.print("\nSeleccione las tres condiciones climáticas más relevantes (separadas por comas): ");
        String userInput = scanner.nextLine();


        List<String> selectedConditions = Arrays.asList(userInput.split(","));
        System.out.println(selectedConditions);


        System.out.println("\nCondiciones Seleccionadas:");
        for (String condition : selectedConditions) {
            System.out.println("- " + condition.trim());
        }

        String fileName = BusinessUnit.generateDataMartFileName();
        List<WeatherData> weatherDataList = BusinessUnit.processWeatherDatamart(fileName);
        Map<String, List<WeatherData>> islandWeatherData = BusinessUnit.getWeatherData(selectedConditions, weatherDataList);

        displayWeatherData(islandWeatherData, selectedConditions);

        List<String> allIslands = getAllIslands();


        System.out.println("\nIslas Disponibles:");
        for (int i = 0; i < allIslands.size(); i++) {
            System.out.println((i + 1) + ". " + allIslands.get(i));
        }


        System.out.print("\nSeleccione la isla que desea visitar(ingrese el número): ");
        int selectedIslandIndex = scanner.nextInt();


        if (selectedIslandIndex >= 1 && selectedIslandIndex <= allIslands.size()) {
            String selectedIsland = allIslands.get(selectedIslandIndex - 1);


            List<Booking> bookingDataList = BusinessUnit.processHotelDatamart(fileName);

            if (bookingDataList.isEmpty()) {
                System.out.println("No hay hoteles disponibles para la isla seleccionada.");
            } else {

                List<Booking> hotelesOrdenadosPorTarifa = BusinessUnit.sortHotelsByRate(bookingDataList, selectedIsland);


                System.out.println("\nHoteles en " + selectedIsland + " Ordenados por Tarifa:");
                for (Booking hotel : hotelesOrdenadosPorTarifa) {
                    System.out.println("Hotel: " + hotel.getHotel().getHotel_name() + " - Tarifa: " + BusinessUnit.getMinRateFromBooking(hotel) + "€");
                }
            }
        }
    }



    private static List<String> getAvailableWeatherConditions() {
        return Arrays.asList("Temperatura", "Lluvia", "Nubes", "Viento", "Humedad");
    }
    private static List<String> getAllIslands() {

        return Arrays.asList("Gran Canaria", "Tenerife", "La Palma", "La Gomera", "El Hierro", "La Graciosa", "Fuerteventura", "Lanzarote");
    }
    private static void displayWeatherData(Map<String, List<WeatherData>> islandWeatherData, List<String> selectedConditions) {

        for (Map.Entry<String, List<WeatherData>> entry : islandWeatherData.entrySet()) {
            String island = entry.getKey();
            List<WeatherData> islandData = entry.getValue();

            System.out.println("\nDatos Climáticos para la isla de " + island + ":");

            for (WeatherData data : islandData) {
                System.out.println("Fecha: " + convertEpochToDateTime(data.getPredictionTime()));

                for (String condition : selectedConditions) {
                    switch (condition.trim().toLowerCase()) {
                        case "temperatura":
                            System.out.println("   Temperatura: " + data.getTemperature() + "º");
                            break;
                        case "lluvia":
                            System.out.println("   Lluvia: " + data.getRainProbability()+ " %");
                            break;
                        case "nubes":
                            System.out.println("   Nubes: " + data.getClouds());
                            break;
                        case "viento":
                            System.out.println("   Viento: " + data.getWindSpeed() + " km/h");
                            break;
                        case "humedad":
                            System.out.println("   Humedad: " + data.getHumidity());
                            break;
                        default:
                            System.out.println("   Variable desconocida: " + condition);
                            break;
                    }
                }
            }
        }
    }




}


