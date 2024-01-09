package dacd.suarez.controller;

public interface Listener {
    void consume(String message, String topicName);
}
