package dacd.suarez.control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import dacd.suarez.model.Booking;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.time.Instant;

public class JMSHotelSender implements HotelSender{
    private static String brokerUrl = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static String topicName = "hotel.booking";


    public void send(Booking booking) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic(topicName);
            MessageProducer producer = session.createProducer(destination);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Instant.class, (JsonSerializer<Instant>) (src, typeOfSrc, context) ->
                            context.serialize(src.getEpochSecond()))
                    .create();

            String bookingJson = gson.toJson(booking);
            TextMessage bookingMessage = session.createTextMessage(bookingJson);

            producer.send(bookingMessage);

            System.out.println("Booking information sent to JMS broker:" + bookingJson);

            connection.close();
        } catch (JMSException e) {
            System.err.println("Error establishing connection to JMS broker: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
