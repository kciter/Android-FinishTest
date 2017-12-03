package kciter.famuosfood.network.request;

import java.util.HashMap;

/**
 * Created by kciter on 2017. 12. 1..
 */

public class AuthLoginRequest {
    private String username;
    private String password;
    private HashMap<String, String> params;

    public AuthLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public HashMap<String, String> getParams() {
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        return params;
    }
}