package kciter.famuosfood.network.response;

import com.google.gson.annotations.SerializedName;

import kciter.famuosfood.model.User;

/**
 * Created by kciter on 2017. 12. 3..
 */

public class AuthCheckResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private User user;

    public AuthCheckResponse(boolean success, User user) {
        this.success = success;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
