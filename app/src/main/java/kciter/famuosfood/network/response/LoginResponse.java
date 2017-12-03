package kciter.famuosfood.network.response;

/**
 * Created by kciter on 2017. 12. 1..
 */

public class LoginResponse {
    private String message;
    private String token;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
