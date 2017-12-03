package kciter.famuosfood.ui.auth;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import kciter.famuosfood.R;
import kciter.famuosfood.databinding.ActivitySignupBinding;
import kciter.famuosfood.network.api.AuthServiceApi;
import kciter.famuosfood.network.event.ApiErrorEvent;
import kciter.famuosfood.network.event.MessageEvent;
import kciter.famuosfood.network.request.AuthRegisterRequest;
import kciter.famuosfood.util.BusProvider;

public class SignupActivity extends AppCompatActivity {
    public static final String TAG = SignupActivity.class.getSimpleName();

    private ActivitySignupBinding binding;
    private AuthServiceApi authServiceApi;

    private String filePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        authServiceApi = new AuthServiceApi();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);

        binding.imageView.setClickable(true);
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 0);
            }
        });

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authServiceApi.onRequestRegister(filePath, new AuthRegisterRequest(
                        binding.username.getText().toString(),
                        binding.password.getText().toString(),
                        binding.name.getText().toString(),
                        binding.phone.getText().toString(),
                        binding.genderMale.isChecked() ? "male" : "female",
                        Integer.parseInt(binding.age.getText().toString())
                ));
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
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
    public void onRegister(MessageEvent event) {
        Toast.makeText(this, "회원가입 되었습니다", Toast.LENGTH_SHORT).show();
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
}
