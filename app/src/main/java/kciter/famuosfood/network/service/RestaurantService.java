package kciter.famuosfood.network.service;

import java.util.HashMap;
import java.util.List;

import kciter.famuosfood.model.Restaurant;
import kciter.famuosfood.network.response.MessageResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * Created by kciter on 2017. 12. 1..
 */

public interface RestaurantService {
    @Multipart
    @Headers("Accept: application/json")
    @POST("/api/restaurant")
    Call<MessageResponse> register(@Part MultipartBody.Part image, @PartMap HashMap<String, RequestBody> fields);

    @Headers("Accept: application/json")
    @GET("/api/restaurant")
    Call<List<Restaurant>> list();

    @Headers("Accept: application/json")
    @GET("/api/restaurant/{id}")
    Call<Restaurant> get(@Path("id") String id);
}
