package dacd.suarez;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


public class AMQDataLakeSubscriber implements Subscriber {

    private static final String clientID = "datalake-builder";
    private Connection connection;
    private Session session;
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
            Topic destination = session.createTopic(topicWeather);
            MessageConsumer subscriber = session.createDurableSubscriber(destination, clientID + topicWeather);
            subscriber.setMessageListener(message -> {
                try {
                    listener.consume(((TextMessage) message).getText(), topicWeather);
                    System.out.println(message);
                } catch (JMSException e) {
                    throw new RuntimeException("Error while processing JMS message", e);
                }

            });
        } catch (JMSException e) {
            throw new RuntimeException("Error receiving JMS message", e);
        }

        try {
            Topic destination = session.createTopic(topicHotel);
            MessageConsumer subscriber = session.createDurableSubscriber(destination, clientID + topicHotel);
            subscriber.setMessageListener(message -> {
                try {
                    listener.consume(((TextMessage) message).getText(), topicHotel);
                    System.out.println(message);
                } catch (JMSException e) {
                    throw new RuntimeException("Error while processing JMS message", e);
                }

            });
        } catch (JMSException e) {
            throw new RuntimeException("Error receiving JMS message", e);
        }
    }

}

