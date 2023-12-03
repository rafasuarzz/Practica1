package dacd.suarez;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import dacd.suarez.model.Weather;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.time.Instant;

public class EventStoreBuilder {
    private static String url = "tcp://localhost:61616";

    public void receiveMessage() {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.setClientID("Rafa");
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic destination = session.createTopic("prediction.Weather");
            TopicSubscriber durableSubscriber = session.createDurableSubscriber(destination, "DurableSubscriber");

            durableSubscriber.setMessageListener(message -> {
                if (message instanceof TextMessage) {
                    processTextMessage((TextMessage) message);
                }
            });

            Thread.sleep(Long.MAX_VALUE);

        } catch (JMSException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void processTextMessage(TextMessage textMessage) {
        try {
            String json = textMessage.getText();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Instant.class, (JsonDeserializer<Instant>) (jsonElement, type, jsonDeserializationContext) ->
                            Instant.ofEpochSecond(jsonElement.getAsLong()))
                    .create();

            Weather weather = gson.fromJson(json, Weather.class);

            System.out.println("Received Weather: " + json);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        EventStoreBuilder weatherStore = new EventStoreBuilder();
        weatherStore.receiveMessage();
    }
}