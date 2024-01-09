package dacd.suarez;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


public class AMQDataLakeSubscriber implements Subscriber {

    private final static String clientID = "datalake-builder-";
    private final Connection connection;
    private final Session session;
    private final String topicWeather = "prediction.Weather";
    private final String topicHotel = "hotel.booking";


    public AMQDataLakeSubscriber(String BROKER_URL) throws JMSException{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        connection = connectionFactory.createConnection();
        connection.setClientID(clientID);
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    @Override
    public void start(Listener listener) {
        try {
            Topic weatherDestination = session.createTopic(topicWeather);
            Topic hotelDestination = session.createTopic(topicHotel);

            MessageConsumer weatherConsumer = session.createDurableSubscriber(weatherDestination, clientID + topicWeather);
            MessageConsumer hotelConsumer = session.createDurableSubscriber(hotelDestination, clientID + topicHotel);

            weatherConsumer.setMessageListener(message -> handleMessage(message, listener, topicWeather));
            hotelConsumer.setMessageListener(message -> handleMessage(message, listener, topicHotel));

        } catch (JMSException e) {
            throw new RuntimeException("Error receiving JMS message", e);
        }
    }
    private void handleMessage(Message message, Listener listener, String topicName){
        try {
            System.out.println("Handling message from " + topicName);
            listener.consume(((TextMessage) message).getText(), topicName);
            System.out.println(message);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

}

