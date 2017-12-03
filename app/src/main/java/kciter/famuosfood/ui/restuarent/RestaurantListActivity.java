package kciter.famuosfood.ui.restuarent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.List;

import kciter.famuosfood.R;
import kciter.famuosfood.databinding.ActivityRestaurantListBinding;
import kciter.famuosfood.model.Restaurant;
import kciter.famuosfood.network.api.RestaurantServiceApi;
import kciter.famuosfood.network.event.ApiErrorEvent;
import kciter.famuosfood.network.event.FetchRestaurantListEvent;
import kciter.famuosfood.ui.auth.LoginActivity;
import kciter.famuosfood.ui.decoration.DividerItemDecoration;
import kciter.famuosfood.ui.user.UserEditActivity;
import kciter.famuosfood.ui.user.UserInfoActivity;
import kciter.famuosfood.util.BusProvider;

public class RestaurantListActivity extends AppCompatActivity {
    public static final String TAG = RestaurantListActivity.class.getSimpleName();

    private ActivityRestaurantListBinding binding;
    private RestaurantServiceApi restaurentServiceApi;
    private RestaurantListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant_list);
        restaurentServiceApi = new RestaurantServiceApi();

        adapter = new RestaurantListAdapter();
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClick(new RestaurantListAdapter.OnItemClick() {
            @Override
            public void onClick(View view, int position) {
                String id = adapter.getItem(position).getId();
                Intent intent = new Intent(RestaurantListActivity.this, RestaurantDetailActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getGlobal().register(this);
        restaurentServiceApi.onReqeustFetchList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getGlobal().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.restaurant_list_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_restaurant_register:
                startActivity(new Intent(RestaurantListActivity.this, RestaurantRegisterActivity.class));
                return true;

            case R.id.action_user_info:
                startActivity(new Intent(RestaurantListActivity.this, UserInfoActivity.class));
                return true;

            case R.id.action_user_edit:
                startActivity(new Intent(RestaurantListActivity.this, UserEditActivity.class));
                return true;

            case R.id.action_logout:
                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("Preference", Context.MODE_PRIVATE).edit();
                editor.putString("token", null);
                editor.commit();

                Intent intent = new Intent(RestaurantListActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onFetchRestaurantList(FetchRestaurantListEvent event) {
        List<Restaurant> restaurantList = event.getRestaurentList();
        adapter.setRestaurantList(restaurantList);
    }

    @Subscribe
    public void onApiError(ApiErrorEvent event){
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
