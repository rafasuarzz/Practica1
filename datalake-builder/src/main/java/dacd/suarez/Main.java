package dacd.suarez;

import javax.jms.JMSException;

public class Main {
    public static void main(String[] args) throws JMSException {
        String topicName = "prediction.Weather";
        Subscriber subscriber = new AMQTopicSubscriber(args[0]);
        Listener listener = new FileEventStoreBuilder();
        subscriber.start(topicName, listener);
    }

}
