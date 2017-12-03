package kciter.famuosfood.network.api;

import java.io.File;

import kciter.famuosfood.network.event.ApiErrorEvent;
import kciter.famuosfood.network.event.AuthCheckEvent;
import kciter.famuosfood.network.event.AuthLoginEvent;
import kciter.famuosfood.network.event.MessageEvent;
import kciter.famuosfood.network.request.AuthLoginRequest;
import kciter.famuosfood.network.request.AuthRegisterRequest;
import kciter.famuosfood.network.response.AuthCheckResponse;
import kciter.famuosfood.network.response.LoginResponse;
import kciter.famuosfood.network.response.MessageResponse;
import kciter.famuosfood.network.service.AuthService;
import kciter.famuosfood.util.BusProvider;
import kciter.famuosfood.util.NetworkProvider;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kciter on 2017. 12. 1..
 */

public class AuthServiceApi {
    private AuthService service;

    public AuthServiceApi() {
        service = NetworkProvider.getGlobal().create(AuthService.class);
    }

    public void onRequestRegister(String filePath, AuthRegisterRequest request) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("profile_image", file.getName(), requestFile);

        service.register(body, request.getParams())
                .enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if (!response.isSuccessful()) {
                            BusProvider.getGlobal().post(new ApiErrorEvent());
                            return;
                        }

                        MessageResponse messageResponse = response.body();
                        BusProvider.getGlobal().post(new MessageEvent(messageResponse.getMessage()));
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        BusProvider.getGlobal().post(new ApiErrorEvent());
                    }
                });
    }

    public void onRequestLogin(AuthLoginRequest request) {
        service.login(request.getParams())
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            BusProvider.getGlobal().post(new ApiErrorEvent());
                            return;
                        }

                        LoginResponse loginResponse = response.body();
                        BusProvider.getGlobal().post(new AuthLoginEvent(loginResponse));
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        BusProvider.getGlobal().post(new ApiErrorEvent());
                    }
                });
    }

    public void onRequestCheck(String token) {
        service.check(token)
                .enqueue(new Callback<AuthCheckResponse>() {
                    @Override
                    public void onResponse(Call<AuthCheckResponse> call, Response<AuthCheckResponse> response) {
                        if (!response.isSuccessful()) {
                            BusProvider.getGlobal().post(new ApiErrorEvent());
                            return;
                        }

                        AuthCheckResponse authCheckResponse = response.body();
                        BusProvider.getGlobal().post(new AuthCheckEvent(authCheckResponse));
                    }

                    @Override
                    public void onFailure(Call<AuthCheckResponse> call, Throwable t) {
                        BusProvider.getGlobal().post(new ApiErrorEvent());
                    }
                });
    }
}
