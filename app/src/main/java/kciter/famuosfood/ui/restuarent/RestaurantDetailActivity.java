package kciter.famuosfood.ui.restuarent;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.List;

import kciter.famuosfood.R;
import kciter.famuosfood.databinding.ActivityRestaurantDetailBinding;
import kciter.famuosfood.model.Restaurant;
import kciter.famuosfood.network.api.RestaurantServiceApi;
import kciter.famuosfood.network.event.ApiErrorEvent;
import kciter.famuosfood.network.event.GetRestaurantEvent;
import kciter.famuosfood.util.BusProvider;
import kciter.famuosfood.util.ImageUtil;

public class RestaurantDetailActivity extends AppCompatActivity {
    public static final String TAG = RestaurantDetailActivity.class.getSimpleName();

    private ActivityRestaurantDetailBinding binding;
    private RestaurantServiceApi restaurentServiceApi;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant_detail);
        restaurentServiceApi = new RestaurantServiceApi();

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getGlobal().register(this);
        restaurentServiceApi.onRequestGet(id);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getGlobal().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onFetchRestaurantList(GetRestaurantEvent event) {
        Restaurant restaurant = event.getRestaurent();
        binding.setRestaurant(restaurant);
        binding.executePendingBindings();

        getSupportActionBar().setTitle(restaurant.getTitle());
    }

    @Subscribe
    public void onApiError(ApiErrorEvent event){
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @BindingAdapter({ "bind:imageUrl" })
    public static void loadImage(ImageView imageView, String url) {
        Log.d("asdf", url);
        ImageUtil.loadImage(imageView, "http://192.168.0.46:8080/" + url);
    }
}
