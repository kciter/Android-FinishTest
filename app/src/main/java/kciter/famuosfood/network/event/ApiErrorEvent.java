package kciter.famuosfood.network.event;

/**
 * Created by kciter on 2017. 12. 1..
 */

public class ApiErrorEvent {
    private String message;

    public ApiErrorEvent() {
        this.message = "네트워크 요청에 실패했습니다.";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}