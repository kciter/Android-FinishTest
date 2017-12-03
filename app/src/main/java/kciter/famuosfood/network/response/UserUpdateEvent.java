package kciter.famuosfood.network.response;

import kciter.famuosfood.model.User;

/**
 * Created by kciter on 2017. 12. 3..
 */

public class UserUpdateEvent {
    private User user;

    public UserUpdateEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
