package dacd.suarez;

import javax.jms.JMSException;

public class Main {
    public static void main(String[] args) throws JMSException {
        Subscriber subscriber = new AMQDataLakeSubscriber(args[0]);
        Listener listener = new FileDataLakeBuilder(args[1]);
        subscriber.start(listener);
    }

}
