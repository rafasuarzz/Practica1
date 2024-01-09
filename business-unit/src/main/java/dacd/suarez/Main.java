package dacd.suarez;

import dacd.suarez.controller.*;

import javax.jms.JMSException;
import java.io.File;

public class Main {
    public static void main(String[] args) throws JMSException {
        Subscriber subscriber = new AMQDataMartSubscriber(args[0]);
        Listener listener = new FileDataMartBuilder();
        subscriber.start(listener);

    }
}
