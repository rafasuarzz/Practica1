package dacd.suarez;

public interface Subscriber {
    void start(String topic, Listener listener);
}
