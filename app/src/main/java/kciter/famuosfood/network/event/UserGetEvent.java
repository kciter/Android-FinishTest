package kciter.famuosfood.network.event;

import kciter.famuosfood.model.User;

/**
 * Created by kciter on 2017. 12. 3..
 */

public class UserGetEvent {
    private User user;

    public UserGetEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
