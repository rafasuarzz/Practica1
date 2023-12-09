package dacd.suarez;

import javax.jms.JMSException;

public class Main_event_store_builder {
    public static void main(String[] args) throws JMSException {
        String topicName = "prediction.Weather";
        Subscriber subscriber = new AMQTopicSubscriber(args[0]);
        Listener listener = new FileEventStoreBuilder(args[1]);
        subscriber.start(topicName, listener);
    }

}
