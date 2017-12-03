package kciter.famuosfood.network.api;

import java.io.File;

import kciter.famuosfood.model.User;
import kciter.famuosfood.network.event.ApiErrorEvent;
import kciter.famuosfood.network.event.UserGetEvent;
import kciter.famuosfood.network.request.UserUpdateRequest;
import kciter.famuosfood.network.response.UserUpdateEvent;
import kciter.famuosfood.network.service.UserService;
import kciter.famuosfood.util.BusProvider;
import kciter.famuosfood.util.NetworkProvider;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kciter on 2017. 12. 3..
 */

public class UserServiceApi {
    private UserService service;

    public UserServiceApi() {
        service = NetworkProvider.getGlobal().create(UserService.class);
    }

    public void onRequestUpdate(String token, String filePath, UserUpdateRequest request) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("profile_image", file.getName(), requestFile);

        service.update(token, body, request.getParams())
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (!response.isSuccessful()) {
                            BusProvider.getGlobal().post(new ApiErrorEvent());
                            return;
                        }

                        User user = response.body();
                        BusProvider.getGlobal().post(new UserUpdateEvent(user));
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        BusProvider.getGlobal().post(new ApiErrorEvent());
                    }
                });
    }

    public void onRequestGet(String token) {
        service.get(token)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (!response.isSuccessful()) {
                            BusProvider.getGlobal().post(new ApiErrorEvent());
                            return;
                        }

                        User user = response.body();
                        BusProvider.getGlobal().post(new UserGetEvent(user));
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        BusProvider.getGlobal().post(new ApiErrorEvent());
                    }
                });
    }
}
