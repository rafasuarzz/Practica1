package dacd.suarez;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


public class AMQTopicSubscriber implements Subscriber {

    private static final String clientID = "datalake-builder";
    private Connection connection;
    private Session session;

    public AMQTopicSubscriber(String BROKER_URL) throws JMSException{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        connection = connectionFactory.createConnection();
        connection.setClientID(clientID);
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    @Override
    public void start(String topicName, Listener listener) {
        try {
            Topic destination = session.createTopic(topicName);
            MessageConsumer subscriber = session.createDurableSubscriber(destination, clientID+ topicName);
            subscriber.setMessageListener(message -> {
                try {
                    listener.consume(((TextMessage) message).getText(), topicName);
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

