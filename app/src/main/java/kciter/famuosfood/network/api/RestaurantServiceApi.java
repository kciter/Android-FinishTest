package kciter.famuosfood.network.api;

import android.util.Log;

import java.io.File;
import java.util.List;

import kciter.famuosfood.model.Restaurant;
import kciter.famuosfood.network.event.ApiErrorEvent;
import kciter.famuosfood.network.event.GetRestaurantEvent;
import kciter.famuosfood.network.event.MessageEvent;
import kciter.famuosfood.network.request.RestaurantRegisterRequest;
import kciter.famuosfood.network.event.FetchRestaurantListEvent;
import kciter.famuosfood.network.response.MessageResponse;
import kciter.famuosfood.network.service.RestaurantService;
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

public class RestaurantServiceApi {
    private RestaurantService service;

    public RestaurantServiceApi() {
        service = NetworkProvider.getGlobal().create(RestaurantService.class);
    }

    public void onRequestRegister(String filePath, RestaurantRegisterRequest request) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

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

    public void onReqeustFetchList() {
        service.list()
                .enqueue(new Callback<List<Restaurant>>() {
                    @Override
                    public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                        if (!response.isSuccessful()) {
                            BusProvider.getGlobal().post(new ApiErrorEvent());
                            return;
                        }

                        List<Restaurant> restaurantList = response.body();
                        BusProvider.getGlobal().post(new FetchRestaurantListEvent(restaurantList));
                    }

                    @Override
                    public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                        BusProvider.getGlobal().post(new ApiErrorEvent());
                    }
                });
    }

    public void onRequestGet(String id) {
        service.get(id)
                .enqueue(new Callback<Restaurant>() {
                    @Override
                    public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                        if (!response.isSuccessful()) {
                            BusProvider.getGlobal().post(new ApiErrorEvent());
                            return;
                        }

                        Restaurant restaurant = response.body();
                        BusProvider.getGlobal().post(new GetRestaurantEvent(restaurant));
                    }

                    @Override
                    public void onFailure(Call<Restaurant> call, Throwable t) {
                        BusProvider.getGlobal().post(new ApiErrorEvent());
                    }
                });
    }
}
