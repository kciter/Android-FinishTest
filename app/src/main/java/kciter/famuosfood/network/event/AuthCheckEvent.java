package kciter.famuosfood.network.event;

import kciter.famuosfood.network.response.AuthCheckResponse;

/**
 * Created by kciter on 2017. 12. 3..
 */

public class AuthCheckEvent {
    private AuthCheckResponse authCheckResponse;

    public AuthCheckEvent(AuthCheckResponse authCheckResponse) {
        this.authCheckResponse = authCheckResponse;
    }

    public AuthCheckResponse getAuthCheckResponse() {
        return authCheckResponse;
    }

    public void setAuthCheckResponse(AuthCheckResponse authCheckResponse) {
        this.authCheckResponse = authCheckResponse;
    }
}
