package dacd.suarez;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import dacd.suarez.model.Weather;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.time.Instant;

public class JMRWeatherStore implements Subscriber {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String CLIENT_ID = "Rafa";
    private Connection connection;
    private Session session;
    private TopicSubscriber durableSubscriber;

    @Override
    public void start(String topicName, Listener listener) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.setClientID(CLIENT_ID);
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic destination = session.createTopic(topicName);
            durableSubscriber = session.createDurableSubscriber(destination, CLIENT_ID);

            durableSubscriber.setMessageListener(message -> {
                if (message instanceof TextMessage) {
                    processTextMessage((TextMessage) message, listener);
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
            throw new RuntimeException("Error starting subscriber", e);
        }
    }

    private void processTextMessage(TextMessage textMessage, Listener listener) {
        try {
            String json = textMessage.getText();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Instant.class, (JsonDeserializer<Instant>) (jsonElement, type, jsonDeserializationContext) ->
                            Instant.ofEpochSecond(jsonElement.getAsLong()))
                    .create();

            Weather weather = gson.fromJson(json, Weather.class);

            System.out.println("Received Weather: " + json);

            // Notify the listener
            listener.consume(json);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

