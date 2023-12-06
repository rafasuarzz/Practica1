package dacd.suarez;

public class Main {
    public static void main(String[] args) {
        MessageSaver messageSaver = new MessageSaver();

        Subscriber weatherStore = new JMRWeatherStore();
        Listener messageListener = messageSaver::consume;

        weatherStore.start("prediction.Weather", messageListener);
    }

}
