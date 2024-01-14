# Final Project
- **Name:** Rafael Suárez Saavedra
- **Subject:** Desarrollo de Aplicaciones para la Ciencia de Datos
- **Year:** Second
- **Degree:** Degree in data science and engineering
- **School:** School of Computer Engineering
- **University:** University of Las Palmas de Gran Canaria


**Functionality:** 
- This project involves creating a smart travel planning application for the Canary Islands. It integrates weather data from previous projects with new event data to showcase the best hotels with the most competitive rates for a 5-night stay in the selected location. The project comprises four main modules. The first module (prediction-provider) fetches weather data from the OpenWeatherMap API and sends it to a message broker using Java Message Service (JMS). The hotel-price-provider retrieves hotel data from the Xotelo API and sends it to a message broker via JMS. Subsequently, the datalake-builder module subscribes to the durable broker, processes incoming messages, and stores them in a datalake. Finally, the canary-insights-business-unit module subscribes to the durable broker, processes messages, and stores them in a datamart. Additionally, this module facilitates client interaction through a Command Line Interface (CLI).

**How to use it:**
   - In the businessUnit and the datatalake-builder you will have to pass your broker url and the root directory to store your datalake as an argument and in the prediction-provider your apikey.
   - After running both providers you can run the hotel-recommendation-business-unit and you will have to follow the steps shown in your console to reach to a list of the hotels shown in ascendant price.


**Design Principles:**
- Single Responsibility Principle (SRP): Each class is designed with a specific role, adhering to SRP. For example, OpenWeatherMapProvider manages weather data retrieval, WeatherController orchestrates application flow, JmsWeatherStore oversees broker storage, AMQDataMartSubscriber subscribes to message topics, and BusinessUnit handles user interactions and data processing...

- Dependency Inversion Principle (DIP): The code follows DIP as WeatherController relies on WeatherProvider and WeatherStore interfaces (similarly with other modules using interfaces), enabling dependency injection for enhanced testability and extensibility.

- Interface Usage: Interfaces introduce abstraction, facilitating the incorporation of different provider, store, and event listener implementations without necessitating modifications to client code.

**Design Patterns:**
- Observer Pattern (AMQDataLakeSubscriber/AMQDataMartSubscribe): Leveraged the Observer pattern by implementing MessageListener to observe messages on a specific topic. When a new message is received, observers (in this case, the Listener) get notified.

- Composition and Aggregation (Controllers): The controllers are composed of instances of Providers and Stores, offering flexibility in altering implementations without altering the controllers.

- MVC: The MVC pattern segregates presentation, business logic, and event control. The model manages business logic and data manipulation, the view handles user presentation, and the controller coordinates interactions between the view and the model.

**Resources Used:**
- To develop the project, I used only the IntelliJ development environment. Additionally, I used git and GitHub as version control tools for my code. Finally, I used two OpenAI models, ChatGPT and AskCodi, to help me with issues that arose in the code. I tried not to rely solely on one to compare and verify the information they provided, aiming to achieve more reliable and effective code.


**Design:**

 prediction-provider:
 
 <img width="850" alt="Captura de pantalla 2023-12-09 a las 17 47 22" src="https://github.com/rafasuarzz/Practica1/assets/145263164/598e40fa-5c9f-41ab-af4e-0db3af541d60">

datalake-builder:

<img width="877" alt="Captura de pantalla 2024-01-09 a las 23 03 58" src="https://github.com/rafasuarzz/Practica1/assets/145263164/0d0dc63f-4599-4c46-93c0-370e86184af7">

hotel-price-provider:
<img width="875" alt="Captura de pantalla 2024-01-09 a las 23 26 30" src="https://github.com/rafasuarzz/Practica1/assets/145263164/a16b56d6-397f-42ef-b2fe-15402e7c2426">

hotel-recommendation-business-unit:

<img width="918" alt="Captura de pantalla 2024-01-09 a las 23 38 53" src="https://github.com/rafasuarzz/Practica1/assets/145263164/8b3d8f9b-7fb8-484a-ab61-46cba54e928d">








