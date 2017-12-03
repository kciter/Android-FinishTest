package kciter.famuosfood.network.event;

/**
 * Created by kciter on 2017. 12. 1..
 */

public class MessageEvent {
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
