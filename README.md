# Practice 1
- **Name:** Rafael Suárez Saavedra
- **Subject:** Desarrollo de Aplicaciones para la Ciencia de Datos
- **Year:** Second
- **Degree:** Degree in data science and engineering
- **School:** School of Computer Engineering
- **University:** University of Las Palmas de Gran Canaria


**Functionality:** The provided project is a meteorological data management system that uses an SQLite database to store and update information about the weather for the next 5 days in different locations in the Canary Islands. Here is a summary of the main functionalities and components of the system:

1. **Main:**
   - Initializes the system with a timer that periodically updates meteorological data using a controller.
   - To run the code correctly, an API key and the desired database path must be passed as arguments, in that order.

2. **Weather:**
   - Class representing meteorological data, including temperature, humidity, wind speed, etc.

3. **Location:**
   - Class representing a location with a name, latitude, and longitude.

4. **WeatherController:**
   - Coordinates the retrieval, printing, and loading of meteorological data for various locations and times.
   - Uses a weather data provider and a store to manage data persistence.

5. **WeatherProvider:**
    - Interface defining a method to obtain meteorological data for a specific location and time.

6. **WeatherStore:**
   - Interface defining methods to save and load meteorological data.
  
7. **OpenWeatherMapProvider::**
   - Implementation of WeatherProvider that uses the OpenWeatherMap API to obtain meteorological data.

8. **SqliteWeatherStore:**
   - Implementation of `WeatherStore` that uses an SQLite database to persist meteorological data.
   - Creates tables for each location in the database.
   - Implements methods to save, update, check existence, and load meteorological data.

In summary, the system loads meteorological data from OpenWeatherMap and stores it in an SQLite database. It also manages the periodic update of data using a timer.


**Resources Used:**
To develop the project, I used only the IntelliJ development environment. Additionally, I used git and GitHub as version control tools for my code. Finally, I used two OpenAI models, ChatGPT and AskCodi, to help me with issues that arose in the code. I tried not to rely solely on one to compare and verify the information they provided, aiming to achieve more reliable and effective code.


**Design:**
 
 
 <img width="903" alt="Captura de pantalla 2023-11-18 a las 19 49 22" src="https://github.com/rafasuarzz/Practica1/assets/145263164/2b9d8e09-932b-4d9c-ba5e-274184f59bd6">






