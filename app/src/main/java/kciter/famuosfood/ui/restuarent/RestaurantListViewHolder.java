package kciter.famuosfood.ui.restuarent;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import kciter.famuosfood.databinding.RestaurantItemBinding;
import kciter.famuosfood.util.ImageUtil;

/**
 * Created by kciter on 2017. 12. 1..
 */

public class RestaurantListViewHolder extends RecyclerView.ViewHolder {
    RestaurantItemBinding binding;

    public RestaurantListViewHolder(RestaurantItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @BindingAdapter({ "bind:imageUrl" })
    public static void loadImage(ImageView imageView, String url) {
        ImageUtil.loadImage(imageView, "http://192.168.0.46:8080/" + url);
    }
}
