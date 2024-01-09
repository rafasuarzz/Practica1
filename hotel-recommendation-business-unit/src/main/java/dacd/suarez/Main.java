package dacd.suarez;

import dacd.suarez.controller.*;
import dacd.suarez.view.UserInterface;

import javax.jms.JMSException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws JMSException {
        try{
            AMQDataMartSubscriber subscriberAMQ = new AMQDataMartSubscriber(args[0]);
            FileDataMartBuilder fileDataMartBuilder = new FileDataMartBuilder();
            UserInterface userInterface = new UserInterface();

            CountDownLatch latch = new CountDownLatch(1);

            Listener listener = new Listener() {
                @Override
                public void consume(String message, String topicName) {
                    fileDataMartBuilder.consume(message, topicName);
                    latch.countDown();
                }
            };
            subscriberAMQ.start(listener);

            if (!latch.await(10, TimeUnit.SECONDS)) {
                System.out.println("\n");
                System.out.println("No messages were received within the specified time. Accessing the command line..");
                System.out.println("\n");
            }

            Thread.sleep(5000);

            subscriberAMQ.close();

            System.out.println("\n");

            userInterface.chooseConditions();

        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }



    }
}
