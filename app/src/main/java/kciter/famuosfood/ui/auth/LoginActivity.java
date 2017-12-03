package kciter.famuosfood.ui.auth;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.List;

import kciter.famuosfood.R;
import kciter.famuosfood.databinding.ActivityLoginBinding;
import kciter.famuosfood.network.api.AuthServiceApi;
import kciter.famuosfood.network.event.ApiErrorEvent;
import kciter.famuosfood.network.event.AuthCheckEvent;
import kciter.famuosfood.network.event.AuthLoginEvent;
import kciter.famuosfood.network.request.AuthLoginRequest;
import kciter.famuosfood.network.response.LoginResponse;
import kciter.famuosfood.ui.restuarent.RestaurantListActivity;
import kciter.famuosfood.util.BusProvider;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = LoginActivity.class.getSimpleName();

    private ActivityLoginBinding binding;
    private AuthServiceApi authServiceApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authServiceApi = new AuthServiceApi();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                authServiceApi.onRequestLogin(new AuthLoginRequest(binding.username.getText().toString(), binding.password.getText().toString()));
            }
        });

        binding.signupButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Preference", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "");
        if (token != null) {
            authServiceApi.onRequestCheck(token);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getGlobal().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getGlobal().unregister(this);
    }

    @Subscribe
    public void onCheckSuccess(AuthCheckEvent event) {
        if (event.getAuthCheckResponse().isSuccess()) {
            Toast.makeText(this, "로그인 되었습니다", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, RestaurantListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Subscribe
    public void onLogin(AuthLoginEvent event) {
        Toast.makeText(this, "로그인 되었습니다", Toast.LENGTH_SHORT).show();

        LoginResponse response = event.getResponse();

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", response.getToken());
        editor.commit();

        Intent intent = new Intent(LoginActivity.this, RestaurantListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Subscribe
    public void onApiError(ApiErrorEvent event){
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
    }
}

