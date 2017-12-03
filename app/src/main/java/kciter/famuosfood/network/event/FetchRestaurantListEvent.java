package kciter.famuosfood.network.event;

import java.util.List;

import kciter.famuosfood.model.Restaurant;

/**
 * Created by kciter on 2017. 12. 1..
 */

public class FetchRestaurantListEvent {
    List<Restaurant> restaurantList;

    public FetchRestaurantListEvent(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    public List<Restaurant> getRestaurentList() {
        return restaurantList;
    }

    public void setRestaurentList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }
}
