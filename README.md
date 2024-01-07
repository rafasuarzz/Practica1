# Practice 2
- **Name:** Rafael Suárez Saavedra
- **Subject:** Desarrollo de Aplicaciones para la Ciencia de Datos
- **Year:** Second
- **Degree:** Degree in data science and engineering
- **School:** School of Computer Engineering
- **University:** University of Las Palmas de Gran Canaria


**Functionality:** The provided project is a meteorological data management system designed to interact with a broker for event storage. The system includes the following components:

1. **Main_event_provider:**
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
   - Interface defining method save from meteorological data.
  
7. **OpenWeatherMapProvider::**
   - Implementation of WeatherProvider that uses the OpenWeatherMap API to obtain meteorological data.

8. **JMSWeatherStore:**
   - Previously responsible for saving weather data to an SQLite database.
   - Modified to interact with the ActiveMQ broker instead of persisting data in SQLite.
  
9. **AMQTopicSubscriber:**
   - Represents a subscriber to a topic in ActiveMQ, allowing the system to receive weather-related messages.
   - Utilizes the Java Message Service (JMS) API for communication.
   - Creates a durable subscriber to a specified topic and listens for incoming messages.
   - Passes received messages to a listener for further processing.
  
10. **FileEventStoreBuilder:**
   - Implements the Listener interface to handle incoming weather-related messages.
   - Parses JSON messages and extracts relevant data such as location, timestamp, etc.
   - Utilizes the Gson library for JSON parsing.
   - Processes the data and saves it as an event in a directory structure based on location and date.

11. **Listener Interface:**
   - Defines a common interface for classes that handle incoming messages.
  
12. **Subscriber Interface:**
   - Defines a common interface for classes that handle received messages.

13. **Main_event_store_builder:**
   - Main class responsible for initializing the system with an AMQTopicSubscriber and FileEventStoreBuilder.
   - Configures the ActiveMQ broker connection and specifies the directory for event storage.

**Design Principles:**
Adheres to the Single Responsibility Principle (SRP) by assigning specific responsibilities to each class. Implements dependency injection in WeatherController to enable testing and enhance flexibility.

**Design Principles and Patterns:**
Aligns with the Single Responsibility Principle (SRP) and Dependency Inversion Principle (DIP). Incorporates the Observer Pattern in AMQTopicSubscriber for asynchronous message processing.


**Resources Used:**
To develop the project, I used only the IntelliJ development environment. Additionally, I used git and GitHub as version control tools for my code. Finally, I used two OpenAI models, ChatGPT and AskCodi, to help me with issues that arose in the code. I tried not to rely solely on one to compare and verify the information they provided, aiming to achieve more reliable and effective code.


**Design:**

 prediction-provider:
 
 <img width="850" alt="Captura de pantalla 2023-12-09 a las 17 47 22" src="https://github.com/rafasuarzz/Practica1/assets/145263164/598e40fa-5c9f-41ab-af4e-0db3af541d60">

event-store-builder:

<img width="592" alt="Captura de pantalla 2023-12-09 a las 18 00 42" src="https://github.com/rafasuarzz/Practica1/assets/145263164/e8f6c884-6549-4a30-8ef2-089e2403ee37">






