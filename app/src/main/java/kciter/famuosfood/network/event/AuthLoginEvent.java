package kciter.famuosfood.network.event;

import kciter.famuosfood.network.response.LoginResponse;

/**
 * Created by kciter on 2017. 12. 1..
 */

public class AuthLoginEvent {
    private LoginResponse response;

    public AuthLoginEvent(LoginResponse response) {
        this.response = response;
    }

    public LoginResponse getResponse() {
        return response;
    }

    public void setResponse(LoginResponse response) {
        this.response = response;
    }
}
