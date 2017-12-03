package kciter.famuosfood.ui.user;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import kciter.famuosfood.R;
import kciter.famuosfood.databinding.ActivityUserEditBinding;
import kciter.famuosfood.model.User;
import kciter.famuosfood.network.api.AuthServiceApi;
import kciter.famuosfood.network.api.UserServiceApi;
import kciter.famuosfood.network.event.ApiErrorEvent;
import kciter.famuosfood.network.event.AuthCheckEvent;
import kciter.famuosfood.network.event.MessageEvent;
import kciter.famuosfood.network.event.UserGetEvent;
import kciter.famuosfood.network.request.UserUpdateRequest;
import kciter.famuosfood.network.response.UserUpdateEvent;
import kciter.famuosfood.ui.auth.LoginActivity;
import kciter.famuosfood.ui.restuarent.RestaurantListActivity;
import kciter.famuosfood.util.BusProvider;
import kciter.famuosfood.util.ImageUtil;

public class UserEditActivity extends AppCompatActivity {
    public static final String TAG = UserEditActivity.class.getSimpleName();

    private ActivityUserEditBinding binding;
    private UserServiceApi userServiceApi;

    private String filePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userServiceApi = new UserServiceApi();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_edit);

        binding.imageView.setClickable(true);
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 0);
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Preference", Context.MODE_PRIVATE);
        final String token = preferences.getString("token", "");
        if (token != null) {
            userServiceApi.onRequestGet(token);

            binding.updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userServiceApi.onRequestUpdate(token, filePath, new UserUpdateRequest(
                            binding.name.getText().toString(),
                            binding.phone.getText().toString(),
                            binding.genderMale.isChecked() ? "male" : "female",
                            Integer.parseInt(binding.age.getText().toString())
                    ));
                }
            });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                binding.imageView.setImageBitmap(imageBitmap);

                Uri selectedImageURI = data.getData();
                filePath = getRealPathFromURI(selectedImageURI);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe
    public void onUserGet(UserGetEvent event) {
        User user = event.getUser();
        binding.setUser(user);
        binding.executePendingBindings();
    }

    @Subscribe
    public void onUpdate(UserUpdateEvent event) {
        Toast.makeText(this, "수정 되었습니다", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Subscribe
    public void onApiError(ApiErrorEvent event){
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @BindingAdapter({ "bind:imageUrl" })
    public static void loadImage(ImageView imageView, String url) {
        ImageUtil.loadImage(imageView, "http://192.168.0.46:8080/" + url);
    }
}
