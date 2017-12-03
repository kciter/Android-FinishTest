package kciter.famuosfood.ui.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import kciter.famuosfood.R;
import kciter.famuosfood.databinding.ActivityUserInfoBinding;
import kciter.famuosfood.model.User;
import kciter.famuosfood.network.api.AuthServiceApi;
import kciter.famuosfood.network.api.UserServiceApi;
import kciter.famuosfood.network.event.ApiErrorEvent;
import kciter.famuosfood.network.event.AuthCheckEvent;
import kciter.famuosfood.network.event.UserGetEvent;
import kciter.famuosfood.util.BusProvider;
import kciter.famuosfood.util.ImageUtil;

public class UserInfoActivity extends AppCompatActivity {
    public static final String TAG = UserInfoActivity.class.getSimpleName();

    private ActivityUserInfoBinding binding;
    private UserServiceApi userServiceApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info);
        userServiceApi = new UserServiceApi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getGlobal().register(this);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Preference", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "");
        if (token != null) {
            userServiceApi.onRequestGet(token);
        }
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
    public void onUserGet(UserGetEvent event) {
        User user = event.getUser();
        binding.setUser(user);
        binding.executePendingBindings();
    }

    @Subscribe
    public void onApiError(ApiErrorEvent event){
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @BindingAdapter({ "bind:imageUrl" })
    public static void loadImage(ImageView imageView, String url) {
        ImageUtil.loadImage(imageView, "http://192.168.0.46:8080/" + url);
    }
}
