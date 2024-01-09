package dacd.suarez.view;

import dacd.suarez.model.WeatherData;


import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static dacd.suarez.model.BusinessUnit.processWeatherDatamart;

public class UserInterface {


    private final Scanner scanner;
    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }



    public void chooseConditions() {
        // Obtén las condiciones climáticas disponibles
        List<String> weatherConditions = getAvailableWeatherConditions();

        // Muestra las condiciones climáticas al usuario
        System.out.println("Condiciones Climáticas Disponibles:");
        for (int i = 0; i < weatherConditions.size(); i++) {
            System.out.println((i + 1) + ". " + weatherConditions.get(i));
        }

        // Solicita al usuario que elija las condiciones más relevantes
        System.out.print("\nSeleccione las tres condiciones climáticas más relevantes (separadas por comas): ");
        String userInput = scanner.nextLine();

        // Procesa la entrada del usuario
        List<String> selectedConditions = Arrays.asList(userInput.split(","));
        System.out.println(selectedConditions);

        // Muestra la selección del usuario
        System.out.println("\nCondiciones Seleccionadas:");
        for (String condition : selectedConditions) {
            System.out.println("- " + condition.trim());
        }
        String filePath = "datamart" + File.separator + "eventstore" + File.separator + 20240109 + File.separator + "all_events.events";
        // Simula la obtención de datos climáticos
        List<WeatherData> weatherDataList = processWeatherDatamart(filePath);
        getWeatherData(selectedConditions, weatherDataList);

        List<String> allIslands = getAllIslands();

        // Muestra las islas disponibles al usuario
        System.out.println("\nIslas Disponibles:");
        for (int i = 0; i < allIslands.size(); i++) {
            System.out.println((i + 1) + ". " + allIslands.get(i));
        }

        // Solicita al usuario que elija la isla que desea visitar
        System.out.print("\nSeleccione la ciudad que desea visitar(ingrese el número): ");
        int selectedIslandIndex = scanner.nextInt();

        // Verifica si el índice seleccionado es válido
        if (selectedIslandIndex >= 1 && selectedIslandIndex <= allIslands.size()) {
            String selectedIsland = allIslands.get(selectedIslandIndex - 1);

        }
    }


    private static List<String> getAvailableWeatherConditions() {
        // Aquí puedes obtener las condiciones climáticas disponibles desde tus datos meteorológicos
        // Puedes cargarlas desde tu datamart o definirlas en el código según tus necesidades
        return Arrays.asList("Temperatura", "Lluvia", "Nubes", "Viento", "Humedad");
    }
    private static List<String> getAllIslands() {
        // Simula obtener la lista de todas las islas desde tus datos meteorológicos
        // Puedes cargarlas desde tu datamart o definirlas en el código según tus necesidades
        return Arrays.asList("Arrecife", "Santa Cruz de Tenerife", "Santa Cruz de la Palma", "San Sebastián de la Gomera", "Valverde", "Caleta de Sebo", "Puerto del Rosario", "Las Palmas de Gran Canaria");
    }
    public static Map<String, List<WeatherData>> getWeatherData(List<String> selectedConditions, List<WeatherData> weatherDataList) {
        // Simulación de datos climáticos para cada isla
        Map<String, Set<Long>> uniquePredictionTimes = new HashMap<>();
        Map<String, List<WeatherData>> islandWeatherData = new HashMap<>();

        // Agrupa los datos meteorológicos por isla y mantiene un conjunto de tiempos de predicción únicos
        for (WeatherData weatherData : weatherDataList) {
            String island = weatherData.getLocation().getName();
            Set<Long> predictionTimes = uniquePredictionTimes.computeIfAbsent(island, k -> new HashSet<>());

            if (predictionTimes.add(weatherData.getPredictionTime())) {
                // Si el tiempo de predicción no estaba en el conjunto (era único), se agrega a los datos climáticos
                islandWeatherData.computeIfAbsent(island, k -> new ArrayList<>()).add(weatherData);
            }
        }

        // Muestra la información para cada isla y las variables seleccionadas
        for (Map.Entry<String, List<WeatherData>> entry : islandWeatherData.entrySet()) {
            String island = entry.getKey();
            List<WeatherData> islandData = entry.getValue();

            System.out.println("\nDatos Climáticos para la Ciudad de " + island + ":");

            for (WeatherData data : islandData) {
                System.out.println("Fecha: " + convertEpochToDateTime(data.getPredictionTime()));

                for (String condition : selectedConditions) {
                    switch (condition.trim().toLowerCase()) {
                        case "temperatura":
                            System.out.println("   Temperatura: " + data.getTemperature());
                            break;
                        case "lluvia":
                            System.out.println("   Lluvia: " + data.getRainProbability());
                            break;
                        case "nubes":
                            System.out.println("   Nubes: " + data.getClouds());
                            break;
                        case "viento":
                            System.out.println("   Viento: " + data.getWindSpeed());
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
        return islandWeatherData;
    }

    private static LocalDateTime convertEpochToDateTime(long epochSeconds) {
        Instant instant = Instant.ofEpochSecond(epochSeconds);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}


