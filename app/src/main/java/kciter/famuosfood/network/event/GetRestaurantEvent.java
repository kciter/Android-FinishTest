package kciter.famuosfood.network.event;

import kciter.famuosfood.model.Restaurant;

/**
 * Created by kciter on 2017. 12. 1..
 */

public class GetRestaurantEvent {
    private Restaurant restaurant;

    public GetRestaurantEvent(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurent() {
        return restaurant;
    }

    public void setRestaurent(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
