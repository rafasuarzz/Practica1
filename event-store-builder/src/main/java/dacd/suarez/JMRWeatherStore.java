package dacd.suarez;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import dacd.suarez.model.Weather;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.time.Instant;

public class JMRWeatherStore {

    private static final String BROKER_URL = "tcp://localhost:61616";
    public static final String TOPIC_NAME = "prediction.Weather";
    private static final String CLIENT_ID = "Rafa";

    public void receiveMessage(MessageSaver messageSaver) {
        Connection connection = null;

        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.setClientID(CLIENT_ID);
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic destination = session.createTopic(TOPIC_NAME);
            TopicSubscriber durableSubscriber = session.createDurableSubscriber(destination, CLIENT_ID);

            durableSubscriber.setMessageListener(message -> {
                if (message instanceof TextMessage) {
                    processTextMessage((TextMessage) message, messageSaver);
                }
            });

            // Keep the program running to receive messages
            Thread.sleep(Long.MAX_VALUE);

        } catch (JMSException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private void processTextMessage(TextMessage textMessage, MessageSaver messageSaver) {
        try {
            String json = textMessage.getText();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Instant.class, (JsonDeserializer<Instant>) (jsonElement, type, jsonDeserializationContext) ->
                            Instant.ofEpochSecond(jsonElement.getAsLong()))
                    .create();

            Weather weather = gson.fromJson(json, Weather.class);

            System.out.println("Received Weather: " + json);

            // Save the message to MessageSaver
            messageSaver.saveToEventStore(weather);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MessageSaver messageSaver = new MessageSaver();
        JMRWeatherStore weatherStore = new JMRWeatherStore();
        weatherStore.receiveMessage(messageSaver);
    }
}
