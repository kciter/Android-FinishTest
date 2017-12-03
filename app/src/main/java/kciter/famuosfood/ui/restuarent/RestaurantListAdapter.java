package kciter.famuosfood.ui.restuarent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kciter.famuosfood.databinding.RestaurantItemBinding;
import kciter.famuosfood.model.Restaurant;

/**
 * Created by kciter on 2017. 12. 1..
 */

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListViewHolder> {
    private List<Restaurant> restaurantList = new ArrayList<>();

    private OnItemClick itemClick;
    public interface OnItemClick {
        void onClick(View view, int position);
    }


    @Override
    public RestaurantListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RestaurantItemBinding itemBinding = RestaurantItemBinding.inflate(layoutInflater, parent, false);
        return new RestaurantListViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(RestaurantListViewHolder holder, final int position) {
        Restaurant restaurant = getItem(position);
        holder.binding.setRestaurant(restaurant);
        holder.binding.executePendingBindings();
        holder.binding.wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onClick(view, position);
            }
        });
    }

    public Restaurant getItem(int position) {
        return restaurantList.get(position);
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
        notifyDataSetChanged();
    }

    public void setOnItemClick(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }
}
