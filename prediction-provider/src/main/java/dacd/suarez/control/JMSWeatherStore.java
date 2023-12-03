package dacd.suarez.control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import dacd.suarez.model.Weather;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.time.Instant;

public class JMSWeatherStore implements WeatherStore {
    private static String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static String TOPIC_NAME = " prediction.Weather";


    public void save(Weather weather) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
           Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(TOPIC_NAME);
            MessageProducer producer = session.createProducer(topic);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Instant.class, (JsonSerializer<Instant>) (src, typeOfSrc, context) ->
                            context.serialize(src.getEpochSecond()))
                    .create();

            String weatherJson = gson.toJson(weather);
            TextMessage weatherMessage = session.createTextMessage(weatherJson);

            producer.send(weatherMessage);

            System.out.println("Weather sent to JMS broker:" + weatherJson);

            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
